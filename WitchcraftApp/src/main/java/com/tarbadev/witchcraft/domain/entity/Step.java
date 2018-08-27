package com.tarbadev.witchcraft.domain.entity;

import lombok.*;

import javax.persistence.*;

@Value
@Builder
public class Step {
  private Integer id;
  private String name;
}
