package com.example.networksimulation;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

public class YamlParser {

    public static Map<String, Object> parseTopologyFromFile(File file) throws IOException {
        try (InputStream in = new FileInputStream(file)) {
            return parseYamlStream(in);
        }
    }

    public static Map<String, Object> parseTopologyFromClasspath(String resourcePath) throws IOException {
        InputStream in = YamlParser.class.getClassLoader().getResourceAsStream(resourcePath);
        if (in == null) throw new IOException("Topology resource not found on classpath: " + resourcePath);
        try (InputStream autoClose = in) {
            return parseYamlStream(autoClose);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> parseYamlStream(InputStream in) {
        Yaml yaml = new Yaml();
        Object loaded = yaml.load(in);
        if (!(loaded instanceof Map)) throw new RuntimeException("Unexpected YAML structure - expected top-level map");
        return (Map<String, Object>) loaded;
    }
}
