package com.tarbadev.witchcraft.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Item {
  private Integer id;
  private String name;
  private Double quantity;
  private String unit;

}
