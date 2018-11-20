package com.tarbadev.witchcraft.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "days")
public class DayEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String name;
  @ManyToOne
  @JoinColumn(name = "lunch_recipe_id")
  private RecipeEntity lunch;
  @ManyToOne
  @JoinColumn(name = "diner_recipe_id")
  private RecipeEntity diner;
}
