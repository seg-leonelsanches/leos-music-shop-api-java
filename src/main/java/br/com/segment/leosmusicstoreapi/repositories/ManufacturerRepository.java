package br.com.segment.leosmusicstoreapi.repositories;

import br.com.segment.leosmusicstoreapi.models.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
