package br.com.segment.leosmusicstoreapi.repositories;

import br.com.segment.leosmusicstoreapi.models.GuestOrderDrumKit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestOrderDrumKitRepository extends JpaRepository<GuestOrderDrumKit, Long> {
}
