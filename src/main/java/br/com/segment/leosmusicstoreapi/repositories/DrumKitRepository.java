package br.com.segment.leosmusicstoreapi.repositories;

import br.com.segment.leosmusicstoreapi.models.DrumKit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrumKitRepository extends JpaRepository<DrumKit, Long> {

    @Query("select drumKit from drum_kits drumKit left join fetch drumKit.manufacturer")
    List<DrumKit> findAllWithManufacturer();
    @Query("select drumKit from drum_kits drumKit left join fetch drumKit.manufacturer where drumKit.id = :id")
    DrumKit findByIdWithManufacturer(Long id);
}
