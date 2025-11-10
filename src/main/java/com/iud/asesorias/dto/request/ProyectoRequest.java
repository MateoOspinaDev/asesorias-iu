package com.iud.asesorias.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoRequest {
    
    @NotBlank(message = "El número es obligatorio")
    @Size(max = 50, message = "El número no puede exceder 50 caracteres")
    private String numero;
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;
    
    @NotNull(message = "La fecha de entrega es obligatoria")
    private LocalDate fechaEntrega;
    
    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser positivo")
    private BigDecimal valor;
    
    @NotNull(message = "El tipo de proyecto es obligatorio")
    private Long tipoProyectoId;
    
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;
    
    @NotNull(message = "La universidad es obligatoria")
    private Long universidadId;
    
    @NotNull(message = "La etapa es obligatoria")
    private Long etapaId;
}
