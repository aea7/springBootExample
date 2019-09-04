package com.bayes.overwatch.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = "ability")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(length = 3000)
    private String description;

    @Column(name = "is_ultimate")
    private boolean isUltimate;

    @ManyToOne (cascade = CascadeType.ALL)
    private Hero hero;

}
