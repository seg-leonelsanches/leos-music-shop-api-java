package br.com.segment.leosmusicstoreapi.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;
}
