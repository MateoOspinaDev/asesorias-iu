package com.iud.asesorias.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iud.asesorias.dto.request.ClienteRequest;
import com.iud.asesorias.dto.response.ClienteResponse;
import com.iud.asesorias.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@ActiveProfiles("test")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    private ClienteRequest clienteRequest;
    private ClienteResponse clienteResponse;

    @BeforeEach
    void setUp() {
        clienteRequest = new ClienteRequest();
        clienteRequest.setNombre("Juan Pérez");
        clienteRequest.setDocumento("123456789");
        clienteRequest.setEmail("juan@example.com");
        clienteRequest.setTelefono("3001234567");
        clienteRequest.setDireccion("Calle 123");

        clienteResponse = new ClienteResponse();
        clienteResponse.setId(1L);
        clienteResponse.setNombre("Juan Pérez");
        clienteResponse.setDocumento("123456789");
        clienteResponse.setEmail("juan@example.com");
        clienteResponse.setTelefono("3001234567");
        clienteResponse.setDireccion("Calle 123");
        clienteResponse.setCreatedAt(LocalDateTime.now());
        clienteResponse.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testFindAll() throws Exception {
        Page<ClienteResponse> page = new PageImpl<>(List.of(clienteResponse), PageRequest.of(0, 10), 1);
        when(clienteService.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nombre").value("Juan Pérez"));
    }

    @Test
    void testFindById() throws Exception {
        when(clienteService.findById(1L)).thenReturn(clienteResponse);

        mockMvc.perform(get("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.documento").value("123456789"));
    }

    @Test
    void testCreate() throws Exception {
        when(clienteService.create(any(ClienteRequest.class))).thenReturn(clienteResponse);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void testUpdate() throws Exception {
        when(clienteService.update(eq(1L), any(ClienteRequest.class))).thenReturn(clienteResponse);

        mockMvc.perform(put("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(clienteService).delete(1L);

        mockMvc.perform(delete("/api/v1/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).delete(1L);
    }
}
