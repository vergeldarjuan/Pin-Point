package pinpoint;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class OpeningController {

    @FXML
    private ImageView shadowImage;
    @FXML
    private ImageView pinImage;
    @FXML
    private Text pinpointText;
    @FXML
    private Rectangle backgroundRect;

    public void initialize() {

        // Load images (adjust paths as needed)
        shadowImage.setImage(new Image(getClass().getResource("/images/logo.png").toExternalForm()));
        // pinImage.setImage(new
        // Image(getClass().getResource("/images/pin.png").toExternalForm()));

        playAnimation();
    }

    private void playAnimation() {

        shadowImage.setVisible(true);

        // Step 1: Show shadow
        PauseTransition pause1 = new PauseTransition(Duration.seconds(1));

        // Step 2: Drop pin
        pause1.setOnFinished(e -> {
            pinImage.setVisible(true);
            TranslateTransition drop = new TranslateTransition(Duration.seconds(1), pinImage);
            drop.setFromY(-100);
            drop.setToY(0);
            drop.setOnFinished(ev -> showText());
            drop.play();
        });

        pause1.play();
    }

    private void showText() {

        PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
        pause2.setOnFinished(e -> {

            pinpointText.setVisible(true);
            FadeTransition fadeText = new FadeTransition(Duration.seconds(1), pinpointText);
            fadeText.setFromValue(0);
            fadeText.setToValue(1);

            fadeText.setOnFinished(ev -> showFinalLogo());
            fadeText.play();
        });

        pause2.play();
    }

    private void showFinalLogo() {

        PauseTransition pause3 = new PauseTransition(Duration.seconds(1));
        pause3.setOnFinished(e -> {

            backgroundRect.setVisible(true);
            pinpointText.setStyle("-fx-font-size: 28px; -fx-fill: #FFFFFF;");
            pinpointText.setLayoutX(160);
            pinpointText.setLayoutY(300);

        });

        pause3.play();
    }
}
