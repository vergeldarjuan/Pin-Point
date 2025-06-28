import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.ArrayList;

public class MapPanel extends JPanel {
    private Graph graph;
    private int currentFloor;
    private List<Node> currentPath;
    private Node selectedStartNode;
    private Node selectedEndNode;
    private Node hoveredNode;

    // Zoom and Pan
    private double zoomFactor = 1.0;
    private double panX = 0;
    private double panY = 0;
    private Point lastPanPoint;
    private boolean isDragging = false;

    // Colors
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color NODE_COLOR = new Color(70, 130, 180);
    private static final Color EDGE_COLOR = new Color(180, 180, 180);
    private static final Color PATH_COLOR = new Color(255, 69, 0);
    private static final Color START_NODE_COLOR = new Color(34, 139, 34);
    private static final Color END_NODE_COLOR = new Color(220, 20, 60);
    private static final Color HOVER_COLOR = new Color(255, 215, 0);
    private static final Color TEXT_COLOR = new Color(50, 50, 50);

    // Node types colors
    private static final Color ROOM_COLOR = new Color(70, 130, 180);
    private static final Color ENTRANCE_COLOR = new Color(34, 139, 34);
    private static final Color STAIRS_COLOR = new Color(160, 82, 45);
    private static final Color ELEVATOR_COLOR = new Color(75, 0, 130);
    private static final Color HALLWAY_COLOR = new Color(128, 128, 128);

    public MapPanel(Graph graph) {
        this.graph = graph;
        this.currentFloor = 0;
        this.currentPath = new ArrayList<>();

        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(800, 600));

        setupMouseListeners();

        // Enable mouse wheel for zooming
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                handleMouseWheel(e);
            }
        });
    }

    private void setupMouseListeners() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastPanPoint = e.getPoint();
                isDragging = true;

                // Check if clicking on a node
                Node clickedNode = getNodeAtPoint(e.getPoint());
                if (clickedNode != null && e.getButton() == MouseEvent.BUTTON1) {
                    handleNodeClick(clickedNode, e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
                lastPanPoint = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging && lastPanPoint != null) {
                    int dx = e.getX() - lastPanPoint.x;
                    int dy = e.getY() - lastPanPoint.y;

                    panX += dx / zoomFactor;
                    panY += dy / zoomFactor;

                    lastPanPoint = e.getPoint();
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Node newHoveredNode = getNodeAtPoint(e.getPoint());
                if (newHoveredNode != hoveredNode) {
                    hoveredNode = newHoveredNode;
                    repaint();

                    // Update tooltip
                    if (hoveredNode != null) {
                        setToolTipText(hoveredNode.getName() + " - " + hoveredNode.getDescription());
                    } else {
                        setToolTipText(null);
                    }
                }
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    private void handleMouseWheel(MouseWheelEvent e) {
        double oldZoom = zoomFactor;

        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1; // Zoom in
        } else {
            zoomFactor /= 1.1; // Zoom out
        }

        // Limit zoom levels
        zoomFactor = Math.max(0.1, Math.min(5.0, zoomFactor));

        // Adjust pan to zoom towards mouse position
        Point2D mousePoint = e.getPoint();
        panX += (mousePoint.getX() / oldZoom - mousePoint.getX() / zoomFactor);
        panY += (mousePoint.getY() / oldZoom - mousePoint.getY() / zoomFactor);

        repaint();
    }

    private void handleNodeClick(Node node, MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.isControlDown()) {
                // Ctrl+Click to set end node
                selectedEndNode = node;
            } else {
                // Regular click to set start node
                selectedStartNode = node;
            }

            // Notify parent component about selection
            firePropertyChange("nodeSelected", null, node);
            repaint();
        }
    }

    private Node getNodeAtPoint(Point screenPoint) {
        try {
            AffineTransform transform = getTransform();
            Point2D worldPoint = transform.inverseTransform(screenPoint, null);

            for (Node node : graph.getNodesByFloor(currentFloor)) {
                double distance = Math.sqrt(
                        Math.pow(worldPoint.getX() - node.getX(), 2) +
                                Math.pow(worldPoint.getY() - node.getY(), 2));

                if (distance <= 15) { // Node radius
                    return node;
                }
            }
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private AffineTransform getTransform() {
        AffineTransform transform = new AffineTransform();
        transform.scale(zoomFactor, zoomFactor);
        transform.translate(panX, panY);
        return transform;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Apply transformation
        g2d.setTransform(getTransform());

        // Draw floor info
        drawFloorInfo(g2d);

        // Draw edges first (so they appear behind nodes)
        drawEdges(g2d);

        // Draw path if exists
        if (currentPath != null && !currentPath.isEmpty()) {
            drawPath(g2d);
        }

        // Draw nodes
        drawNodes(g2d);

        // Draw zoom and pan info
        drawInfo(g2d);

        g2d.dispose();
    }

    private void drawFloorInfo(Graphics2D g2d) {
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));

        // Reset transform for UI elements
        AffineTransform oldTransform = g2d.getTransform();
        g2d.setTransform(new AffineTransform());

        String floorText = "Floor: " + (currentFloor == 0 ? "Ground" : currentFloor);
        g2d.drawString(floorText, 10, 25);

        // Instructions
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Left click: Select start | Ctrl+Left click: Select end", 10, getHeight() - 40);
        g2d.drawString("Mouse wheel: Zoom | Drag: Pan", 10, getHeight() - 25);
        g2d.drawString("Selected: Start=" + (selectedStartNode != null ? selectedStartNode.getName() : "None") +
                ", End=" + (selectedEndNode != null ? selectedEndNode.getName() : "None"), 10, getHeight() - 10);

        g2d.setTransform(oldTransform);
    }

    private void drawEdges(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2.0f));

        for (Edge edge : graph.getAllEdges()) {
            Node node1 = edge.getNode1();
            Node node2 = edge.getNode2();

            // Only draw edges where both nodes are on current floor
            if (node1.getFloor() == currentFloor && node2.getFloor() == currentFloor) {
                g2d.setColor(getEdgeColor(edge));
                g2d.drawLine((int) node1.getX(), (int) node1.getY(),
                        (int) node2.getX(), (int) node2.getY());
            }
        }
    }

    private void drawPath(Graphics2D g2d) {
        if (currentPath.size() < 2)
            return;

        ((Graphics) g2d).setColor(PATH_COLOR);
        Graphics2D list = null;
        list.setStroke(new BasicStroke(4.0f));

        for (int i = 0; i < currentPath.size() - 1; i++) {
            Node current = currentPath.get(i);
            Node next = currentPath.get(i + 1);

            // Only draw path segments on current floor
            if (current.getFloor() == currentFloor && next.getFloor() == currentFloor) {
                ((Graphics) g2d).drawLine((int) current.getX(), (int) current.getY(),
                        (int) next.getX(), (int) next.getY());

                // Draw arrow to show direction
                drawArrow(list, current, next);
            }
        }
    }

    private void drawArrow(Graphics2D g2d, Node from, Node to) {
        double dx = to.getX() - from.getX();
        double dy = to.getY() - from.getY();
        double angle = Math.atan2(dy, dx);

        // Arrow position (midpoint)
        int arrowX = (int) ((from.getX() + to.getX()) / 2);
        int arrowY = (int) ((from.getY() + to.getY()) / 2);

        // Arrow size
        int arrowLength = 10;
        double arrowAngle = Math.PI / 6;

        int x1 = (int) (arrowX - arrowLength * Math.cos(angle - arrowAngle));
        int y1 = (int) (arrowY - arrowLength * Math.sin(angle - arrowAngle));
        int x2 = (int) (arrowX - arrowLength * Math.cos(angle + arrowAngle));
        int y2 = (int) (arrowY - arrowLength * Math.sin(angle + arrowAngle));

        g2d.drawLine(arrowX, arrowY, x1, y1);
        g2d.drawLine(arrowX, arrowY, x2, y2);
    }

    private void drawNodes(Graphics2D g2d) {
        for (Node node : graph.getNodesByFloor(currentFloor)) {
            Color nodeColor = getNodeColor(node);

            // Highlight special nodes
            if (node.equals(selectedStartNode)) {
                nodeColor = START_NODE_COLOR;
            } else if (node.equals(selectedEndNode)) {
                nodeColor = END_NODE_COLOR;
            } else if (node.equals(hoveredNode)) {
                nodeColor = HOVER_COLOR;
            }

            // Draw node
            int nodeSize = 15;
            g2d.setColor(nodeColor);
            g2d.fillOval((int) node.getX() - nodeSize / 2, (int) node.getY() - nodeSize / 2,
                    nodeSize, nodeSize);

            // Draw border
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.drawOval((int) node.getX() - nodeSize / 2, (int) node.getY() - nodeSize / 2,
                    nodeSize, nodeSize);

            // Draw node label
            g2d.setColor(TEXT_COLOR);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            FontMetrics fm = g2d.getFontMetrics();
            String label = node.getName();
            int labelWidth = fm.stringWidth(label);
            g2d.drawString(label, (int) node.getX() - labelWidth / 2,
                    (int) node.getY() - nodeSize / 2 - 5);
        }
    }

    private void drawInfo(Graphics2D g2d) {
        // Reset transform for UI elements
        AffineTransform oldTransform = g2d.getTransform();
        g2d.setTransform(new AffineTransform());

        // Draw zoom level
        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Zoom: " + String.format("%.1f%%", zoomFactor * 100), getWidth() - 100, 20);

        g2d.setTransform(oldTransform);
    }

    private Color getNodeColor(Node node) {
        switch (node.getType()) {
            case "entrance":
                return ENTRANCE_COLOR;
            case "stairs":
                return STAIRS_COLOR;
            case "elevator":
                return ELEVATOR_COLOR;
            case "hallway":
                return HALLWAY_COLOR;
            default:
                return ROOM_COLOR;
        }
    }

    private Color getEdgeColor(Edge edge) {
        switch (edge.getType()) {
            case "stairs":
                return STAIRS_COLOR;
            case "elevator":
                return ELEVATOR_COLOR;
            default:
                return EDGE_COLOR;
        }
    }

    // Public methods for controlling the map
    public void setCurrentFloor(int floor) {
        this.currentFloor = floor;
        repaint();
    }

    public void setCurrentPath(List<Node> path) {
        this.currentPath = path;
        repaint();
    }

    public void clearPath() {
        this.currentPath.clear();
        repaint();
    }

    public void clearSelection() {
        selectedStartNode = null;
        selectedEndNode = null;
        repaint();
    }

    public void centerOnNode(Node node) {
        if (node != null) {
            panX = getWidth() / (2 * zoomFactor) - node.getX();
            panY = getHeight() / (2 * zoomFactor) - node.getY();
            repaint();
        }
    }

    public void resetView() {
        zoomFactor = 1.0;
        panX = 0;
        panY = 0;
        repaint();
    }

    public void fitToContent() {
        List<Node> floorNodes = graph.getNodesByFloor(currentFloor);
        if (floorNodes.isEmpty())
            return;

        double minX = floorNodes.stream().mapToDouble(Node::getX).min().orElse(0);
        double maxX = floorNodes.stream().mapToDouble(Node::getX).max().orElse(0);
        double minY = floorNodes.stream().mapToDouble(Node::getY).min().orElse(0);
        double maxY = floorNodes.stream().mapToDouble(Node::getY).max().orElse(0);

        double contentWidth = maxX - minX;
        double contentHeight = maxY - minY;

        if (contentWidth > 0 && contentHeight > 0) {
            double scaleX = getWidth() * 0.8 / contentWidth;
            double scaleY = getHeight() * 0.8 / contentHeight;
            zoomFactor = Math.min(scaleX, scaleY);

            panX = (getWidth() / zoomFactor - (minX + maxX)) / 2;
            panY = (getHeight() / zoomFactor - (minY + maxY)) / 2;

            repaint();
        }
    }

    // Getters
    public Node getSelectedStartNode() {
        return selectedStartNode;
    }

    public Node getSelectedEndNode() {
        return selectedEndNode;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public List<Node> getCurrentPath() {
        return currentPath;
    }

    // Setters
    public void setSelectedStartNode(Node node) {
        selectedStartNode = node;
        repaint();
    }

    public void setSelectedEndNode(Node node) {
        selectedEndNode = node;
        repaint();
    }

    public void fitToFloor(int floor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fitToFloor'");
    }

    public Object zoomIn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'zoomIn'");
    }

    public Object zoomOut() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'zoomOut'");
    }
}