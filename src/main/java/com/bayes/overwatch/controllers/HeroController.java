package com.bayes.overwatch.controllers;

import com.bayes.overwatch.models.Ability;
import com.bayes.overwatch.models.Hero;
import com.bayes.overwatch.services.HeroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/heros")
@Api("This is the hero controller of REST api")
public class HeroController {

    @Autowired
    private HeroService heroService;

    @GetMapping("")
    @ApiOperation("Listing all heroes")
    public List<Hero> findAll() throws IOException {
        return heroService.findAll();
    }

    @PostMapping("")
    @ApiOperation("creating new hero")
    public Hero create(@RequestBody Hero hero){
        System.out.println(hero);
        return heroService.create(hero);
    }

    @GetMapping("/{hero_id}")
    @ApiOperation("Get one hero with id")
    public Hero findOne(@PathVariable("hero_id") long hero_id){
        return heroService.findOne(hero_id);
    }

    @GetMapping("/{hero_id}/abilities")
    @ApiOperation("Get abilities of one hero with id")
    public List<Ability> findAbilities(@PathVariable("hero_id") long hero_id){
        return heroService.findAbilities(hero_id);
    }

    @DeleteMapping("/delete/{hero_id}")
    @ApiOperation("Delete one hero with id")
    public void delete(@PathVariable("hero_id") long heroId){
        heroService.delete(heroId);
    }

}
