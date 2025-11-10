package com.iud.asesorias.repository;

import com.iud.asesorias.model.Universidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversidadRepository extends JpaRepository<Universidad, Long> {
    Optional<Universidad> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
