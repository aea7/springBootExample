package com.bayes.overwatch.repositories;

import com.bayes.overwatch.models.Ability;
import com.bayes.overwatch.models.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbilityRepository extends JpaRepository<Ability, Long> {

    List<Ability> findByName(String name);

}
