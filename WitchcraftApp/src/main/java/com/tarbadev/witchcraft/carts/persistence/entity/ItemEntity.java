package com.tarbadev.witchcraft.carts.persistence.entity;

import com.tarbadev.witchcraft.carts.domain.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "items")
public class ItemEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Integer id;
  private String name;
  private Double quantity;
  private String unit;

  public ItemEntity(Item item) {
    id = item.getId();
        name = item.getName();
    quantity = item.getQuantity();
        unit = item.getUnit();
  }

  public Item item() {
    return Item.builder()
        .id(id)
        .name(name)
        .quantity(quantity)
        .unit(unit)
        .build();
  }
}
