package com.iud.asesorias.service;

import com.iud.asesorias.dto.request.TipoProyectoRequest;
import com.iud.asesorias.dto.response.TipoProyectoResponse;
import com.iud.asesorias.exception.DuplicateResourceException;
import com.iud.asesorias.exception.ResourceNotFoundException;
import com.iud.asesorias.model.TipoProyecto;
import com.iud.asesorias.repository.TipoProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TipoProyectoService {
    
    private final TipoProyectoRepository tipoProyectoRepository;
    
    @Transactional(readOnly = true)
    public Page<TipoProyectoResponse> findAll(Pageable pageable) {
        return tipoProyectoRepository.findAll(pageable)
                .map(this::toResponse);
    }
    
    @Transactional(readOnly = true)
    public TipoProyectoResponse findById(Long id) {
        TipoProyecto tipoProyecto = tipoProyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de proyecto no encontrado con id: " + id));
        return toResponse(tipoProyecto);
    }
    
    @Transactional
    public TipoProyectoResponse create(TipoProyectoRequest request) {
        if (tipoProyectoRepository.existsByNombre(request.getNombre())) {
            throw new DuplicateResourceException("Ya existe un tipo de proyecto con el nombre: " + request.getNombre());
        }
        
        TipoProyecto tipoProyecto = new TipoProyecto();
        tipoProyecto.setNombre(request.getNombre());
        tipoProyecto.setDescripcion(request.getDescripcion());
        
        TipoProyecto saved = tipoProyectoRepository.save(tipoProyecto);
        return toResponse(saved);
    }
    
    @Transactional
    public TipoProyectoResponse update(Long id, TipoProyectoRequest request) {
        TipoProyecto tipoProyecto = tipoProyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de proyecto no encontrado con id: " + id));
        
        if (!tipoProyecto.getNombre().equals(request.getNombre()) &&
                tipoProyectoRepository.existsByNombre(request.getNombre())) {
            throw new DuplicateResourceException("Ya existe un tipo de proyecto con el nombre: " + request.getNombre());
        }
        
        tipoProyecto.setNombre(request.getNombre());
        tipoProyecto.setDescripcion(request.getDescripcion());
        
        TipoProyecto updated = tipoProyectoRepository.save(tipoProyecto);
        return toResponse(updated);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!tipoProyectoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tipo de proyecto no encontrado con id: " + id);
        }
        tipoProyectoRepository.deleteById(id);
    }
    
    private TipoProyectoResponse toResponse(TipoProyecto tipoProyecto) {
        return new TipoProyectoResponse(
                tipoProyecto.getId(),
                tipoProyecto.getNombre(),
                tipoProyecto.getDescripcion(),
                tipoProyecto.getCreatedAt(),
                tipoProyecto.getUpdatedAt()
        );
    }
}
