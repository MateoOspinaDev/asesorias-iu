package com.iud.asesorias.service;

import com.iud.asesorias.dto.request.TipoProyectoRequest;
import com.iud.asesorias.dto.response.TipoProyectoResponse;
import com.iud.asesorias.exception.DuplicateResourceException;
import com.iud.asesorias.exception.ResourceNotFoundException;
import com.iud.asesorias.model.TipoProyecto;
import com.iud.asesorias.repository.TipoProyectoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoProyectoServiceTest {

    @Mock
    private TipoProyectoRepository tipoProyectoRepository;

    @InjectMocks
    private TipoProyectoService tipoProyectoService;

    private TipoProyecto tipoProyecto;
    private TipoProyectoRequest request;

    @BeforeEach
    void setUp() {
        tipoProyecto = new TipoProyecto();
        tipoProyecto.setId(1L);
        tipoProyecto.setNombre("Desarrollo Web");
        tipoProyecto.setDescripcion("Proyectos de desarrollo web");

        request = new TipoProyectoRequest();
        request.setNombre("Desarrollo Web");
        request.setDescripcion("Proyectos de desarrollo web");
    }

    @Test
    void testFindById_Success() {
        when(tipoProyectoRepository.findById(1L)).thenReturn(Optional.of(tipoProyecto));

        TipoProyectoResponse response = tipoProyectoService.findById(1L);

        assertNotNull(response);
        assertEquals("Desarrollo Web", response.getNombre());
        verify(tipoProyectoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(tipoProyectoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tipoProyectoService.findById(1L));
    }

    @Test
    void testCreate_Success() {
        when(tipoProyectoRepository.existsByNombre(anyString())).thenReturn(false);
        when(tipoProyectoRepository.save(any(TipoProyecto.class))).thenReturn(tipoProyecto);

        TipoProyectoResponse response = tipoProyectoService.create(request);

        assertNotNull(response);
        assertEquals("Desarrollo Web", response.getNombre());
        verify(tipoProyectoRepository, times(1)).save(any(TipoProyecto.class));
    }

    @Test
    void testCreate_DuplicateName() {
        when(tipoProyectoRepository.existsByNombre(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> tipoProyectoService.create(request));
        verify(tipoProyectoRepository, never()).save(any(TipoProyecto.class));
    }

    @Test
    void testDelete_Success() {
        when(tipoProyectoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tipoProyectoRepository).deleteById(1L);

        tipoProyectoService.delete(1L);

        verify(tipoProyectoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(tipoProyectoRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> tipoProyectoService.delete(1L));
        verify(tipoProyectoRepository, never()).deleteById(any());
    }
}
