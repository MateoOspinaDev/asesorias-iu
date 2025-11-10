package com.iud.asesorias.controller;

import com.iud.asesorias.dto.request.TipoProyectoRequest;
import com.iud.asesorias.dto.response.TipoProyectoResponse;
import com.iud.asesorias.service.TipoProyectoService;
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
@RequestMapping("/api/v1/tipos-proyecto")
@RequiredArgsConstructor
@Tag(name = "Tipos de Proyecto", description = "API para gestión de tipos de proyecto")
public class TipoProyectoController {
    
    private final TipoProyectoService tipoProyectoService;
    
    @GetMapping
    @Operation(summary = "Listar todos los tipos de proyecto")
    public ResponseEntity<Page<TipoProyectoResponse>> findAll(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(tipoProyectoService.findAll(pageable));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo de proyecto por ID")
    public ResponseEntity<TipoProyectoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoProyectoService.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo tipo de proyecto")
    public ResponseEntity<TipoProyectoResponse> create(@Valid @RequestBody TipoProyectoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoProyectoService.create(request));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tipo de proyecto")
    public ResponseEntity<TipoProyectoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TipoProyectoRequest request) {
        return ResponseEntity.ok(tipoProyectoService.update(id, request));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tipo de proyecto")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoProyectoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
