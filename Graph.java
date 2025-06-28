import java.util.*;

public class Graph {
    private Map<String, Node> nodes;
    private List<Edge> edges;
    private String buildingName;

    public Graph(String buildingName) {
        this.buildingName = buildingName;
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
        initializePUPMainBuilding();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public Node getNode(String id) {
        return nodes.get(id);
    }

    public List<Node> getAllNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Edge> getAllEdges() {
        return new ArrayList<>(edges);
    }

    public List<Node> getNodesByFloor(int floor) {
        List<Node> floorNodes = new ArrayList<>();
        for (Node node : nodes.values()) {
            if (node.getFloor() == floor) {
                floorNodes.add(node);
            }
        }
        return floorNodes;
    }

    public List<Node> searchNodes(String query) {
        List<Node> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Node node : nodes.values()) {
            if (node.getName().toLowerCase().contains(lowerQuery) ||
                    node.getId().toLowerCase().contains(lowerQuery) ||
                    node.getDescription().toLowerCase().contains(lowerQuery)) {
                results.add(node);
            }
        }

        return results;
    }

    public void resetAllNodes() {
        for (Node node : nodes.values()) {
            node.resetPathfindingData();
        }
    }

    public int getMaxFloor() {
        int maxFloor = 0;
        for (Node node : nodes.values()) {
            maxFloor = Math.max(maxFloor, node.getFloor());
        }
        return maxFloor;
    }

    public int getMinFloor() {
        int minFloor = Integer.MAX_VALUE;
        for (Node node : nodes.values()) {
            minFloor = Math.min(minFloor, node.getFloor());
        }
        return minFloor == Integer.MAX_VALUE ? 0 : minFloor;
    }

    private void initializePUPMainBuilding() {
        // Ground Floor (Floor 0)
        addNode(new Node("ENTRANCE_MAIN", "Main Entrance", "Primary entrance to the building", 100, 300, 0,
                "entrance"));
        addNode(new Node("LOBBY", "Main Lobby", "Central lobby area", 200, 300, 0, "hallway"));
        addNode(new Node("REGISTRAR", "Registrar's Office", "Student records and registration", 150, 200, 0, "room"));
        addNode(new Node("CASHIER", "Cashier's Office", "Payment and financial services", 250, 200, 0, "room"));
        addNode(new Node("LIBRARY_GF", "Library Ground Floor", "Library entrance and information desk", 300, 300, 0,
                "room"));
        addNode(new Node("CAFETERIA", "Cafeteria", "Student dining area", 100, 400, 0, "room"));
        addNode(new Node("STAIRS_MAIN_GF", "Main Staircase (GF)", "Main staircase ground floor", 400, 300, 0,
                "stairs"));
        addNode(new Node("ELEVATOR_GF", "Elevator (GF)", "Elevator ground floor", 420, 320, 0, "elevator"));

        // Second Floor (Floor 1)
        addNode(new Node("STAIRS_MAIN_2F", "Main Staircase (2F)", "Main staircase second floor", 400, 300, 1,
                "stairs"));
        addNode(new Node("ELEVATOR_2F", "Elevator (2F)", "Elevator second floor", 420, 320, 1, "elevator"));
        addNode(new Node("HALLWAY_2F_MAIN", "Second Floor Main Hallway", "Main corridor on second floor", 300, 300, 1,
                "hallway"));
        addNode(new Node("ROOM_201", "Room 201", "Classroom", 200, 200, 1, "room"));
        addNode(new Node("ROOM_202", "Room 202", "Classroom", 250, 200, 1, "room"));
        addNode(new Node("ROOM_203", "Room 203", "Computer Laboratory", 300, 200, 1, "room"));
        addNode(new Node("FACULTY_OFFICE", "Faculty Office", "Faculty and staff office", 350, 200, 1, "room"));
        addNode(new Node("LIBRARY_2F", "Library Second Floor", "Library reading area and collections", 300, 400, 1,
                "room"));

        // Third Floor (Floor 2)
        addNode(new Node("STAIRS_MAIN_3F", "Main Staircase (3F)", "Main staircase third floor", 400, 300, 2, "stairs"));
        addNode(new Node("ELEVATOR_3F", "Elevator (3F)", "Elevator third floor", 420, 320, 2, "elevator"));
        addNode(new Node("HALLWAY_3F_MAIN", "Third Floor Main Hallway", "Main corridor on third floor", 300, 300, 2,
                "hallway"));
        addNode(new Node("ROOM_301", "Room 301", "Lecture Hall", 200, 200, 2, "room"));
        addNode(new Node("ROOM_302", "Room 302", "Conference Room", 250, 200, 2, "room"));
        addNode(new Node("DEAN_OFFICE", "Dean's Office", "Dean's administrative office", 350, 200, 2, "room"));
        addNode(new Node("ADMIN_OFFICE", "Administrative Office", "General administrative services", 300, 400, 2,
                "room"));

        // Ground Floor Connections
        addEdge(new Edge(getNode("ENTRANCE_MAIN"), getNode("LOBBY"), 15.0, "hallway", true));
        addEdge(new Edge(getNode("LOBBY"), getNode("REGISTRAR"), 12.0, "hallway", true));
        addEdge(new Edge(getNode("LOBBY"), getNode("CASHIER"), 8.0, "hallway", true));
        addEdge(new Edge(getNode("LOBBY"), getNode("LIBRARY_GF"), 10.0, "hallway", true));
        addEdge(new Edge(getNode("LOBBY"), getNode("CAFETERIA"), 20.0, "hallway", true));
        addEdge(new Edge(getNode("LOBBY"), getNode("STAIRS_MAIN_GF"), 18.0, "hallway", true));
        addEdge(new Edge(getNode("LOBBY"), getNode("ELEVATOR_GF"), 20.0, "hallway", true));
        addEdge(new Edge(getNode("REGISTRAR"), getNode("CASHIER"), 10.0, "hallway", true));

        // Vertical Connections (Stairs and Elevator)
        addEdge(new Edge(getNode("STAIRS_MAIN_GF"), getNode("STAIRS_MAIN_2F"), 25.0, "stairs", true));
        addEdge(new Edge(getNode("STAIRS_MAIN_2F"), getNode("STAIRS_MAIN_3F"), 25.0, "stairs", true));
        addEdge(new Edge(getNode("ELEVATOR_GF"), getNode("ELEVATOR_2F"), 30.0, "elevator", true));
        addEdge(new Edge(getNode("ELEVATOR_2F"), getNode("ELEVATOR_3F"), 30.0, "elevator", true));

        // Second Floor Connections
        addEdge(new Edge(getNode("STAIRS_MAIN_2F"), getNode("HALLWAY_2F_MAIN"), 8.0, "hallway", true));
        addEdge(new Edge(getNode("ELEVATOR_2F"), getNode("HALLWAY_2F_MAIN"), 5.0, "hallway", true));
        addEdge(new Edge(getNode("HALLWAY_2F_MAIN"), getNode("ROOM_201"), 12.0, "hallway", true));
        addEdge(new Edge(getNode("HALLWAY_2F_MAIN"), getNode("ROOM_202"), 8.0, "hallway", true));
        addEdge(new Edge(getNode("HALLWAY_2F_MAIN"), getNode("ROOM_203"), 6.0, "hallway", true));
        addEdge(new Edge(getNode("HALLWAY_2F_MAIN"), getNode("FACULTY_OFFICE"), 10.0, "hallway", true));
        addEdge(new Edge(getNode("HALLWAY_2F_MAIN"), getNode("LIBRARY_2F"), 15.0, "hallway", true));
        addEdge(new Edge(getNode("ROOM_201"), getNode("ROOM_202"), 5.0, "hallway", true));
        addEdge(new Edge(getNode("ROOM_202"), getNode("ROOM_203"), 5.0, "hallway", true));

        // Third Floor Connections
        addEdge(new Edge(getNode("STAIRS_MAIN_3F"), getNode("HALLWAY_3F_MAIN"), 8.0, "hallway", true));
        addEdge(new Edge(getNode("ELEVATOR_3F"), getNode("HALLWAY_3F_MAIN"), 5.0, "hallway", true));
        addEdge(new Edge(getNode("HALLWAY_3F_MAIN"), getNode("ROOM_301"), 12.0, "hallway", true));
        addEdge(new Edge(getNode("HALLWAY_3F_MAIN"), getNode("ROOM_302"), 8.0, "hallway", true));
        addEdge(new Edge(getNode("HALLWAY_3F_MAIN"), getNode("DEAN_OFFICE"), 10.0, "hallway", true));
        addEdge(new Edge(getNode("HALLWAY_3F_MAIN"), getNode("ADMIN_OFFICE"), 15.0, "hallway", true));
        addEdge(new Edge(getNode("ROOM_301"), getNode("ROOM_302"), 5.0, "hallway", true));
    }

    // Getters
    public String getBuildingName() {
        return buildingName;
    }

    public Map<String, Node> getNodesMap() {
        return nodes;
    }
}