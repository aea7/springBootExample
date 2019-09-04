package com.bayes.overwatch.services;

import com.bayes.overwatch.models.Ability;
import com.bayes.overwatch.models.Hero;
import com.bayes.overwatch.repositories.AbilityRepository;
import com.bayes.overwatch.repositories.HeroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private AbilityRepository abilityRepository;

    public Hero create(Hero hero){
        return heroRepository.save(hero);
    }

    public List<Hero> findAll() throws IOException {
        getHeroes();
        return heroRepository.findAll();
    }

    public Hero findOne(Long id){
        Hero hero = heroRepository.getOne(id);
        return hero;
    }

    public List<Hero> findByName(String name){
        return heroRepository.findByName(name);
    }

    public void delete(Long id){
        heroRepository.deleteById(id);
    }

    private void getHeroes() throws IOException {
        final String uri = "https://overwatch-api.net/api/v1/hero/";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(result);
        ArrayNode data = (ArrayNode) root.path("data");
        data.forEach(heroNode -> {
            Hero hero = new Hero();
            hero.setName(heroNode.get("name").textValue());
            hero.setReal_name(heroNode.get("real_name").textValue());
            hero.setArmour(heroNode.get("armour").intValue());
            hero.setHealth(heroNode.get("health").intValue());
            hero.setShield(heroNode.get("shield").intValue());
            this.create(hero);
        });
    }

    public List<Ability> findAbilities(long hero_id) {
        List<Ability> heroAbilities = new ArrayList<Ability>();
        List<Ability> abilities = abilityRepository.findAll();
        abilities.forEach(ability -> {
            if (ability.getHero().getId() == hero_id)
                heroAbilities.add(ability);
        });
        return heroAbilities;
    }
}
