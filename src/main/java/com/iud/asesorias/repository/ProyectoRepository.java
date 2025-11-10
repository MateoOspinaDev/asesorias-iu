package com.iud.asesorias.repository;

import com.iud.asesorias.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    Optional<Proyecto> findByNumero(String numero);
    boolean existsByNumero(String numero);
    
    @Query("SELECT p FROM Proyecto p WHERE " +
           "(:titulo IS NULL OR LOWER(p.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) AND " +
           "(:numero IS NULL OR LOWER(p.numero) LIKE LOWER(CONCAT('%', :numero, '%'))) AND " +
           "(:fechaInicio IS NULL OR p.fechaInicio >= :fechaInicio) AND " +
           "(:fechaEntrega IS NULL OR p.fechaEntrega <= :fechaEntrega) AND " +
           "(:tipoProyectoId IS NULL OR p.tipoProyecto.id = :tipoProyectoId) AND " +
           "(:clienteId IS NULL OR p.cliente.id = :clienteId) AND " +
           "(:universidadId IS NULL OR p.universidad.id = :universidadId) AND " +
           "(:etapaId IS NULL OR p.etapa.id = :etapaId)")
    Page<Proyecto> findByFilters(
            @Param("titulo") String titulo,
            @Param("numero") String numero,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaEntrega") LocalDate fechaEntrega,
            @Param("tipoProyectoId") Long tipoProyectoId,
            @Param("clienteId") Long clienteId,
            @Param("universidadId") Long universidadId,
            @Param("etapaId") Long etapaId,
            Pageable pageable);
}
