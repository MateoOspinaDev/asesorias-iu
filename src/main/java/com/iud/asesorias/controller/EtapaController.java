package com.iud.asesorias.controller;

import com.iud.asesorias.dto.request.EtapaRequest;
import com.iud.asesorias.dto.response.EtapaResponse;
import com.iud.asesorias.service.EtapaService;
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
@RequestMapping("/api/v1/etapas")
@RequiredArgsConstructor
@Tag(name = "Etapas", description = "API para gestión de etapas")
public class EtapaController {
    
    private final EtapaService etapaService;
    
    @GetMapping
    @Operation(summary = "Listar todas las etapas")
    public ResponseEntity<Page<EtapaResponse>> findAll(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(etapaService.findAll(pageable));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener etapa por ID")
    public ResponseEntity<EtapaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(etapaService.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Crear nueva etapa")
    public ResponseEntity<EtapaResponse> create(@Valid @RequestBody EtapaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(etapaService.create(request));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar etapa")
    public ResponseEntity<EtapaResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody EtapaRequest request) {
        return ResponseEntity.ok(etapaService.update(id, request));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar etapa")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etapaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
