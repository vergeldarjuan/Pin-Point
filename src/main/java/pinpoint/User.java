package pinpoint;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class User extends Application {

    private PinPointLoginSystem.User userData; // Store user info

    public User() {
    }

    public User(PinPointLoginSystem.User userData) {
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
        contentArea.setPadding(new Insets(30, 30, 30, 30));
        contentArea.setSpacing(20);
        contentArea.setAlignment(Pos.TOP_CENTER);

        // Title
        Label title = new Label("PERSONAL DETAILS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#800000"));
        title.setAlignment(Pos.CENTER);

        // Profile picture section
        VBox profileSection = new VBox();
        profileSection.setAlignment(Pos.CENTER);
        profileSection.setSpacing(10);

        // Rectangle profile area with pylon.jpg
        StackPane profileContainer = new StackPane();
        profileContainer.setPrefSize(120, 120);

        // Rectangle border
        Rectangle borderRect = new Rectangle(300, 120);
        borderRect.setArcWidth(20);
        borderRect.setArcHeight(20);
        borderRect.setFill(Color.TRANSPARENT);
        borderRect.setStroke(Color.web("#800000"));
        borderRect.setStrokeWidth(3);

        // Pylon image as profile picture
        ImageView pylonImage = new ImageView(new Image("/images/pylon.jpg"));
        pylonImage.setFitWidth(295);
        pylonImage.setFitHeight(115);
        pylonImage.setPreserveRatio(false);
        pylonImage.setSmooth(true);
        pylonImage.setClip(null);

        profileContainer.getChildren().addAll(borderRect, pylonImage);

        profileSection.getChildren().add(profileContainer);

        // Form fields (with references for editing)
        TextField[] nameField = new TextField[1];
        TextField[] usernameField = new TextField[1];
        TextField[] emailField = new TextField[1];
        TextField[] idField = new TextField[1];
        TextField[] courseField = new TextField[1];

        VBox formSection = new VBox();
        formSection.setSpacing(15);
        formSection.setAlignment(Pos.CENTER);

        VBox nameSection = createFormField("Your Name", userData != null ? userData.name : "", false, nameField);
        VBox usernameSection = createFormField("Username", userData != null ? userData.username : "", false,
                usernameField);
        VBox emailSection = createFormField("Email", userData != null ? userData.email : "", false, emailField);
        VBox idSection = createFormField("PUP Student/Employee ID", userData != null ? userData.pupId : "", false,
                idField);
        VBox courseSection = createFormField("Course/Position", userData != null ? userData.coursePosition : "", false,
                courseField);

        formSection.getChildren().addAll(nameSection, usernameSection, emailSection, idSection, courseSection);

        // Edit/Save button
        Button editButton = new Button("EDIT");
        editButton.setPrefWidth(140);
        editButton.setPrefHeight(40);
        editButton.setStyle(
                "-fx-background-color: #800000; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-family: Arial; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-cursor: hand;");
        editButton.setOnAction(e -> {
            if (editButton.getText().equals("EDIT")) {
                // Enable editing
                nameField[0].setEditable(true);
                usernameField[0].setEditable(true);
                emailField[0].setEditable(true);
                idField[0].setEditable(true);
                courseField[0].setEditable(true);
                editButton.setText("SAVE");
            } else {
                // Save changes
                if (userData != null) {
                    userData.name = nameField[0].getText();
                    userData.username = usernameField[0].getText();
                    userData.email = emailField[0].getText();
                    userData.pupId = idField[0].getText();
                    userData.coursePosition = courseField[0].getText();
                }
                nameField[0].setEditable(false);
                usernameField[0].setEditable(false);
                emailField[0].setEditable(false);
                idField[0].setEditable(false);
                courseField[0].setEditable(false);
                editButton.setText("EDIT");
            }
        });

        // Add edit button to formSection
        formSection.getChildren().add(editButton);

        // Log Out button
        Button logOutButton = new Button("LOG OUT");
        logOutButton.setPrefWidth(140);
        logOutButton.setPrefHeight(40);
        logOutButton.setAlignment(Pos.CENTER);
        logOutButton.setStyle(
                "-fx-background-color: #FFD700; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-family: Arial; " +
                        "-fx-font-size: 15px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-cursor: hand;");

        // HBox for Edit and Log Out buttons
        HBox buttonRow = new HBox(15);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.getChildren().setAll(editButton, logOutButton);

        // Add buttonRow to formSection
        formSection.getChildren().add(buttonRow);

        // Add logout confirmation
        logOutButton.setOnAction(e -> {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Logout");
            confirmAlert.setHeaderText("Are you sure you want to log out?");
            confirmAlert.setContentText("You will need to sign in again to access your account.");

            // Customize button text
            ButtonType yesButton = new ButtonType("Yes, Log Out");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirmAlert.getButtonTypes().setAll(yesButton, cancelButton);

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == yesButton) {
                    try {
                        new PinPointLoginSystem().start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

        });

        // Add all sections to content area
        contentArea.getChildren().addAll(
                title,
                profileSection,
                formSection);

        // Scroll pane for content
        ScrollPane scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #F8F8F8; -fx-background: #F8F8F8;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Bottom navigation bar
        HBox bottomNav = new HBox();
        bottomNav.setPrefHeight(80);
        bottomNav.setStyle("-fx-background-color: #800000;");
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setSpacing(60);

        // Navigation icons (SVGPath)
        SVGPath homeSVG = new SVGPath();
        homeSVG.setContent("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
        homeSVG.setFill(Color.WHITE);
        homeSVG.setScaleX(1.5);
        homeSVG.setScaleY(1.5);

        SVGPath starSVG = new SVGPath();
        starSVG.setContent("M12 17.27L18.18 21l-1.64-7.03L22 9.24l-7.19-.61L12 2 9.19 8.63 2 9.24l5.46 4.73L5.82 21z");
        starSVG.setFill(Color.WHITE);
        starSVG.setScaleX(1.2);
        starSVG.setScaleY(1.2);

        SVGPath mapSVG = new SVGPath();
        mapSVG.setContent(
                "M20.5 3l-.16.03L15 5.1 9 3 3.36 4.9c-.21.07-.36.25-.36.48V20.5c0 .28.22.5.5.5l.16-.03L9 18.9l6 2.1 5.64-1.9c.21-.07.36-.25.36-.48V3.5c0-.28-.22-.5-.5-.5zM15 19l-6-2.11V5l6 2.11V19z");
        mapSVG.setFill(Color.WHITE);
        mapSVG.setScaleX(1.2);
        mapSVG.setScaleY(1.2);

        SVGPath profileSVG = new SVGPath();
        profileSVG.setContent(
                "M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z");
        profileSVG.setFill(Color.web("#FFD700"));
        profileSVG.setScaleX(1.2);
        profileSVG.setScaleY(1.2);

        // Create navigation buttons
        Button homeButton = new Button();
        homeButton.setGraphic(homeSVG);
        homeButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        homeButton.setPrefSize(30, 30);

        Button starButton = new Button();
        starButton.setGraphic(starSVG);
        starButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        starButton.setPrefSize(30, 30);

        Button mapButton = new Button();
        mapButton.setGraphic(mapSVG);
        mapButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        mapButton.setPrefSize(30, 30);

        Button profileButton = new Button();
        profileButton.setGraphic(profileSVG);
        profileButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        profileButton.setPrefSize(30, 30);

        bottomNav.getChildren().setAll(homeButton, starButton, mapButton, profileButton);

        // --- Navigation actions ---
        homeButton.setOnAction(e -> {
            homeSVG.setFill(Color.web("#FFD700"));
            mapSVG.setFill(Color.WHITE);
            profileSVG.setFill(Color.WHITE);
            starSVG.setFill(Color.WHITE);
            try {
                // Pass userData to HomePage so changes are reflected
                new HomePage(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        mapButton.setOnAction(e -> {
            homeSVG.setFill(Color.WHITE);
            mapSVG.setFill(Color.web("#FFD700"));
            profileSVG.setFill(Color.WHITE);
            starSVG.setFill(Color.WHITE);
            try {
                new Map(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        profileButton.setOnAction(e -> {
            homeSVG.setFill(Color.WHITE);
            mapSVG.setFill(Color.WHITE);
            profileSVG.setFill(Color.web("#FFD700"));
            starSVG.setFill(Color.WHITE);
        });

        starButton.setOnAction(e -> {
            homeSVG.setFill(Color.WHITE);
            mapSVG.setFill(Color.WHITE);
            profileSVG.setFill(Color.WHITE);
            starSVG.setFill(Color.web("#FFD700"));
            try {
                new Location(userData).start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Set up the main layout
        mainContainer.setCenter(scrollPane);
        mainContainer.setBottom(bottomNav);

        Scene scene = new Scene(mainContainer, 375, 667);
        primaryStage.setTitle("Profile Information");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createFormField(String labelText, String value, boolean editable, TextField[] fieldRef) {
        VBox fieldContainer = new VBox();
        fieldContainer.setSpacing(5);

        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#333333"));

        TextField textField = new TextField(value);
        textField.setPrefHeight(40);
        textField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #800000; " +
                        "-fx-border-width: 1.5; " +
                        "-fx-border-radius: 8; " +
                        "-fx-background-radius: 8; " +
                        "-fx-padding: 10; " +
                        "-fx-font-size: 14px;");
        textField.setEditable(editable);

        if (fieldRef != null && fieldRef.length > 0) {
            fieldRef[0] = textField;
        }

        fieldContainer.getChildren().addAll(label, textField);
        return fieldContainer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}