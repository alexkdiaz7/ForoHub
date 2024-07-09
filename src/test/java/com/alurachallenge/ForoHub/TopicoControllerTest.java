package com.alurachallenge.ForoHub;

import com.alurachallenge.ForoHub.controller.TopicoController;
import com.alurachallenge.ForoHub.topico.Topico;
import com.alurachallenge.ForoHub.topico.TopicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TopicoControllerTest {

    @Mock
    private TopicoRepository topicoRepository;

    @InjectMocks
    private TopicoController topicoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearTopico() {
        Topico topico = new Topico();
        topico.setId(1L);
        topico.setTitulo("Título del Tópico");
        topico.setMensaje("Mensaje del Tópico");

        when(topicoRepository.findByTituloAndMensaje(anyString(), anyString())).thenReturn(Optional.empty());
        when(topicoRepository.save(any(Topico.class))).thenReturn(topico);

        ResponseEntity<?> responseEntity = topicoController.crearTopico(topico);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(topico, responseEntity.getBody());
    }

    @Test
    public void testListarTopicos() {
        List<Topico> topicos = new ArrayList<>();
        topicos.add(new Topico());
        topicos.add(new Topico());

        when(topicoRepository.findAll()).thenReturn(topicos);

        List<Topico> result = topicoController.listarTopicos();

        assertEquals(2, result.size());
    }

    @Test
    public void testObtenerTopico() {
        Topico topico = new Topico();
        topico.setId(1L);
        topico.setTitulo("Título del Tópico");
        topico.setMensaje("Mensaje del Tópico");

        when(topicoRepository.findById(anyLong())).thenReturn(Optional.of(topico));

        ResponseEntity<Topico> responseEntity = topicoController.obtenerTopico(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(topico, responseEntity.getBody());
    }

    @Test
    public void testActualizarTopico() {
        Topico topico = new Topico();
        topico.setId(1L);
        topico.setTitulo("Título del Tópico");
        topico.setMensaje("Mensaje del Tópico");

        Topico detallesTopico = new Topico();
        detallesTopico.setTitulo("Nuevo Título");
        detallesTopico.setMensaje("Nuevo Mensaje");
        detallesTopico.setStatus("ACTIVO");

        when(topicoRepository.findById(anyLong())).thenReturn(Optional.of(topico));
        when(topicoRepository.save(any(Topico.class))).thenReturn(detallesTopico);

        ResponseEntity<Topico> responseEntity = topicoController.actualizarTopico(1L, detallesTopico);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Nuevo Título", responseEntity.getBody().getTitulo());
        assertEquals("Nuevo Mensaje", responseEntity.getBody().getMensaje());
        assertEquals("ACTIVO", responseEntity.getBody().getStatus());
    }

    @Test
    public void testEliminarTopico() {
        Topico topico = new Topico();
        topico.setId(1L);
        topico.setTitulo("Título del Tópico");
        topico.setMensaje("Mensaje del Tópico");

        when(topicoRepository.findById(anyLong())).thenReturn(Optional.of(topico));

        ResponseEntity<Void> responseEntity = topicoController.eliminarTopico(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}