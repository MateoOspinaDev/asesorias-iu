package com.iud.asesorias.controller;

import com.iud.asesorias.dto.request.UniversidadRequest;
import com.iud.asesorias.dto.response.UniversidadResponse;
import com.iud.asesorias.service.UniversidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/universidades")
@RequiredArgsConstructor
@Tag(name = "Universidades", description = "API para gestión de universidades")
public class UniversidadController {
    
    private final UniversidadService universidadService;
    
    @GetMapping
    @Operation(summary = "Listar todas las universidades")
    public ResponseEntity<Page<UniversidadResponse>> findAll(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(universidadService.findAll(pageable));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener universidad por ID")
    public ResponseEntity<UniversidadResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(universidadService.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Crear nueva universidad")
    public ResponseEntity<UniversidadResponse> create(@Valid @RequestBody UniversidadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(universidadService.create(request));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar universidad")
    public ResponseEntity<UniversidadResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UniversidadRequest request) {
        return ResponseEntity.ok(universidadService.update(id, request));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar universidad")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        universidadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
