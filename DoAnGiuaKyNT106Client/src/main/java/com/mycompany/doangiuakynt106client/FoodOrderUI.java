package com.mycompany.doangiuakynt106client;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FoodOrderUI extends JFrame {

    private JPanel gridPanel;
    private JPanel cartItemsPanel;
    private JLabel lblTotal;
    private JTextArea txtNote;
    private int totalAmount = 0;
    private Map<String, int[]> cart = new HashMap<>();

    public FoodOrderUI() {
        initComponents();
        loadCategory("Đồ ăn");
    }

    private void initComponents() {
        setTitle("MENU DỊCH VỤ - CYBER CAFE");
        setSize(1250, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sử dụng BackgroundPanel để đồng bộ ảnh nền Anh_5.jpg
        BackgroundPanel mainBackground = new BackgroundPanel("/images/Anh_5.jpg");
        mainBackground.setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(880);
        splitPane.setDividerSize(1);
        splitPane.setOpaque(false);
        splitPane.setBorder(null);

        // --- PANEL MENU (BÊN TRÁI) ---
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setOpaque(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(25, 40, 10, 40));

        JLabel lblTitle = new JLabel("HỆ THỐNG DỊCH VỤ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(0, 204, 255));
        header.add(lblTitle, BorderLayout.NORTH);

        JPanel catButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        catButtons.setOpaque(false);
        String[] cats = {"Đồ ăn", "Thức uống"};
        for (String cat : cats) {
            JButton btn = new JButton(cat.toUpperCase());
            styleNavButton(btn);
            btn.addActionListener(e -> loadCategory(cat));
            catButtons.add(btn);
        }
        header.add(catButtons, BorderLayout.CENTER);
        menuPanel.add(header, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(0, 3, 25, 25));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        
        JScrollPane scrollMenu = new JScrollPane(gridPanel);
        scrollMenu.setOpaque(false);
        scrollMenu.getViewport().setOpaque(false);
        scrollMenu.setBorder(null);
        menuPanel.add(scrollMenu, BorderLayout.CENTER);

        // --- PANEL GIỎ HÀNG (BÊN PHẢI) ---
        RoundedPanel cartPanel = new RoundedPanel(0, new Color(30, 41, 59, 200), new Color(0, 204, 255, 50), 1);
        cartPanel.setLayout(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        JLabel lblCartTitle = new JLabel("GIỎ HÀNG CỦA BẠN", SwingConstants.CENTER);
        lblCartTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCartTitle.setForeground(Color.WHITE);
        cartPanel.add(lblCartTitle, BorderLayout.NORTH);

        cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setOpaque(false);
        
        JScrollPane scrollCart = new JScrollPane(cartItemsPanel);
        scrollCart.setOpaque(false);
        scrollCart.getViewport().setOpaque(false);
        scrollCart.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(255, 255, 255, 30)));
        cartPanel.add(scrollCart, BorderLayout.CENTER);

        JPanel cartFooter = new JPanel(new BorderLayout(0, 15));
        cartFooter.setOpaque(false);
        cartFooter.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        txtNote = new JTextArea(3, 10);
        txtNote.setBackground(new Color(15, 23, 42));
        txtNote.setForeground(Color.WHITE);
        txtNote.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNote.setLineWrap(true);
        TitledBorder noteBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 204, 255, 80)), "Ghi chú gửi bếp");
        noteBorder.setTitleColor(Color.LIGHT_GRAY);
        txtNote.setBorder(noteBorder);

        lblTotal = new JLabel("TỔNG: 0 VNĐ");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTotal.setForeground(new Color(0, 204, 255));

        JButton btnSubmit = new JButton("XÁC NHẬN ĐẶT MÓN");
        btnSubmit.setBackground(new Color(0, 255, 127));
        btnSubmit.setForeground(Color.BLACK);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSubmit.setFocusPainted(false);

        cartFooter.add(txtNote, BorderLayout.NORTH);
        cartFooter.add(lblTotal, BorderLayout.CENTER);
        cartFooter.add(btnSubmit, BorderLayout.SOUTH);
        cartPanel.add(cartFooter, BorderLayout.SOUTH);

        splitPane.setLeftComponent(menuPanel);
        splitPane.setRightComponent(cartPanel);
        
        mainBackground.add(splitPane, BorderLayout.CENTER);
        setContentPane(mainBackground);
    }

    private void styleNavButton(JButton btn) {
        btn.setBackground(new Color(51, 65, 85));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    }

    private void loadCategory(String category) {
        gridPanel.removeAll();
        if (category.equals("Đồ ăn")) {
            addMenuCard("Mì tôm trứng", 20000, "mitom.jpg");
            addMenuCard("Mì trộn", 25000, "mitron.jpg");
            addMenuCard("Combo xiên bẩn", 35000, "xienban.jpg");
            addMenuCard("Cơm chiên dương châu", 45000, "comchien.jpg");
            addMenuCard("Cơm trộn bò", 50000, "comtron.jpg");
            addMenuCard("Snack các loại", 10000, "snack.jpg");
        } else if (category.equals("Thức uống")) {
            addMenuCard("Redbull", 15000, "redbull.jpg");
            addMenuCard("Coca Cola", 12000, "coca.jpg");
            addMenuCard("7Up", 12000, "7up.png");
            addMenuCard("Sting", 12000, "sting.jpg");
            addMenuCard("Cà phê", 15000, "cafe.jpg");
            addMenuCard("Nước khoáng", 8000, "nuockhoang.jpg");
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void addMenuCard(String name, int price, String imgName) {
        gridPanel.add(new FoodCard(name, price, imgName));
    }

    private void updateCartUI() {
        cartItemsPanel.removeAll();
        totalAmount = 0;
        for (Map.Entry<String, int[]> entry : cart.entrySet()) {
            String name = entry.getKey();
            int qty = entry.getValue()[0];
            int price = entry.getValue()[1];
            totalAmount += (qty * price);

            JPanel itemRow = new JPanel(new BorderLayout());
            itemRow.setOpaque(false);
            itemRow.setMaximumSize(new Dimension(400, 65));
            itemRow.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

            JLabel lblInfo = new JLabel("<html><b style='color:white'>" + name + "</b><br/><span style='color:#00CCFF'>" + qty + " x " + String.format("%,d", price) + "đ</span></html>");
            
            JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
            controls.setOpaque(false);
            
            JButton btnMinus = new JButton("-");
            btnMinus.addActionListener(e -> {
                if (qty > 1) cart.get(name)[0]--;
                else cart.remove(name);
                updateCartUI();
            });
            
            JButton btnPlus = new JButton("+");
            btnPlus.addActionListener(e -> {
                cart.get(name)[0]++;
                updateCartUI();
            });

            controls.add(btnMinus);
            controls.add(btnPlus);
            itemRow.add(lblInfo, BorderLayout.CENTER);
            itemRow.add(controls, BorderLayout.EAST);
            cartItemsPanel.add(itemRow);
        }
        lblTotal.setText("TỔNG: " + String.format("%,d", totalAmount) + " VNĐ");
        cartItemsPanel.revalidate();
        cartItemsPanel.repaint();
    }

    // --- INNER CLASS: THẺ MÓN ĂN ---
    class FoodCard extends RoundedPanel {
        public FoodCard(String name, int price, String imgName) {
            super(20, new Color(30, 41, 59, 180), new Color(0, 204, 255, 60), 1);
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

            // Ảnh món ăn
            JLabel lblImg = new JLabel();
            lblImg.setHorizontalAlignment(SwingConstants.CENTER);
            lblImg.setPreferredSize(new Dimension(180, 130));
            try {
                java.net.URL imgURL = getClass().getResource("/images/" + imgName);
                if (imgURL != null) {
                    ImageIcon icon = new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(210, 125, Image.SCALE_SMOOTH));
                    lblImg.setIcon(icon);
                }
            } catch (Exception e) {}

            // Thông tin
            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            infoPanel.setOpaque(false);
            infoPanel.setBorder(BorderFactory.createEmptyBorder(12, 5, 12, 5));

            JLabel lblName = new JLabel(name);
            lblName.setForeground(Color.WHITE);
            lblName.setFont(new Font("Segoe UI", Font.BOLD, 15));

            JLabel lblPrice = new JLabel(String.format("%,d VNĐ", price));
            lblPrice.setForeground(new Color(0, 204, 255));

            infoPanel.add(lblName);
            infoPanel.add(lblPrice);

            // Nút Thêm (ĐÃ BỎ VIỀN XANH)
            JButton btnAdd = new JButton("THÊM VÀO GIỎ +");
            btnAdd.setBackground(new Color(0, 204, 255)); // Nền xanh neon
            btnAdd.setForeground(Color.BLACK);             // Chữ đen cho dễ đọc
            btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnAdd.setFocusPainted(false);
            btnAdd.setBorderPainted(false); // Quan trọng: Bỏ vẽ viền của nút
            
            btnAdd.addActionListener(e -> {
                if (cart.containsKey(name)) cart.get(name)[0]++;
                else cart.put(name, new int[]{1, price});
                updateCartUI();
            });

            add(lblImg, BorderLayout.NORTH);
            add(infoPanel, BorderLayout.CENTER);
            add(btnAdd, BorderLayout.SOUTH);
        }
    }

    // Hàm Main để xem thử UI nhanh (Preview)
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        EventQueue.invokeLater(() -> new FoodOrderUI().setVisible(true));
    }
}