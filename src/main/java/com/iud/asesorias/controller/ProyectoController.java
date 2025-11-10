package com.iud.asesorias.controller;

import com.iud.asesorias.dto.request.ProyectoRequest;
import com.iud.asesorias.dto.response.ProyectoResponse;
import com.iud.asesorias.service.ProyectoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/proyectos")
@RequiredArgsConstructor
@Tag(name = "Proyectos", description = "API para gestión de proyectos")
public class ProyectoController {
    
    private final ProyectoService proyectoService;
    
    @GetMapping
    @Operation(summary = "Listar todos los proyectos con filtros opcionales")
    public ResponseEntity<Page<ProyectoResponse>> findAll(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrega,
            @RequestParam(required = false) Long tipoProyectoId,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long universidadId,
            @RequestParam(required = false) Long etapaId,
            @PageableDefault(size = 10, sort = "fechaInicio", direction = Sort.Direction.DESC) Pageable pageable) {
        
        if (titulo != null || numero != null || fechaInicio != null || fechaEntrega != null ||
                tipoProyectoId != null || clienteId != null || universidadId != null || etapaId != null) {
            return ResponseEntity.ok(proyectoService.findByFilters(
                    titulo, numero, fechaInicio, fechaEntrega,
                    tipoProyectoId, clienteId, universidadId, etapaId,
                    pageable));
        }
        
        return ResponseEntity.ok(proyectoService.findAll(pageable));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener proyecto por ID")
    public ResponseEntity<ProyectoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo proyecto")
    public ResponseEntity<ProyectoResponse> create(@Valid @RequestBody ProyectoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.create(request));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proyecto")
    public ResponseEntity<ProyectoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProyectoRequest request) {
        return ResponseEntity.ok(proyectoService.update(id, request));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proyecto")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proyectoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
