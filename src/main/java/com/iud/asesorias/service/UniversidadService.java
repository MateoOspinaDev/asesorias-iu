package com.iud.asesorias.service;

import com.iud.asesorias.dto.request.UniversidadRequest;
import com.iud.asesorias.dto.response.UniversidadResponse;
import com.iud.asesorias.exception.DuplicateResourceException;
import com.iud.asesorias.exception.ResourceNotFoundException;
import com.iud.asesorias.model.Universidad;
import com.iud.asesorias.repository.UniversidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UniversidadService {
    
    private final UniversidadRepository universidadRepository;
    
    @Transactional(readOnly = true)
    public Page<UniversidadResponse> findAll(Pageable pageable) {
        return universidadRepository.findAll(pageable)
                .map(this::toResponse);
    }
    
    @Transactional(readOnly = true)
    public UniversidadResponse findById(Long id) {
        Universidad universidad = universidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Universidad no encontrada con id: " + id));
        return toResponse(universidad);
    }
    
    @Transactional
    public UniversidadResponse create(UniversidadRequest request) {
        if (universidadRepository.existsByNombre(request.getNombre())) {
            throw new DuplicateResourceException("Ya existe una universidad con el nombre: " + request.getNombre());
        }
        
        Universidad universidad = new Universidad();
        universidad.setNombre(request.getNombre());
        universidad.setDireccion(request.getDireccion());
        universidad.setTelefono(request.getTelefono());
        universidad.setEmail(request.getEmail());
        
        Universidad saved = universidadRepository.save(universidad);
        return toResponse(saved);
    }
    
    @Transactional
    public UniversidadResponse update(Long id, UniversidadRequest request) {
        Universidad universidad = universidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Universidad no encontrada con id: " + id));
        
        if (!universidad.getNombre().equals(request.getNombre()) &&
                universidadRepository.existsByNombre(request.getNombre())) {
            throw new DuplicateResourceException("Ya existe una universidad con el nombre: " + request.getNombre());
        }
        
        universidad.setNombre(request.getNombre());
        universidad.setDireccion(request.getDireccion());
        universidad.setTelefono(request.getTelefono());
        universidad.setEmail(request.getEmail());
        
        Universidad updated = universidadRepository.save(universidad);
        return toResponse(updated);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!universidadRepository.existsById(id)) {
            throw new ResourceNotFoundException("Universidad no encontrada con id: " + id);
        }
        universidadRepository.deleteById(id);
    }
    
    private UniversidadResponse toResponse(Universidad universidad) {
        return new UniversidadResponse(
                universidad.getId(),
                universidad.getNombre(),
                universidad.getDireccion(),
                universidad.getTelefono(),
                universidad.getEmail(),
                universidad.getCreatedAt(),
                universidad.getUpdatedAt()
        );
    }
}
