package br.com.segment.leosmusicstoreapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity(name = "drum_kits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrumKit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String model;
    @NotNull
    private double price;
    private String mainImage;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="manufacturer_id", nullable=false)
    private Manufacturer manufacturer;

    @JsonManagedReference
    @OneToMany(mappedBy = "drumKit")
    private Set<OrderDrumKit> drumKitOrders;

    @JsonManagedReference
    @OneToMany(mappedBy = "drumKit")
    private Set<GuestOrderDrumKit> drumKitGuestOrders;
}
