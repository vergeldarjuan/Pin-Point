package pinpoint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Location extends Application {
    private PinPointLoginSystem.User userData;

    public Location(PinPointLoginSystem.User userData) {
        this.userData = userData;
    }

    @Override
    public void start(Stage primaryStage) {
        // Main container
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPrefSize(400, 600);
        mainContainer.setStyle("-fx-background-color: #F8F8F8;");

        // Main content area
        VBox contentArea = new VBox();
        contentArea.setPadding(new Insets(40, 30, 40, 30));
        contentArea.setSpacing(25);
        contentArea.setAlignment(Pos.TOP_CENTER);

        // Title
        Label title = new Label("Track Destination");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#333333"));
        title.setAlignment(Pos.CENTER);

        // Current location section
        VBox currentLocationSection = new VBox();
        currentLocationSection.setSpacing(8);

        Label currentLocationLabel = new Label("Enter current location");
        currentLocationLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        currentLocationLabel.setTextFill(Color.web("#333333"));

        TextField currentLocationField = new TextField();
        currentLocationField.setPrefHeight(45);
        currentLocationField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #800000; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px;");

        currentLocationSection.getChildren().addAll(currentLocationLabel, currentLocationField);

        // Destination location section
        VBox destinationSection = new VBox();
        destinationSection.setSpacing(8);

        Label destinationLabel = new Label("Enter destination location");
        destinationLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        destinationLabel.setTextFill(Color.web("#333333"));

        TextField destinationField = new TextField();
        destinationField.setPrefHeight(45);
        destinationField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #800000; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px;");

        destinationSection.getChildren().addAll(destinationLabel, destinationField);

        // Start button
        Button startButton = new Button("Start");
        startButton.setPrefWidth(100);
        startButton.setPrefHeight(40);
        startButton.setAlignment(Pos.CENTER);
        startButton.setStyle(
                "-fx-background-color: #800000; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-family: Arial; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-cursor: hand;");

        // Center the start button
        HBox startButtonContainer = new HBox();
        startButtonContainer.setAlignment(Pos.CENTER);
        startButtonContainer.getChildren().add(startButton);
        startButton.setOnAction(e -> {
            // Switch to Map
            try {
                new GeneratedPath().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add some spacing before the bottom section
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Bottom info section
        VBox bottomInfoSection = new VBox();
        bottomInfoSection.setAlignment(Pos.CENTER);
        bottomInfoSection.setSpacing(15);
        bottomInfoSection.setPadding(new Insets(0, 0, 60, 0));

        // Map icon
        Region mapLocationIcon = createMapLocationIcon();

        // Info text
        Text infoText1 = new Text("To view the full PUP Main Building map,");
        infoText1.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        infoText1.setFill(Color.web("#666666"));
        infoText1.setTextAlignment(TextAlignment.CENTER);

        Text infoText2 = new Text("proceed to the \"map\" section");
        infoText2.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        infoText2.setFill(Color.web("#666666"));
        infoText2.setTextAlignment(TextAlignment.CENTER);

        VBox infoTextContainer = new VBox();
        infoTextContainer.setAlignment(Pos.CENTER);
        infoTextContainer.setSpacing(2);
        infoTextContainer.getChildren().addAll(infoText1, infoText2);

        bottomInfoSection.getChildren().addAll(mapLocationIcon, infoTextContainer);

        // Add all sections to content area
        contentArea.getChildren().addAll(
                title,
                currentLocationSection,
                destinationSection,
                startButtonContainer,
                spacer,
                bottomInfoSection);

        // Bottom navigation bar
        HBox bottomNav = new HBox();
        bottomNav.setPrefHeight(80);
        bottomNav.setStyle("-fx-background-color: #800000;");
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setSpacing(60);

        // --- Navigation icons with color change and navigation ---

        // Home icon SVGPath
        SVGPath homeIconSVG = new SVGPath();
        homeIconSVG.setContent("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
        homeIconSVG.setFill(Color.WHITE);
        homeIconSVG.setScaleX(1.5);
        homeIconSVG.setScaleY(1.5);
        StackPane homeIcon = new StackPane(homeIconSVG);
        homeIcon.setPrefSize(30, 30);

        // Map icon SVGPath
        SVGPath mapIconSVG = new SVGPath();
        mapIconSVG.setContent(
                "M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM15 19l-6-2.11V5l6 2.11V19z");
        mapIconSVG.setFill(Color.WHITE);
        mapIconSVG.setScaleX(1.2);
        mapIconSVG.setScaleY(1.2);
        StackPane mapIcon = new StackPane(mapIconSVG);
        mapIcon.setPrefSize(30, 30);

        // Profile icon SVGPath
        SVGPath profileIconSVG = new SVGPath();
        profileIconSVG.setContent(
                "M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z");
        profileIconSVG.setFill(Color.WHITE);
        profileIconSVG.setScaleX(1.2);
        profileIconSVG.setScaleY(1.2);
        StackPane profileIcon = new StackPane(profileIconSVG);
        profileIcon.setPrefSize(30, 30);

        // Star icon (no color change needed)
        Button starButton = new Button();
        starButton.setGraphic(createStarIcon());
        starButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        starButton.setPrefSize(30, 30);

        // Navigation buttons
        Button homeButton = new Button();
        homeButton.setGraphic(homeIcon);
        homeButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        homeButton.setPrefSize(30, 30);

        Button mapButton = new Button();
        mapButton.setGraphic(mapIcon);
        mapButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        mapButton.setPrefSize(30, 30);

        Button profileButton = new Button();
        profileButton.setGraphic(profileIcon);
        profileButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        profileButton.setPrefSize(30, 30);

        bottomNav.getChildren().setAll(homeButton, starButton, mapButton, profileButton);

        // --- Navigation actions ---

        homeButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.web("#FFD700")); // Home yellow
            mapIconSVG.setFill(Color.WHITE); // Map white
            profileIconSVG.setFill(Color.WHITE); // Profile white
            // Switch to HomePage
            try {
                new HomePage(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        mapButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.WHITE); // Home white
            mapIconSVG.setFill(Color.web("#FFD700")); // Map yellow
            profileIconSVG.setFill(Color.WHITE); // Profile white
            // Switch to Map
            try {
                new Map(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Profile button
        profileButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.WHITE); // Home white
            mapIconSVG.setFill(Color.WHITE); // Map white
            profileIconSVG.setFill(Color.web("#FFD700")); // Profile yellow

            // Switch to UserProfile
            try {
                new User(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Set up the main layouts
        mainContainer.setCenter(contentArea);
        mainContainer.setBottom(bottomNav);

        Scene scene = new Scene(mainContainer, 375, 667);
        primaryStage.setTitle("Location");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Region createMapLocationIcon() {
        SVGPath mapLocationIcon = new SVGPath();
        mapLocationIcon.setContent(
                "M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z M9 21h6v2H9v-2z M7 19h10v2H7v-2z");
        mapLocationIcon.setFill(Color.web("#800000"));
        mapLocationIcon.setScaleX(2.5);
        mapLocationIcon.setScaleY(2.5);

        StackPane container = new StackPane();
        container.getChildren().add(mapLocationIcon);
        container.setPrefSize(60, 60);
        container.setAlignment(Pos.CENTER);

        return container;
    }

    private Region createHomeIcon() {
        SVGPath homeIcon = new SVGPath();
        homeIcon.setContent("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
        homeIcon.setFill(Color.WHITE);
        homeIcon.setScaleX(1.5);
        homeIcon.setScaleY(1.5);

        StackPane container = new StackPane();
        container.getChildren().add(homeIcon);
        container.setPrefSize(30, 30);

        return container;
    }

    private Region createStarIcon() {
        SVGPath starIcon = new SVGPath();
        starIcon.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starIcon.setFill(Color.web("#FFDF00"));
        starIcon.setScaleX(1.2);
        starIcon.setScaleY(1.2);

        StackPane container = new StackPane();
        container.getChildren().add(starIcon);
        container.setPrefSize(30, 30);

        return container;
    }

    private Region createMapIcon() {
        SVGPath mapIcon = new SVGPath();
        mapIcon.setContent(
                "M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM15 19l-6-2.11V5l6 2.11V19z");
        mapIcon.setFill(Color.WHITE);
        mapIcon.setScaleX(1.2);
        mapIcon.setScaleY(1.2);

        StackPane container = new StackPane();
        container.getChildren().add(mapIcon);
        container.setPrefSize(30, 30);

        return container;
    }

    private Region createProfileIcon() {
        SVGPath profileIcon = new SVGPath();
        profileIcon.setContent(
                "M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z");
        profileIcon.setFill(Color.WHITE);
        profileIcon.setScaleX(1.2);
        profileIcon.setScaleY(1.2);

        StackPane container = new StackPane();
        container.getChildren().add(profileIcon);
        container.setPrefSize(30, 30);

        return container;
    }

    public static void main(String[] args) {
        launch(args);
    }
}