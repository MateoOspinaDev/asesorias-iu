package com.iud.asesorias.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String documento;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
