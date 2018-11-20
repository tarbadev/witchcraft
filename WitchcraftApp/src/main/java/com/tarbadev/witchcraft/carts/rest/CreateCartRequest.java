package com.tarbadev.witchcraft.carts.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class CreateCartRequest {
  private Integer id;
}
