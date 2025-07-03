package pinpoint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Location extends Application {
    private PinPointLoginSystem.User userData;
    private ComboBox<String> currentLocationCombo;
    private ComboBox<String> destinationCombo;
    private ComboBox<String> algorithmCombo;
    private Button startButton;
    private Algos.BuildingGraph buildingGraph;

    public Location(PinPointLoginSystem.User userData) {
        this.userData = userData;
        this.buildingGraph = new Algos.BuildingGraph();
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPrefSize(400, 600);
        mainContainer.setStyle("-fx-background-color: #F8F8F8;");

        // Header
        Label headerLabel = new Label("Find Your Route");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setPadding(new Insets(20, 0, 20, 0));
        headerLabel.setStyle("-fx-background-color: white; -fx-text-fill: #800000;");
        headerLabel.setMaxWidth(Double.MAX_VALUE);

        // Main content
        VBox contentContainer = new VBox(20);
        contentContainer.setPadding(new Insets(30, 20, 30, 20));
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setStyle("-fx-background-color: white;");

        // Get all room names from the building graph
        ObservableList<String> roomNames = FXCollections.observableArrayList();
        roomNames.addAll(buildingGraph.getAllRoomNames());
        roomNames.sort(String::compareTo);

        // Current Location Section
        VBox currentLocationSection = new VBox(10);
        currentLocationSection.setAlignment(Pos.CENTER_LEFT);

        Label currentLocationLabel = new Label("Current Location:");
        currentLocationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        currentLocationLabel.setTextFill(Color.web("#800000"));

        currentLocationCombo = new ComboBox<>(roomNames);
        currentLocationCombo.setPromptText("Select your current location");
        currentLocationCombo.setPrefWidth(320);
        currentLocationCombo.setPrefHeight(45);
        currentLocationCombo.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #800000; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-font-size: 14px; " +
                        "-fx-cursor: hand;");
        currentLocationCombo.setEditable(false); // FIX: Only allow selection from list

        currentLocationSection.getChildren().addAll(currentLocationLabel, currentLocationCombo);

        // Destination Section
        VBox destinationSection = new VBox(10);
        destinationSection.setAlignment(Pos.CENTER_LEFT);

        Label destinationLabel = new Label("Destination:");
        destinationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        destinationLabel.setTextFill(Color.web("#800000"));

        destinationCombo = new ComboBox<>(roomNames);
        destinationCombo.setPromptText("Select your destination");
        destinationCombo.setPrefWidth(320);
        destinationCombo.setPrefHeight(45);
        destinationCombo.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #800000; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-font-size: 14px; " +
                        "-fx-cursor: hand;");
        destinationCombo.setEditable(false); // FIX: Only allow selection from list

        destinationSection.getChildren().addAll(destinationLabel, destinationCombo);

        // Algorithm Selection Section
        VBox algorithmSection = new VBox(10);
        algorithmSection.setAlignment(Pos.CENTER_LEFT);

        Label algorithmLabel = new Label("Pathfinding Algorithm:");
        algorithmLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        algorithmLabel.setTextFill(Color.web("#800000"));

        algorithmCombo = new ComboBox<>();
        algorithmCombo.getItems().addAll("A*", "Dijkstra");
        algorithmCombo.setValue("A*"); // Default selection
        algorithmCombo.setPrefWidth(320);
        algorithmCombo.setPrefHeight(45);
        algorithmCombo.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #800000; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-font-size: 14px; " +
                        "-fx-cursor: hand;");

        algorithmSection.getChildren().addAll(algorithmLabel, algorithmCombo);

        // Algorithm description
        Label algorithmDescription = new Label();
        algorithmDescription.setFont(Font.font("Arial", 12));
        algorithmDescription.setTextFill(Color.web("#666666"));
        algorithmDescription.setWrapText(true);
        algorithmDescription.setMaxWidth(320);
        algorithmDescription.setPadding(new Insets(0, 0, 10, 0));

        // Update description based on selected algorithm
        algorithmCombo.setOnAction(e -> {
            String selected = algorithmCombo.getValue();
            if ("A*".equals(selected)) {
                algorithmDescription.setText(
                        "A* Algorithm: Uses heuristics to find the optimal path faster. Best for most navigation scenarios.");
            } else {
                algorithmDescription.setText(
                        "Dijkstra's Algorithm: Explores all possible paths systematically. Guarantees shortest path but may be slower.");
            }
        });
        // FIX: Set initial description after setting default value
        algorithmCombo.getOnAction().handle(null);

        // Start Button
        startButton = new Button("Find Route");
        startButton.setPrefWidth(320);
        startButton.setPrefHeight(50);
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        startButton.setStyle(
                "-fx-background-color: #800000; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 25; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        startButton.setOnAction(e -> findRoute(primaryStage));

        // Add hover effects
        startButton.setOnMouseEntered(e -> {
            startButton.setStyle(
                    "-fx-background-color: #a00000; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 25; " +
                            "-fx-cursor: hand; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 3);");
        });

        startButton.setOnMouseExited(e -> {
            startButton.setStyle(
                    "-fx-background-color: #800000; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 25; " +
                            "-fx-cursor: hand; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        });

        contentContainer.getChildren().addAll(
                currentLocationSection,
                destinationSection,
                algorithmSection,
                algorithmDescription,
                startButton);

        // Bottom Navigation
        HBox bottomNav = createBottomNavigation(primaryStage);

        mainContainer.setTop(headerLabel);
        mainContainer.setCenter(contentContainer);
        mainContainer.setBottom(bottomNav);

        Scene scene = new Scene(mainContainer, 375, 667);
        primaryStage.setTitle("Location Navigation");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void findRoute(Stage primaryStage) {
        String currentLocation = currentLocationCombo.getValue();
        String destination = destinationCombo.getValue();
        String algorithm = algorithmCombo.getValue();

        // Validate inputs
        if (currentLocation == null || currentLocation.trim().isEmpty()) {
            showAlert("Error", "Please select your current location.");
            return;
        }

        if (destination == null || destination.trim().isEmpty()) {
            showAlert("Error", "Please select your destination.");
            return;
        }

        if (currentLocation.equals(destination)) {
            showAlert("Error", "Current location and destination cannot be the same.");
            return;
        }

        // Check if the locations exist in the building graph
        if (buildingGraph.getNode(currentLocation) == null) {
            showAlert("Error", "Current location '" + currentLocation + "' not found in the building.");
            return;
        }

        if (buildingGraph.getNode(destination) == null) {
            showAlert("Error", "Destination '" + destination + "' not found in the building.");
            return;
        }

        try {
            // Find the path using the selected algorithm
            Algos.PathResult pathResult = Algos.findOptimalPath(currentLocation, destination, algorithm);

            if (pathResult.getPath().isEmpty()) {
                showAlert("No Route Found", "No path could be found between the specified locations.");
                return;
            }

            // Navigate to GeneratedPath with the result
            GeneratedPath generatedPath = new GeneratedPath(userData, pathResult);
            generatedPath.start(primaryStage);

        } catch (Exception ex) {
            showAlert("Error", "An error occurred while finding the route: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private HBox createBottomNavigation(Stage primaryStage) {
        HBox bottomNav = new HBox();
        bottomNav.setPrefHeight(80);
        bottomNav.setStyle("-fx-background-color: #800000;");
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setSpacing(60);

        // Create SVG icons
        SVGPath homeIconSVG = new SVGPath();
        homeIconSVG.setContent("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
        homeIconSVG.setFill(Color.WHITE);
        homeIconSVG.setScaleX(1.5);
        homeIconSVG.setScaleY(1.5);
        StackPane homeIcon = new StackPane(homeIconSVG);

        SVGPath starIconSVG = new SVGPath();
        starIconSVG.setContent(
                "M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z");
        starIconSVG.setFill(Color.web("#FFD700")); // Star is selected
        starIconSVG.setScaleX(1.2);
        starIconSVG.setScaleY(1.2);
        StackPane starIcon = new StackPane(starIconSVG);

        SVGPath mapIconSVG = new SVGPath();
        mapIconSVG.setContent(
                "M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM15 19l-6-2.11V5l6 2.11V19z");
        mapIconSVG.setFill(Color.WHITE);
        mapIconSVG.setScaleX(1.2);
        mapIconSVG.setScaleY(1.2);
        StackPane mapIcon = new StackPane(mapIconSVG);

        SVGPath profileIconSVG = new SVGPath();
        profileIconSVG.setContent(
                "M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z");
        profileIconSVG.setFill(Color.WHITE);
        profileIconSVG.setScaleX(1.2);
        profileIconSVG.setScaleY(1.2);
        StackPane profileIcon = new StackPane(profileIconSVG);

        // Create buttons
        Button homeButton = new Button("", homeIcon);
        Button starButton = new Button("", starIcon);
        Button mapButton = new Button("", mapIcon);
        Button profileButton = new Button("", profileIcon);

        // Style buttons
        for (Button btn : new Button[] { homeButton, starButton, mapButton, profileButton }) {
            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
            btn.setPrefSize(30, 30);
        }

        // Add navigation actions
        homeButton.setOnAction(e -> {
            try {
                new HomePage(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        mapButton.setOnAction(e -> {
            try {
                new Map(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        profileButton.setOnAction(e -> {
            try {
                if (userData != null) {
                    new User(userData).start(primaryStage);
                } else {
                    System.out.println("User data is null - cannot navigate to User profile");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        bottomNav.getChildren().addAll(homeButton, starButton, mapButton, profileButton);
        return bottomNav;
    }

    public static void main(String[] args) {
        launch(args);
    }
}