package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.persistence.RecipeEntity;
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
public class Day {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @Enumerated(EnumType.STRING)
  private DayName name;
  @ManyToOne
  @JoinColumn(name = "lunch_recipe_id")
  private RecipeEntity lunch;
  @ManyToOne
  @JoinColumn(name = "diner_recipe_id")
  private RecipeEntity diner;
}
