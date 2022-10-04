package br.com.segment.leosmusicstoreapi.repositories;

import br.com.segment.leosmusicstoreapi.models.Customer;
import br.com.segment.leosmusicstoreapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    User findByEmail(String email);
}
