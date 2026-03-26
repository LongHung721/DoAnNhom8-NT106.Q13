package com.mycompany.doangiuakynt106;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.mycompany.doangiuakynt106.partials.BackgroundPanel;

public class ComputerManagementUI extends JFrame {

    private JPanel gridPanel;
    private JScrollPane scrollPane;

    public ComputerManagementUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản lý sơ đồ máy trạm");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Cửa sổ bình thường, cho phép Alt+Tab và co giãn
        setUndecorated(false); 
        setSize(1100, 750);
        setLocationRelativeTo(null);

        BackgroundPanel container = new BackgroundPanel("/images/Anh_5.jpg");
        container.setLayout(new BorderLayout());

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel lblTitle = new JLabel("SƠ ĐỒ MÁY TRẠM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(0, 204, 255));
        
        JButton btnBack = new JButton("QUAY LẠI");
        styleSecondaryButton(btnBack);
        btnBack.addActionListener(e -> {
            new HomeUI().setVisible(true);
            dispose();
        });

        header.add(lblTitle, BorderLayout.WEST);
        header.add(btnBack, BorderLayout.EAST);
        container.add(header, BorderLayout.NORTH);

        // --- GRID PANEL (Nơi chứa icon) ---
        // Sử dụng FlowLayout.LEFT để các icon tự động trôi từ trái sang phải
        gridPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 25));
        gridPanel.setOpaque(false);
        
        // Thêm các máy trạm (Giả lập 24 máy)
        for (int i = 1; i <= 24; i++) {
            String name = String.format("USER%02d", i);
            String status = (i % 5 == 0) ? "OFFLINE" : "ONLINE";
            String user = (status.equals("ONLINE")) ? "Khách " + i : "";

            MachineComponents.ComputerIcon machineIcon = new MachineComponents.ComputerIcon(name, status, user);
            
            machineIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new MachineComponents.MachineDetailDialog(ComputerManagementUI.this, name, status, user).setVisible(true);
                }
            });
            gridPanel.add(machineIcon);
        }

        // --- SCROLL PANE ---
        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // LOGIC QUAN TRỌNG: Tự động tính toán lại layout khi co giãn cửa sổ
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Ép gridPanel có chiều rộng bằng với vùng hiển thị của ScrollPane
                // Điều này buộc FlowLayout phải tính toán lại việc xuống dòng
                gridPanel.setPreferredSize(new Dimension(
                    scrollPane.getViewport().getWidth(),
                    gridPanel.getPreferredSize().height
                ));
                gridPanel.revalidate();
            }
        });

        container.add(scrollPane, BorderLayout.CENTER);
        setContentPane(container);
    }

    private void styleSecondaryButton(JButton btn) {
        btn.setBackground(new Color(51, 65, 85));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
}