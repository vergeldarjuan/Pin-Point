package pinpoint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Map extends Application {
    private PinPointLoginSystem.User userData;

    public Map(PinPointLoginSystem.User userData) {
        this.userData = userData;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPrefSize(400, 600);
        mainContainer.setStyle("-fx-background-color: #F8F8F8;");

        // Top Section
        VBox topSection = new VBox(10); // spacing of 10 between children
        topSection.setPadding(new Insets(20, 20, 0, 20));
        topSection.setAlignment(Pos.TOP_LEFT);

        ComboBox<String> floorSelector = new ComboBox<>();
        floorSelector.getItems().addAll(
                "1st Floor", "2nd Floor", "3rd Floor", "4th Floor", "5th Floor", "6th Floor");
        floorSelector.setValue("1st Floor");
        floorSelector.setPrefWidth(160);
        floorSelector.setPrefHeight(35);
        floorSelector.setStyle(
                "-fx-background-color: #800000; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-family: Arial; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 18; " +
                        "-fx-border-radius: 18;" +
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

        // Legend Button
        Button legendButton = new Button("Legend");
        legendButton.setStyle(
                "-fx-background-color: #800000; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx.cursor: hand;");
        legendButton.setPadding(new Insets(5, 15, 5, 15));
        legendButton.setOnAction(e -> RoomNames());

        topSection.getChildren().addAll(floorSelector, legendButton);

        // Center Section
        StackPane centerSection = new StackPane();
        centerSection.setAlignment(Pos.CENTER);

        ImageView floorImageView = new ImageView();
        floorImageView.setFitWidth(250);
        floorImageView.setFitHeight(250);
        floorImageView.setPreserveRatio(true);
        floorImageView.setImage(new Image(getClass().getResourceAsStream("/images/f1.jpg")));

        centerSection.getChildren().add(floorImageView);

        floorSelector.setOnAction(e -> {
            int selectedIndex = floorSelector.getSelectionModel().getSelectedIndex() + 1;
            String imagePath = "/images/f" + selectedIndex + ".jpg";
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            floorImageView.setImage(img);
        });

        // Bottom Navigation
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

        SVGPath mapIconSVG = new SVGPath();
        mapIconSVG.setContent(
                "M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM15 19l-6-2.11V5l6 2.11V19z");
        mapIconSVG.setFill(Color.web("#FFD700"));
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

        Button homeButton = createNavButton(homeIcon, "");
        Button starButton = createNavButton(createStarIcon(), "");
        Button mapButton = createNavButton(mapIcon, "");
        Button profileButton = createNavButton(profileIcon, "");

        bottomNav.getChildren().addAll(homeButton, starButton, mapButton, profileButton);

        mainContainer.setTop(topSection);
        mainContainer.setCenter(centerSection);
        mainContainer.setBottom(bottomNav);

        Scene scene = new Scene(mainContainer, 375, 667);
        primaryStage.setTitle("Maps");
        primaryStage.setScene(scene);
        primaryStage.show();

        homeButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.web("#FFD700"));
            mapIconSVG.setFill(Color.WHITE);
            profileIconSVG.setFill(Color.WHITE);
            try {
                new HomePage(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        starButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.WHITE);
            mapIconSVG.setFill(Color.WHITE);
            profileIconSVG.setFill(Color.WHITE);
            try {
                new Location(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        mapButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.WHITE);
            mapIconSVG.setFill(Color.web("#FFD700"));
            profileIconSVG.setFill(Color.WHITE);
        });

        profileButton.setOnAction(e -> {
            homeIconSVG.setFill(Color.WHITE);
            mapIconSVG.setFill(Color.WHITE);
            profileIconSVG.setFill(Color.web("#FFD700"));
            try {
                new User(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
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

    private void RoomNames() {
        Stage legendStage = new Stage();
        legendStage.setTitle("Room Names");

        TextArea legendText = new TextArea();
        legendText.setEditable(false);
        legendText.setWrapText(true);
        legendText.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 13px;");
        legendText.setText(
                " 6th Floor South:\n601 - CAL Dean\n602 - CPSPA Grad\n603 - CPSPA\n604 - Education Grad\n605 - CBA Grad\n607 - CAL Chair\n610 - Data & Stats\n612 - CSSD Chair\n613 - CSSD Dean\n614 - Psych Lab\n\n"
                        +
                        " 5th Floor South:\n505 - Consultation\n506 - CCIS Faculty\n507 - Lab Ops\n512 - Dean Office\n514 - Science Faculty\n515 - Math & Stats\n516 - Comp Lab\n517 - Unknown\n518 - Accountancy\n\n"
                        +
                        " 4th Floor South:\n401–425 - Research, Language, Gender, Innovation\n\n" +
                        " 3rd Floor South:\n311–318 - Admin, Planning, Legal, Intl, HR\n\n" +
                        " 2nd Floor South:\n202–215 - President, VPs, Server Room\n\n" +
                        " 1st Floor South:\n101–109 - Accounting, Budget, Registrar\n\n" +
                        " 2nd Floor East:\n201–208 - DCSD, NSTP, Faculty\n\n" +
                        " 1st Floor East:\n101, DMST, CCHQ, Clinic, Facilities\n\n" +
                        " 1st Floor West:\nAdmission & Registration");

        ScrollPane scrollPane = new ScrollPane(legendText);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(400, 500);
        VBox layout = new VBox(scrollPane);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout);
        legendStage.setScene(scene);
        legendStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
