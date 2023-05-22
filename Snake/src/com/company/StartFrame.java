package com.company;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame {
    private int w = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int h = Toolkit.getDefaultToolkit().getScreenSize().height;
    boolean writeNickname = false;
    private Font font = new Font("Arial", Font.BOLD, 30);

    public StartFrame() throws InterruptedException {
        JFrame startFrame = new JFrame("Snake by epsilonline");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setBounds(0, 0, w, h);
        startFrame.setVisible(true);

        // labelName *****************************************************
        JLabel lName = new JLabel("Name:");
        lName.setFont(font);
        lName.setBounds(w/2 - 150, h/2 - 250, 300, 50);

        // textField Name ************************************************
        JTextField tName = new JTextField("");
        tName.setFont(font);
        tName.setBackground(Color.getHSBColor(73,62,87));
        tName.setBounds(w/2 - 250, h/2 - 200, 300, 100);

        // button ********************************************************
        JButton bStart = new JButton("Start");
        bStart.setFont(font);
        bStart.setBackground(Color.getHSBColor(73,62,87));
        bStart.setBounds(w/2 - 250, h/2 - 75, 300, 100);

        // background image **********************************************
        JLabel lImage = new JLabel(new ImageIcon("src/Background.png"));
        lImage.setBounds(0, 0, w, h);

        // label speed ***************************************************
        JLabel lSpeed = new JLabel("Speed:");
        lSpeed.setFont(font);
        lSpeed.setBounds(w/2 + 100, h/2 - 250, 300, 50);

        // radio button **************************************************
        JRadioButton rLow = new JRadioButton("Low");
        rLow.setFont(font);
        rLow.setBackground(Color.getHSBColor(73,62,87));
        rLow.setBounds(w/2 + 75, h/2 - 200, 150, 100);

        JRadioButton rMedium = new JRadioButton("Medium");
        rMedium.setFont(font);
        rMedium.setSelected(true);
        rMedium.setBackground(Color.getHSBColor(73,62,87));
        rMedium.setBounds(w/2 + 75, h/2 - 75, 150, 100);

        JRadioButton rHard = new JRadioButton("Hard");
        rHard.setFont(font);
        rHard.setBackground(Color.getHSBColor(73,62,87));
        rHard.setBounds(w/2 + 75, h/2 + 50, 150, 100);

        Container container = startFrame.getContentPane();
        container.setBackground(Color.WHITE);

        container.add(lName);
        container.add(tName);

        container.add(bStart);

        container.add(lSpeed);
        container.add(rLow);
        container.add(rMedium);
        container.add(rHard);

        container.add(lImage);

        bStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tName.getText().length() == 0 && tName.getText().charAt(0) != ' ' || tName.getForeground() == Color.red) {
                    tName.setText("     Enter nickname");
                    tName.setForeground(Color.red);
                    bStart.setForeground(Color.red);
                }

                int counter = 0;
                if (rLow.isSelected())
                    counter++;
                if (rMedium.isSelected())
                    counter++;
                if (rHard.isSelected())
                    counter++;
                if (counter > 1 || counter == 0) {
                    rLow.setForeground(Color.red);
                    rMedium.setForeground(Color.red);
                    rHard.setForeground(Color.red);
                }

                if (counter == 1 && tName.getText().length() != 0 && tName.getText().charAt(0) != ' ' && tName.getForeground() != Color.red) {
                    writeNickname = true;
                    startFrame.setVisible(false);

                    int speed = rLow.isSelected() ? 450 : rMedium.isSelected() ? 250 : 50;

                    JFrame gameFrame = new JFrame("Snake by epsilonline");
                    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gameFrame.setBounds(0, 0, 960, 960);
                    gameFrame.setVisible(true);

                    gameFrame.add(new GameField(speed, tName.getText()));
                }
            }
        });

        while (!writeNickname) {
            Thread.sleep(1);
            if (tName.getForeground() == Color.red) {
                Thread.sleep(1000);

                tName.setText("");
                tName.setForeground(Color.black);
                bStart.setForeground(Color.black);

                rLow.setForeground(Color.black);
                rMedium.setForeground(Color.black);
                rHard.setForeground(Color.black);
            }
        }
    }
}
