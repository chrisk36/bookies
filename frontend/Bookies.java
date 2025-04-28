package org.cis1200.frontend;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Bookies extends JPanel{
    private boolean introPage;
    private boolean actualProject;

    public Bookies(JLabel statusInit) {
        this.introPage = true;
        this.actualProject = false;

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    actions();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    actions();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    actions();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    actions();
                }
            }

            public void actions() {
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });
    }

    public void setActualProject (boolean isTrue) {
        this.actualProject = isTrue;
        repaint();

    }
    public boolean getActualProject() {
        System.out.println (actualProject);
        return this.actualProject;
    }

    public void setIntroPage (boolean isTrue) {
        this.introPage = isTrue;
        repaint();
    }
    public boolean getIntroPage() {
        System.out.println (introPage);
        return this.introPage;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (introPage) {
            g.setColor(Color.decode("#F2E9DC"));
            g.fillRect(0, 0, 1000, 750);
            g.setColor(Color.decode("#808f85"));
            g.setFont(new Font("Times", Font.BOLD, 110));
            g.drawString("bookies", 290, 380);
            ImageIcon icon = new ImageIcon("frontend/images/book.png");
            Image img = icon.getImage();
            g.drawImage(img, 100, 300, 100, 100, this);
            g.drawImage(img, 800, 300, 100, 100, this);
            g.setFont(new Font("Times", Font.BOLD, 30));
            //g.setColor(Color.decode("#d36135"));
            //g.drawString("generate text in the style of your fave authors", 130, 300);
            g.setColor(Color.decode("#595959"));
            g.setFont(new Font("Times", Font.BOLD, 20));
            //g.drawString("click anywhere to get started", 340, 500);
        } else if (actualProject) {
            g.setColor(Color.decode("#F2E9DC"));
            g.fillRect(0, 0, 1000, 750);
            g.setFont(new Font("Times", Font.BOLD, 30));
            g.setColor(Color.decode("#d36135"));
            g.drawString("generate text in the style of your fave authors!", 130, 250);
            g.setFont(new Font("Times", Font.BOLD, 200));
            g.setColor(Color.decode("#808f85"));
            g.drawString("↓", 430, 550);
        }
    }
}
