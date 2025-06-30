import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class PinPointLoginSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField usernameField, emailFieldSignup, emailFieldLogin;
    private JPasswordField passwordFieldSignup, passwordFieldLogin;
    private JCheckBox termsCheckbox;

    public PinPointLoginSystem() {
        setupFrame();
        createMainContainer();
        createSignUpScreen();
        createLoginScreen();
        createMainApp();

        setVisible(true);
    }

    private void setupFrame() {
        setTitle("PIN*point");
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

    private void createSignUpScreen() {
        JPanel signUpPanel = new JPanel(new BorderLayout());
        signUpPanel.setBackground(new Color(245, 245, 245));

        // Main content container
        JPanel outerContainer = new JPanel(new BorderLayout());
        outerContainer.setBackground(new Color(245, 245, 245));
        outerContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // White content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        // PIN*point logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel pinLabel = new JLabel("PIN");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 28));
        pinLabel.setForeground(new Color(139, 24, 24));

        JLabel starLabel = new JLabel("*");
        starLabel.setFont(new Font("Arial", Font.BOLD, 28));
        starLabel.setForeground(new Color(255, 223, 0));

        JLabel pointLabel = new JLabel("point");
        pointLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        pointLabel.setForeground(Color.BLACK);

        logoPanel.add(pinLabel);
        logoPanel.add(starLabel);
        logoPanel.add(pointLabel);

        // Register title
        JLabel titleLabel = new JLabel("Register", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form fields
        usernameField = createStyledTextField("Enter your username");
        emailFieldSignup = createStyledTextField("Enter your email");
        passwordFieldSignup = createStyledPasswordField("Enter your password");

        // Terms checkbox
        JPanel termsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        termsPanel.setBackground(Color.WHITE);
        termsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        termsCheckbox = new JCheckBox();
        termsCheckbox.setBackground(Color.WHITE);
        termsCheckbox.setForeground(new Color(255, 193, 7)); // Yellow color

        JLabel agreeLabel = new JLabel("I agree to the ");
        agreeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        agreeLabel.setForeground(Color.GRAY);

        JLabel termsLabel = new JLabel("terms & conditions");
        termsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        termsLabel.setForeground(new Color(139, 24, 24));

        termsPanel.add(termsCheckbox);
        termsPanel.add(agreeLabel);
        termsPanel.add(termsLabel);

        // Sign Up button
        JButton signUpButton = createStyledButton("Sign Up");

        // Divider
        JLabel dividerLabel = new JLabel("or Sign up with", SwingConstants.CENTER);
        dividerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dividerLabel.setForeground(Color.GRAY);
        dividerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Social buttons
        JPanel socialPanel = createSocialButtonPanel();

        // Already have account
        JPanel loginLinkPanel = new JPanel(new FlowLayout());
        loginLinkPanel.setBackground(Color.WHITE);
        loginLinkPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel haveAccountLabel = new JLabel("Already have an account? ");
        haveAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        haveAccountLabel.setForeground(Color.GRAY);

        JLabel loginLinkLabel = new JLabel("Login");
        loginLinkLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginLinkLabel.setForeground(new Color(139, 24, 24));

        loginLinkPanel.add(haveAccountLabel);
        loginLinkPanel.add(loginLinkLabel);

        // Add components with proper spacing
        contentPanel.add(logoPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(30));

        addFieldWithLabel(contentPanel, "Username", usernameField);
        addFieldWithLabel(contentPanel, "Email", emailFieldSignup);
        addFieldWithLabel(contentPanel, "Password", passwordFieldSignup);

        contentPanel.add(termsPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(signUpButton);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(dividerLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(socialPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(loginLinkPanel);

        outerContainer.add(contentPanel, BorderLayout.CENTER);
        signUpPanel.add(outerContainer, BorderLayout.CENTER);

        // Event listeners
        signUpButton.addActionListener(e -> {
            if (validateSignUp()) {
                JOptionPane.showMessageDialog(this, "Account created successfully!");
                cardLayout.show(mainPanel, "LOGIN");
            }
        });

        loginLinkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(mainPanel, "LOGIN");
            }
        });

        mainPanel.add(signUpPanel, "SIGNUP");
    }

    private void createLoginScreen() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(245, 245, 245));

        // Main content container
        JPanel outerContainer = new JPanel(new BorderLayout());
        outerContainer.setBackground(new Color(245, 245, 245));
        outerContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // White content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        // PIN point logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel pinLabel = new JLabel("PIN");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 28));
        pinLabel.setForeground(new Color(139, 24, 24));

        JLabel starLabel = new JLabel("*");
        starLabel.setFont(new Font("Arial", Font.BOLD, 28));
        starLabel.setForeground(new Color(255, 223, 0));

        JLabel pointLabel = new JLabel("point");
        pointLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        pointLabel.setForeground(Color.BLACK);

        logoPanel.add(pinLabel);
        logoPanel.add(starLabel);
        logoPanel.add(pointLabel);

        // Login title
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form fields
        emailFieldLogin = createStyledTextField("Enter your email");
        passwordFieldLogin = createStyledPasswordField("Enter your password");

        // Forgot password
        JPanel forgotPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        forgotPanel.setBackground(Color.WHITE);
        forgotPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JLabel forgotLabel = new JLabel("Forgot Password?");
        forgotLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotLabel.setForeground(Color.GRAY);

        forgotPanel.add(forgotLabel);

        // Sign In button
        JButton signInButton = createStyledButton("Sign In");

        // Divider
        JLabel dividerLabel = new JLabel("or Sign in with", SwingConstants.CENTER);
        dividerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dividerLabel.setForeground(Color.GRAY);
        dividerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Social buttons
        JPanel socialPanel = createSocialButtonPanel();

        // Don't have account
        JPanel signUpLinkPanel = new JPanel(new FlowLayout());
        signUpLinkPanel.setBackground(Color.WHITE);
        signUpLinkPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel noAccountLabel = new JLabel("Don't have an account? ");
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        noAccountLabel.setForeground(Color.GRAY);

        JLabel registerLinkLabel = new JLabel("Register");
        registerLinkLabel.setFont(new Font("Arial", Font.BOLD, 14));
        registerLinkLabel.setForeground(new Color(139, 24, 24));

        signUpLinkPanel.add(noAccountLabel);
        signUpLinkPanel.add(registerLinkLabel);

        // Add components
        contentPanel.add(logoPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(30));

        addFieldWithLabel(contentPanel, "Email", emailFieldLogin);
        addFieldWithLabel(contentPanel, "Password", passwordFieldLogin);

        contentPanel.add(forgotPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(signInButton);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(dividerLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(socialPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(signUpLinkPanel);

        outerContainer.add(contentPanel, BorderLayout.CENTER);
        loginPanel.add(outerContainer, BorderLayout.CENTER);

        // Event listeners
        signInButton.addActionListener(e -> {
            if (validateLogin()) {
                cardLayout.show(mainPanel, "MAIN_APP");
            }
        });

        registerLinkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(mainPanel, "SIGNUP");
            }
        });

        mainPanel.add(loginPanel, "LOGIN");
    }

    private void createMainApp() {
        // Create your existing MobileAppInterface and integrate it
        MobileAppInterface appInterface = new MobileAppInterface();

        // Remove the default close operation from the main app since it's now embedded
        appInterface.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Get the content from your existing app
        JPanel appContent = (JPanel) appInterface.getContentPane();

        mainPanel.add(appContent, "MAIN_APP");
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 40)));

        // Add focus listener for placeholder behavior
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 40)));

        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 25, 25));
                super.paintComponent(g);
                g2.dispose();
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(139, 24, 24));
        button.setForeground(Color.WHITE);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    private JPanel createSocialButtonPanel() {
        JPanel socialPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        socialPanel.setBackground(Color.WHITE);
        socialPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Apple button
        // ImageIcon appleIcon = new ImageIcon(
        // "C:/Users/Vergel Dar Juan/Documents/GitHub/Pin-Point/img/fb.png");
        // JLabel appleLabel = new JLabel(appleIcon);
        JButton appleBtn = createSocialButton("A", Color.BLACK);
        // Google button
        JButton googleBtn = createSocialButton("G", new Color(219, 68, 55));
        // Facebook button
        JButton facebookBtn = createSocialButton("F", new Color(24, 119, 242));

        // socialPanel.add(appleLabel);
        socialPanel.add(appleBtn);
        socialPanel.add(googleBtn);
        socialPanel.add(facebookBtn);

        return socialPanel;
    }

    private JButton createSocialButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
                g2.dispose();
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(45, 45));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    private void addFieldWithLabel(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
    }

    private boolean validateLogin() {
        String email = emailFieldLogin.getText().trim();
        String password = new String(passwordFieldLogin.getPassword());

        if (email.isEmpty() || email.equals("Enter your email") || password.isEmpty()) {
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
        String username = usernameField.getText().trim();
        String email = emailFieldSignup.getText().trim();
        String password = new String(passwordFieldSignup.getPassword());

        if (username.isEmpty() || username.equals("Enter your username") ||
                email.isEmpty() || email.equals("Enter your email") ||
                password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return false;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email");
            return false;
        }

        if (!termsCheckbox.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please agree to the terms & conditions");
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
            new PinPointLoginSystem();
        });
    }
}