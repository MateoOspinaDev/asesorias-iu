package com.iud.asesorias.repository;

import com.iud.asesorias.model.TipoProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoProyectoRepository extends JpaRepository<TipoProyecto, Long> {
    Optional<TipoProyecto> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
