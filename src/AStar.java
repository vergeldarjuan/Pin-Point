import java.util.*;

public class AStar {
    private Graph graph;
    private boolean accessibleOnly;

    public AStar(Graph graph) {
        this.graph = graph;
        this.accessibleOnly = false;
    }

    public AStar(Graph graph, boolean accessibleOnly) {
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

        // Priority queue ordered by f-cost (total cost)
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getFCost));
        Set<Node> closedSet = new HashSet<>();

        // Initialize start node
        start.setGCost(0);
        start.setHCost(calculateHeuristic(start, end));
        openSet.offer(start);

        int nodesVisited = 0;

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (closedSet.contains(current))
                continue;

            closedSet.add(current);
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
                if (closedSet.contains(neighbor))
                    continue;

                Edge edge = current.getEdgeTo(neighbor);
                if (edge == null)
                    continue;

                // Skip non-accessible edges if accessibility mode is on
                if (accessibleOnly && !edge.isAccessible())
                    continue;

                double tentativeGCost = current.getGCost() + edge.getActualWeight();

                // If this path to neighbor is better than any previous one
                if (tentativeGCost < neighbor.getGCost()) {
                    neighbor.setParent(current);
                    neighbor.setGCost(tentativeGCost);
                    neighbor.setHCost(calculateHeuristic(neighbor, end));

                    // Add to open set if not already there
                    if (!openSet.contains(neighbor)) {
                        openSet.offer(neighbor);
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        return new PathResult(null, Double.MAX_VALUE,
                "No path found", nodesVisited, endTime - startTime);
    }

    private double calculateHeuristic(Node from, Node to) {
        // Euclidean distance heuristic
        double dx = from.getX() - to.getX();
        double dy = from.getY() - to.getY();
        double euclideanDistance = Math.sqrt(dx * dx + dy * dy);

        // Add floor difference penalty
        int floorDiff = Math.abs(from.getFloor() - to.getFloor());
        double floorPenalty = floorDiff * 25.0; // Approximate cost of changing floors

        return euclideanDistance + floorPenalty;
    }

    private double calculateManhattanDistance(Node from, Node to) {
        // Alternative heuristic - Manhattan distance
        double dx = Math.abs(from.getX() - to.getX());
        double dy = Math.abs(from.getY() - to.getY());
        int floorDiff = Math.abs(from.getFloor() - to.getFloor());

        return dx + dy + (floorDiff * 25.0);
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

    // Compare performance with Dijkstra
    public ComparisonResult compareWithDijkstra(String startId, String endId) {
        long astarStart = System.currentTimeMillis();
        PathResult astarResult = findShortestPath(startId, endId);
        long astarTime = System.currentTimeMillis() - astarStart;

        Dijkstra dijkstra = new Dijkstra(graph, accessibleOnly);
        long dijkstraStart = System.currentTimeMillis();
        Dijkstra.PathResult dijkstraResult = dijkstra.findShortestPath(startId, endId);
        long dijkstraTime = System.currentTimeMillis() - dijkstraStart;

        return new ComparisonResult(astarResult, dijkstraResult, astarTime, dijkstraTime);
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

    // Inner class for algorithm comparison
    public static class ComparisonResult {
        private PathResult astarResult;
        private Dijkstra.PathResult dijkstraResult;
        private long astarTime;
        private long dijkstraTime;

        public ComparisonResult(PathResult astarResult, Dijkstra.PathResult dijkstraResult,
                long astarTime, long dijkstraTime) {
            this.astarResult = astarResult;
            this.dijkstraResult = dijkstraResult;
            this.astarTime = astarTime;
            this.dijkstraTime = dijkstraTime;
        }

        public String getPerformanceReport() {
            StringBuilder report = new StringBuilder();
            report.append("Algorithm Performance Comparison:\n\n");

            report.append("A* Algorithm:\n");
            report.append("- Execution Time: ").append(astarTime).append("ms\n");
            report.append("- Nodes Visited: ").append(astarResult.getNodesVisited()).append("\n");
            report.append("- Path Distance: ").append(String.format("%.2f", astarResult.getTotalDistance()))
                    .append("\n");
            report.append("- Status: ").append(astarResult.getMessage()).append("\n\n");

            report.append("Dijkstra's Algorithm:\n");
            report.append("- Execution Time: ").append(dijkstraTime).append("ms\n");
            report.append("- Nodes Visited: ").append(dijkstraResult.getNodesVisited()).append("\n");
            report.append("- Path Distance: ").append(String.format("%.2f", dijkstraResult.getTotalDistance()))
                    .append("\n");
            report.append("- Status: ").append(dijkstraResult.getMessage()).append("\n\n");

            if (astarResult.hasPath() && dijkstraResult.hasPath()) {
                double speedup = (double) dijkstraTime / astarTime;
                int nodeReduction = dijkstraResult.getNodesVisited() - astarResult.getNodesVisited();

                report.append("Performance Summary:\n");
                report.append("- A* was ").append(String.format("%.2fx", speedup)).append(" faster\n");
                report.append("- A* visited ").append(nodeReduction).append(" fewer nodes\n");

                if (Math.abs(astarResult.getTotalDistance() - dijkstraResult.getTotalDistance()) < 0.01) {
                    report.append("- Both algorithms found the same optimal path\n");
                } else {
                    report.append("- Different path lengths found (this shouldn't happen with consistent heuristic)\n");
                }
            }

            return report.toString();
        }

        // Getters
        public PathResult getAstarResult() {
            return astarResult;
        }

        public Dijkstra.PathResult getDijkstraResult() {
            return dijkstraResult;
        }

        public long getAstarTime() {
            return astarTime;
        }

        public long getDijkstraTime() {
            return dijkstraTime;
        }
    }

    public PathResult findPath(Node start, Node end, boolean accessibleOnly2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findPath'");
    }
}