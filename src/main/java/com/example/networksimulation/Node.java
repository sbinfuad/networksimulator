package com.example.networksimulation;

public class Node {
    private String name;
    private int layer;

    public Node() {}

    public Node(String name, int layer) {
        this.name = name;
        this.layer = layer;
    }

    public String getName() { return name; }
    public int getLayer() { return layer; }

    @Override
    public String toString() {
        return "Node{name='" + name + "', layer=" + layer + "}";
    }
}
