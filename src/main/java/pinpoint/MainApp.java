package pinpoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.application.Platform;

import javax.swing.*;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/pinpoint/opening.fxml"));
            Scene scene = new Scene(root, 400, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Opening Animation");
            primaryStage.show();

            // Delay or detect when to show your Swing app (example: after 5 seconds)
            new Thread(() -> {
                try {
                    Thread.sleep(5000); // Wait 5 seconds or trigger after animation completes
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Close JavaFX stage and open Swing app
                Platform.runLater(() -> {
                    primaryStage.close();
                    launchSwingApp();
                });
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch JavaFX, Swing starts after splash
    }

    private void launchSwingApp() {
        SwingUtilities.invokeLater(() -> {
            PinPointLoginSystem appInterface = new PinPointLoginSystem();
            appInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            appInterface.setVisible(true);
        });
    }
}
