package com.bayes.overwatch.controllers;

import com.bayes.overwatch.models.Ability;
import com.bayes.overwatch.services.AbilityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/abilities")
@Api("This is the ability controller of REST api")
public class AbilityController {

    @Autowired
    private AbilityService abilityService;

    @GetMapping("")
    @ApiOperation("Listing all abilities")
    public List<Ability> findAll() throws IOException {
        return abilityService.findAll();
    }

    @PostMapping("")
    @ApiOperation("create an ability")
    public Ability create(@RequestBody Ability ability){
        return abilityService.create(ability);
    }

    @GetMapping("/{ability_id}")
    @ApiOperation("find one ability by id")
    public Ability findOne(@PathVariable("ability_id") long ability_id){
        return abilityService.findOne(ability_id);
    }

    @GetMapping("/find/{name}")
    @ApiOperation("find one ability by name")
    public List<Ability> findOne(@PathVariable("name") String name){
        return abilityService.findByName(name);
    }

    @DeleteMapping("/delete/{ability_id}")
    @ApiOperation("delete one ability by id")
    public void delete(@PathVariable("ability_id") long abilityId){
        abilityService.delete(abilityId);
    }

}
