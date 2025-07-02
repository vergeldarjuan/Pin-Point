package pinpoint;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PinPointLoginSystem extends Application {

    // In-memory user storage (in real app, use database)
    private Map<String, User> users = new HashMap<>();
    private Stage primaryStage;

    // User model
    static class User {
        String name;
        String username;
        String email;
        String password;
        String pupId;
        String coursePosition;

        User(String name, String username, String email, String password, String pupId, String coursePosition) {
            this.name = name;
            this.username = username;
            this.email = email;
            this.password = password;
            this.pupId = pupId;
            this.coursePosition = coursePosition;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("PIN*Point");
        primaryStage.setResizable(false);

        showLoginScene();
    }

    private void showLoginScene() {
        VBox loginPanel = createLoginPanel();
        Scene loginScene = new Scene(loginPanel, 375, 667);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showSignUpScene() {
        VBox registerPanel = createRegisterPanel();
        Scene signUpScene = new Scene(registerPanel, 375, 667);
        primaryStage.setScene(signUpScene);
        primaryStage.show();
    }

    private VBox createRegisterPanel() {
        VBox panel = new VBox(20);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #f5f5f5;");
        panel.setPadding(new Insets(30, 30, 30, 30));

        // PIN point logo
        HBox logoBox = new HBox(5);
        logoBox.setAlignment(Pos.CENTER);
        Text pinText = new Text("PIN");
        pinText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        pinText.setFill(Color.web("#800000"));
        Text starText = new Text("*");
        starText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        starText.setFill(Color.web("#FFDF00"));
        Text pointText = new Text("point");
        pointText.setFont(Font.font("Arial", FontWeight.NORMAL, 28));
        pointText.setFill(Color.web("#800000"));
        logoBox.getChildren().addAll(pinText, starText, pointText);

        // Register title
        Text registerTitle = new Text("Register");
        registerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        registerTitle.setFill(Color.BLACK);

        // Enlarged form fields
        TextField nameField = createStyledTextField("Enter your full name");
        nameField.setPrefHeight(50);
        nameField.setPrefWidth(320);

        TextField usernameField = createStyledTextField("Enter your username");
        usernameField.setPrefHeight(50);
        usernameField.setPrefWidth(320);

        TextField emailField = createStyledTextField("Enter your email");
        emailField.setPrefHeight(50);
        emailField.setPrefWidth(320);

        PasswordField passwordField = createStyledPasswordField("Enter your password");
        passwordField.setPrefHeight(50);
        passwordField.setPrefWidth(320);

        TextField pupIdField = createStyledTextField("Enter your PUP Student/Employee ID");
        pupIdField.setPrefHeight(50);
        pupIdField.setPrefWidth(320);

        TextField coursePositionField = createStyledTextField("Enter your Course/Position");
        coursePositionField.setPrefHeight(50);
        coursePositionField.setPrefWidth(320);

        // Terms checkbox
        CheckBox termsBox = new CheckBox();
        termsBox.setStyle("-fx-text-fill: black;");
        HBox termsRow = new HBox(10);
        termsRow.setAlignment(Pos.CENTER_LEFT);
        Text agreeText = new Text("I agree to the ");
        Text termsLink = new Text("terms & conditions");
        termsLink.setFill(Color.web("#4A90E2"));
        termsLink.setStyle("-fx-cursor: hand;");
        termsRow.getChildren().addAll(termsBox, agreeText, termsLink);

        // Sign Up button
        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setPrefWidth(320);
        signUpBtn.setPrefHeight(50);
        signUpBtn.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; " +
                "-fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 25;");

        // Already have account
        HBox accountBox = new HBox(5);
        accountBox.setAlignment(Pos.CENTER);
        Text alreadyText = new Text("Already have an account? ");
        Text loginLink = new Text("Login");
        loginLink.setFill(Color.web("#8B0000"));
        loginLink.setStyle("-fx-cursor: hand; -fx-font-weight: bold;");
        accountBox.getChildren().addAll(alreadyText, loginLink);

        // Sign up button action
        signUpBtn.setOnAction(e -> handleSignUp(nameField, usernameField, emailField,
                passwordField, pupIdField, coursePositionField, termsBox));

        // Switch to login scene
        loginLink.setOnMouseClicked(e -> showLoginScene());

        panel.getChildren().addAll(logoBox, registerTitle,
                createFieldWithLabel("Name", nameField),
                createFieldWithLabel("Username", usernameField),
                createFieldWithLabel("Email", emailField),
                createFieldWithLabel("Password", passwordField),
                createFieldWithLabel("PUP Student/Employee ID", pupIdField),
                createFieldWithLabel("Course/Position", coursePositionField),
                termsRow, signUpBtn, accountBox);

        // Wrap the panel in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(panel);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f5f5f5; -fx-border-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox root = new VBox(scrollPane);
        root.setPrefSize(375, 667);
        root.setStyle("-fx-background-color: #f5f5f5;");

        return root;
    }

    private VBox createLoginPanel() {
        // Main container
        BorderPane mainContainer = new BorderPane();
        mainContainer.setPrefSize(375, 667);
        mainContainer.setStyle("-fx-background-color: #F8F8F8;");

        // Header section
        VBox headerSection = new VBox();
        headerSection.setAlignment(Pos.CENTER);
        headerSection.setPadding(new Insets(100, 0, 20, 0));

        // PIN*Point logo
        HBox logoBox = new HBox(5);
        logoBox.setAlignment(Pos.CENTER);
        Text pinText = new Text("PIN");
        pinText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        pinText.setFill(Color.web("#800000"));
        Text starText = new Text("*");
        starText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        starText.setFill(Color.web("#FFDF00"));
        Text pointText = new Text("point");
        pointText.setFont(Font.font("Arial", FontWeight.NORMAL, 28));
        pointText.setFill(Color.web("#800000"));
        logoBox.getChildren().addAll(pinText, starText, pointText);

        headerSection.getChildren().add(logoBox);

        // Form area (white card)
        VBox formCard = new VBox(18);
        formCard.setAlignment(Pos.CENTER);
        formCard.setPadding(new Insets(30, 30, 30, 30));
        formCard.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, #cccccc, 8, 0, 0, 2);");
        formCard.setMaxWidth(320);

        Label loginLabel = new Label("Login");
        loginLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        loginLabel.setTextFill(Color.web("#800000"));

        TextField emailField = createStyledTextField("Enter your email");
        PasswordField passwordField = createStyledPasswordField("Enter your password");

        Button signInBtn = new Button("Sign In");
        signInBtn.setPrefWidth(220);
        signInBtn.setPrefHeight(40);
        signInBtn.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        signInBtn.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-background-radius: 20;");

        // Don't have account
        HBox accountBox = new HBox(5);
        accountBox.setAlignment(Pos.CENTER);
        Label dontHaveText = new Label("Don't have an account?");
        dontHaveText.setFont(Font.font("Arial", 13));
        dontHaveText.setTextFill(Color.GRAY);
        Hyperlink registerLink = new Hyperlink("Register");
        registerLink.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        registerLink.setTextFill(Color.web("#800000"));
        registerLink.setBorder(Border.EMPTY);
        registerLink.setPadding(Insets.EMPTY);
        accountBox.getChildren().addAll(dontHaveText, registerLink);

        // Sign in button action
        signInBtn.setOnAction(e -> handleSignIn(emailField, passwordField));
        registerLink.setOnAction(e -> showSignUpScene());

        formCard.getChildren().addAll(
                loginLabel,
                createFieldWithLabel("Email", emailField),
                createFieldWithLabel("Password", passwordField),
                signInBtn,
                accountBox);

        // Center the form card
        VBox centerBox = new VBox(formCard);
        centerBox.setAlignment(Pos.CENTER);

        mainContainer.setTop(headerSection);
        mainContainer.setCenter(centerBox);

        VBox root = new VBox(mainContainer);
        return root;
    }

    private VBox createFieldWithLabel(String labelText, Control field) {
        VBox fieldBox = new VBox(8);
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        label.setTextFill(Color.BLACK);
        fieldBox.getChildren().addAll(label, field);
        return fieldBox;
    }

    private TextField createStyledTextField(String promptText) {
        TextField field = new TextField();
        field.setPromptText(promptText);
        field.setPrefHeight(40);
        field.setPrefWidth(290);
        field.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; " +
                "-fx-border-color: #800000; " +
                "-fx-border-width: 1.5; " +
                "-fx-background-color: white; -fx-padding: 0 15 0 15;");
        return field;
    }

    private PasswordField createStyledPasswordField(String promptText) {
        PasswordField field = new PasswordField();
        field.setPromptText(promptText);
        field.setPrefHeight(40);
        field.setPrefWidth(290);
        field.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; " +
                "-fx-border-color: #800000; " +
                "-fx-border-width: 1.5; " +
                "-fx-background-color: white; -fx-padding: 0 15 0 15;");
        return field;
    }

    private void handleSignUp(TextField nameField, TextField usernameField, TextField emailField,
            PasswordField passwordField, TextField pupIdField, TextField coursePositionField,
            CheckBox termsBox) {

        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String pupId = pupIdField.getText().trim();
        String coursePosition = coursePositionField.getText().trim();

        // Validation
        if (name.isEmpty() || username.isEmpty() || email.isEmpty() ||
                password.isEmpty() || pupId.isEmpty() || coursePosition.isEmpty()) {
            showAlert("Error", "All fields are required!");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert("Error", "Please enter a valid email address!");
            return;
        }

        if (password.length() < 6) {
            showAlert("Error", "Password must be at least 6 characters long!");
            return;
        }

        if (!termsBox.isSelected()) {
            showAlert("Error", "Please agree to the terms and conditions!");
            return;
        }

        if (users.containsKey(email)) {
            showAlert("Error", "An account with this email already exists!");
            return;
        }

        if (users.values().stream().anyMatch(u -> u.username.equals(username))) {
            showAlert("Error", "Username already taken!");
            return;
        }

        // Create user
        User newUser = new User(name, username, email, password, pupId, coursePosition);
        users.put(email, newUser);

        showAlert("Success", "Account created successfully! You can now log in.");

        // Clear fields
        nameField.clear();
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        pupIdField.clear();
        coursePositionField.clear();
        termsBox.setSelected(false);

        // Go back to login after successful sign up
        showLoginScene();
    }

    private void handleSignIn(TextField emailField, PasswordField passwordField) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both email and password!");
            return;
        }

        User user = users.get(email);
        if (user == null || !user.password.equals(password)) {
            showAlert("Error", "Invalid email or password!");
            return;
        }

        // Successful login - redirect to User page with user data
        try {
            new pinpoint.User(user).start(primaryStage);
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Failed to load User Page.");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}