import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tablet extends JFrame implements ActionListener {
    private StyledTextField formField;
    private StyledTextField fatherField;
    private StyledTextField nameField;
    private StyledTextField imeiField;
    private StyledTextField serialField;
    private StyledTextField phoneField;
    private StyledButton saveButton;
    private StyledButton clearButton;

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = new Color(255, 255, 255);
    private static final Color TEXT_COLOR = new Color(33, 33, 33);
    private static final Color LABEL_COLOR = new Color(52, 73, 94);

    public Tablet() {
        initializeFrame();
        createUI();
    }

    private void initializeFrame() {
        setTitle("Device Registration Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());
    }

    private void createUI() {
        JPanel headerPanel = createHeaderPanel();
        JPanel formPanel = createFormPanel();
        JPanel footerPanel = createFooterPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(PRIMARY_COLOR);
        header.setPreferredSize(new Dimension(550, 90));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Device Registration");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Register your tablet device");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(236, 240, 241));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(titleLabel);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitleLabel);

        return header;
    }

    private JPanel createFormPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(CARD_COLOR);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        cardPanel.setMaximumSize(new Dimension(570, 500));

        // Form Number
        JPanel formGroup = createFieldGroup("Form Number", "Enter your Form Number");
        formField = (StyledTextField) formGroup.getComponent(2); // assign correctly
        cardPanel.add(formGroup);
        cardPanel.add(Box.createVerticalStrut(20));

        // Full Name field
        JPanel nameGroup = createFieldGroup("Full Name", "Enter your full name");
        nameField = (StyledTextField) nameGroup.getComponent(2);
        cardPanel.add(nameGroup);
        cardPanel.add(Box.createVerticalStrut(20));

        // Father Name
        JPanel fatherGroup = createFieldGroup("Father's Name", "Enter your Father's name");
        fatherField = (StyledTextField) fatherGroup.getComponent(2);
        cardPanel.add(fatherGroup);
        cardPanel.add(Box.createVerticalStrut(20));

        // IMEI field
        JPanel imeiGroup = createFieldGroup("IMEI Number", "e.g., 123456789012345");
        imeiField = (StyledTextField) imeiGroup.getComponent(2);
        cardPanel.add(imeiGroup);
        cardPanel.add(Box.createVerticalStrut(20));

        // Serial Number field
        JPanel serialGroup = createFieldGroup("Serial Number", "Enter device serial number");
        serialField = (StyledTextField) serialGroup.getComponent(2);
        cardPanel.add(serialGroup);
        cardPanel.add(Box.createVerticalStrut(20));

        // Phone Number field
        JPanel phoneGroup = createFieldGroup("Phone Number", "e.g., +1-234-567-8900");
        phoneField = (StyledTextField) phoneGroup.getComponent(2);
        cardPanel.add(phoneGroup);

        mainPanel.add(cardPanel);
        mainPanel.add(Box.createVerticalGlue());

        return mainPanel;
    }

    private JPanel createFieldGroup(String label, String placeholder) {
        JPanel group = new JPanel();
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.setBackground(CARD_COLOR);
        group.setOpaque(false);
        group.setMaximumSize(new Dimension(400, 70));

        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        fieldLabel.setForeground(LABEL_COLOR);
        fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        StyledTextField textField = new StyledTextField(placeholder);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setMaximumSize(new Dimension(400, 40));

        group.add(fieldLabel);
        group.add(Box.createVerticalStrut(8));
        group.add(textField);

        return group;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel();
        footer.setBackground(BACKGROUND_COLOR);
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));
        footer.setPreferredSize(new Dimension(550,80));

        saveButton = new StyledButton("Save Registration", SUCCESS_COLOR);
        saveButton.addActionListener(this);

        clearButton = new StyledButton("Clear Form", new Color(149, 165, 166));
        clearButton.addActionListener(e -> clearForm());

        footer.add(saveButton);
        footer.add(clearButton);

        return footer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // defensive: ensure fields were created
        if (formField == null || nameField == null || fatherField == null || imeiField == null || serialField == null || phoneField == null) {
            showError("Internal Error", "Form fields are not initialized correctly.");
            return;
        }

        String formno = formField.getActualText();
        String name = nameField.getActualText();
        String fatherName = fatherField.getActualText();
        String imei = imeiField.getActualText();
        String serial = serialField.getActualText();
        String phone = phoneField.getActualText();

        if (!validateForm(formno, name, fatherName, imei, serial, phone)) {
            return;
        }

        saveToDatabase(formno, name, fatherName, imei, serial, phone);
    }

    private boolean validateForm(String formno, String name, String FatherName, String imei, String serial, String phone) {
        if (formno.isEmpty()) {
            showError("Form Number Required", "Please enter the form number");
            formField.requestFocus();
            return false;
        }
        if (name.isEmpty()) {
            showError("Name is Required", "Please enter your full name");
            nameField.requestFocus();
            return false;
        }
        if (FatherName.isEmpty()) {
            showError("Father's Name Required", "Please enter father's name");
            fatherField.requestFocus();
            return false;
        }
        if (imei.isEmpty()) {
            showError("IMEI Required", "Please enter your device IMEI number");
            imeiField.requestFocus();
            return false;
        }
        if (serial.isEmpty()) {
            showError("Serial Number Required", "Please enter your device serial number");
            serialField.requestFocus();
            return false;
        }
        if (phone.isEmpty()) {
            showError("Phone Number Required", "Please enter your phone number");
            phoneField.requestFocus();
            return false;
        }
        return true;
    }

    private void saveToDatabase(String formno, String name, String fatherName, String imei, String serial, String phone) {
        try {
            Database db = new Database();
            // It's better to list columns explicitly. Adjust column order to match your table.
            String query = "INSERT INTO Room3 (Name,IMEI, Serial, Phone,FatherName,ApplicationNo) VALUES(?,?,?,?,?,?)";
          PreparedStatement pst = db.conn.prepareStatement(query);

            pst.setString(6, formno);
            pst.setString(1, name);
            pst.setString(5, fatherName);
            pst.setString(2, imei);
            pst.setString(3, serial);
            pst.setString(4, phone);
            pst.executeUpdate();
            showSuccess("Registration Successful", "Your device has been registered successfully!");
            clearForm();
        } catch (Exception e) {
            showError("Database Error", "Failed to save registration. Please try again.");
            e.printStackTrace();
        }
    }

    private void clearForm() {
        if (formField != null) formField.clearField();
        if (nameField != null) nameField.clearField();
        if (fatherField != null) fatherField.clearField();
        if (imeiField != null) imeiField.clearField();
        if (serialField != null) serialField.clearField();
        if (phoneField != null) phoneField.clearField();
        if (formField != null) formField.requestFocus();
    }

    private void showSuccess(String title, String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        optionPane.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JDialog dialog = optionPane.createDialog(this, title);
        dialog.setVisible(true);
    }

    private void showError(String title, String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        optionPane.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JDialog dialog = optionPane.createDialog(this, title);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Tablet());
    }
}

/* StyledTextField and StyledButton classes unchanged (same as your original),
   but include them here to make the file self-contained. */

class StyledTextField extends JTextField {
    private String placeholder;
    private boolean isPlaceholder;
    private final Color PLACEHOLDER_COLOR = new Color(149, 165, 166);
    private final Color TEXT_COLOR = new Color(33, 33, 33);
    private final Color BORDER_COLOR = new Color(189, 195, 199);
    private final Color FOCUS_COLOR = new Color(41, 128, 185);

    public StyledTextField(String placeholder) {
        this.placeholder = placeholder;
        this.isPlaceholder = true;

        setText(placeholder);
        setForeground(PLACEHOLDER_COLOR);
        setCaretColor(TEXT_COLOR);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        setBackground(new Color(245, 246, 247));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPlaceholder) {
                    setText("");
                    setForeground(TEXT_COLOR);
                    isPlaceholder = false;
                }
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(FOCUS_COLOR, 2),
                        BorderFactory.createEmptyBorder(10, 12, 10, 12)));
                setBackground(Color.WHITE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().trim().isEmpty()) {
                    setText(placeholder);
                    setForeground(PLACEHOLDER_COLOR);
                    isPlaceholder = true;
                    setBackground(new Color(245, 246, 247));
                }
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(10, 12, 10, 12)));
            }
        });
    }

    public String getActualText() {
        return isPlaceholder ? "" : super.getText().trim();
    }

    public void clearField() {
        setText(placeholder);
        setForeground(PLACEHOLDER_COLOR);
        isPlaceholder = true;
        setBackground(new Color(245, 246, 247));
    }
}

class StyledButton extends JButton {
    private final Color buttonColor;
    private final Color hoverColor;
    private boolean isHovered;

    public StyledButton(String text, Color baseColor) {
        super(text);
        this.buttonColor = baseColor;
        this.hoverColor = new Color(
                Math.min(baseColor.getRed() + 20, 255),
                Math.min(baseColor.getGreen() + 20, 255),
                Math.min(baseColor.getBlue() + 20, 255));

        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setForeground(Color.WHITE);
        setBackground(buttonColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);
        setPreferredSize(new Dimension(140, 45));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(hoverColor);
                isHovered = true;
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(buttonColor);
                isHovered = false;
            }
        });
    }
}
