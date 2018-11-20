package com.tarbadev.witchcraft.weeks.persistence.entity;

import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity;
import com.tarbadev.witchcraft.weeks.domain.entity.Day;
import com.tarbadev.witchcraft.weeks.domain.entity.DayName;
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

  public DayEntity(Day day) {
    id = day.getId();
    name = day.getName().name();
    lunch = day.getLunch() != null ? new RecipeEntity(day.getLunch()) : null;
    diner = day.getDiner() != null ? new RecipeEntity(day.getDiner()) : null;
  }

  public Day day() {
    return Day.builder()
        .id(id)
        .name(DayName.valueOf(name))
        .lunch(lunch != null ? lunch.recipe() : null)
        .diner(diner != null ? diner.recipe() : null)
        .build();
  }
}
