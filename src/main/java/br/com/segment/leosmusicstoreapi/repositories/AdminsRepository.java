package br.com.segment.leosmusicstoreapi.repositories;

import br.com.segment.leosmusicstoreapi.models.Admin;
import br.com.segment.leosmusicstoreapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminsRepository extends JpaRepository<Admin, Long> {
    User findByEmail(String email);
}
