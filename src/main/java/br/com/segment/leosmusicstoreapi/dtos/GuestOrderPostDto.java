package br.com.segment.leosmusicstoreapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestOrderPostDto {
    private String firstName;
    private String lastName;
    private String email;
    private String addressFirstLine;
    private String addressSecondLine;
    private String city;
    private String state;
    private int zipCode;

    private List<GuestOrderDrumKitPostDto> guestOrderDrumKits;
}
