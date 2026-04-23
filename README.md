# Campus Path Finder

A Java implementation of Dijkstra's shortest path algorithm for finding optimal routes between campus locations.

## How It Works

- Uses a **priority queue** to efficiently explore the lowest-cost paths first
- Tracks visited nodes to avoid redundant processing
- Chains **predecessor nodes** to reconstruct the full path from start to end
- Generic implementation supporting any node and edge types (`NodeType`, `EdgeType extends Number`)

## Files

- `DijkstraGraph.java` — core shortest path implementation
- `BaseGraph.java` — base graph data structure
- `GraphADT.java` — graph interface
- `MapADT.java` — map interface
- `PlaceholderMap.java` — map implementation

## Usage

```java
