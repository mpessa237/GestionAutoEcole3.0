package com.felicite.SGAE30.repositories;

import com.felicite.SGAE30.enums.StatusVehicle;
import com.felicite.SGAE30.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle,Long> {

    // 1. Pour vérifier l'existence avant la création (évite les doublons)
    boolean existsByRegistration(String registration);

    // 2. Pour la recherche par plaque d'immatriculation exacte
    Optional<Vehicle> findByRegistration(String registration);

    // 3. Pour filtrer les véhicules par catégorie de permis (ex: tous les permis B)
    List<Vehicle> findByTypePermit(String typePermit);

    // 4. Pour lister les véhicules selon leur état (ex: uniquement les AVAILABLE)
    List<Vehicle> findByStatusVehicle(StatusVehicle statusVehicle);

    // 5. Recherche par marque (utile pour les stati
}
