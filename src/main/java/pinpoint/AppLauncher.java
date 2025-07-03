package pinpoint;

import javafx.application.Application;

public class AppLauncher {
    private static boolean javafxLaunched = false;

    public static void launchApp() {
        if (!javafxLaunched) {
            javafxLaunched = true;
            new Thread(() -> Application.launch(PinPointLoginSystem.class)).start();
        }
    }
}