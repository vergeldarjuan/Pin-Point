package pinpoint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Map extends Application {
    private PinPointLoginSystem.User userData;

    public Map() {
        this.userData = null;
    }

    public Map(PinPointLoginSystem.User userData) {
        this.userData = userData;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPrefSize(400, 667);
        mainContainer.setStyle("-fx-background-color: #F8F8F8;");

        // Top Section
        VBox topSection = new VBox(10);
        topSection.setPadding(new Insets(20, 20, 0, 20));
        topSection.setAlignment(Pos.TOP_LEFT);

        ComboBox<String> floorSelector = new ComboBox<>();
        floorSelector.getItems().addAll("1st Floor", "2nd Floor", "3rd Floor", "4th Floor", "5th Floor", "6th Floor");
        floorSelector.setValue("1st Floor");
        floorSelector.setPrefWidth(160);
        floorSelector.setPrefHeight(35);
        floorSelector.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-family: Arial; " +
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 18; -fx-border-radius: 18;" +
                "-fx-cursor: hand;");

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
                    setStyle("-fx-background-color: #800000;");
                }
            }
        };
        floorSelector.setCellFactory(cellFactory);
        floorSelector.setButtonCell(cellFactory.call(null));

        Button legendButton = new Button("Legend");
        legendButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");
        legendButton.setPadding(new Insets(5, 15, 5, 15));
        legendButton.setOnAction(e -> showRoomNames());

        Button navigateButton = new Button("Navigate");
        navigateButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: #800000; -fx-font-weight: bold; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");
        navigateButton.setPadding(new Insets(5, 15, 5, 15));
        navigateButton.setOnAction(e -> {
            try {
                new Location(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox buttonContainer = new HBox(10, legendButton, navigateButton);
        topSection.getChildren().addAll(floorSelector, buttonContainer);

        // Center Section with Zoomable Image
        ImageView floorImageView = new ImageView();
        floorImageView.setPreserveRatio(true);
        floorImageView.setFitWidth(380);
        floorImageView.setFitHeight(380);

        try {
            floorImageView.setImage(new Image(getClass().getResourceAsStream("/images/f1.png")));
        } catch (Exception e) {
            System.out.println("Image not found");
        }

        StackPane imageContainer = new StackPane(floorImageView);
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(imageContainer);
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        imageContainer.setOnScroll(e -> {
            double zoomFactor = 1.1;
            if (e.getDeltaY() < 0) {
                zoomFactor = 1 / zoomFactor;
            }
            floorImageView.setScaleX(floorImageView.getScaleX() * zoomFactor);
            floorImageView.setScaleY(floorImageView.getScaleY() * zoomFactor);
        });

        floorSelector.setOnAction(e -> {
            int selectedIndex = floorSelector.getSelectionModel().getSelectedIndex() + 1;
            String imagePath = "/images/f" + selectedIndex + ".png";
            try {
                Image img = new Image(getClass().getResourceAsStream(imagePath));
                floorImageView.setImage(img);
                floorImageView.setScaleX(1); // Reset zoom
                floorImageView.setScaleY(1);
            } catch (Exception ex) {
                System.out.println("Could not load image: " + imagePath);
            }
        });

        HBox bottomNav = createBottomNavigation(primaryStage);

        mainContainer.setTop(topSection);
        mainContainer.setCenter(scrollPane);
        mainContainer.setBottom(bottomNav);

        Scene scene = new Scene(mainContainer, 375, 667);
        primaryStage.setTitle("Maps");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private HBox createBottomNavigation(Stage primaryStage) {
        HBox bottomNav = new HBox();
        bottomNav.setPrefHeight(80);
        bottomNav.setStyle("-fx-background-color: #800000;");
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setSpacing(60);

        SVGPath homeIconSVG = new SVGPath();
        homeIconSVG.setContent("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
        homeIconSVG.setFill(Color.WHITE);
        homeIconSVG.setScaleX(1.5);
        homeIconSVG.setScaleY(1.5);
        StackPane homeIcon = new StackPane(homeIconSVG);

        SVGPath starIconSVG = new SVGPath();
        starIconSVG.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starIconSVG.setFill(Color.WHITE);
        starIconSVG.setScaleX(1.2);
        starIconSVG.setScaleY(1.2);
        StackPane starIcon = new StackPane(starIconSVG);

        SVGPath mapIconSVG = new SVGPath();
        mapIconSVG.setContent("M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM15 19l-6-2.11V5l6 2.11V19z");
        mapIconSVG.setFill(Color.web("#FFD700"));
        mapIconSVG.setScaleX(1.2);
        mapIconSVG.setScaleY(1.2);
        StackPane mapIcon = new StackPane(mapIconSVG);

        SVGPath profileIconSVG = new SVGPath();
        profileIconSVG.setContent("M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z");
        profileIconSVG.setFill(Color.WHITE);
        profileIconSVG.setScaleX(1.2);
        profileIconSVG.setScaleY(1.2);
        StackPane profileIcon = new StackPane(profileIconSVG);

        Button homeButton = createNavButton(homeIcon, "");
        Button starButton = createNavButton(starIcon, "");
        Button mapButton = createNavButton(mapIcon, "");
        Button profileButton = createNavButton(profileIcon, "");

        bottomNav.getChildren().addAll(homeButton, starButton, mapButton, profileButton);

        homeButton.setOnAction(e -> {
            try {
                new HomePage(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        starButton.setOnAction(e -> {
            try {
                new Location(userData).start(primaryStage);
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

        return bottomNav;
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
        button.setPrefSize(30, 30);
        button.setFocusTraversable(false);
        return button;
    }

    private void showRoomNames() {
        Stage legendStage = new Stage();
        legendStage.setTitle("Room Names & Locations");

        TextArea legendText = new TextArea();
        legendText.setEditable(false);
        legendText.setWrapText(true);
        legendText.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 13px;");
        legendText.setText(getRoomLegendText());

        ScrollPane scrollPane = new ScrollPane(legendText);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(400, 500);

        VBox layout = new VBox(scrollPane);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout);
        legendStage.setScene(scene);
        legendStage.setResizable(false);
        legendStage.show();
    }

    private String getRoomLegendText() {
        return "AVAILABLE ROOMS FOR NAVIGATION:\n\n" +
                "═══════════════════════════════════════\n" +
                "1st Floor East:\n" +
                "• 1E01 - East Wing Office\n" +
                "• DMST - Digital Media & Sound Technology\n" +
                "• CCHQ - Campus Security Headquarters\n" +
                "• Clinic - University Health Center\n" +
                "• Facilities - Facilities Management\n\n" +
                "1st Floor West:\n" +
                "• Admission - Admissions Office\n" +
                "• Registration - Registration Office\n\n" +
                "... and more rooms listed.";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
