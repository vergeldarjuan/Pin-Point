import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField emailField, nameField, phoneField;
    private JPasswordField passwordField, confirmPasswordField;

    public Start() {
        setupFrame();
        createMainContainer();
        createLoginScreen();
        createSignUpScreen();
        createMainApp();

        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Mobile App Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(375, 667);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
    }

    private void createMainContainer() {
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
    }

    private void createLoginScreen() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(Color.WHITE);

        // Status bar
        // JPanel statusBar = createStatusBar();
        // loginPanel.add(statusBar, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(250, 243, 224));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        // App logo/title
        ImageIcon logoIcon = new ImageIcon(
                "C:/Users/Vergel Dar Juan/Documents/GitHub/Pin-Point/img/logo.png");
        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("Welcome Back", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(139, 24, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sign in to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Email field
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setForeground(Color.DARK_GRAY);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.DARK_GRAY);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        // Login button
        JButton loginButton = new JButton("Sign In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(139, 24, 24));
        loginButton.setForeground(Color.WHITE);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        loginButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Forgot password
        JLabel forgotLabel = new JLabel("Forgot Password?", SwingConstants.CENTER);
        forgotLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        forgotLabel.setForeground(new Color(139, 24, 24));
        forgotLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sign up link
        JPanel signUpPanel = new JPanel(new FlowLayout());
        signUpPanel.setBackground(Color.WHITE);
        signUpPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel noAccountLabel = new JLabel("Don't have an account? ");
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        noAccountLabel.setForeground(Color.GRAY);

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 14));
        signUpLabel.setForeground(new Color(139, 24, 24));

        signUpPanel.add(noAccountLabel);
        signUpPanel.add(signUpLabel);

        // Add components with spacing
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(40));

        contentPanel.add(emailLabel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(emailField);
        contentPanel.add(Box.createVerticalStrut(20));

        contentPanel.add(passwordLabel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(passwordField);
        contentPanel.add(Box.createVerticalStrut(30));

        contentPanel.add(loginButton);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(forgotLabel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(signUpPanel);

        loginPanel.add(contentPanel, BorderLayout.CENTER);

        // Event listeners
        loginButton.addActionListener(e -> {
            if (validateLogin()) {
                cardLayout.show(mainPanel, "MAIN_APP");
            }
        });

        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(mainPanel, "SIGNUP");
            }
        });

        mainPanel.add(loginPanel, "LOGIN");
    }

    private void createSignUpScreen() {
        JPanel signUpPanel = new JPanel(new BorderLayout());
        signUpPanel.setBackground(Color.WHITE);

        // Status bar
        // JPanel statusBar = createStatusBar();
        // signUpPanel.add(statusBar, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Title
        JLabel titleLabel = new JLabel("Create Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(139, 24, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sign up to get started", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form fields
        nameField = createTextField("Full Name");
        emailField = createTextField("Email");
        phoneField = createTextField("Phone Number");
        passwordField = createPasswordField("Password");
        confirmPasswordField = createPasswordField("Confirm Password");

        // Sign up button
        JButton signUpButton = new JButton("Create Account");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16));
        signUpButton.setBackground(new Color(139, 24, 24));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        signUpButton.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        signUpButton.setFocusPainted(false);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sign in link
        JPanel signInPanel = new JPanel(new FlowLayout());
        signInPanel.setBackground(Color.WHITE);
        signInPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel haveAccountLabel = new JLabel("Already have an account? ");
        haveAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        haveAccountLabel.setForeground(Color.GRAY);

        JLabel signInLabel = new JLabel("Sign In");
        signInLabel.setFont(new Font("Arial", Font.BOLD, 14));
        signInLabel.setForeground(new Color(139, 24, 24));

        signInPanel.add(haveAccountLabel);
        signInPanel.add(signInLabel);

        // Add components
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(30));

        addFieldToPanel(contentPanel, "Full Name", nameField);
        addFieldToPanel(contentPanel, "Email", emailField);
        addFieldToPanel(contentPanel, "Phone", phoneField);
        addFieldToPanel(contentPanel, "Password", passwordField);
        addFieldToPanel(contentPanel, "Confirm Password", confirmPasswordField);

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(signUpButton);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(signInPanel);

        signUpPanel.add(contentPanel, BorderLayout.CENTER);

        // Event listeners
        signUpButton.addActionListener(e -> {
            if (validateSignUp()) {
                JOptionPane.showMessageDialog(this, "Account created successfully!");
                cardLayout.show(mainPanel, "LOGIN");
            }
        });

        signInLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(mainPanel, "LOGIN");
            }
        });

        mainPanel.add(signUpPanel, "SIGNUP");
    }

    public void createMainApp() {
        // // Create the main app (similar to previous interface)
        // JPanel appPanel = new JPanel(new BorderLayout());
        // appPanel.setBackground(Color.WHITE);

        // // Status bar
        // appPanel.add(createStatusBar(), BorderLayout.NORTH);

        // // Content
        // JPanel contentPanel = new JPanel(new BorderLayout());
        // contentPanel.setBackground(new Color(248, 248, 248));

        // JLabel welcomeLabel = new JLabel("Welcome to the App!",
        // SwingConstants.CENTER);
        // welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        // welcomeLabel.setForeground(new Color(139, 24, 24));

        // contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        // // Bottom navigation
        // JPanel bottomNav = new JPanel(new GridLayout(1, 4));
        // bottomNav.setBackground(new Color(139, 24, 24));
        // bottomNav.setPreferredSize(new Dimension(375, 70));

        // String[] navItems = { "🏠 HOME", "⭐ FAVORITES", "📋 MENU", "👤 PROFILE" };
        // for (int i = 0; i < navItems.length; i++) {
        // JButton navButton = new JButton(navItems[i]);
        // navButton.setBackground(new Color(139, 24, 24));
        // navButton.setForeground(i == 0 ? Color.YELLOW : Color.WHITE);
        // navButton.setBorder(BorderFactory.createEmptyBorder());
        // navButton.setFocusPainted(false);
        // bottomNav.add(navButton);
        // }

        // appPanel.add(contentPanel, BorderLayout.CENTER);
        // appPanel.add(bottomNav, BorderLayout.SOUTH);

        // mainPanel.add(appPanel, "MAIN_APP");
        // MobileAppInterface appInterface = new MobileAppInterface();
    }

    // private JPanel createStatusBar() {
    // JPanel statusBar = new JPanel(new BorderLayout());
    // statusBar.setBackground(Color.WHITE);
    // statusBar.setPreferredSize(new Dimension(375, 30));
    // statusBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

    // JLabel timeLabel = new JLabel("9:41");
    // timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
    // timeLabel.setForeground(Color.BLACK);

    // JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
    // rightPanel.setBackground(Color.WHITE);
    // rightPanel.add(new JLabel("●●●"));
    // rightPanel.add(new JLabel("📶"));
    // rightPanel.add(new JLabel("🔋"));

    // statusBar.add(timeLabel, BorderLayout.WEST);
    // statusBar.add(rightPanel, BorderLayout.EAST);

    // return statusBar;
    // }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        return field;
    }

    private void addFieldToPanel(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.DARK_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
    }

    private boolean validateLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return false;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email");
            return false;
        }

        return true;
    }

    private boolean validateSignUp() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return false;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email");
            return false;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Start();
        });
    }
}