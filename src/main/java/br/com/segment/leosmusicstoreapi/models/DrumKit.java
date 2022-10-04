package br.com.segment.leosmusicstoreapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity(name = "drum_kits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrumKit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="manufacturer_id", nullable=false)
    private Manufacturer manufacturer;
}
