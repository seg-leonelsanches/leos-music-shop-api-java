package br.com.segment.leosmusicstoreapi.repositories;

import br.com.segment.leosmusicstoreapi.models.DrumKit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrumKitRepository extends JpaRepository<DrumKit, Long> {
}
