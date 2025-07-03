package pinpoint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GeneratedPath extends Application {
    private PinPointLoginSystem.User userData;

    public GeneratedPath() {
        this.userData = null; // Default constructor
    }

    public GeneratedPath(PinPointLoginSystem.User userData) {
        this.userData = userData;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPrefSize(400, 600);
        mainContainer.setStyle("-fx-background-color: #F8F8F8;");

        // Header
        Label headerLabel = new Label("Generated Path");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setPadding(new Insets(20, 0, 20, 0));
        headerLabel.setStyle("-fx-background-color: white;");
        headerLabel.setMaxWidth(Double.MAX_VALUE);
        BorderPane.setAlignment(headerLabel, Pos.CENTER);

        // Map container
        VBox mapContainer = new VBox();
        mapContainer.setAlignment(Pos.CENTER);
        mapContainer.setStyle("-fx-background-color: white;");
        mapContainer.setPrefHeight(300);
        Rectangle mapPlaceholderIcon = new Rectangle(40, 40);
        mapPlaceholderIcon.setFill(Color.LIGHTGRAY);
        mapPlaceholderIcon.setStroke(Color.GRAY);
        mapPlaceholderIcon.setStrokeWidth(2);
        mapPlaceholderIcon.setArcHeight(5);
        mapPlaceholderIcon.setArcWidth(5);
        mapContainer.getChildren().add(mapPlaceholderIcon);

        // Description section
        VBox descriptionContainer = new VBox();
        descriptionContainer.setAlignment(Pos.CENTER);
        descriptionContainer.setStyle("-fx-background-color: #f0e6d2;");
        descriptionContainer.setPadding(new Insets(20));
        descriptionContainer.setPrefHeight(120);
        Label descriptionLabel = new Label("Detailed Path Description");
        descriptionLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        descriptionLabel.setTextFill(Color.web("#8B4513"));
        descriptionContainer.getChildren().add(descriptionLabel);

        VBox centerContainer = new VBox(mapContainer, descriptionContainer);
        VBox.setVgrow(mapContainer, Priority.ALWAYS);

        // Bottom navigation bar
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

        // Add to BorderPane
        mainContainer.setTop(headerLabel);
        mainContainer.setCenter(centerContainer);
        mainContainer.setBottom(bottomNav);

        Scene scene = new Scene(mainContainer, 375, 667);
        primaryStage.setTitle("Generated Path");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
