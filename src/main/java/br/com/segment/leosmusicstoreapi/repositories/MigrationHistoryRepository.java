package br.com.segment.leosmusicstoreapi.repositories;

import br.com.segment.leosmusicstoreapi.models.MigrationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MigrationHistoryRepository extends JpaRepository<MigrationHistory, Long> {
    Optional<MigrationHistory> findByName(String name);
}
