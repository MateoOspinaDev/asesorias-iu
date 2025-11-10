package com.iud.asesorias.controller;

import com.iud.asesorias.dto.request.ClienteRequest;
import com.iud.asesorias.dto.response.ClienteResponse;
import com.iud.asesorias.service.ClienteService;
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
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "API para gestión de clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    
    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    public ResponseEntity<Page<ClienteResponse>> findAll(
            @PageableDefault(size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(clienteService.findAll(pageable));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    public ResponseEntity<ClienteResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }
    
    @PostMapping
    @Operation(summary = "Crear nuevo cliente")
    public ResponseEntity<ClienteResponse> create(@Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.create(request));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente")
    public ResponseEntity<ClienteResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.update(id, request));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
