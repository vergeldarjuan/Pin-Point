public class Edge {
    private Node node1;
    private Node node2;
    private double weight;
    private String type; // "hallway", "stairs", "elevator", "outdoor"
    private boolean isAccessible; // For accessibility features
    private String description;

    public Edge(Node node1, Node node2, double weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
        this.type = "hallway";
        this.isAccessible = true;
        this.description = "";

        // Add this edge to both nodes
        node1.addEdge(this);
        node2.addEdge(this);
    }

    public Edge(Node node1, Node node2, double weight, String type, boolean isAccessible) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
        this.type = type;
        this.isAccessible = isAccessible;
        this.description = "";

        // Add this edge to both nodes
        node1.addEdge(this);
        node2.addEdge(this);
    }

    public Edge(Node node1, Node node2, double weight, String type, boolean isAccessible, String description) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
        this.type = type;
        this.isAccessible = isAccessible;
        this.description = description;

        // Add this edge to both nodes
        node1.addEdge(this);
        node2.addEdge(this);
    }

    public Node getOtherNode(Node node) {
        if (node.equals(node1)) {
            return node2;
        } else if (node.equals(node2)) {
            return node1;
        } else {
            throw new IllegalArgumentException("Node is not part of this edge");
        }
    }

    public boolean containsNode(Node node) {
        return node.equals(node1) || node.equals(node2);
    }

    public double getActualWeight() {
        // You can modify this to add penalties for stairs, etc.
        double penalty = 1.0;

        switch (type) {
            case "stairs":
                penalty = 1.5; // Stairs take more effort
                break;
            case "elevator":
                penalty = 2.0; // Waiting time for elevator
                break;
            case "outdoor":
                penalty = 1.2; // Outdoor paths might be longer
                break;
            default:
                penalty = 1.0;
        }

        return weight * penalty;
    }

    public String getDirectionInstruction(Node fromNode) {
        Node toNode = getOtherNode(fromNode);
        String instruction = "";

        switch (type) {
            case "stairs":
                if (toNode.getFloor() > fromNode.getFloor()) {
                    instruction = "Go up the stairs to floor " + toNode.getFloor();
                } else {
                    instruction = "Go down the stairs to floor " + toNode.getFloor();
                }
                break;
            case "elevator":
                if (toNode.getFloor() != fromNode.getFloor()) {
                    instruction = "Take elevator to floor " + toNode.getFloor();
                } else {
                    instruction = "Continue through elevator area";
                }
                break;
            default:
                instruction = "Walk to " + toNode.getName();
        }

        if (!description.isEmpty()) {
            instruction += " (" + description + ")";
        }

        return instruction;
    }

    // Getters and Setters
    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return node1.getName() + " -> " + node2.getName() + " (weight: " + weight + ")";
    }
}