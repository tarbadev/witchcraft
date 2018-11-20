package com.tarbadev.witchcraft.recipes.persistence.entity;

import com.tarbadev.witchcraft.recipes.domain.entity.Step;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StepEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer id;
  @Column(length = 1000)
  private String name;

  public StepEntity(Step step) {
    id = step.getId();
    name = step.getName();
  }

  public Step step() {
    return Step.builder()
        .id(id)
        .name(name)
        .build();
  }
}
