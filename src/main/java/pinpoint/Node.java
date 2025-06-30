package pinpoint;

import java.util.*;

public class Node {
    private String id;
    private String name;
    private String description;
    private double x;
    private double y;
    private int floor;
    private String type; // "room", "hallway", "entrance", "stairs", "elevator"
    private List<Edge> adjacentEdges;

    // For pathfinding algorithms
    private double gCost; // Distance from start
    private double hCost; // Heuristic cost to goal
    private double fCost; // Total cost (g + h)
    private Node parent;
    private boolean visited;

    public Node(String id, String name, double x, double y, int floor) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.floor = floor;
        this.adjacentEdges = new ArrayList<>();
        this.type = "room";
        this.description = "";
        resetPathfindingData();
    }

    public Node(String id, String name, String description, double x, double y, int floor, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.x = x;
        this.y = y;
        this.floor = floor;
        this.type = type;
        this.adjacentEdges = new ArrayList<>();
        resetPathfindingData();
    }

    public void resetPathfindingData() {
        this.gCost = Double.MAX_VALUE;
        this.hCost = 0;
        this.fCost = Double.MAX_VALUE;
        this.parent = null;
        this.visited = false;
    }

    public void addEdge(Edge edge) {
        adjacentEdges.add(edge);
    }

    public double getDistanceTo(Node other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Node> getNeighbors() {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : adjacentEdges) {
            neighbors.add(edge.getOtherNode(this));
        }
        return neighbors;
    }

    public Edge getEdgeTo(Node neighbor) {
        for (Edge edge : adjacentEdges) {
            if (edge.getOtherNode(this).equals(neighbor)) {
                return edge;
            }
        }
        return null;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Edge> getAdjacentEdges() {
        return adjacentEdges;
    }

    public double getGCost() {
        return gCost;
    }

    public void setGCost(double gCost) {
        this.gCost = gCost;
        this.fCost = this.gCost + this.hCost;
    }

    public double getHCost() {
        return hCost;
    }

    public void setHCost(double hCost) {
        this.hCost = hCost;
        this.fCost = this.gCost + this.hCost;
    }

    public double getFCost() {
        return fCost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Node node = (Node) obj;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }

    public boolean isAccessible() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method'isAccessible'");
    }
}