package com.example.networksimulation;

import java.util.*;

public class NetworkSimulation {
    private final Map<String, Node> nodeMap = new LinkedHashMap<>();
    private final List<Connection> connections = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public NetworkSimulation(Map<String, Object> topology) {
        Object nodesObj = topology.get("nodes");
        Object connsObj = topology.get("connections");

        if (!(nodesObj instanceof List) || !(connsObj instanceof List)) {
            throw new RuntimeException("Topology must contain 'nodes' and 'connections' lists");
        }

        List<Map<String, Object>> nodesList = (List<Map<String, Object>>) nodesObj;
        for (Map<String, Object> n : nodesList) {
            String name = (String) n.get("name");
            Object layerObj = n.get("layer");
            if (name == null || layerObj == null) {
                throw new RuntimeException("Each node must have 'name' and 'layer'");
            }
            int layer = (layerObj instanceof Integer) ? (Integer) layerObj : Integer.parseInt(layerObj.toString());
            nodeMap.put(name, new Node(name, layer));
        }

        List<Map<String, Object>> connList = (List<Map<String, Object>>) connsObj;
        for (Map<String, Object> c : connList) {
            String from = (String) c.get("from");
            String to = (String) c.get("to");
            if (from == null || to == null) throw new RuntimeException("Each connection must have 'from' and 'to'");
            if (!nodeMap.containsKey(from) || !nodeMap.containsKey(to)) {
                throw new RuntimeException("Connection refers to unknown node: " + from + " -> " + to);
            }
            connections.add(new Connection(from, to));
        }
    }

    public List<String> simulate(String message) throws InterruptedException {
        List<String> logs = new ArrayList<>();
        logs.add("Starting network simulation");
        logs.add("Message: \"" + message + "\"");

        if (connections.isEmpty()) {
            logs.add("No connections defined in topology!");
            return logs;
        }

        for (Connection conn : connections) {
            Node from = nodeMap.get(conn.getFrom());
            Node to = nodeMap.get(conn.getTo());

            logs.add("[Hop] " + from.getName() + " -> " + to.getName());
            processNode(from, message, logs);
            Thread.sleep(400);
        }

        Node last = nodeMap.get(connections.get(connections.size() - 1).getTo());
        processNode(last, message, logs);
        logs.add("Message successfully delivered through topology!");
        return logs;
    }

    private void processNode(Node node, String message, List<String> logs) throws InterruptedException {
        String name = node.getName().toLowerCase();
        switch (name) {
            case "waverouter" ->
                    logs.add("[Layer 3] WaveRouter: Routing message → \"" + message + "\"");
            case "waveserver" ->
                    logs.add("[Layer 1] Waveserver: Converting digital data to optical signal...");
            case "rls" -> {
                logs.add("[Layer 0] RLS: Managing optical wavelengths...");
                logs.add("Physical layer delay (simulated) - waiting 2s");
                Thread.sleep(2000);
            }
            default -> logs.add("Processing node: " + node);
        }
    }
}
