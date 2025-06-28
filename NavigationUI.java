import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class NavigationUI extends JFrame {
    private Graph graph;
    private MapPanel mapPanel;
    private Dijkstra dijkstra;
    private AStar aStar;

    // UI Components
    private JComboBox<Node> startComboBox;
    private JComboBox<Node> endComboBox;
    private JComboBox<String> algorithmComboBox;
    private JSpinner floorSpinner;
    private JButton findPathButton;
    private JButton clearPathButton;
    private JButton resetViewButton;
    private JButton fitViewButton;
    private JCheckBox accessibleOnlyCheckBox;
    private JTextArea directionsArea;
    private JTextArea performanceArea;
    private JLabel statusLabel;

    public NavigationUI() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupMenuBar();

        // updateFloorSpinner();
        populateComboBoxes();

        setTitle("PUP Main Building Navigation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        showWelcomeDialog();
    }

    private void initializeComponents() {
        graph = new Graph("PUP Main Building");
        dijkstra = new Dijkstra(graph);
        aStar = new AStar(graph);

        mapPanel = new MapPanel(graph);

        startComboBox = new JComboBox<>();
        endComboBox = new JComboBox<>();
        algorithmComboBox = new JComboBox<>(new String[] { "A* Algorithm", "Dijkstra's Algorithm", "Compare Both" });
        floorSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

        findPathButton = new JButton("Find Path");
        clearPathButton = new JButton("Clear Path");
        resetViewButton = new JButton("Reset View");
        fitViewButton = new JButton("Fit to Floor");

        accessibleOnlyCheckBox = new JCheckBox("Accessible Routes Only");

        directionsArea = new JTextArea(10, 30);
        directionsArea.setEditable(false);
        directionsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        performanceArea = new JTextArea(8, 30);
        performanceArea.setEditable(false);
        performanceArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

        statusLabel = new JLabel("Status: Ready");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Status", TitledBorder.LEFT, TitledBorder.TOP));
        statusLabel.setPreferredSize(new Dimension(300, 30));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(mapPanel, BorderLayout.CENTER);
        mainPanel.add(createLeftPanel(), BorderLayout.WEST);
        mainPanel.add(createRightPanel(), BorderLayout.EAST);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(statusLabel);

        add(mainPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel navPanel = new JPanel(new GridBagLayout());
        navPanel.setBorder(BorderFactory.createTitledBorder("Navigation Controls"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        navPanel.add(new JLabel("Floor:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        navPanel.add(floorSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        navPanel.add(new JLabel("From:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        navPanel.add(startComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        navPanel.add(new JLabel("To:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        navPanel.add(endComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        navPanel.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        navPanel.add(algorithmComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        navPanel.add(accessibleOnlyCheckBox, gbc);

        panel.add(navPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonPanel.add(findPathButton);
        buttonPanel.add(clearPathButton);
        buttonPanel.add(resetViewButton);
        buttonPanel.add(fitViewButton);

        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);

        JPanel perfPanel = new JPanel(new BorderLayout());
        perfPanel.setBorder(BorderFactory.createTitledBorder("Performance Metrics"));
        perfPanel.add(new JScrollPane(performanceArea), BorderLayout.CENTER);

        panel.add(Box.createVerticalStrut(10));
        panel.add(perfPanel);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel directionsPanel = new JPanel(new BorderLayout());
        directionsPanel.setBorder(BorderFactory.createTitledBorder("Turn-by-Turn Directions"));
        directionsPanel.add(new JScrollPane(directionsArea), BorderLayout.CENTER);

        panel.add(directionsPanel, BorderLayout.CENTER);

        return panel;
    }

    private void setupEventHandlers() {
        findPathButton.addActionListener(e -> findPath());
        clearPathButton.addActionListener(e -> clearPath());
        resetViewButton.addActionListener(e -> {
            mapPanel.resetView();
            updateStatus("View reset to default");
        });
        fitViewButton.addActionListener(e -> {
            int floor = (Integer) floorSpinner.getValue();
            mapPanel.fitToFloor(floor);
            updateStatus("View fitted to floor " + floor);
        });

        floorSpinner.addChangeListener(e -> {
            int floor = (Integer) floorSpinner.getValue();
            mapPanel.setCurrentFloor(floor);
            populateComboBoxes();
            updateStatus("Switched to floor " + floor);
        });

        accessibleOnlyCheckBox.addActionListener(e -> {
            populateComboBoxes();
            clearPath();
        });

        algorithmComboBox.addActionListener(e -> clearPath());
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu viewMenu = new JMenu("View");
        JMenuItem zoomIn = new JMenuItem("Zoom In");
        JMenuItem zoomOut = new JMenuItem("Zoom Out");
        JMenuItem reset = new JMenuItem("Reset View");
        zoomIn.addActionListener(e -> mapPanel.zoomIn());
        zoomOut.addActionListener(e -> mapPanel.zoomOut());
        reset.addActionListener(e -> {
            mapPanel.resetView();
            updateStatus("View reset");
        });
        viewMenu.add(zoomIn);
        viewMenu.add(zoomOut);
        viewMenu.addSeparator();
        viewMenu.add(reset);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void populateComboBoxes() {
        int floor = (Integer) floorSpinner.getValue();
        boolean accessibleOnly = accessibleOnlyCheckBox.isSelected();

        List<Node> nodes = graph.getNodesByFloor(floor);
        if (nodes == null)
            nodes = new ArrayList<>();

        if (accessibleOnly) {
            nodes.removeIf(n -> !n.isAccessible());
        }

        startComboBox.removeAllItems();
        endComboBox.removeAllItems();

        for (Node node : nodes) {
            startComboBox.addItem(node);
            endComboBox.addItem(node);
        }
    }

    private void findPath() {
        Node start = (Node) startComboBox.getSelectedItem();
        Node end = (Node) endComboBox.getSelectedItem();
        String algo = (String) algorithmComboBox.getSelectedItem();

        if (start == null || end == null) {
            updateStatus("Select both start and end points");
            return;
        }
        if (start.equals(end)) {
            updateStatus("Start and end cannot be the same");
            return;
        }

        boolean accessible = accessibleOnlyCheckBox.isSelected();
        updateStatus("Finding path...");

        if ("Compare Both".equals(algo)) {
            compareAlgorithms(start, end, accessible);
        } else {
            AStar.PathResult result = findSinglePath(start, end, algo, accessible);
            if (result != null) {
                displayPathResult(result, algo);
                mapPanel.drawPath((Graphics2D) result.getPath());
                updateStatus("Path found using " + algo);
            } else {
                updateStatus("No path found");
                clearPath();
            }
        }
    }

    private AStar.PathResult findSinglePath(Node start, Node end, String algorithm, boolean accessible) {
        if ("A* Algorithm".equals(algorithm)) {
            return aStar.findPath(start, end, accessible);
        } else if ("Dijkstra's Algorithm".equals(algorithm)) {
            Dijkstra.PathResult result = dijkstra.findPath(start, end, accessible);
            // Convert Dijkstra.PathResult to AStar.PathResult if needed, or adjust method
            // signature to accept both
            // For now, return null as types are incompatible
            return null;
        } else {
            return null;
        }
    }

    private void compareAlgorithms(Node start, Node end, boolean accessible) {
        long t1 = System.nanoTime();
        AStar.PathResult aStarResult = aStar.findPath(start, end, accessible);
        long t2 = System.nanoTime();

        long t3 = System.nanoTime();
        Dijkstra.PathResult dijkstraResult = dijkstra.findPath(start, end, accessible);
        long t4 = System.nanoTime();

        StringBuilder sb = new StringBuilder();
        sb.append("ALGORITHM COMPARISON\n====================\n");

        sb.append("A* Algorithm:\n");
        if (aStarResult != null) {
            sb.append(String.format("Distance: %.2f m\nSteps: %d\nTime: %.2f ms\nNodes: %d\n",
                    aStarResult.getTotalDistance(),
                    aStarResult.getPath().size(),
                    (t2 - t1) / 1_000_000.0,
                    aStarResult.getNodesVisited()));
        } else
            sb.append("No path found\n");

        sb.append("\nDijkstra's Algorithm:\n");
        if (dijkstraResult != null) {
            sb.append(String.format("Distance: %.2f m\nSteps: %d\nTime: %.2f ms\nNodes: %d\n",
                    dijkstraResult.getTotalDistance(),
                    dijkstraResult.getPath().size(),
                    (t4 - t3) / 1_000_000.0,
                    dijkstraResult.getNodesVisited()));
        } else
            sb.append("No path found\n");

        performanceArea.setText(sb.toString());

        if (aStarResult != null) {
            mapPanel.drawPath((Graphics2D) aStarResult.getPath());
            displayDirections(aStarResult.getDirections());
        } else if (dijkstraResult != null) {
            mapPanel.drawPath((Graphics2D) dijkstraResult.getPath());
            displayDirections(dijkstraResult.getDirections());
        } else {
            mapPanel.clearPath();
            directionsArea.setText("");
        }
    }

    private void displayPathResult(AStar.PathResult result, String algorithm) {
        displayDirections(result.getDirections());
        StringBuilder sb = new StringBuilder();
        sb.append("Algorithm: ").append(algorithm).append("\n");
        sb.append(String.format("Distance: %.2f meters\n", result.getTotalDistance()));
        sb.append("Steps: ").append(result.getPath().size()).append("\n");
        sb.append(String.format("Execution Time: %.2f ms\n", result.getExecutionTime() / 1_000_000.0));
        sb.append("Nodes Explored: ").append(result.getNodesVisited()).append("\n");
        performanceArea.setText(sb.toString());
    }

    private void displayDirections(List<String> directions) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < directions.size(); i++) {
            sb.append((i + 1)).append(". ").append(directions.get(i)).append("\n");
        }
        directionsArea.setText(sb.toString());
        directionsArea.setCaretPosition(0);
    }

    private void clearPath() {
        mapPanel.clearPath();
        directionsArea.setText("");
        performanceArea.setText("");
        updateStatus("Path cleared");
    }

    private void updateStatus(String msg) {
        statusLabel.setText("Status: " + msg);
    }

    private void showWelcomeDialog() {
        JOptionPane.showMessageDialog(this,
                "Welcome to PUP Indoor Navigation System!\n\n" +
                        "Select your start and end points, choose an algorithm,\n" +
                        "and find your optimal path with accessibility options.",
                "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
                "PUP Main Building Navigation System\nVersion 1.0\n\nDeveloped for Polytechnic University of the Philippines\nAlgorithms: A* and Dijkstra's\n© PinPoint",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }
}
