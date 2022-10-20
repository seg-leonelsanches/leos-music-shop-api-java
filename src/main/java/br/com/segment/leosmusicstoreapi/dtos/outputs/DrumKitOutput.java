package br.com.segment.leosmusicstoreapi.dtos.outputs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrumKitOutput {
    private Long id;
    private String model;
    private double price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mainImage;
    private String manufacturer;
}
