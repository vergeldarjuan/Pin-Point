package pinpoint;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Objects;

public class Algos {

    // Node class to represent rooms/locations
    public static class Node {
        public String name;
        public String floor;
        public double x, y; // coordinates for A* heuristic
        public Map<Node, Double> neighbors;

        public Node(String name, String floor, double x, double y) {
            this.name = name;
            this.floor = floor;
            this.x = x;
            this.y = y;
            this.neighbors = new HashMap<>();
        }

        public void addNeighbor(Node neighbor, double distance) {
            neighbors.put(neighbor, distance);
        }

        @Override
        public String toString() {
            return name + " (" + floor + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Node node = (Node) obj;
            return Objects.equals(name, node.name) && Objects.equals(floor, node.floor);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, floor);
        }
    }

    // Graph class to represent the building
    public static class BuildingGraph {
        private Map<String, Node> nodes;

        public BuildingGraph() {
            nodes = new HashMap<>();
            initializeBuilding();
        }

        private void initializeBuilding() {
            // Initialize all rooms with their floor and approximate coordinates
            // 1st Floor South
            addNode("101", "1st Floor South", 0, 0);
            addNode("102", "1st Floor South", 1, 0);
            addNode("103", "1st Floor South", 2, 0);
            addNode("104", "1st Floor South", 3, 0);
            addNode("105", "1st Floor South", 4, 0);
            addNode("106", "1st Floor South", 5, 0);
            addNode("107", "1st Floor South", 6, 0);
            addNode("108", "1st Floor South", 7, 0);
            addNode("109", "1st Floor South", 8, 0);

            // 1st Floor East
            addNode("1E01", "1st Floor East", 0, 1);
            addNode("DMST", "1st Floor East", 1, 1);
            addNode("CCHQ", "1st Floor East", 2, 1);
            addNode("Clinic", "1st Floor East", 3, 1);
            addNode("Facilities", "1st Floor East", 4, 1);

            // 1st Floor West
            addNode("Admission", "1st Floor West", 0, -1);
            addNode("Registration", "1st Floor West", 1, -1);

            // 2nd Floor South
            addNode("202", "2nd Floor South", 0, 10);
            addNode("203", "2nd Floor South", 1, 10);
            addNode("204", "2nd Floor South", 2, 10);
            addNode("205", "2nd Floor South", 3, 10);
            addNode("206", "2nd Floor South", 4, 10);
            addNode("207", "2nd Floor South", 5, 10);
            addNode("208", "2nd Floor South", 6, 10);
            addNode("209", "2nd Floor South", 7, 10);
            addNode("210", "2nd Floor South", 8, 10);
            addNode("211", "2nd Floor South", 9, 10);
            addNode("212", "2nd Floor South", 10, 10);
            addNode("213", "2nd Floor South", 11, 10);
            addNode("214", "2nd Floor South", 12, 10);
            addNode("215", "2nd Floor South", 13, 10);

            // 2nd Floor East
            addNode("201", "2nd Floor East", 0, 11);
            addNode("2E02", "2nd Floor East", 1, 11);
            addNode("2E03", "2nd Floor East", 2, 11);
            addNode("2E04", "2nd Floor East", 3, 11);
            addNode("2E05", "2nd Floor East", 4, 11);
            addNode("2E06", "2nd Floor East", 5, 11);
            addNode("2E07", "2nd Floor East", 6, 11);
            addNode("2E08", "2nd Floor East", 7, 11);

            // 3rd Floor South
            addNode("311", "3rd Floor South", 0, 20);
            addNode("312", "3rd Floor South", 1, 20);
            addNode("313", "3rd Floor South", 2, 20);
            addNode("314", "3rd Floor South", 3, 20);
            addNode("315", "3rd Floor South", 4, 20);
            addNode("316", "3rd Floor South", 5, 20);
            addNode("317", "3rd Floor South", 6, 20);
            addNode("318", "3rd Floor South", 7, 20);

            // 4th Floor South
            addNode("401", "4th Floor South", 0, 30);
            addNode("402", "4th Floor South", 1, 30);
            addNode("403", "4th Floor South", 2, 30);
            addNode("404", "4th Floor South", 3, 30);
            addNode("405", "4th Floor South", 4, 30);
            addNode("406", "4th Floor South", 5, 30);
            addNode("407", "4th Floor South", 6, 30);
            addNode("408", "4th Floor South", 7, 30);
            addNode("409", "4th Floor South", 8, 30);
            addNode("410", "4th Floor South", 9, 30);
            addNode("411", "4th Floor South", 10, 30);
            addNode("412", "4th Floor South", 11, 30);
            addNode("413", "4th Floor South", 12, 30);
            addNode("414", "4th Floor South", 13, 30);
            addNode("415", "4th Floor South", 14, 30);
            addNode("416", "4th Floor South", 15, 30);
            addNode("417", "4th Floor South", 16, 30);
            addNode("418", "4th Floor South", 17, 30);
            addNode("419", "4th Floor South", 18, 30);
            addNode("420", "4th Floor South", 19, 30);
            addNode("421", "4th Floor South", 20, 30);
            addNode("422", "4th Floor South", 21, 30);
            addNode("423", "4th Floor South", 22, 30);
            addNode("424", "4th Floor South", 23, 30);
            addNode("425", "4th Floor South", 24, 30);

            // 5th Floor South
            addNode("505", "5th Floor South", 0, 40);
            addNode("506", "5th Floor South", 1, 40);
            addNode("507", "5th Floor South", 2, 40);
            addNode("512", "5th Floor South", 3, 40);
            addNode("514", "5th Floor South", 4, 40);
            addNode("515", "5th Floor South", 5, 40);
            addNode("516", "5th Floor South", 6, 40);
            addNode("517", "5th Floor South", 7, 40);
            addNode("518", "5th Floor South", 8, 40);

            // 6th Floor South
            addNode("601", "6th Floor South", 0, 50);
            addNode("602", "6th Floor South", 1, 50);
            addNode("603", "6th Floor South", 2, 50);
            addNode("604", "6th Floor South", 3, 50);
            addNode("605", "6th Floor South", 4, 50);
            addNode("607", "6th Floor South", 5, 50);
            addNode("610", "6th Floor South", 6, 50);
            addNode("612", "6th Floor South", 7, 50);
            addNode("613", "6th Floor South", 8, 50);
            addNode("614", "6th Floor South", 9, 50);

            // Create connections between rooms
            createConnections();
        }

        private void addNode(String name, String floor, double x, double y) {
            nodes.put(name, new Node(name, floor, x, y));
        }

        private void createConnections() {
            // Connect adjacent rooms on the same floor
            connectAdjacentRooms("1st Floor South",
                    new String[] { "101", "102", "103", "104", "105", "106", "107", "108", "109" });
            connectAdjacentRooms("1st Floor East", new String[] { "1E01", "DMST", "CCHQ", "Clinic", "Facilities" });
            connectAdjacentRooms("1st Floor West", new String[] { "Admission", "Registration" });

            connectAdjacentRooms("2nd Floor South", new String[] { "202", "203", "204", "205", "206", "207", "208",
                    "209", "210", "211", "212", "213", "214", "215" });
            connectAdjacentRooms("2nd Floor East",
                    new String[] { "201", "2E02", "2E03", "2E04", "2E05", "2E06", "2E07", "2E08" });

            connectAdjacentRooms("3rd Floor South",
                    new String[] { "311", "312", "313", "314", "315", "316", "317", "318" });
            connectAdjacentRooms("4th Floor South",
                    new String[] { "401", "402", "403", "404", "405", "406", "407", "408", "409", "410", "411", "412",
                            "413", "414", "415", "416", "417", "418", "419", "420", "421", "422", "423", "424",
                            "425" });
            connectAdjacentRooms("5th Floor South",
                    new String[] { "505", "506", "507", "512", "514", "515", "516", "517", "518" });
            connectAdjacentRooms("6th Floor South",
                    new String[] { "601", "602", "603", "604", "605", "607", "610", "612", "613", "614" });

            // Connect floors via stairs/elevators (simplified connections)
            connectFloors("101", "202", 15.0); // Stairs from 1st to 2nd
            connectFloors("202", "311", 15.0); // Stairs from 2nd to 3rd
            connectFloors("311", "401", 15.0); // Stairs from 3rd to 4th
            connectFloors("401", "505", 15.0); // Stairs from 4th to 5th
            connectFloors("505", "601", 15.0); // Stairs from 5th to 6th

            // Connect different sections on the same floor
            connectFloors("101", "1E01", 5.0); // 1st floor south to east
            connectFloors("101", "Admission", 5.0); // 1st floor south to west
            connectFloors("202", "201", 5.0); // 2nd floor south to east
        }

        private void connectAdjacentRooms(String floor, String[] roomNames) {
            for (int i = 0; i < roomNames.length - 1; i++) {
                Node current = nodes.get(roomNames[i]);
                Node next = nodes.get(roomNames[i + 1]);
                if (current != null && next != null) {
                    current.addNeighbor(next, 1.0);
                    next.addNeighbor(current, 1.0);
                }
            }
        }

        private void connectFloors(String room1, String room2, double distance) {
            Node node1 = nodes.get(room1);
            Node node2 = nodes.get(room2);
            if (node1 != null && node2 != null) {
                node1.addNeighbor(node2, distance);
                node2.addNeighbor(node1, distance);
            }
        }

        public Node getNode(String name) {
            return nodes.get(name);
        }

        public Set<String> getAllRoomNames() {
            return nodes.keySet();
        }
    }

    // A* Algorithm Implementation
    public static class AStar {
        private static class AStarNode implements Comparable<AStarNode> {
            Node node;
            double gCost; // Distance from start
            double hCost; // Heuristic distance to goal
            double fCost; // Total cost
            AStarNode parent;

            public AStarNode(Node node, double gCost, double hCost, AStarNode parent) {
                this.node = node;
                this.gCost = gCost;
                this.hCost = hCost;
                this.fCost = gCost + hCost;
                this.parent = parent;
            }

            @Override
            public int compareTo(AStarNode other) {
                return Double.compare(this.fCost, other.fCost);
            }
        }

        public static List<Node> findPath(Node start, Node goal) {
            PriorityQueue<AStarNode> openSet = new PriorityQueue<>();
            Set<Node> closedSet = new HashSet<>();
            Map<Node, Double> bestGCost = new HashMap<>();

            openSet.offer(new AStarNode(start, 0, heuristic(start, goal), null));
            bestGCost.put(start, 0.0);

            while (!openSet.isEmpty()) {
                AStarNode current = openSet.poll();

                if (current.node.equals(goal)) {
                    return reconstructPath(current);
                }

                closedSet.add(current.node);

                for (Map.Entry<Node, Double> neighborEntry : current.node.neighbors.entrySet()) {
                    Node neighbor = neighborEntry.getKey();
                    double edgeWeight = neighborEntry.getValue();

                    if (closedSet.contains(neighbor)) {
                        continue;
                    }

                    double tentativeGCost = current.gCost + edgeWeight;

                    if (!bestGCost.containsKey(neighbor) || tentativeGCost < bestGCost.get(neighbor)) {
                        bestGCost.put(neighbor, tentativeGCost);
                        double hCost = heuristic(neighbor, goal);
                        openSet.offer(new AStarNode(neighbor, tentativeGCost, hCost, current));
                    }
                }
            }

            return new ArrayList<>(); // No path found
        }

        private static double heuristic(Node a, Node b) {
            // Manhattan distance + floor difference penalty
            double floorDiff = Math.abs(getFloorNumber(a.floor) - getFloorNumber(b.floor)) * 10;
            return Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + floorDiff;
        }

        private static int getFloorNumber(String floor) {
            if (floor.contains("1st"))
                return 1;
            if (floor.contains("2nd"))
                return 2;
            if (floor.contains("3rd"))
                return 3;
            if (floor.contains("4th"))
                return 4;
            if (floor.contains("5th"))
                return 5;
            if (floor.contains("6th"))
                return 6;
            return 1;
        }

        private static List<Node> reconstructPath(AStarNode goalNode) {
            List<Node> path = new ArrayList<>();
            AStarNode current = goalNode;

            while (current != null) {
                path.add(0, current.node);
                current = current.parent;
            }

            return path;
        }
    }

    // Dijkstra's Algorithm Implementation
    public static class Dijkstra {
        private static class DijkstraNode implements Comparable<DijkstraNode> {
            Node node;
            double distance;
            DijkstraNode parent;

            public DijkstraNode(Node node, double distance, DijkstraNode parent) {
                this.node = node;
                this.distance = distance;
                this.parent = parent;
            }

            @Override
            public int compareTo(DijkstraNode other) {
                return Double.compare(this.distance, other.distance);
            }
        }

        public static List<Node> findPath(Node start, Node goal) {
            PriorityQueue<DijkstraNode> queue = new PriorityQueue<>();
            Map<Node, Double> distances = new HashMap<>();
            Map<Node, DijkstraNode> nodeMap = new HashMap<>();
            Set<Node> visited = new HashSet<>();

            DijkstraNode startNode = new DijkstraNode(start, 0, null);
            queue.offer(startNode);
            distances.put(start, 0.0);
            nodeMap.put(start, startNode);

            while (!queue.isEmpty()) {
                DijkstraNode current = queue.poll();

                if (visited.contains(current.node)) {
                    continue;
                }

                visited.add(current.node);

                if (current.node.equals(goal)) {
                    return reconstructPath(current);
                }

                for (Map.Entry<Node, Double> neighborEntry : current.node.neighbors.entrySet()) {
                    Node neighbor = neighborEntry.getKey();
                    double edgeWeight = neighborEntry.getValue();

                    if (visited.contains(neighbor)) {
                        continue;
                    }

                    double newDistance = current.distance + edgeWeight;

                    if (!distances.containsKey(neighbor) || newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        DijkstraNode neighborNode = new DijkstraNode(neighbor, newDistance, current);
                        nodeMap.put(neighbor, neighborNode);
                        queue.offer(neighborNode);
                    }
                }
            }

            return new ArrayList<>(); // No path found
        }

        private static List<Node> reconstructPath(DijkstraNode goalNode) {
            List<Node> path = new ArrayList<>();
            DijkstraNode current = goalNode;

            while (current != null) {
                path.add(0, current.node);
                current = current.parent;
            }

            return path;
        }
    }

    // Utility class for pathfinding results
    public static class PathResult {
        private List<Node> path;
        private String algorithm;
        private double totalDistance;
        private long executionTime;

        public PathResult(List<Node> path, String algorithm, double totalDistance, long executionTime) {
            this.path = path;
            this.algorithm = algorithm;
            this.totalDistance = totalDistance;
            this.executionTime = executionTime;
        }

        public List<Node> getPath() {
            return path;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public double getTotalDistance() {
            return totalDistance;
        }

        public long getExecutionTime() {
            return executionTime;
        }

        public String getPathDescription() {
            if (path.isEmpty()) {
                return "No path found between the specified locations.";
            }

            StringBuilder description = new StringBuilder();
            description.append("Path found using ").append(algorithm).append(" algorithm:\n");
            description.append("Total distance: ").append(String.format("%.2f", totalDistance)).append(" units\n");
            description.append("Execution time: ").append(executionTime).append(" ms\n\n");
            description.append("Route:\n");

            for (int i = 0; i < path.size(); i++) {
                Node node = path.get(i);
                description.append((i + 1)).append(". ").append(node.name).append(" (").append(node.floor).append(")");
                if (i < path.size() - 1) {
                    description.append(" → ");
                }
                description.append("\n");
            }

            return description.toString();
        }
    }

    // Main pathfinding method
    public static PathResult findOptimalPath(String startRoom, String goalRoom, String algorithm) {
        BuildingGraph graph = new BuildingGraph();
        Node start = graph.getNode(startRoom);
        Node goal = graph.getNode(goalRoom);

        if (start == null || goal == null) {
            return new PathResult(new ArrayList<>(), algorithm, 0, 0);
        }

        long startTime = System.currentTimeMillis();
        List<Node> path;

        switch (algorithm) {
            case "A*":
                path = AStar.findPath(start, goal);
                break;
            case "Dijkstra":
                path = Dijkstra.findPath(start, goal);
                break;
            default:
                // Unknown algorithm, return empty result
                return new PathResult(new ArrayList<>(), algorithm, 0, 0);
        }

        long endTime = System.currentTimeMillis();
        double totalDistance = calculateTotalDistance(path);

        return new PathResult(path, algorithm, totalDistance, endTime - startTime);
    }

    private static double calculateTotalDistance(List<Node> path) {
        double total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);
            if (current.neighbors.containsKey(next)) {
                total += current.neighbors.get(next);
            }
        }
        return total;
    }
}