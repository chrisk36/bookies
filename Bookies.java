package org.cis1200;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

public class Bookies extends JPanel{
    private boolean introPage;
    private boolean actualProject;
    private boolean pickBookPage;
    private boolean generatedTextPage;

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

    public void setPickBookPage (boolean isTrue) {
        this.pickBookPage = isTrue;
        if (isTrue) {
            renderPickBookPage();
        } else {
            this.removeAll();
            this.revalidate();
            this.repaint();
        }
    }
    public boolean getPickBookPage() {
        System.out.println (pickBookPage);
        return this.pickBookPage;
    }

    public void setGeneratedTextPage (boolean isTrue) {
        this.generatedTextPage = isTrue;
    }

    public boolean isGeneratedTextPage() {
        return this.generatedTextPage;
    }

    public void renderPickBookPage() {
        this.removeAll();
        JPanel bookListPanel = new JPanel();
        bookListPanel.setLayout(new BoxLayout(bookListPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Books By " + RunMyProject.savedAuthorName);
        title.setFont(new Font("Monospace", Font.PLAIN, 50));
        title.setForeground(Color.decode("#808f85"));
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bookListPanel.add(title);
        bookListPanel.setBackground(Color.decode("#F2E9DC"));
        List<GutenbergSearch.BookEntry> books = GutenbergSearch.getBooksByAuthor(RunMyProject.savedAuthorName);
        int idx = 0;
        for (GutenbergSearch.BookEntry bookEntry : books) {
            idx++;
            JLabel bookLabel = new JLabel(idx + ") " + bookEntry.title);
            bookLabel.setFont(new Font("Monospace", Font.PLAIN, 20));
            bookLabel.setForeground(Color.decode("#595959"));
            bookLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            bookListPanel.add(bookLabel);
        }

        JScrollPane scrollPane = new JScrollPane(bookListPanel);

        scrollPane.setPreferredSize(new Dimension(1000, 800));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (introPage) {
            g.setColor(Color.decode("#F2E9DC"));
            g.fillRect(0, 0, 1000, 1000);
            g.setColor(Color.decode("#808f85"));
            g.setFont(new Font("Monospaced", Font.PLAIN, 110));
            g.drawString("bookies", 280, 380);
            ImageIcon icon = new ImageIcon("images/book.png");
            Image img = icon.getImage();
            g.drawImage(img, 100, 300, 100, 100, this);
            g.drawImage(img, 800, 300, 100, 100, this);
            g.setFont(new Font("Monospaced", Font.PLAIN, 30));
            g.setColor(Color.decode("#d36135"));
            g.drawString("by natalie lim, christian kim, & kate li", 150, 550);
            g.setColor(Color.decode("#595959"));
            g.setFont(new Font("Times", Font.BOLD, 20));
            //g.drawString("click anywhere to get started", 340, 500);
        } else if (actualProject) {
            g.setColor(Color.decode("#F2E9DC"));
            g.fillRect(0, 0, 1000, 1000);
            g.setFont(new Font("Times", Font.BOLD, 30));
            g.setColor(Color.decode("#d36135"));
            g.drawString("generate text in the style of your fave authors!", 130, 250);
            g.setFont(new Font("Times", Font.BOLD, 200));
            g.setColor(Color.decode("#808f85"));
            g.drawString("↓", 430, 550);
        }
    }
}
