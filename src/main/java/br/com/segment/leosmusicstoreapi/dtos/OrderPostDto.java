package br.com.segment.leosmusicstoreapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPostDto {
    private List<OrderDrumKitPostDto> orderDrumKits;
}
