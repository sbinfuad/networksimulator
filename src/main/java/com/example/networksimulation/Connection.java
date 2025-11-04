package com.example.networksimulation;

public class Connection {
    private String from;
    private String to;

    public Connection() {}

    public Connection(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}
