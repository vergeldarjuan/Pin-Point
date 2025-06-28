import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MobileAppInterface extends JFrame {
    private JLabel statusLabel;
    private JPanel contentPanel;
    private JPanel bottomNavPanel;

    public MobileAppInterface() {
        setupFrame();
        // createStatusBar();
        createContentArea();
        createBottomNavigation();

        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Mobile App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(375, 667); // iPhone-like dimensions
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Set background color
        getContentPane().setBackground(Color.WHITE);
    }

    // private void createStatusBar() {
    // JPanel statusBar = new JPanel(new BorderLayout());
    // statusBar.setBackground(Color.WHITE);
    // statusBar.setPreferredSize(new Dimension(375, 30));
    // statusBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

    // // Time label
    // JLabel timeLabel = new JLabel("9:41");
    // timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
    // timeLabel.setForeground(Color.BLACK);

    // // Signal and battery indicators
    // JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
    // rightPanel.setBackground(Color.WHITE);

    // // Signal bars
    // JLabel signalLabel = new JLabel("●●●");
    // signalLabel.setFont(new Font("Arial", Font.BOLD, 12));

    // // WiFi symbol
    // JLabel wifiLabel = new JLabel("📶");

    // // Battery
    // JLabel batteryLabel = new JLabel("🔋");

    // rightPanel.add(signalLabel);
    // rightPanel.add(wifiLabel);
    // rightPanel.add(batteryLabel);

    // statusBar.add(timeLabel, BorderLayout.WEST);
    // statusBar.add(rightPanel, BorderLayout.EAST);

    // add(statusBar, BorderLayout.NORTH);
    // }

    private void createContentArea() {
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(248, 248, 248));
        contentPanel.setLayout(new BorderLayout());

        // Add some sample content
        JLabel welcomeLabel = new JLabel("Welcome to the App", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.GRAY);

        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void createBottomNavigation() {
        bottomNavPanel = new JPanel(new GridLayout(1, 4));
        bottomNavPanel.setBackground(new Color(139, 24, 24)); // Dark red color
        bottomNavPanel.setPreferredSize(new Dimension(375, 70));

        // Create navigation buttons

        ImageIcon houseIcon = new ImageIcon(
                "C:/Users/Vergel Dar Juan/Documents/GitHub/Pin-Point/img/house.png");
        JLabel houseLabel = new JLabel(houseIcon);
        ImageIcon favoritesIcon = new ImageIcon(
                "C:/Users/Vergel Dar Juan/Documents/GitHub/Pin-Point/img/favorites.png");
        JLabel favoritesLabel = new JLabel(favoritesIcon);
        ImageIcon menuIcon = new ImageIcon(
                "C:/Users/Vergel Dar Juan/Documents/GitHub/Pin-Point/img/menu.png");
        JLabel menuLabel = new JLabel(menuIcon);
        ImageIcon profileIcon = new ImageIcon(
                "C:/Users/Vergel Dar Juan/Documents/GitHub/Pin-Point/img/profile.png");
        JLabel profileLabel = new JLabel(profileIcon);

        createNavButton(houseLabel, "HOME", false);
        createNavButton(favoritesLabel, "LOCATION", false);
        createNavButton(menuLabel, "MAP", false);
        createNavButton(profileLabel, "PROFILE", false);

        add(bottomNavPanel, BorderLayout.SOUTH);
    }

    private void createNavButton(JLabel icon, String text, boolean isSelected) {
        JButton navButton = new JButton();
        navButton.setLayout(new BorderLayout());
        navButton.setBackground(new Color(139, 24, 24));
        navButton.setBorder(BorderFactory.createEmptyBorder());
        navButton.setFocusPainted(false);

        // Icon
        JLabel iconLabel = new JLabel(icon.getIcon(), SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        iconLabel.setForeground(isSelected ? Color.YELLOW : Color.WHITE);

        // Text
        JLabel textLabel = new JLabel(text, SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 10));
        textLabel.setForeground(isSelected ? Color.YELLOW : Color.WHITE);

        // Container for icon and text
        JPanel buttonContent = new JPanel(new BorderLayout());
        buttonContent.setBackground(new Color(139, 24, 24));
        buttonContent.add(iconLabel, BorderLayout.CENTER);
        buttonContent.add(textLabel, BorderLayout.SOUTH);

        navButton.add(buttonContent);

        // Add click listener
        navButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNavigation(text);
                updateContentArea(text);
            }
        });

        bottomNavPanel.add(navButton);
    }

    private void updateNavigation(String selectedTab) {
        // Reset all buttons and highlight selected
        Component[] buttons = bottomNavPanel.getComponents();
        for (Component comp : buttons) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                JPanel content = (JPanel) button.getComponent(0);
                JLabel iconLabel = (JLabel) content.getComponent(0);
                JLabel textLabel = (JLabel) content.getComponent(1);

                if (textLabel.getText().equals(selectedTab)) {
                    iconLabel.setForeground(Color.YELLOW);
                    textLabel.setForeground(Color.YELLOW);
                } else {
                    iconLabel.setForeground(Color.WHITE);
                    textLabel.setForeground(Color.WHITE);
                }
            }
        }
        repaint();
    }

    private void updateContentArea(String tab) {
        contentPanel.removeAll();

        JLabel contentLabel = new JLabel(tab + " Screen", SwingConstants.CENTER);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        contentLabel.setForeground(Color.DARK_GRAY);

        contentPanel.add(contentLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getLookAndFeel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new MobileAppInterface();
            }
        });
    }
}