package pinpoint;

import java.util.*;

public class Dijkstra {
    private Graph graph;
    private boolean accessibleOnly;

    public Dijkstra(Graph graph) {
        this.graph = graph;
        this.accessibleOnly = false;
    }

    public Dijkstra(Graph graph, boolean accessibleOnly) {
        this.graph = graph;
        this.accessibleOnly = accessibleOnly;
    }

    public PathResult findShortestPath(String startId, String endId) {
        Node start = graph.getNode(startId);
        Node end = graph.getNode(endId);

        if (start == null || end == null) {
            return new PathResult(null, Double.MAX_VALUE, "Start or end node not found", 0);
        }

        return findShortestPath(start, end);
    }

    public PathResult findShortestPath(Node start, Node end) {
        long startTime = System.currentTimeMillis();

        // Reset all nodes
        graph.resetAllNodes();

        // Priority queue to hold nodes to visit
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(Node::getGCost));

        // Set start node
        start.setGCost(0);
        pq.offer(start);

        int nodesVisited = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.isVisited())
                continue;

            current.setVisited(true);
            nodesVisited++;

            // Check if we reached the destination
            if (current.equals(end)) {
                long endTime = System.currentTimeMillis();
                List<Node> path = reconstructPath(end);
                double totalDistance = end.getGCost();

                return new PathResult(path, totalDistance,
                        "Path found successfully", nodesVisited, endTime - startTime);
            }

            // Explore neighbors
            for (Node neighbor : current.getNeighbors()) {
                if (neighbor.isVisited())
                    continue;

                Edge edge = current.getEdgeTo(neighbor);
                if (edge == null)
                    continue;

                // Skip non-accessible edges if accessibility mode is on
                if (accessibleOnly && !edge.isAccessible())
                    continue;

                double newDistance = current.getGCost() + edge.getActualWeight();

                if (newDistance < neighbor.getGCost()) {
                    neighbor.setGCost(newDistance);
                    neighbor.setParent(current);
                    pq.offer(neighbor);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        return new PathResult(null, Double.MAX_VALUE,
                "No path found", nodesVisited, endTime - startTime);
    }

    public Map<String, PathResult> findShortestPathsFromSource(String sourceId) {
        Node source = graph.getNode(sourceId);
        if (source == null) {
            return new HashMap<>();
        }

        return findShortestPathsFromSource(source);
    }

    public Map<String, PathResult> findShortestPathsFromSource(Node source) {
        Map<String, PathResult> results = new HashMap<>();

        // Reset all nodes
        graph.resetAllNodes();

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(Node::getGCost));

        source.setGCost(0);
        pq.offer(source);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.isVisited())
                continue;

            current.setVisited(true);

            // Store result for this node
            List<Node> path = reconstructPath(current);
            results.put(current.getId(), new PathResult(path, current.getGCost(),
                    "Path found", 0));

            // Explore neighbors
            for (Node neighbor : current.getNeighbors()) {
                if (neighbor.isVisited())
                    continue;

                Edge edge = current.getEdgeTo(neighbor);
                if (edge == null)
                    continue;

                if (accessibleOnly && !edge.isAccessible())
                    continue;

                double newDistance = current.getGCost() + edge.getActualWeight();

                if (newDistance < neighbor.getGCost()) {
                    neighbor.setGCost(newDistance);
                    neighbor.setParent(current);
                    pq.offer(neighbor);
                }
            }
        }

        return results;
    }

    private List<Node> reconstructPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;

        while (current != null) {
            path.add(0, current);
            current = current.getParent();
        }

        return path;
    }

    public List<String> getDirections(List<Node> path) {
        List<String> directions = new ArrayList<>();

        if (path == null || path.size() < 2) {
            return directions;
        }

        directions.add("Start at " + path.get(0).getName());

        for (int i = 0; i < path.size() - 1; i++) {
            Node from = path.get(i);
            Node to = path.get(i + 1);
            Edge edge = from.getEdgeTo(to);

            if (edge != null) {
                directions.add((i + 1) + ". " + edge.getDirectionInstruction(from));
            }
        }

        directions.add("Arrive at " + path.get(path.size() - 1).getName());

        return directions;
    }

    public void setAccessibleOnly(boolean accessibleOnly) {
        this.accessibleOnly = accessibleOnly;
    }

    public boolean isAccessibleOnly() {
        return accessibleOnly;
    }

    // Inner class to hold path results
    public static class PathResult {
        private List<Node> path;
        private double totalDistance;
        private String message;
        private int nodesVisited;
        private long executionTime;

        public PathResult(List<Node> path, double totalDistance, String message, int nodesVisited) {
            this.path = path;
            this.totalDistance = totalDistance;
            this.message = message;
            this.nodesVisited = nodesVisited;
            this.executionTime = 0;
        }

        public PathResult(List<Node> path, double totalDistance, String message, int nodesVisited, long executionTime) {
            this.path = path;
            this.totalDistance = totalDistance;
            this.message = message;
            this.nodesVisited = nodesVisited;
            this.executionTime = executionTime;
        }

        public boolean hasPath() {
            return path != null && !path.isEmpty();
        }

        // Getters
        public List<Node> getPath() {
            return path;
        }

        public double getTotalDistance() {
            return totalDistance;
        }

        public String getMessage() {
            return message;
        }

        public int getNodesVisited() {
            return nodesVisited;
        }

        public long getExecutionTime() {
            return executionTime;
        }

        public List<String> getDirections() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getDirections'");
        }
    }

    public Dijkstra.PathResult findPath(Node start, Node end, boolean accessibleOnly2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPath'");
    }
}