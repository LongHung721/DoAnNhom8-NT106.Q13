package com.mycompany.doangiuakynt106;

import javax.swing.*;
import java.awt.*;

public class MachineComponents {

    // --- ICON MÁY TÍNH ---
    public static class ComputerIcon extends RoundedPanel {
        public ComputerIcon(String name, String status, String user) {
            super(15, new Color(30, 41, 59, 200), 
                  status.equals("ONLINE") ? new Color(0, 255, 127) : new Color(0, 204, 255), 2);
            setPreferredSize(new Dimension(130, 145));
            setLayout(new BorderLayout());
            
            JLabel lblIcon = new JLabel();
            lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
            String imgName = status.equals("ONLINE") ? "iconPCon.jpg" : "iconPCoff.jpg";
            
            try {
                java.net.URL url = getClass().getResource("/images/" + imgName);
                if (url != null) {
                    lblIcon.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(85, 75, Image.SCALE_SMOOTH)));
                } else {
                    lblIcon.setText("💻");
                    lblIcon.setForeground(status.equals("ONLINE") ? Color.GREEN : Color.GRAY);
                }
            } catch (Exception e) { lblIcon.setText("!"); }

            JPanel p = new JPanel(new GridLayout(2, 1));
            p.setOpaque(false);
            JLabel ln = new JLabel(name, SwingConstants.CENTER); ln.setForeground(Color.WHITE);
            JLabel ls = new JLabel(status, SwingConstants.CENTER);
            ls.setForeground(status.equals("ONLINE") ? Color.GREEN : Color.CYAN);
            p.add(ln); p.add(ls);

            add(lblIcon, BorderLayout.CENTER);
            add(p, BorderLayout.SOUTH);
        }
    }

    // --- POPUP CHI TIẾT ---
    public static class MachineDetailDialog extends JDialog {
        public MachineDetailDialog(JFrame parent, String name, String status, String user) {
            super(parent, true);
            setUndecorated(true);
            setBackground(new Color(0, 0, 0, 0)); 
            RoundedPanel c = new RoundedPanel(30, new Color(15, 23, 42), new Color(0, 204, 255), 2);
            c.setLayout(new BorderLayout());
            c.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

            JLabel title = new JLabel("CHI TIẾT: " + name, SwingConstants.CENTER);
            title.setForeground(new Color(0, 204, 255));
            title.setFont(new Font("Segoe UI", Font.BOLD, 20));
            c.add(title, BorderLayout.NORTH);

            JPanel body = new JPanel(new GridLayout(6, 2, 10, 10));
            body.setOpaque(false);
            String order = status.equals("ONLINE") ? "1x Mì tôm, 1x Sting" : "Trống";
            String note = status.equals("ONLINE") ? "Mì không cay" : "---";

            String[][] rows = {
                {"Khách:", user.isEmpty() ? "---" : user},
                {"Trạng thái:", status},
                {"Dịch vụ:", "<html><font color='green'>" + order + "</font></html>"},
                {"Ghi chú:", "<html><i>" + note + "</i></html>"},
                {"Tổng tiền:", status.equals("ONLINE") ? "35.000đ" : "0đ"}
            };

            for (String[] r : rows) {
                JLabel k = new JLabel(r[0]); k.setForeground(Color.GRAY);
                JLabel v = new JLabel(r[1]); v.setForeground(Color.WHITE);
                body.add(k); body.add(v);
            }
            c.add(body, BorderLayout.CENTER);

            JButton btnClose = new JButton("ĐÓNG");
            btnClose.addActionListener(e -> dispose());
            c.add(btnClose, BorderLayout.SOUTH);

            add(c);
            setSize(420, 500);
            setLocationRelativeTo(parent);
        }
    }
}