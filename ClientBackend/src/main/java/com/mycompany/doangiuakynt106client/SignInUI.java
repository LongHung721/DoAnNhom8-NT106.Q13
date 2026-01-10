package com.mycompany.doangiuakynt106client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SignInUI extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;

    public SignInUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("ĐĂNG NHẬP");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Nền ảnh Anh_5.jpg đồng bộ server
        BackgroundPanel mainPanel = new BackgroundPanel("/images/Anh_5.jpg");
        mainPanel.setLayout(new GridBagLayout());

        // Container chính cho Form
        RoundedPanel formPanel = new RoundedPanel(30, new Color(15, 23, 42, 220), new Color(0, 204, 255), 2);
        formPanel.setPreferredSize(new Dimension(380, 450));
        // Sử dụng BoxLayout theo trục Y để các phần tử xếp chồng lên nhau
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // 1. Tiêu đề ĐĂNG NHẬP (Căn trái)
        JLabel lblMainTitle = new JLabel("ĐĂNG NHẬP");
        lblMainTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblMainTitle.setForeground(new Color(0, 204, 255));
        lblMainTitle.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái

        // 2. Nhãn và Ô nhập Tài khoản
        JLabel lblUser = createFieldLabel("TÀI KHOẢN");
        txtUser = createStyledTextField();
        
        // 3. Nhãn và Ô nhập Mật khẩu
        JLabel lblPass = createFieldLabel("MẬT KHẨU");
        txtPass = createStyledPasswordField();

        // 4. Nút Đăng nhập
        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        stylePrimaryButton(btnLogin, new Color(0, 255, 127)); 
        btnLogin.addActionListener(e -> handleLogin());
        

        // 5. Quên mật khẩu (Bỏ mục đăng ký)
        JLabel lblForgot = createLinkLabel("Quên mật khẩu?");
        lblForgot.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblForgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleForgot();
            }
        });

        // Thêm các thành phần vào Form theo thứ tự
        formPanel.add(lblMainTitle);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Khoảng cách
        
        formPanel.add(lblUser);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtUser);
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        formPanel.add(lblPass);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(txtPass);
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        formPanel.add(btnLogin);
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(lblForgot);

        mainPanel.add(formPanel);
        setContentPane(mainPanel);
    }
    
    private void handleForgot() {
        new ForgotPasswordUI().setVisible(true);
        this.dispose();
    }

    private void handleLogin() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        if (!user.isEmpty() && !pass.isEmpty()) {
            try {
                var authBody = SignIn.SignInUser(user, pass);
                if (authBody == null) {
                    JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
                    return;
                }

                String machineId = MachineIdUtil.getMachineId();
                var sessionRes = ApiClient.startMachineSession(machineId);
                int status = sessionRes.getInt("status");
                if (status != 200) {
                    String msg = sessionRes.getJSONObject("body").optJSONObject("error") != null
                            ? sessionRes.getJSONObject("body").getJSONObject("error").optString("message", "cannot_start_session")
                            : "cannot_start_session";
                    JOptionPane.showMessageDialog(this, "Không thể mở máy: " + msg);
                    return;
                }

                var state = sessionRes.getJSONObject("body");
                int remaining = (int) state.optLong("remainingSeconds", 0);
                String userType = authBody.optString("LoaiKH", "Hội viên");
                Object tong = authBody.opt("TongTienDaNap");
                String balance = tong == null ? "0" : String.valueOf(tong);

                new HomeUI(user, userType, balance, remaining, machineId).setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi kết nối server: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản và mật khẩu!");
        }
    }

    // --- CÁC HÀM HỖ TRỢ GIAO DIỆN ---

    private JLabel createFieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(new Color(148, 163, 184));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT); // Đảm bảo label căn trái
        return lbl;
    }

    private void styleInput(JTextField tf) {
        // Cố định kích thước chiều cao, chiều rộng tự giãn theo container
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tf.setBackground(new Color(30, 41, 59));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT); // Đảm bảo textbox căn trái
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        styleInput(tf);
        return tf;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField pf = new JPasswordField();
        styleInput(pf);
        return pf;
    }

    private void stylePrimaryButton(JButton btn, Color neonColor) {
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setBackground(neonColor);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT); // Nút bấm cũng căn trái cho thẳng hàng
    }

    private JLabel createLinkLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(new Color(0, 204, 255));
        lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { lbl.setText("<html><u>" + text + "</u></html>"); }
            @Override
            public void mouseExited(MouseEvent e) { lbl.setText(text); }
        });
        return lbl;
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        EventQueue.invokeLater(() -> new SignInUI().setVisible(true));
    }
}