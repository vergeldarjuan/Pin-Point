package pinpoint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main container
        VBox mainContainer = new VBox();
        mainContainer.setPrefSize(400, 600);

        // Header section with background image only
        VBox headerSection = new VBox();
        headerSection.setPrefHeight(200);
        headerSection.setAlignment(Pos.CENTER);
        headerSection.setStyle(
                "-fx-background-image: url('/images/pupbg.jpg');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center center;" +
                        "-fx-background-repeat: no-repeat;");

        // Subtitle section (separate from headerSection)
        VBox subtitleContainer = new VBox();
        subtitleContainer.setAlignment(Pos.CENTER);
        subtitleContainer.setPadding(new Insets(10, 20, 10, 20));
        subtitleContainer.setStyle("-fx-background-color: white;"); // Add this line

        Text exploreText = new Text("Explore PUP with ease — ");
        exploreText.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        exploreText.setFill(Color.web("black"));

        Text pinPointText = new Text("PIN*point maps");
        pinPointText.setFont(Font.font("Poppins", FontWeight.BOLD, 16));
        pinPointText.setFill(Color.web("#800000"));

        HBox subtitleLine1 = new HBox();
        subtitleLine1.setAlignment(Pos.CENTER);
        subtitleLine1.getChildren().addAll(exploreText, pinPointText);

        Label pathLabel = new Label("your path and keeps you on track!");
        pathLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        pathLabel.setTextFill(Color.web("black"));
        pathLabel.setAlignment(Pos.CENTER);

        subtitleContainer.getChildren().addAll(subtitleLine1, pathLabel);

        // Guide section
        VBox guideSection = new VBox();
        guideSection.setStyle("-fx-background-color: white;");
        guideSection.setPadding(new Insets(1, 20, 10, 20));
        guideSection.setSpacing(20);

        // Guide title
        Label guideTitle = new Label("GUIDE:");
        guideTitle.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        guideTitle.setTextFill(Color.web("#800000"));
        guideTitle.setAlignment(Pos.CENTER);
        guideTitle.setMaxWidth(Double.MAX_VALUE); // Make it fill the width
        guideTitle.setStyle("-fx-background-color: #FFD700;");
        guideTitle.setPadding(new Insets(5, 0, 5, 0));

        // Step 1
        HBox step1 = createGuideStep("1.) Open PIN*point app, then\nsign in.", createLocationIcon());

        // Step 2
        HBox step2 = createGuideStep("2.) Go to Location and type\n your destination.", createMapIcon());

        // Step 3
        HBox step3 = createGuideStep("3.) Walk along the route and\n finally, you're there!", createRouteIcon());

        guideSection.getChildren().addAll(guideTitle, step1, step2, step3);

        // Bottom navigation
        HBox bottomNav = new HBox();
        bottomNav.setPrefHeight(80);
        bottomNav.setStyle("-fx-background-color: #800000;");
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setSpacing(60);

        // Create navigation buttons
        Button homeButton = createNavButton(createHomeIcon(), "");
        Button starButton = createNavButton(createStarIcon(), "");
        Button mapButton = createNavButton(createMapNavIcon(), "");
        Button profileButton = createNavButton(createProfileIcon(), "");

        // Add buttons to navigation bar
        bottomNav.getChildren().addAll(homeButton, starButton, mapButton, profileButton);

        mainContainer.getChildren().addAll(headerSection, subtitleContainer, guideSection, bottomNav);

        Scene scene = new Scene(mainContainer, 375, 667);
        primaryStage.setTitle("PUP Indoor Navigation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createGuideStep(String text, Region icon) {
        HBox step = new HBox();
        step.setAlignment(Pos.CENTER_LEFT);
        step.setSpacing(20);
        step.setPadding(new Insets(20));
        step.setStyle("-fx-background-color: #FAF3E0; -fx-background-radius: 10;");

        Label stepText = new Label(text);
        stepText.setFont(Font.font("Poppins", FontWeight.NORMAL, 15));
        stepText.setTextFill(Color.web("#333333"));
        stepText.setMaxWidth(200);

        step.getChildren().addAll(icon, stepText);
        step.setMinWidth(340);
        step.setMaxWidth(Double.MAX_VALUE);

        return step;
    }

    private Button createNavButton(Region icon, String label) {
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(5);

        if (!label.isEmpty()) {
            Label navLabel = new Label(label);
            navLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            navLabel.setTextFill(Color.WHITE);
            content.getChildren().addAll(icon, navLabel);
        } else {
            content.getChildren().add(icon);
        }

        Button button = new Button();
        button.setGraphic(content);
        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        button.setPrefSize(30, 30); // Adjust size as needed
        button.setFocusTraversable(false);

        // Optional: Add action handler
        // button.setOnAction(e -> System.out.println(label + " clicked"));

        return button;
    }

    private Region createLocationIcon() {
        SVGPath locationIcon = new SVGPath();
        locationIcon.setContent(
                "M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z");
        locationIcon.setFill(Color.web("#800000"));
        locationIcon.setScaleX(2);
        locationIcon.setScaleY(2);

        StackPane container = new StackPane();
        container.getChildren().add(locationIcon);
        container.setPrefSize(40, 40);

        return container;
    }

    private Region createMapIcon() {
        SVGPath mapIcon = new SVGPath();
        mapIcon.setContent(
                "M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM15 19l-6-2.11V5l6 2.11V19z");
        mapIcon.setFill(Color.web("#800000"));
        mapIcon.setScaleX(1.5);
        mapIcon.setScaleY(1.5);

        StackPane container = new StackPane();
        container.getChildren().add(mapIcon);
        container.setPrefSize(40, 40);

        return container;
    }

    private Region createRouteIcon() {
        SVGPath routeIcon = new SVGPath();
        routeIcon.setContent("M19 15l-6 6-1.42-1.42L15.17 16H4V4h2v10h9.17l-3.59-3.58L13 9l6 6z");
        routeIcon.setFill(Color.web("#800000"));
        routeIcon.setScaleX(1.8);
        routeIcon.setScaleY(1.8);

        StackPane container = new StackPane();
        container.getChildren().add(routeIcon);
        container.setPrefSize(40, 40);

        return container;
    }

    private Region createHomeIcon() {
        SVGPath homeIcon = new SVGPath();
        homeIcon.setContent("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
        homeIcon.setFill(Color.web("#FFD700"));
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
        starIcon.setFill(Color.WHITE);
        starIcon.setScaleX(1.2);
        starIcon.setScaleY(1.2);

        StackPane container = new StackPane();
        container.getChildren().add(starIcon);
        container.setPrefSize(30, 30);

        return container;
    }

    private Region createMapNavIcon() {
        SVGPath mapNavIcon = new SVGPath();
        mapNavIcon.setContent(
                "M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM15 19l-6-2.11V5l6 2.11V19z");
        mapNavIcon.setFill(Color.WHITE);
        mapNavIcon.setScaleX(1.2);
        mapNavIcon.setScaleY(1.2);

        StackPane container = new StackPane();
        container.getChildren().add(mapNavIcon);
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