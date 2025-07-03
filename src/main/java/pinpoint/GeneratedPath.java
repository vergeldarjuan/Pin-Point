package pinpoint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import pinpoint.Algos.PathResult;

public class GeneratedPath extends Application {
    private PinPointLoginSystem.User userData;
    private Algos.PathResult pathResult;

    public GeneratedPath(PinPointLoginSystem.User userData, Algos.PathResult pathResult) {
        this.userData = userData;
        this.pathResult = pathResult;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPrefSize(400, 600);
        mainContainer.setStyle("-fx-background-color: #F8F8F8;");

        // Header with Back Button
        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setSpacing(10);
        headerBox.setPadding(new Insets(20, 0, 20, 0));
        headerBox.setStyle("-fx-background-color: white;");

        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #800000; -fx-cursor: hand;");
        backButton.setOnAction(e -> {
            // Go back to Location selection
            try {
                new Location(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Label headerLabel = new Label("Generated Path");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setMaxWidth(Double.MAX_VALUE);

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        headerBox.getChildren().addAll(backButton, leftSpacer, headerLabel, rightSpacer);

        // Map container with path visualization
        VBox mapContainer = createMapContainer();

        // Description section with path details
        ScrollPane descriptionContainer = createDescriptionContainer();

        VBox centerContainer = new VBox(mapContainer, descriptionContainer);
        VBox.setVgrow(descriptionContainer, Priority.ALWAYS);

        // Bottom navigation bar
        HBox bottomNav = createBottomNavigation(primaryStage);

        // Add to BorderPane
        mainContainer.setTop(headerBox);
        mainContainer.setCenter(centerContainer);
        mainContainer.setBottom(bottomNav);

        Scene scene = new Scene(mainContainer, 375, 667);
        primaryStage.setTitle("Generated Path");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private VBox createMapContainer() {
        VBox mapContainer = new VBox();
        mapContainer.setAlignment(Pos.CENTER);
        mapContainer.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1;");
        mapContainer.setPrefHeight(200);
        mapContainer.setPadding(new Insets(15));

        if (pathResult != null && !pathResult.getPath().isEmpty()) {
            // Display path summary
            VBox pathSummary = new VBox(5);
            pathSummary.setAlignment(Pos.CENTER);

            Label fromLabel = new Label("From: " + pathResult.getPath().get(0).name);
            fromLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            fromLabel.setTextFill(Color.web("#2E7D32"));

            Label toLabel = new Label("To: " + pathResult.getPath().get(pathResult.getPath().size() - 1).name);
            toLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            toLabel.setTextFill(Color.web("#D32F2F"));

            Label algorithmLabel = new Label("Algorithm: " + pathResult.getAlgorithm());
            algorithmLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            algorithmLabel.setTextFill(Color.web("#1976D2"));

            Label distanceLabel = new Label(String.format("Distance: %.2f units", pathResult.getTotalDistance()));
            distanceLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            distanceLabel.setTextFill(Color.web("#FF8F00"));

            Label timeLabel = new Label("Execution Time: " + pathResult.getExecutionTime() + " ms");
            timeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            timeLabel.setTextFill(Color.web("#7B1FA2"));

            // Visual path representation
            Rectangle pathVisual = new Rectangle(280, 40);
            pathVisual.setFill(Color.web("#E3F2FD"));
            pathVisual.setStroke(Color.web("#1976D2"));
            pathVisual.setStrokeWidth(2);
            pathVisual.setArcHeight(10);
            pathVisual.setArcWidth(10);

            StackPane pathVisualization = new StackPane(pathVisual);

            pathSummary.getChildren().addAll(fromLabel, toLabel, algorithmLabel,
                    distanceLabel, timeLabel, pathVisualization);
            mapContainer.getChildren().add(pathSummary);
        } else {
            // Display placeholder when no path is available
            Rectangle mapPlaceholderIcon = new Rectangle(40, 40);
            mapPlaceholderIcon.setFill(Color.LIGHTGRAY);
            mapPlaceholderIcon.setStroke(Color.GRAY);
            mapPlaceholderIcon.setStrokeWidth(2);
            mapPlaceholderIcon.setArcHeight(5);
            mapPlaceholderIcon.setArcWidth(5);

            Label noPathLabel = new Label("No path data available");
            noPathLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            noPathLabel.setTextFill(Color.GRAY);

            VBox placeholder = new VBox(10, mapPlaceholderIcon, noPathLabel);
            placeholder.setAlignment(Pos.CENTER);
            mapContainer.getChildren().add(placeholder);
        }

        return mapContainer;
    }

    private ScrollPane createDescriptionContainer() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: #f0e6d2;");
        scrollPane.setPrefHeight(200);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox descriptionContainer = new VBox();
        descriptionContainer.setPadding(new Insets(15));
        descriptionContainer.setSpacing(10);

        if (pathResult != null && !pathResult.getPath().isEmpty()) {
            // Title
            Label titleLabel = new Label("Step-by-Step Directions");
            titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            titleLabel.setTextFill(Color.web("#5D4037"));

            // Algorithm info
            Label algorithmInfo = new Label("Using " + pathResult.getAlgorithm() + " Algorithm");
            algorithmInfo.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            algorithmInfo.setTextFill(Color.web("#8B4513"));
            algorithmInfo.setStyle("-fx-font-style: italic;");

            descriptionContainer.getChildren().addAll(titleLabel, algorithmInfo);

            // Path steps
            for (int i = 0; i < pathResult.getPath().size(); i++) {
                Algos.Node node = pathResult.getPath().get(i);

                HBox stepContainer = new HBox(10);
                stepContainer.setAlignment(Pos.CENTER_LEFT);

                // Step number
                Label stepNumber = new Label(String.valueOf(i + 1));
                stepNumber.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                stepNumber.setTextFill(Color.WHITE);
                stepNumber.setStyle("-fx-background-color: #8B4513; -fx-background-radius: 15;");
                stepNumber.setPrefSize(25, 25);
                stepNumber.setAlignment(Pos.CENTER);

                // Step description
                VBox stepInfo = new VBox(2);
                Label roomLabel = new Label(node.name);
                roomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                roomLabel.setTextFill(Color.web("#3E2723"));

                Label floorLabel = new Label(node.floor);
                floorLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
                floorLabel.setTextFill(Color.web("#8B4513"));

                stepInfo.getChildren().addAll(roomLabel, floorLabel);

                // Add arrow for non-last steps
                if (i < pathResult.getPath().size() - 1) {
                    Label arrow = new Label("↓");
                    arrow.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                    arrow.setTextFill(Color.web("#8B4513"));
                    stepInfo.getChildren().add(arrow);
                }

                stepContainer.getChildren().addAll(stepNumber, stepInfo);
                descriptionContainer.getChildren().add(stepContainer);
            }

            // Summary
            VBox summaryBox = new VBox(5);
            summaryBox.setStyle("-fx-background-color: rgba(139, 69, 19, 0.1); -fx-background-radius: 8;");
            summaryBox.setPadding(new Insets(10));

            Label summaryTitle = new Label("Path Summary");
            summaryTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            summaryTitle.setTextFill(Color.web("#5D4037"));

            Text summaryText = new Text(String.format(
                    "• Total Distance: %.2f units\n• Execution Time: %d ms\n• Algorithm Used: %s",
                    pathResult.getTotalDistance(),
                    pathResult.getExecutionTime(),
                    pathResult.getAlgorithm()));
            summaryText.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            summaryText.setFill(Color.web("#8B4513"));

            summaryBox.getChildren().addAll(summaryTitle, summaryText);
            descriptionContainer.getChildren().add(summaryBox);

        } else {
            Label noPathLabel = new Label("No path found or invalid locations provided.");
            noPathLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            noPathLabel.setTextFill(Color.web("#8B4513"));
            noPathLabel.setWrapText(true);
            noPathLabel.setTextAlignment(TextAlignment.CENTER);
            descriptionContainer.getChildren().add(noPathLabel);
        }

        scrollPane.setContent(descriptionContainer);
        return scrollPane;
    }

    private HBox createBottomNavigation(Stage primaryStage) {
        HBox bottomNav = new HBox();
        bottomNav.setPrefHeight(80);
        bottomNav.setStyle("-fx-background-color: #800000;");
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setSpacing(60);

        // Icons
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

        // Buttons
        Button homeButton = new Button("", homeIcon);
        Button starButton = new Button("", starIcon);
        Button mapButton = new Button("", mapIcon);
        Button profileButton = new Button("", profileIcon);

        for (Button btn : new Button[] { homeButton, starButton, mapButton, profileButton }) {
            btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
            btn.setPrefSize(30, 30);
        }

        bottomNav.getChildren().addAll(homeButton, starButton, mapButton, profileButton);

        // Navigation actions
        homeButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.web("#FFD700"));
            starIconSVG.setFill(Color.WHITE);
            mapIconSVG.setFill(Color.WHITE);
            profileIconSVG.setFill(Color.WHITE);
            try {
                new HomePage(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        mapButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.WHITE);
            starIconSVG.setFill(Color.WHITE);
            mapIconSVG.setFill(Color.web("#FFD700"));
            profileIconSVG.setFill(Color.WHITE);
            try {
                new Map(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        profileButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.WHITE);
            starIconSVG.setFill(Color.WHITE);
            mapIconSVG.setFill(Color.WHITE);
            profileIconSVG.setFill(Color.web("#FFD700"));
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

        return bottomNav;
    }

    public static void main(String[] args) {
        launch(args);
    }
}