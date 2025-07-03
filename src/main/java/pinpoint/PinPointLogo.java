package pinpoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;

public class PinPointLogo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("PIN*point");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 800);
            frame.setLocationRelativeTo(null);

            LogoPanel logoPanel = new LogoPanel();
            frame.add(logoPanel);
            frame.setVisible(true);

            // Start transition after 2 seconds
            Timer delayTimer = new Timer(100, null);
            delayTimer.setRepeats(false);
            delayTimer.addActionListener(e -> {
                new Timer(2000, evt -> logoPanel.startTransition(() -> {
                    frame.dispose();
                    AppLauncher.launchApp();
                })).start();
            });
            delayTimer.start();
        });
    }

    static class LogoPanel extends JPanel {
        private Color currentBg = new Color(0xFAF3E0); // Start: cream
        private Color targetBg = new Color(0x800000); // End: maroon
        private float progress = 0.0f; // animation progress
        private Timer fadeTimer;
        private Runnable onComplete;

        private Font poppinsExtraBold, poppinsRegular;

        public LogoPanel() {
            setBackground(currentBg);
            try {
                poppinsExtraBold = Font.createFont(Font.TRUETYPE_FONT, new File("Poppins-ExtraBold.ttf"))
                        .deriveFont(48f);
                poppinsRegular = Font.createFont(Font.TRUETYPE_FONT, new File("Poppins-Regular.ttf")).deriveFont(48f);

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(poppinsExtraBold);
                ge.registerFont(poppinsRegular);
            } catch (IOException | FontFormatException ex) {
                System.err.println("Could not load fonts. Using default.");
                poppinsExtraBold = new Font("SansSerif", Font.BOLD, 48);
                poppinsRegular = new Font("SansSerif", Font.PLAIN, 48);
            }
        }

        public void startTransition(Runnable onComplete) {
            this.onComplete = onComplete;
            fadeTimer = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    progress += 0.02f;
                    if (progress >= 1f) {
                        progress = 1f;
                        fadeTimer.stop();
                        if (onComplete != null)
                            onComplete.run();
                    }
                    repaint();
                }
            });
            fadeTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Interpolate background color
            int r = (int) (currentBg.getRed() * (1 - progress) + targetBg.getRed() * progress);
            int gCol = (int) (currentBg.getGreen() * (1 - progress) + targetBg.getGreen() * progress);
            int b = (int) (currentBg.getBlue() * (1 - progress) + targetBg.getBlue() * progress);
            Color blendedBg = new Color(r, gCol, b);

            g2.setColor(blendedBg);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Decide text color based on progress
            Color textColor = progress >= 0.5f ? Color.WHITE : new Color(0x800000);
            Color starColor = progress >= 0.5f ? new Color(0xFFD700) : new Color(0x800000);

            // Draw "PIN*point"
            int x = getWidth() / 2 - 100;
            int y = getHeight() / 2;

            g2.setFont(poppinsExtraBold);
            g2.setColor(textColor);
            g2.drawString("PIN", x, y);
            int pinWidth = g2.getFontMetrics().stringWidth("PIN");

            g2.setColor(starColor);
            g2.drawString("*", x + pinWidth, y);
            int starWidth = g2.getFontMetrics().stringWidth("*");

            g2.setFont(poppinsRegular);
            g2.setColor(textColor);
            g2.drawString("point", x + pinWidth + starWidth, y);
        }
    }
}
