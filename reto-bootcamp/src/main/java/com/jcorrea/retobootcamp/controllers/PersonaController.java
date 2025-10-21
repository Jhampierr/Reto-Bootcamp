package com.jcorrea.retobootcamp.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PersonaController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public Object listarPersonas() {
        // Llamado a la API y trayendo solo la cantidad de registros que piden
        String url = "https://randomuser.me/api/?results=10";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        // Se filtra para traer solo los campos requeridos
        return results.stream().map(persona -> {
            Map<String, Object> filtered = new LinkedHashMap<>();
            filtered.put("nombre", ((Map<String, Object>) persona.get("name")).get("first") + " " + ((Map<String, Object>) persona.get("name")).get("last"));
            filtered.put("genero", persona.get("gender"));
            filtered.put("ubicacion", ((Map<String, Object>) persona.get("location")).get("country"));
            filtered.put("email", persona.get("email"));
            filtered.put("fechaNacimiento", ((Map<String, Object>) persona.get("dob")).get("date"));
            filtered.put("foto", ((Map<String, Object>) persona.get("picture")).get("large"));
            return filtered;
        }).collect(Collectors.toList());
    }
}

