package br.com.segment.leosmusicstoreapi.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {
    public Admin(Long id, String firstName, String lastName, String email, String passwordHash) {
        super(id, firstName, lastName, email, passwordHash);
    }
}
