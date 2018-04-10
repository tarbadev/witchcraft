package com.tarbadev.witchcraft.domain;

import lombok.*;

import javax.persistence.*;

@Value
@Builder
public class Step {
  private Integer id;
  private String name;
}
