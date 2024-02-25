package me.crispyxyz;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Main extends JFrame {
    private JLabel timeLabel;
    private JButton exitButton;
    private Container container;

    private int inputForProperty(Component parent, String message) {
        String input = null;
        int local = 0;
        while (input == null) {
            input = JOptionPane.showInputDialog(parent, message, "时钟", JOptionPane.QUESTION_MESSAGE);
            try {
                local = Integer.parseInt(Objects.requireNonNull(input));
            } catch (NumberFormatException e) {
                input = null;
                JOptionPane.showMessageDialog(parent, "数字格式错误 " + e.getMessage().toLowerCase(), "时钟", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e) {
                System.exit(0);
            }
        }
        return local;
    }

    public Main() {
        setUndecorated(true);

        container = getContentPane();

        container.setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        Properties p = new Properties();
        int size_en = 128, size_zh = 32;
        String filepath = "time-screen.properties";
        try {
            File file = new File("time-screen.properties");
            filepath = file.getAbsolutePath();
            p.load(new BufferedInputStream(new FileInputStream(file)));
            int local_size_en = Integer.parseInt(p.getProperty("size_en"));
            int local_size_zh = Integer.parseInt(p.getProperty("size_zh"));
            size_en = local_size_en;
            size_zh = local_size_zh;
        } catch (IOException | NumberFormatException e1) {
            JOptionPane.showMessageDialog(this,"配置文件"+filepath+"读取失败，将请求手动输入数据。", "时钟", JOptionPane.WARNING_MESSAGE);
            int result = JOptionPane.showConfirmDialog(this, "是否使用默认配置？", "时钟", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch (result) {
                case JOptionPane.NO_OPTION -> {
                    size_en = inputForProperty(this, "size_en");
                    size_zh = inputForProperty(this, "size_zh");
                }
                case JOptionPane.YES_OPTION -> { }
                default -> System.exit(0);
            }
        }

        Font font_en = new Font("Arial", Font.BOLD, size_en);
        Font font_zh = new Font("msyh", Font.BOLD, size_zh);

        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(font_en);
        timeLabel.setForeground(Color.WHITE);

        container.add(BorderLayout.CENTER, timeLabel);

        exitButton = new JButton();
        exitButton.setText("退出");
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setContentAreaFilled(false);
        exitButton.setFont(font_zh);
        exitButton.setForeground(Color.WHITE);

        container.add(BorderLayout.NORTH, exitButton);

        TimeUpdater timeUpdater = new TimeUpdater(timeLabel);
        timeUpdater.init();
    }

    public static void main(String[] args) {
        Main frame = new Main();

        frame.setTitle("时钟");
        frame.setResizable(true);
        frame.setSize(800, 600);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}