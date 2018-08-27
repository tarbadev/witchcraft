package com.tarbadev.witchcraft.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "weeks")
public class WeekEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer id;
  private Integer year;
  private Integer weekNumber;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "week_id")
  private List<DayEntity> days;
}
