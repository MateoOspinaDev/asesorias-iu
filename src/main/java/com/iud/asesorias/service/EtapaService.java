package com.iud.asesorias.service;

import com.iud.asesorias.dto.request.EtapaRequest;
import com.iud.asesorias.dto.response.EtapaResponse;
import com.iud.asesorias.exception.DuplicateResourceException;
import com.iud.asesorias.exception.ResourceNotFoundException;
import com.iud.asesorias.model.Etapa;
import com.iud.asesorias.repository.EtapaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EtapaService {
    
    private final EtapaRepository etapaRepository;
    
    @Transactional(readOnly = true)
    public Page<EtapaResponse> findAll(Pageable pageable) {
        return etapaRepository.findAll(pageable)
                .map(this::toResponse);
    }
    
    @Transactional(readOnly = true)
    public EtapaResponse findById(Long id) {
        Etapa etapa = etapaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etapa no encontrada con id: " + id));
        return toResponse(etapa);
    }
    
    @Transactional
    public EtapaResponse create(EtapaRequest request) {
        if (etapaRepository.existsByNombre(request.getNombre())) {
            throw new DuplicateResourceException("Ya existe una etapa con el nombre: " + request.getNombre());
        }
        
        Etapa etapa = new Etapa();
        etapa.setNombre(request.getNombre());
        etapa.setDescripcion(request.getDescripcion());
        
        Etapa saved = etapaRepository.save(etapa);
        return toResponse(saved);
    }
    
    @Transactional
    public EtapaResponse update(Long id, EtapaRequest request) {
        Etapa etapa = etapaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etapa no encontrada con id: " + id));
        
        if (!etapa.getNombre().equals(request.getNombre()) &&
                etapaRepository.existsByNombre(request.getNombre())) {
            throw new DuplicateResourceException("Ya existe una etapa con el nombre: " + request.getNombre());
        }
        
        etapa.setNombre(request.getNombre());
        etapa.setDescripcion(request.getDescripcion());
        
        Etapa updated = etapaRepository.save(etapa);
        return toResponse(updated);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!etapaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Etapa no encontrada con id: " + id);
        }
        etapaRepository.deleteById(id);
    }
    
    private EtapaResponse toResponse(Etapa etapa) {
        return new EtapaResponse(
                etapa.getId(),
                etapa.getNombre(),
                etapa.getDescripcion(),
                etapa.getCreatedAt(),
                etapa.getUpdatedAt()
        );
    }
}
