package com.example.networksimulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class NetworksimulationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworksimulationApplication.class, args);
    }

    @PostMapping("/simulate")
    public ResponseEntity<?> simulate(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Field 'message' is required"));
        }

        String topologyPath = body.getOrDefault("topology", "topology.yaml");

        try {
            Map<String, Object> topology;
            File f = new File(topologyPath);
            if (f.exists()) {
                topology = YamlParser.parseTopologyFromFile(f);
            } else {
                topology = YamlParser.parseTopologyFromClasspath(topologyPath);
            }

            NetworkSimulation simulation = new NetworkSimulation(topology);
            List<String> logs = simulation.simulate(message);

            return ResponseEntity.ok(Map.of("status", "ok", "logs", logs));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "I/O error: " + e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("status", "error", "message", e.getMessage()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Simulation interrupted"));
        }
    }
}
