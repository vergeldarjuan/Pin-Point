package pinpoint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Map extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main container
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPrefSize(400, 600);
        mainContainer.setStyle("-fx-background-color: #F8F8F8;");

        // Top section with floor selector
        VBox topSection = new VBox();
        topSection.setPadding(new Insets(20, 20, 0, 20));
        topSection.setAlignment(Pos.TOP_LEFT);

        // Floor selector dropdown
        ComboBox<String> floorSelector = new ComboBox<>();
        floorSelector.getItems().addAll(
                "1st Floor",
                "2nd Floor",
                "3rd Floor",
                "4th Floor",
                "5th Floor",
                "6th Floor");
        floorSelector.setValue("1st Floor");
        floorSelector.setPrefWidth(160); // Increased width to fit text
        floorSelector.setMinWidth(160);
        floorSelector.setPrefHeight(35);
        floorSelector.setStyle(
                "-fx-background-color: #800000; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-family: Arial; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 18; " +
                        "-fx-border-radius: 18; " +
                        "-fx-padding: 5 15 5 15;");

        // Style the dropdown list items and the selected value
        javafx.util.Callback<javafx.scene.control.ListView<String>, javafx.scene.control.ListCell<String>> cellFactory = lv -> new javafx.scene.control.ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setTextFill(Color.WHITE);
                    setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    setStyle("-fx-background-color: #800000; -fx-pref-width: 160px;"); // Ensures dropdown width
                }
            }
        };
        floorSelector.setCellFactory(cellFactory);
        floorSelector.setButtonCell(cellFactory.call(null));

        topSection.getChildren().add(floorSelector);

        // Center section with main icon or floor image
        StackPane centerSection = new StackPane();
        centerSection.setAlignment(Pos.CENTER);

        // ImageView for floor images
        ImageView floorImageView = new ImageView();
        floorImageView.setFitWidth(250);
        floorImageView.setFitHeight(250);
        floorImageView.setPreserveRatio(true);

        // Set initial image
        floorImageView.setImage(new Image(getClass().getResourceAsStream("/images/f1.png")));

        centerSection.getChildren().add(floorImageView);

        // Listen for floor selection changes
        floorSelector.setOnAction(e -> {
            int selectedIndex = floorSelector.getSelectionModel().getSelectedIndex() + 1;
            String imagePath = "/images/f" + selectedIndex + ".png";
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            floorImageView.setImage(img);
        });

        // Bottom navigation
        HBox bottomNav = new HBox();
        bottomNav.setPrefHeight(80);
        bottomNav.setStyle("-fx-background-color: #800000;");
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setSpacing(60);

        // Create navigation icons
        SVGPath homeIconSVG = new SVGPath();
        homeIconSVG.setContent("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
        homeIconSVG.setFill(Color.WHITE);
        homeIconSVG.setScaleX(1.5);
        homeIconSVG.setScaleY(1.5);

        StackPane homeIcon = new StackPane(homeIconSVG);
        homeIcon.setPrefSize(30, 30);

        SVGPath mapIconSVG = new SVGPath();
        mapIconSVG.setContent(
                "M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM15 19l-6-2.11V5l6 2.11V19z");
        mapIconSVG.setFill(Color.web("#FFDF00")); // Map icon is yellow by default
        mapIconSVG.setScaleX(1.2);
        mapIconSVG.setScaleY(1.2);

        StackPane mapIcon = new StackPane(mapIconSVG);
        mapIcon.setPrefSize(30, 30);

        // Other icons
        Button starButton = createNavButton(createStarIcon(), "");
        Button profileButton = createNavButton(createProfileIcon(), "");

        // Create navigation buttons
        Button homeButton = createNavButton(homeIcon, "");
        Button mapButton = createNavButton(mapIcon, "");

        // Add buttons to navigation bar
        bottomNav.getChildren().addAll(homeButton, starButton, mapButton, profileButton);

        // Set up the main layout
        mainContainer.setTop(topSection);
        mainContainer.setCenter(centerSection);
        mainContainer.setBottom(bottomNav);

        Scene scene = new Scene(mainContainer, 375, 667);
        primaryStage.setTitle("PUP Floor Selector");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Navigation Button Actions

        // Home button
        homeButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.web("#FFDF00")); // Yellow
            mapIconSVG.setFill(Color.WHITE); // Map icon to white

            // Switch to HomePage
            try {
                new HomePage().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Map button
        mapButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.WHITE);
            mapIconSVG.setFill(Color.web("#FFD700"));
        });
    }

    // Navigation icons
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
        button.setPrefSize(30, 30);
        button.setFocusTraversable(false);

        // Optional: Add action handler
        // button.setOnAction(e -> System.out.println(label + " clicked"));

        return button;
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