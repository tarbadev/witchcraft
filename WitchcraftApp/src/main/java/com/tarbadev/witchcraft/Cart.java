package com.tarbadev.witchcraft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "carts")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @CreationTimestamp
  protected Date createdAt;
  @ManyToMany(cascade = CascadeType.ALL)
  private List<Recipe> recipes;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id")
  private List<Item> items;
}
