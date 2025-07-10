package GUI;

import DAO.TaiKhoanDAO;
import DTO.TaiKhoanDTO;
import GUI.Component.InputForm;
import GUI.Dialog.QuenMatKhau;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import helper.BCrypt;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());

    private static final Color BUTTON_DEFAULT_COLOR = new Color(33, 150, 243); // Accent Blue
    private static final Color BUTTON_HOVER_COLOR = new Color(66, 165, 245); // Accent Blue sáng hơn khi hover

    private JPanel pnlMain, pnlLogIn;
    private JLabel lblTitle, lblForgotPassword;
    private InputForm txtUsername, txtPassword;

    public Login() {
        initializeUI();
        txtUsername.setText("admin");
        txtPassword.setPass("123456");
    }

    private void initializeUI() {
        setPreferredSize(new Dimension(1000, 600));
        setTitle("Đăng nhập");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));

        initImagePanel();
        initMainPanel();
    }

    private void initImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setBorder(new EmptyBorder(3, 10, 5, 5));
        imagePanel.setPreferredSize(new Dimension(500, 600));
        imagePanel.setBackground(Color.WHITE);

        JLabel lblImage = new JLabel(new FlatSVGIcon("./img/login-image.svg"));
        imagePanel.add(lblImage);
        add(imagePanel, BorderLayout.WEST);
    }

    private void initMainPanel() {
        pnlMain = new JPanel();
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(20, 0, 0, 0));
        pnlMain.setPreferredSize(new Dimension(500, 600));
        pnlMain.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        lblTitle = new JLabel("ĐĂNG NHẬP VÀO HỆ THỐNG");
        lblTitle.setFont(new Font(FlatRobotoFont.FAMILY_SEMIBOLD, Font.BOLD, 20));
        pnlMain.add(lblTitle);

        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(400, 200));
        inputPanel.setLayout(new GridLayout(2, 1, 0, 10));

        txtUsername = new InputForm("Tên đăng nhập");
        txtPassword = new InputForm("Mật khẩu", "password");
        inputPanel.add(txtUsername);
        inputPanel.add(txtPassword);

        txtUsername.getTxtForm().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
        txtPassword.getTxtPass().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });

        initLoginButton();

        lblForgotPassword = new JLabel("Quên mật khẩu", JLabel.RIGHT);
        lblForgotPassword.setPreferredSize(new Dimension(380, 50));
        lblForgotPassword.setFont(new Font(FlatRobotoFont.FAMILY, Font.ITALIC, 18));
        lblForgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                new QuenMatKhau(Login.this, true).setVisible(true);
            }
        });

        pnlMain.add(inputPanel);
        pnlMain.add(lblForgotPassword);
        pnlMain.add(pnlLogIn);
        
        // thêm pnlMain vào bên phải của frame 
        add(pnlMain, BorderLayout.EAST);
    }

    private void initLoginButton() {
        pnlLogIn = new JPanel();
        pnlLogIn.putClientProperty(FlatClientProperties.STYLE, "arc: 99");
        pnlLogIn.setBackground(BUTTON_DEFAULT_COLOR);
        pnlLogIn.setPreferredSize(new Dimension(380, 50));
        pnlLogIn.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));

        JLabel lblLoginText = new JLabel("ĐĂNG NHẬP");
        lblLoginText.setFont(new Font(FlatRobotoFont.FAMILY, Font.BOLD, 16));
        lblLoginText.setForeground(Color.WHITE);
        pnlLogIn.add(lblLoginText);

        pnlLogIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                performLogin();
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                pnlLogIn.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                pnlLogIn.setBackground(BUTTON_DEFAULT_COLOR);
            }
        });
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getPass().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            TaiKhoanDTO account = TaiKhoanDAO.getInstance().selectByUser(username);
            if (account == null) {
                JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LOGGER.info("Tìm thấy tài khoản: " + account.getUsername() + ", Trạng thái: " + account.getTrangthai());
            if (account.getTrangthai() == 0) {
                JOptionPane.showMessageDialog(this, "Tài khoản đang bị khóa", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (BCrypt.checkpw(password, account.getMatkhau())) {
                dispose();
                new Main(account).setVisible(true);
                LOGGER.info("Chuyển sang màn hình chính.");
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu không đúng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi đăng nhập: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống, vui lòng thử lại", "Lỗi!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatRobotoFont.install();
            FlatLaf.setPreferredFontFamily(FlatRobotoFont.FAMILY);
            FlatLaf.setPreferredLightFontFamily(FlatRobotoFont.FAMILY_LIGHT);
            FlatLaf.setPreferredSemiboldFontFamily(FlatRobotoFont.FAMILY_SEMIBOLD);
            FlatIntelliJLaf.registerCustomDefaultsSource("style");
            FlatIntelliJLaf.setup();
            UIManager.put("PasswordField.showRevealButton", true);

            new Login().setVisible(true);
        });
    }
}