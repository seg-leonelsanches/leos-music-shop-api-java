package br.com.segment.leosmusicstoreapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDrumKitPostDto {
    private Long drumKitId;
    private int quantity;
}
