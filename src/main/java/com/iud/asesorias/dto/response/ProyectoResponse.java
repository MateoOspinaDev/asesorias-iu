package com.iud.asesorias.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoResponse {
    private Long id;
    private String numero;
    private String titulo;
    private LocalDate fechaInicio;
    private LocalDate fechaEntrega;
    private BigDecimal valor;
    private TipoProyectoResponse tipoProyecto;
    private ClienteResponse cliente;
    private UniversidadResponse universidad;
    private EtapaResponse etapa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
