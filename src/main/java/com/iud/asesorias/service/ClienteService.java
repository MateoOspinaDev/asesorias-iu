package com.iud.asesorias.service;

import com.iud.asesorias.dto.request.ClienteRequest;
import com.iud.asesorias.dto.response.ClienteResponse;
import com.iud.asesorias.exception.DuplicateResourceException;
import com.iud.asesorias.exception.ResourceNotFoundException;
import com.iud.asesorias.model.Cliente;
import com.iud.asesorias.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    @Transactional(readOnly = true)
    public Page<ClienteResponse> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable)
                .map(this::toResponse);
    }
    
    @Transactional(readOnly = true)
    public ClienteResponse findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return toResponse(cliente);
    }
    
    @Transactional
    public ClienteResponse create(ClienteRequest request) {
        if (clienteRepository.existsByDocumento(request.getDocumento())) {
            throw new DuplicateResourceException("Ya existe un cliente con el documento: " + request.getDocumento());
        }
        
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setDocumento(request.getDocumento());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        
        Cliente saved = clienteRepository.save(cliente);
        return toResponse(saved);
    }
    
    @Transactional
    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        
        if (!cliente.getDocumento().equals(request.getDocumento()) &&
                clienteRepository.existsByDocumento(request.getDocumento())) {
            throw new DuplicateResourceException("Ya existe un cliente con el documento: " + request.getDocumento());
        }
        
        cliente.setNombre(request.getNombre());
        cliente.setDocumento(request.getDocumento());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        
        Cliente updated = clienteRepository.save(cliente);
        return toResponse(updated);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getDocumento(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                cliente.getCreatedAt(),
                cliente.getUpdatedAt()
        );
    }
}
