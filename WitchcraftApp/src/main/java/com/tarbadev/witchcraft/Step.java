package com.tarbadev.witchcraft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "steps")
public class Step {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer id;
  @Column(length = 1000)
  private String name;
}
