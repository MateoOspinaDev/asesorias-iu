package com.iud.asesorias.repository;

import com.iud.asesorias.model.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Long> {
    Optional<Etapa> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
