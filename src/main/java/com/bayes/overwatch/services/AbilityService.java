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
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AbilityService {

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private HeroRepository heroRepository;

    public Ability create(Ability ability) {
        return abilityRepository.save(ability);
    }

    public List<Ability> findAll() throws IOException {
        getAbilities();
        return abilityRepository.findAll();
    }

    public Ability findOne(Long id) {
        Ability ability = abilityRepository.getOne(id);
        return ability;
    }

    public List<Ability> findByName(String name) {
        return abilityRepository.findByName(name);
    }

    public void delete(Long id) {
        abilityRepository.deleteById(id);
    }

    private void getAbilities() throws IOException {
        String uri = "https://overwatch-api.net/api/v1/ability/";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(result);
        String next = root.path("next").textValue();
        ArrayNode data = (ArrayNode) root.path("data");
        data.forEach(abilityNode -> {
            JsonNode heroNode = abilityNode.get("hero");
            Hero hero;
            try {
                hero = heroRepository.findByName(heroNode.get("name").textValue()).get(0);
            } catch (Exception e) {
                hero = new Hero();
                hero.setName(heroNode.get("name").textValue());
                hero.setReal_name(heroNode.get("real_name").textValue());
                hero.setArmour(heroNode.get("armour").intValue());
                hero.setHealth(heroNode.get("health").intValue());
                hero.setShield(heroNode.get("shield").intValue());
            }
            Ability ability = new Ability();
            ability.setHero(hero);
            ability.setDescription(abilityNode.get("description").textValue());
            ability.setName(abilityNode.get("name").textValue());
            ability.setUltimate(abilityNode.get("is_ultimate").booleanValue());
            this.create(ability);
        });

        while (next != null){
            String new_uri = next.replaceAll("http", "https");
            result = restTemplate.getForObject(new_uri, String.class);
            root = mapper.readTree(result);
            next = root.path("next").textValue();
            data = (ArrayNode) root.path("data");
            data.forEach(abilityNode -> {
                JsonNode heroNode = abilityNode.get("hero");
                Hero hero;
                try {
                    hero = heroRepository.findByName(heroNode.get("name").textValue()).get(0);
                } catch (Exception e) {
                    hero = new Hero();
                    hero.setName(heroNode.get("name").textValue());
                    hero.setReal_name(heroNode.get("real_name").textValue());
                    hero.setArmour(heroNode.get("armour").intValue());
                    hero.setHealth(heroNode.get("health").intValue());
                    hero.setShield(heroNode.get("shield").intValue());
                }
                Ability ability = new Ability();
                ability.setHero(hero);
                ability.setDescription(abilityNode.get("description").textValue());
                ability.setName(abilityNode.get("name").textValue());
                ability.setUltimate(abilityNode.get("is_ultimate").booleanValue());
                this.create(ability);
            });
        }


    }
}
