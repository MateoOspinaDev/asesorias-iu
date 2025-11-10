package com.iud.asesorias.service;

import com.iud.asesorias.dto.request.ProyectoRequest;
import com.iud.asesorias.dto.response.*;
import com.iud.asesorias.exception.DuplicateResourceException;
import com.iud.asesorias.exception.ResourceNotFoundException;
import com.iud.asesorias.model.*;
import com.iud.asesorias.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProyectoService {
    
    private final ProyectoRepository proyectoRepository;
    private final TipoProyectoRepository tipoProyectoRepository;
    private final ClienteRepository clienteRepository;
    private final UniversidadRepository universidadRepository;
    private final EtapaRepository etapaRepository;
    
    @Transactional(readOnly = true)
    public Page<ProyectoResponse> findAll(Pageable pageable) {
        return proyectoRepository.findAll(pageable)
                .map(this::toResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<ProyectoResponse> findByFilters(
            String titulo, String numero, LocalDate fechaInicio, LocalDate fechaEntrega,
            Long tipoProyectoId, Long clienteId, Long universidadId, Long etapaId,
            Pageable pageable) {
        return proyectoRepository.findByFilters(
                titulo, numero, fechaInicio, fechaEntrega,
                tipoProyectoId, clienteId, universidadId, etapaId,
                pageable
        ).map(this::toResponse);
    }
    
    @Transactional(readOnly = true)
    public ProyectoResponse findById(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con id: " + id));
        return toResponse(proyecto);
    }
    
    @Transactional
    public ProyectoResponse create(ProyectoRequest request) {
        if (proyectoRepository.existsByNumero(request.getNumero())) {
            throw new DuplicateResourceException("Ya existe un proyecto con el número: " + request.getNumero());
        }
        
        TipoProyecto tipoProyecto = tipoProyectoRepository.findById(request.getTipoProyectoId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de proyecto no encontrado con id: " + request.getTipoProyectoId()));
        
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + request.getClienteId()));
        
        Universidad universidad = universidadRepository.findById(request.getUniversidadId())
                .orElseThrow(() -> new ResourceNotFoundException("Universidad no encontrada con id: " + request.getUniversidadId()));
        
        Etapa etapa = etapaRepository.findById(request.getEtapaId())
                .orElseThrow(() -> new ResourceNotFoundException("Etapa no encontrada con id: " + request.getEtapaId()));
        
        Proyecto proyecto = new Proyecto();
        proyecto.setNumero(request.getNumero());
        proyecto.setTitulo(request.getTitulo());
        proyecto.setFechaInicio(request.getFechaInicio());
        proyecto.setFechaEntrega(request.getFechaEntrega());
        proyecto.setValor(request.getValor());
        proyecto.setTipoProyecto(tipoProyecto);
        proyecto.setCliente(cliente);
        proyecto.setUniversidad(universidad);
        proyecto.setEtapa(etapa);
        
        Proyecto saved = proyectoRepository.save(proyecto);
        return toResponse(saved);
    }
    
    @Transactional
    public ProyectoResponse update(Long id, ProyectoRequest request) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con id: " + id));
        
        if (!proyecto.getNumero().equals(request.getNumero()) &&
                proyectoRepository.existsByNumero(request.getNumero())) {
            throw new DuplicateResourceException("Ya existe un proyecto con el número: " + request.getNumero());
        }
        
        TipoProyecto tipoProyecto = tipoProyectoRepository.findById(request.getTipoProyectoId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de proyecto no encontrado con id: " + request.getTipoProyectoId()));
        
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + request.getClienteId()));
        
        Universidad universidad = universidadRepository.findById(request.getUniversidadId())
                .orElseThrow(() -> new ResourceNotFoundException("Universidad no encontrada con id: " + request.getUniversidadId()));
        
        Etapa etapa = etapaRepository.findById(request.getEtapaId())
                .orElseThrow(() -> new ResourceNotFoundException("Etapa no encontrada con id: " + request.getEtapaId()));
        
        proyecto.setNumero(request.getNumero());
        proyecto.setTitulo(request.getTitulo());
        proyecto.setFechaInicio(request.getFechaInicio());
        proyecto.setFechaEntrega(request.getFechaEntrega());
        proyecto.setValor(request.getValor());
        proyecto.setTipoProyecto(tipoProyecto);
        proyecto.setCliente(cliente);
        proyecto.setUniversidad(universidad);
        proyecto.setEtapa(etapa);
        
        Proyecto updated = proyectoRepository.save(proyecto);
        return toResponse(updated);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proyecto no encontrado con id: " + id);
        }
        proyectoRepository.deleteById(id);
    }
    
    private ProyectoResponse toResponse(Proyecto proyecto) {
        TipoProyectoResponse tipoProyectoResponse = new TipoProyectoResponse(
                proyecto.getTipoProyecto().getId(),
                proyecto.getTipoProyecto().getNombre(),
                proyecto.getTipoProyecto().getDescripcion(),
                proyecto.getTipoProyecto().getCreatedAt(),
                proyecto.getTipoProyecto().getUpdatedAt()
        );
        
        ClienteResponse clienteResponse = new ClienteResponse(
                proyecto.getCliente().getId(),
                proyecto.getCliente().getNombre(),
                proyecto.getCliente().getDocumento(),
                proyecto.getCliente().getEmail(),
                proyecto.getCliente().getTelefono(),
                proyecto.getCliente().getDireccion(),
                proyecto.getCliente().getCreatedAt(),
                proyecto.getCliente().getUpdatedAt()
        );
        
        UniversidadResponse universidadResponse = new UniversidadResponse(
                proyecto.getUniversidad().getId(),
                proyecto.getUniversidad().getNombre(),
                proyecto.getUniversidad().getDireccion(),
                proyecto.getUniversidad().getTelefono(),
                proyecto.getUniversidad().getEmail(),
                proyecto.getUniversidad().getCreatedAt(),
                proyecto.getUniversidad().getUpdatedAt()
        );
        
        EtapaResponse etapaResponse = new EtapaResponse(
                proyecto.getEtapa().getId(),
                proyecto.getEtapa().getNombre(),
                proyecto.getEtapa().getDescripcion(),
                proyecto.getEtapa().getCreatedAt(),
                proyecto.getEtapa().getUpdatedAt()
        );
        
        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getNumero(),
                proyecto.getTitulo(),
                proyecto.getFechaInicio(),
                proyecto.getFechaEntrega(),
                proyecto.getValor(),
                tipoProyectoResponse,
                clienteResponse,
                universidadResponse,
                etapaResponse,
                proyecto.getCreatedAt(),
                proyecto.getUpdatedAt()
        );
    }
}
