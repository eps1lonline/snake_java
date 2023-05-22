package com.company;

import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameField extends JPanel implements ActionListener {
    String name;

    private int size = 960;
    private int dotSize = 32;
    private int allDots = 900;

    private Image dot;
    private Image apple;

    private int dots;
    private int speed;
    private int appleX;
    private int appleY;

    private int[] x = new int[allDots];
    private int[] y = new int[allDots];

    private Timer timer;

    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = true;
    private boolean inGame = true;

    private Font font = new Font("Arial", Font.BOLD, 30);

    private int w = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int h = Toolkit.getDefaultToolkit().getScreenSize().height;

    public GameField(int speed, String name) {
        this.speed = speed;
        this.name = name;

        setBackground(Color.GREEN);
        loadImage();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 1;

        for (int i = 0; i < dots; i++) {
            x[i] = size / 2 - i * dotSize;
            y[i] = size / 2;
        }

        timer = new Timer(speed, this);
        timer.start();

        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(30) * dotSize;
        appleY = new Random().nextInt(30) * dotSize;
    }

    public void loadImage() {
        ImageIcon iia = new ImageIcon("src/Apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("src/Dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0,0, 960, 960);
        g.setColor(Color.getHSBColor(80, -26, 53));
        g.fillRect(0,0,960,960);

        super.paintComponents(g);

        if (inGame) {
            g.drawImage(apple, appleX, appleY,this);

            for (int i = 0; i < dots; i++)
                g.drawImage(dot, x[i], y[i], this);
        }
        else {
            String str = "Game Over";
            g.setFont(font);
            g.setColor(Color.black);
            g.drawString(str, 975/2 - 60, 400);

            g.drawString("Name: " + name, 975/2 - 60, 450);

            g.drawString("Score: " + (dots-1), 975/2 - 60, 500);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (left)
            x[0] -= dotSize;
        if (right)
            x[0] += dotSize;
        if (up)
            y[0] -= dotSize;
        if (down)
            y[0] += dotSize;
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--)
            if (i >= 4 && x[0] == x[i] && y[0] == y[i])
                inGame = false;

        if (x[0] > size)
            inGame = false;
        if (x[0] < 0)
            inGame = false;
        if (y[0] > size)
            inGame = false;
        if (y[0] < 0)
            inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }

        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            int key = e.getKeyCode();
            if (dots != 1) {
                if (key == KeyEvent.VK_LEFT && !right) {
                    left = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_RIGHT && !left) {
                    right = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_UP && !down) {
                    up = true;
                    left = false;
                    right = false;
                }
                if (key == KeyEvent.VK_DOWN && !up) {
                    down = true;
                    left = false;
                    right = false;
                }
            }
            else {
                if (key == KeyEvent.VK_LEFT) {
                    right = false;
                    left = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_RIGHT) {
                    left = false;
                    right = true;
                    up = false;
                    down = false;
                }
                if (key == KeyEvent.VK_UP) {
                    down = false;
                    up = true;
                    left = false;
                    right = false;
                }
                if (key == KeyEvent.VK_DOWN) {
                    up = false;
                    down = true;
                    left = false;
                    right = false;
                }
            }
        }
    }
}
