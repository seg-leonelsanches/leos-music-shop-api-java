package br.com.segment.leosmusicstoreapi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "guest_orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuestOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String addressFirstLine;
    private String addressSecondLine;
    private String city;
    private String state;
    private int zipCode;

    @JsonManagedReference
    @OneToMany(mappedBy = "guestOrder")
    private Set<GuestOrderDrumKit> guestOrderDrumKits;
}
