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
            // 1st Floor South
            addNode("Stairs_1S", "1st Floor SOUTH", -1, -6);
            addNode("S101", "1st Floor SOUTH", 5, -4);
            addNode("S102", "1st Floor SOUTH", 6, -5);
            addNode("S103", "1st Floor SOUTH", 6, -6);
            addNode("S104", "1st Floor SOUTH", 5, -8);
            addNode("S105", "1st Floor SOUTH", -5, -8);
            addNode("S106", "1st Floor SOUTH", -6, -6);
            addNode("S107", "1st Floor SOUTH", -6, -4);
            addNode("S108", "1st Floor SOUTH", -5, -3);
            addNode("S109", "1st Floor SOUTH", -4, -3);

            // 1st Floor East
            addNode("Stairs_1E", "1st Floor EAST", 1, -1);
            addNode("E101", "1st Floor EAST", 2, -1);
            addNode("DMST", "1st Floor EAST", 3, -1);
            addNode("CCHQ", "1st Floor EAST", 4, -1);
            addNode("E117", "1st Floor EAST", 5, -1);
            addNode("Medical Clinic", "1st Floor EAST", 1, 1);
            addNode("DMHS", "1st Floor EAST", 2, 1);
            addNode("Dental Clinic", "1st Floor EAST", 3, 1);

            // 1st Floor West
            addNode("Stairs_1W", "1st Floor WEST", -1, 1);
            addNode("AARS", "1st Floor WEST", -2, 1);
            addNode("W115", "1st Floor WEST", -3, 1);
            addNode("W117", "1st Floor WEST", -4, 1);
            addNode("W119", "1st Floor WEST", -5, 1);
            addNode("W108", "1st Floor WEST", -1, -1);
            addNode("W102", "1st Floor WEST", -2, -1);
            addNode("RECORDS 1-5", "1st Floor WEST", -3, -1);

            // 2nd Floor South
            addNode("Stairs_2S", "2nd Floor SOUTH", -1, -6);
            addNode("S201", "2nd Floor SOUTH", 1, -4);
            addNode("S202", "2nd Floor SOUTH", 2, -4);
            addNode("S203", "2nd Floor SOUTH", 3, -4);
            addNode("S204", "2nd Floor SOUTH", 4, -3);
            addNode("S205", "2nd Floor SOUTH", 4, -5);
            addNode("S206", "2nd Floor SOUTH", 4, -7);
            addNode("S207", "2nd Floor SOUTH", 3, -9);
            addNode("S208", "2nd Floor SOUTH", 2, -9);
            addNode("S209", "2nd Floor SOUTH", 1, -9);
            addNode("S210", "2nd Floor SOUTH", -6, -9);
            addNode("S211", "2nd Floor SOUTH", -8, -7);
            addNode("S212", "2nd Floor SOUTH", -8, -5);
            addNode("S213", "2nd Floor SOUTH", 1, -4);
            addNode("S214", "2nd Floor SOUTH", 2, -4);
            addNode("S215", "2nd Floor SOUTH", 3, -4);

            // 2nd Floor East
            addNode("Stairs_2E", "2nd Floor EAST", 1, -1);
            addNode("E201", "2nd Floor EAST", 2, -1);
            addNode("E203", "2nd Floor EAST", 3, -1);
            addNode("E205", "2nd Floor EAST", 4, -1);
            addNode("E207", "2nd Floor EAST", 5, -1);
            addNode("E209", "2nd Floor EAST", 6, -1);
            addNode("E211", "2nd Floor EAST", 7, -1);
            addNode("E213", "2nd Floor EAST", 8, -1);
            addNode("E215", "2nd Floor EAST", 9, -1);
            addNode("E217", "2nd Floor EAST", 10, -1);
            addNode("E219", "2nd Floor EAST", 11, -1);
            addNode("E202", "2nd Floor EAST", 1, 1);
            addNode("E204", "2nd Floor EAST", 2, 1);
            addNode("E206", "2nd Floor EAST", 3, 1);
            addNode("E208", "2nd Floor EAST", 4, 1);
            addNode("E210", "2nd Floor EAST", 5, 1);
            addNode("E212", "2nd Floor EAST", 6, 1);
            addNode("E214", "2nd Floor EAST", 7, 1);
            addNode("E216", "2nd Floor EAST", 8, 1);
            addNode("E218", "2nd Floor EAST", 9, 1);
            addNode("E220", "2nd Floor EAST", 10, 1);

            // 2nd Floor West
            addNode("Stairs_2W", "2nd Floor WEST", -1, 1);
            addNode("W201", "2nd Floor WEST", -2, 1);
            addNode("W203", "2nd Floor WEST", -3, 1);
            addNode("W205", "2nd Floor WEST", -4, 1);
            addNode("W207", "2nd Floor WEST", -5, 1);
            addNode("W209", "2nd Floor WEST", -6, 1);
            addNode("W211", "2nd Floor WEST", -7, 1);
            addNode("W213", "2nd Floor WEST", -8, 1);
            addNode("W215", "2nd Floor WEST", -9, 1);
            addNode("W217", "2nd Floor WEST", -10, 1);
            addNode("W219", "2nd Floor WEST", -11, 1);
            addNode("W202", "2nd Floor WEST", -1, -1);
            addNode("W204", "2nd Floor WEST", -2, -1);
            addNode("W206", "2nd Floor WEST", -3, -1);
            addNode("W208", "2nd Floor WEST", -4, -1);
            addNode("W210", "2nd Floor WEST", -5, -1);
            addNode("W212", "2nd Floor WEST", -6, -1);
            addNode("W214", "2nd Floor WEST", -7, -1);
            addNode("W216", "2nd Floor WEST", -8, -1);
            addNode("W218", "2nd Floor WEST", -9, -1);
            addNode("W220", "2nd Floor WEST", -10, -1);

            // 3rd Floor South
            addNode("Stairs_3S", "3rd Floor SOUTH", -1, -6);
            addNode("S301", "3rd Floor SOUTH", 1, -4);
            addNode("S302", "3rd Floor SOUTH", 2, -4);
            addNode("S303", "3rd Floor SOUTH", 3, -4);
            addNode("S304", "3rd Floor SOUTH", 4, -4);
            addNode("HRDM", "3rd Floor SOUTH", 5, -5);
            addNode("S306", "3rd Floor SOUTH", 5, -6);
            addNode("S307", "3rd Floor SOUTH", 5, -7);
            addNode("S308", "3rd Floor SOUTH", 4, -8);
            addNode("S309", "3rd Floor SOUTH", 2, -8);
            addNode("S310", "3rd Floor SOUTH", 0.5, -8);
            addNode("S311", "3rd Floor SOUTH", -1.5, -8);
            addNode("S312", "3rd Floor SOUTH", -2.5, -8);
            addNode("S313", "3rd Floor SOUTH", -3.5, -8);
            addNode("S314", "3rd Floor SOUTH", -5, -7);
            addNode("S315", "3rd Floor SOUTH", -4, -4);
            addNode("S316", "3rd Floor SOUTH", -3, -4);
            addNode("S317", "3rd Floor SOUTH", -2, -4);
            addNode("S318", "3rd Floor SOUTH", -1, -4);

            // 3rd floor EAST
            addNode("Stairs_3E", "3rd Floor EAST", 1, -1);
            addNode("E301", "3rd Floor EAST", 2, -1);
            addNode("E303", "3rd Floor EAST", 3, -1);
            addNode("E305", "3rd Floor EAST", 4, -1);
            addNode("E307", "3rd Floor EAST", 5, -1);
            addNode("E309", "3rd Floor EAST", 6, -1);
            addNode("E311", "3rd Floor EAST", 7, -1);
            addNode("E313", "3rd Floor EAST", 8, -1);
            addNode("E315", "3rd Floor EAST", 9, -1);
            addNode("E302", "3rd Floor EAST", 1, 1);
            addNode("E304", "3rd Floor EAST", 2, 1);
            addNode("E306", "3rd Floor EAST", 3, 1);
            addNode("E308", "3rd Floor EAST", 4, 1);
            addNode("E310", "3rd Floor EAST", 5, 1);
            addNode("E312", "3rd Floor EAST", 6, 1);
            addNode("E314", "3rd Floor EAST", 7, 1);
            addNode("E316", "3rd Floor EAST", 8, 1);
            addNode("E318", "3rd Floor EAST", 9, 1);

            // 3rd floor WEST
            addNode("Stairs_3W", "3rd Floor WEST", -1, 1);
            addNode("W301", "3rd Floor WEST", -2, 1);
            addNode("W303", "3rd Floor WEST", -3, 1);
            addNode("W305", "3rd Floor WEST", -4, 1);
            addNode("W307", "3rd Floor WEST", -5, 1);
            addNode("W309", "3rd Floor WEST", -6, 1);
            addNode("W311", "3rd Floor WEST", -7, 1);
            addNode("W313", "3rd Floor WEST", -8, 1);
            addNode("W315", "3rd Floor WEST", -9, 1);
            addNode("W317", "3rd Floor WEST", -10, 1);
            addNode("W202", "3rd Floor WEST", -1, -1);
            addNode("W204", "3rd Floor WEST", -2, -1);
            addNode("W206", "3rd Floor WEST", -3, -1);
            addNode("W208", "3rd Floor WEST", -4, -1);
            addNode("W210", "3rd Floor WEST", -5, -1);
            addNode("W212", "3rd Floor WEST", -6, -1);
            addNode("W214", "3rd Floor WEST", -7, -1);
            addNode("W216", "3rd Floor WEST", -8, -1);
            addNode("W218", "3rd Floor WEST", -9, -1);
            addNode("W220", "3rd Floor WEST", -10, -1);

            // 4th Floor South
            addNode("Stairs_4S", "4th Floor SOUTH", -1, -6);
            addNode("S401", "4th Floor SOUTH", 1, -4);
            addNode("S402", "4th Floor SOUTH", 2, -4);
            addNode("S403", "4th Floor SOUTH", 3, -4);
            addNode("S404", "4th Floor SOUTH", 4, -4);
            addNode("S405", "4th Floor SOUTH", 5, -5);
            addNode("S406", "4th Floor SOUTH", 5, -6);
            addNode("S407", "4th Floor SOUTH", 5, -7);
            addNode("S408", "4th Floor SOUTH", 5, -8);
            addNode("S409", "4th Floor SOUTH", 4, -9);
            addNode("S410", "4th Floor SOUTH", 2.5, -9);
            addNode("S411", "4th Floor SOUTH", 1.5, -9);
            addNode("S412", "4th Floor SOUTH", 0, -9);
            addNode("S413", "4th Floor SOUTH", -1, -9);
            addNode("S414", "4th Floor SOUTH", -2, -9);
            addNode("S415", "4th Floor SOUTH", -3, -9);
            addNode("S416", "4th Floor SOUTH", -4, -9);
            addNode("S417", "4th Floor SOUTH", -5, -9);
            addNode("S418", "4th Floor SOUTH", -6, -8);
            addNode("S419", "4th Floor SOUTH", -6, -7);
            addNode("S420", "4th Floor SOUTH", -6, -6);
            addNode("S421", "4th Floor SOUTH", -6, -5);
            addNode("S422", "4th Floor SOUTH", -1, -4);
            addNode("S423", "4th Floor SOUTH", -2, -4);
            addNode("S424", "4th Floor SOUTH", -3, -4);
            addNode("S425", "4th Floor SOUTH", -4, -4);

            // 4th Floor EAST
            addNode("Stairs_4E", "4th Floor EAST", 1, -1);
            addNode("E401", "4th Floor EAST", 2, -1);
            addNode("E403", "4th Floor EAST", 3, -1);
            addNode("E405", "4th Floor EAST", 4, -1);
            addNode("E407", "4th Floor EAST", 5, -1);
            addNode("E409", "4th Floor EAST", 6, -1);
            addNode("E411", "4th Floor EAST", 7, -1);
            addNode("E413", "4th Floor EAST", 8, -1);
            addNode("E415", "4th Floor EAST", 9, -1);
            addNode("E417", "4th Floor EAST", 10, -1);
            addNode("E402", "4th Floor EAST", 1, 1);
            addNode("E404", "4th Floor EAST", 2, 1);
            addNode("E406", "4th Floor EAST", 3, 1);
            addNode("E408", "4th Floor EAST", 4, 1);
            addNode("E410", "4th Floor EAST", 5, 1);
            addNode("E412", "4th Floor EAST", 6, 1);
            addNode("E414", "4th Floor EAST", 7, 1);
            addNode("E416", "4th Floor EAST", 8, 1);
            addNode("E418", "4th Floor EAST", 9, 1);
            addNode("E420", "4th Floor EAST", 10, 1);

            // 4th Floor WEST
            addNode("Stairs_4W", "4th Floor WEST", -1, 1);
            addNode("W401", "4th Floor WEST", -2, 1);
            addNode("W403", "4th Floor WEST", -3, 1);
            addNode("W405", "4th Floor WEST", -4, 1);
            addNode("W407", "4th Floor WEST", -5, 1);
            addNode("W409", "4th Floor WEST", -6, 1);
            addNode("W411", "4th Floor WEST", -7, 1);
            addNode("W413", "4th Floor WEST", -8, 1);
            addNode("W415", "4th Floor WEST", -9, 1);
            addNode("W417", "4th Floor WEST", -10, 1);
            addNode("W402", "4th Floor WEST", -1, -1);
            addNode("W404", "4th Floor WEST", -2, -1);
            addNode("W406", "4th Floor WEST", -3, -1);
            addNode("W408", "4th Floor WEST", -4, -1);
            addNode("W410", "4th Floor WEST", -5, -1);
            addNode("W412", "4th Floor WEST", -6, -1);
            addNode("W414", "4th Floor WEST", -7, -1);
            addNode("W416", "4th Floor WEST", -8, -1);
            addNode("W418", "4th Floor WEST", -9, -1);

            // 5th Floor South
            addNode("Stairs_5S", "5th Floor SOUTH", -1, -6);
            addNode("S501", "5th Floor SOUTH", -1, -4);
            addNode("S502", "5th Floor SOUTH", -2, -4);
            addNode("S503", "5th Floor SOUTH", -3, -4);
            addNode("S504", "5th Floor SOUTH", -6.5, -5);
            addNode("S505", "5th Floor SOUTH", -6.5, -5);
            addNode("S506", "5th Floor SOUTH", 6.5, -5);
            addNode("S507", "5th Floor SOUTH", -5.5, -9);
            addNode("S508", "5th Floor SOUTH", -4.5, -9);
            addNode("S509", "5th Floor SOUTH", -3.5, -9);
            addNode("S510", "5th Floor SOUTH", -2.5, -9);
            addNode("S511", "5th Floor SOUTH", -1.5, -9);
            addNode("S512", "5th Floor SOUTH", 2, -9);
            addNode("S512A", "5th Floor SOUTH", -0.5, -9);
            addNode("S512B", "5th Floor SOUTH", 1, -9);
            addNode("S514", "5th Floor SOUTH", 4, -6);
            addNode("S515", "5th Floor SOUTH", 4, -5);
            addNode("S516", "5th Floor SOUTH", 3.5, -4);
            addNode("S517", "5th Floor SOUTH", 2.5, -4);
            addNode("S518", "5th Floor SOUTH", 1, -4);

            // 5th Floor EAST
            addNode("Stairs_5E", "5th Floor EAST", 1, -1);
            addNode("E501", "5th Floor EAST", 2, -1);
            addNode("E503", "5th Floor EAST", 3, -1);
            addNode("E505", "5th Floor EAST", 4, -1);
            addNode("E507", "5th Floor EAST", 5, -1);
            addNode("E509", "5th Floor EAST", 6, -1);
            addNode("E511", "5th Floor EAST", 7, -1);
            addNode("E513", "5th Floor EAST", 8, -1);
            addNode("E515", "5th Floor EAST", 9, -1);
            addNode("E517", "5th Floor EAST", 10, -1);
            addNode("E502", "5th Floor EAST", 1, 1);
            addNode("E504", "5th Floor EAST", 2, 1);
            addNode("E506", "5th Floor EAST", 3, 1);
            addNode("E508", "5th Floor EAST", 4, 1);
            addNode("E510", "5th Floor EAST", 5, 1);
            addNode("E512", "5th Floor EAST", 6, 1);
            addNode("E514", "5th Floor EAST", 7, 1);
            addNode("E516", "5th Floor EAST", 8, 1);
            addNode("E518", "5th Floor EAST", 9, 1);
            addNode("E520", "5th Floor EAST", 10, 1);

            // 5th Floor WEST
            addNode("Stairs_5W", "5th Floor WEST", -1, 1);
            addNode("W501", "5th Floor WEST", -2, 1);
            addNode("W503", "5th Floor WEST", -3, 1);
            addNode("W505", "5th Floor WEST", -4, 1);
            addNode("W507", "5th Floor WEST", -5, 1);
            addNode("W509", "5th Floor WEST", -6, 1);
            addNode("W511", "5th Floor WEST", -7, 1);
            addNode("W513", "5th Floor WEST", -8, 1);
            addNode("W515", "5th Floor WEST", -9, 1);
            addNode("W517", "5th Floor WEST", -10, 1);
            addNode("W502", "5th Floor WEST", -1, -1);
            addNode("W504", "5th Floor WEST", -2, -1);
            addNode("W506", "5th Floor WEST", -3, -1);
            addNode("W508", "5th Floor WEST", -4, -1);
            addNode("W510", "5th Floor WEST", -5, -1);
            addNode("W512", "5th Floor WEST", -6, -1);
            addNode("W514", "5th Floor WEST", -7, -1);
            addNode("W516", "5th Floor WEST", -8, -1);
            addNode("W518", "5th Floor WEST", -9, -1);
            addNode("W520", "5th Floor WEST", -10, -1);

            // 6th Floor South
            addNode("Stairs_6S", "6th Floor SOUTH", -1, -6);
            addNode("S601", "6th Floor SOUTH", -1, -4);
            addNode("S602", "6th Floor SOUTH", -2, -4);
            addNode("S603", "6th Floor SOUTH", -3, -4);
            addNode("S604", "6th Floor SOUTH", -4, -5);
            addNode("S605", "6th Floor SOUTH", -4, -6);
            addNode("S606", "6th Floor SOUTH", -4, -7);
            addNode("S607", "6th Floor SOUTH", -3.5, -8);
            addNode("S609", "6th Floor SOUTH", 3.5, -8);
            addNode("S610", "6th Floor SOUTH", 4, -6);
            addNode("S612", "6th Floor SOUTH", 4, -5);
            addNode("S613", "6th Floor SOUTH", 3, -4);
            addNode("S614", "6th Floor SOUTH", 1.5, -4);
            addNode("Claro M. Recto Hall", "6th Floor SOUTH", 0, -8);

            // 6th Floor EAST
            addNode("Stairs_6E", "6th Floor EAST", 1, -1);
            addNode("E601", "6th Floor EAST", 2, -1);
            addNode("E603", "6th Floor EAST", 3, -1);
            addNode("E605", "6th Floor EAST", 4, -1);
            addNode("E607", "6th Floor EAST", 5, -1);
            addNode("E609", "6th Floor EAST", 6, -1);
            addNode("E611", "6th Floor EAST", 7, -1);
            addNode("E613", "6th Floor EAST", 8, -1);
            addNode("E615", "6th Floor EAST", 9, -1);
            addNode("E617", "6th Floor EAST", 10, -1);
            addNode("E602", "6th Floor EAST", 1, 1);
            addNode("E604", "6th Floor EAST", 2, 1);
            addNode("E606", "6th Floor EAST", 3, 1);
            addNode("E608", "6th Floor EAST", 4, 1);
            addNode("E610", "6th Floor EAST", 5, 1);
            addNode("E612", "6th Floor EAST", 6, 1);
            addNode("E614", "6th Floor EAST", 7, 1);
            addNode("E616", "6th Floor EAST", 8, 1);
            addNode("E618", "6th Floor EAST", 9, 1);
            addNode("E620", "6th Floor EAST", 10, 1);

            // 6th Floor WEST
            addNode("Stairs_6W", "6th Floor WEST", -1, 1);
            addNode("W601", "6th Floor WEST", -2, 1);
            addNode("W603", "6th Floor WEST", -3, 1);
            addNode("W605", "6th Floor WEST", -4, 1);
            addNode("W607", "6th Floor WEST", -5, 1);
            addNode("W609", "6th Floor WEST", -6, 1);
            addNode("W611", "6th Floor WEST", -7, 1);
            addNode("W613", "6th Floor WEST", -8, 1);
            addNode("W615", "6th Floor WEST", -9, 1);
            addNode("W617", "6th Floor WEST", -10, 1);
            addNode("W602", "6th Floor WEST", -1, -1);
            addNode("W604", "6th Floor WEST", -2, -1);
            addNode("W606", "6th Floor WEST", -3, -1);
            addNode("W608", "6th Floor WEST", -4, -1);
            addNode("W610", "6th Floor WEST", -5, -1);
            addNode("W612", "6th Floor WEST", -6, -1);
            addNode("W614", "6th Floor WEST", -7, -1);
            addNode("W616", "6th Floor WEST", -8, -1);
            addNode("W618", "6th Floor WEST", -9, -1);
            addNode("W620", "6th Floor WEST", -10, -1);

            // Create connections between rooms
            createConnections();
        }

        private void addNode(String name, String floor, double x, double y) {
            nodes.put(name, new Node(name, floor, x, y));
        }

        private void createConnections() {
            // 1st Floor South
            connectAdjacentRooms("1st Floor South", new String[] { "Stairs_1S", "S101", "S102", "S103", "S104", "S105",
                    "S106", "S107", "S108", "S109" });

            // 1st Floor East
            connectAdjacentRooms("1st Floor East", new String[] { "Stairs_1E", "E101", "DMST", "CCHQ", "E117",
                    "Medical Clinic", "DMHS", "Dental Clinic" });

            // 1st Floor West
            connectAdjacentRooms("1st Floor West",
                    new String[] { "Stairs_1W", "AARS", "W115", "W117", "W119", "W108", "W102", "RECORDS 1-5" });

            // 2nd Floor South
            connectAdjacentRooms("2nd Floor South", new String[] { "Stairs_2S", "S201", "S202", "S203", "S204", "S205",
                    "S206", "S207", "S208", "S209", "S210", "S211", "S212", "S213", "S214", "S215" });

            // 2nd Floor East
            connectAdjacentRooms("2nd Floor East",
                    new String[] { "Stairs_2E", "E201", "E203", "E205", "E207", "E209", "E211", "E213", "E215", "E217",
                            "E219", "E202", "E204", "E206", "E208", "E210", "E212", "E214", "E216", "E218", "E220" });

            // 2nd Floor West
            connectAdjacentRooms("2nd Floor West",
                    new String[] { "Stairs_2W", "W201", "W203", "W205", "W207", "W209", "W211", "W213", "W215", "W217",
                            "W219", "W202", "W204", "W206", "W208", "W210", "W212", "W214", "W216", "W218", "W220" });

            // 3rd Floor South
            connectAdjacentRooms("3rd Floor South",
                    new String[] { "Stairs_3S", "S301", "S302", "S303", "S304", "S305", "S306", "S307", "S308", "S309",
                            "S310", "S311", "S312", "S313", "S314", "S315", "S316", "S317", "S318" });

            // 3rd floor EAST
            connectAdjacentRooms("3rd Floor EAST",
                    new String[] { "Stairs_3E", "E301", "E303", "E305", "E307", "E309", "E311", "E313", "E315", "E317",
                            "E319", "E302", "E304", "E306", "E308", "E310", "E312", "E314", "E316", "E318" });

            // 3rd floor WEST
            connectAdjacentRooms("3rd Floor WEST",
                    new String[] { "Stairs_3W", "W301", "W303", "W305", "W307", "W309", "W311", "W313", "W315", "W317",
                            "W319", "W202", "W204", "W206", "W208", "W210", "W212", "W214", "W216", "W218", "W220" });

            // 4th Floor South
            connectAdjacentRooms("4th Floor South",
                    new String[] { "Stairs_4S", "S401", "S402", "S403", "S404", "S405", "S406", "S407", "S408", "S409",
                            "S410", "S411", "S412", "S413", "S414", "S415", "S416", "S417", "S418", "S419", "S420",
                            "S421", "S422", "S423", "S424", "S425" });

            // 4th Floor EAST
            connectAdjacentRooms("4th Floor EAST",
                    new String[] { "Stairs_4E", "E401", "E403", "E405", "E407", "E409", "E411", "E413", "E415", "E417",
                            "E419", "E402", "E404", "E406", "E408", "E410", "E412", "E414", "E416", "E418", "E420" });

            // 4th Floor WEST
            connectAdjacentRooms("4th Floor WEST",
                    new String[] { "Stairs_4W", "W401", "W403", "W405", "W407", "W409", "W411", "W413", "W415", "W417",
                            "W419", "W402", "W404", "W406", "W408", "W410", "W412", "W414", "W416", "W418" });

            // 5th Floor South
            connectAdjacentRooms("5th Floor South",
                    new String[] { "Stairs_5S", "S501", "S502", "S503", "S504", "S505", "S506", "S507", "S508", "S509",
                            "S510", "S511", "S512", "S512A", "S512B", "S514", "S515", "S516", "S517", "S518" });

            // 5th Floor EAST
            connectAdjacentRooms("5th Floor EAST",
                    new String[] { "Stairs_5E", "E501", "E503", "E505", "E507", "E509", "E511", "E513", "E515", "E517",
                            "E519", "E502", "E504", "E506", "E508", "E510", "E512", "E514", "E516", "E518", "E520" });

            // 5th Floor WEST
            connectAdjacentRooms("5th Floor WEST",
                    new String[] { "Stairs_5W", "W501", "W503", "W505", "W507", "W509", "W511", "W513", "W515", "W517",
                            "W519", "W502", "W504", "W506", "W508", "W510", "W512", "W514", "W516", "W518", "W520" });

            // 6th Floor South
            connectAdjacentRooms("6th Floor South", new String[] {
                    "Stairs_6S", "S601", "S602", "S603", "S604", "S605", "S606", "S607", "Claro M. Recto Hall", "S609",
                    "S610", "S612", "S613", "S614"
            });

            // 6th Floor EAST
            connectAdjacentRooms("6th Floor EAST",
                    new String[] { "Stairs_6E", "E601", "E603", "E605", "E607", "E609", "E611", "E613", "E615", "E617",
                            "E619", "E602", "E604", "E606", "E608", "E610", "E612", "E614", "E616", "E618", "E620" });

            // 6th Floor WEST
            connectAdjacentRooms("6th Floor WEST",
                    new String[] { "Stairs_6W", "W601", "W603", "W605", "W607", "W609", "W611", "W613", "W615", "W617",
                            "W619", "W602", "W604", "W606", "W608", "W610", "W612", "W614", "W616", "W618", "W620" });

            // South Rooms
            connectFloors("S101", "S201", 15.0);
            connectFloors("S201", "S301", 15.0);
            connectFloors("S301", "S401", 15.0);
            connectFloors("S401", "S501", 15.0);
            connectFloors("S501", "S601", 15.0);

            // East Rooms
            connectFloors("E101", "E201", 15.0);
            connectFloors("E201", "E301", 15.0);
            connectFloors("E301", "E401", 15.0);
            connectFloors("E401", "E501", 15.0);
            connectFloors("E501", "E601", 15.0);

            // West Rooms
            connectFloors("W101", "W201", 15.0);
            connectFloors("AARS", "W201", 15.0);
            connectFloors("W201", "W301", 15.0);
            connectFloors("W301", "W401", 15.0);
            connectFloors("W401", "W501", 15.0);
            connectFloors("W501", "W601", 15.0);

            // Stairs South
            connectFloors("Stairs_1S", "Stairs_2S", 15.0);
            connectFloors("Stairs_2S", "Stairs_3S", 15.0);
            connectFloors("Stairs_3S", "Stairs_4S", 15.0);
            connectFloors("Stairs_4S", "Stairs_5S", 15.0);
            connectFloors("Stairs_5S", "Stairs_6S", 15.0);

            // Stairs East
            connectFloors("Stairs_1E", "Stairs_2E", 15.0);
            connectFloors("Stairs_2E", "Stairs_3E", 15.0);
            connectFloors("Stairs_3E", "Stairs_4E", 15.0);
            connectFloors("Stairs_4E", "Stairs_5E", 15.0);
            connectFloors("Stairs_5E", "Stairs_6E", 15.0);

            // Stairs West
            connectFloors("Stairs_1W", "Stairs_2W", 15.0);
            connectFloors("Stairs_2W", "Stairs_3W", 15.0);
            connectFloors("Stairs_3W", "Stairs_4W", 15.0);
            connectFloors("Stairs_4W", "Stairs_5W", 15.0);
            connectFloors("Stairs_5W", "Stairs_6W", 15.0);

            // Connect sections on the same floor (South/East/West)
            connectFloors("S101", "E101", 5.0);
            connectFloors("S201", "E201", 5.0);
            connectFloors("S301", "E301", 5.0);
            connectFloors("S401", "E401", 5.0);
            connectFloors("S501", "E501", 5.0);
            connectFloors("S601", "E601", 5.0);

            connectFloors("S101", "AARS", 5.0);
            connectFloors("S201", "W201", 5.0);
            connectFloors("S301", "W301", 5.0);
            connectFloors("S401", "W401", 5.0);
            connectFloors("S501", "W501", 5.0);
            connectFloors("S601", "W601", 5.0);

            connectFloors("E101", "AARS", 5.0);
            connectFloors("E201", "W201", 5.0);
            connectFloors("E301", "W301", 5.0);
            connectFloors("E401", "W401", 5.0);
            connectFloors("E501", "W501", 5.0);
            connectFloors("E601", "W601", 5.0);
            connectFloors("W101", "AARS", 5.0);
            connectFloors("W201", "W301", 5.0);
            connectFloors("W301", "W401", 5.0);
            connectFloors("W401", "W501", 5.0);
            connectFloors("W501", "W601", 5.0);
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