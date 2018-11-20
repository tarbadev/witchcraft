package com.tarbadev.witchcraft.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "carts")
public class CartEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @CreationTimestamp
  protected LocalDateTime createdAt;
  @ManyToMany(cascade = CascadeType.MERGE)
  private List<RecipeEntity> recipes;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id")
  private List<ItemEntity> items;
}
