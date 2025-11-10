package com.iud.asesorias.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversidadResponse {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
