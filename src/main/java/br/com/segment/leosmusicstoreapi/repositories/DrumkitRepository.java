package br.com.segment.leosmusicstoreapi.repositories;

import br.com.segment.leosmusicstoreapi.models.Drumkit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrumkitRepository extends JpaRepository<Drumkit, Long> {
}
