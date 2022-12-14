package br.com.segment.leosmusicstoreapi.data;

import br.com.segment.leosmusicstoreapi.models.Admin;
import br.com.segment.leosmusicstoreapi.models.DrumKit;
import br.com.segment.leosmusicstoreapi.models.Manufacturer;
import br.com.segment.leosmusicstoreapi.models.MigrationHistory;
import br.com.segment.leosmusicstoreapi.repositories.AdminRepository;
import br.com.segment.leosmusicstoreapi.repositories.DrumKitRepository;
import br.com.segment.leosmusicstoreapi.repositories.ManufacturerRepository;
import br.com.segment.leosmusicstoreapi.repositories.MigrationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private DrumKitRepository drumkitRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private MigrationHistoryRepository migrationHistoryRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void run(ApplicationArguments args) {
        Optional<MigrationHistory> attemptedInitialMigration = migrationHistoryRepository.findByName("Initial");
        if (attemptedInitialMigration.isPresent()) {
            return;
        }

        MigrationHistory initialMigrationHistory = new MigrationHistory(null, "Initial", LocalDateTime.now());
        migrationHistoryRepository.save(initialMigrationHistory);

        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(new Manufacturer(null, "Tama", null));
        manufacturers.add(new Manufacturer(null, "DW", null));

        manufacturerRepository.save(manufacturers.get(0));
        manufacturerRepository.save(manufacturers.get(1));

        drumkitRepository.save(
                new DrumKit(
                        null,
                        "DW Bobby Jarzombek Signature",
                        3200F,
                        "https://media.sweetwater.com/api/i/q-82__f-webp__ha-b01aee1aa16930d2__hmac-8ea28569a925eef634fed18445eb91ff84bfe99e/images/items/750/DRFP5PGB-BN-large.jpg.auto.webp",
                        manufacturers.get(1),
                        new HashSet<>(),
                        new HashSet<>()
                )
        );

        adminRepository.save(
                new Admin(
                        null,
                        "Leonel",
                        "Sanches",
                        "leonel.sanches@segment.com",
                        passwordEncoder.encode("hackme#123")
                )
        );
    }
}