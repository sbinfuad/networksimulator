# Network Simulation (Spring Boot + YAML)

A simple Java + Spring Boot application that simulates how a text message travels through a network —
using Ciena-style optical devices (WaveRouter, Waveserver, and RLS) — across different OSI layers.

---

## Features

- YAML topology configuration (customizable)
- Command-line interface for direct simulation
- Spring Boot REST API endpoint (`/simulate`) for triggering simulations via curl or Postman
- Simulates data flow through optical devices
- Demonstrates concept of network layering (Layer 0/1/3)
- Clean modular design (Node, Connection, YAML parser, Simulation engine)

---

## Technologies Used

- **Java 17**
- **Spring Boot 3**
- **SnakeYAML** (for parsing YAML topology)
- **Maven** (for project build)
- **REST API** (Spring Web)
- **Command-Line Interface** (Scanner input)

---

## How to Run

### 1. Clone the repository
```
git clone https://github.com/YOUR_USERNAME/network-simulation.git
cd network-simulation
 ```

### 2. Build and run using Maven
```
mvn spring-boot:run
```

### 3. Run via CLI
```
java -jar target/network-simulation-0.0.1-SNAPSHOT.jar

```

You’ll be prompted to:

Enter a message

Provide a YAML topology file path (default: src/main/resources/topology.yaml)

## REST API Usage
```
curl -X POST "http://localhost:8080/simulate" \
-H "Content-Type: application/json" \
-d '{"message": "Hello from Ottawa!", "topologyFile": "src/main/resources/topology.yaml"}'

```

## Example Output

```
=== Network Simulation ===
Message: "Hello from Ottawa!"

[Layer 3] WaveRouter: Routing message → "Hello from Ottawa!"
→ Data flows from WaveRouter to Waveserver
[Layer 1] Waveserver: Converting digital data to optical signal...
→ Data flows from Waveserver to RLS
[Layer 0] RLS: Managing optical wavelengths...

Message successfully delivered through topology!


```

