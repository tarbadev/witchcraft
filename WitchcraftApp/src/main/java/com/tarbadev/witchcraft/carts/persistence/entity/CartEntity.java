package com.tarbadev.witchcraft.carts.persistence.entity;

import com.tarbadev.witchcraft.carts.domain.entity.Cart;
import com.tarbadev.witchcraft.recipes.persistence.entity.RecipeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

  public CartEntity(Cart cart) {
    id = cart.getId();
        recipes = cart.getRecipes().stream().map(RecipeEntity::new).collect(Collectors.toList());
    items = cart.getItems().stream().map(ItemEntity::new).collect(Collectors.toList());
  }

  public Cart cart() {
    return Cart.builder()
        .id(id)
        .createdAt(createdAt)
        .recipes(recipes.stream().map(RecipeEntity::recipe).collect(Collectors.toList()))
        .items(items.stream().map(ItemEntity::item).collect(Collectors.toList()))
        .build();
  }
}
