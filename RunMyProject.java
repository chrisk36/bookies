package org.cis1200;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.NoSuchElementException;

public class RunMyProject implements Runnable {
    public static String savedAuthorName;
    public static int savedTextLength;
    public static int bookNumber;
    public static String keyword;
    public static GutenbergSearch.BookEntry chosenBook;

    @Override
    public void run() {
        JFrame frame = new JFrame("Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel status = new JLabel("Status: Ready");
        frame.add(status, java.awt.BorderLayout.NORTH);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.SOUTH);

        Bookies bookies = new Bookies(status);
        frame.add(bookies, java.awt.BorderLayout.CENTER);

        frame.pack();
        frame.setSize(1000, 1000);
        frame.setVisible(true);

        final JButton start = new JButton("Get Started (might take a few seconds to load)");
        start.addActionListener(e -> {
            bookies.setActualProject(true);
            bookies.setIntroPage(false);
            start.setEnabled(false);

            JLabel labelOne = new JLabel("Enter author name:");
            JTextField authorName = new JTextField(20);
            JLabel labelTwo = new JLabel("Enter text length:");
            JTextField textLength = new JTextField(20);
            JButton submitButton = new JButton("Submit");

            try {
                GutenbergSearch.loadIndex();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            submitButton.addActionListener(ev -> {
                savedAuthorName = authorName.getText();
                //System.out.println (savedAuthorName);
                try {
                    savedTextLength = Integer.parseInt(textLength.getText());
                    if (!GutenbergSearch.authorExists(savedAuthorName)) {
                        JOptionPane.showMessageDialog(null, "This author does not exist! " +
                                "Please try again.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number for text length.");
                    return;
                }
                if (GutenbergSearch.authorExists(savedAuthorName)) {
                    JOptionPane.showMessageDialog(frame,
                            "Saved!\nAuthor: " + savedAuthorName + "\nLength: " + savedTextLength);
                    bookies.setPickBookPage(true);
                    bookies.setActualProject(false);
                    control_panel.removeAll();
                    control_panel.revalidate();
                    control_panel.repaint();
                    JLabel label = new JLabel("Enter Book Number (i.e. 1, 2, 3): ");
                    JTextField bookNumber = new JTextField(20);
                    JLabel labelKey = new JLabel("Enter keyword");
                    JTextField keyword = new JTextField(20);
                    JButton submitButton2 = new JButton("Generate!");
                    control_panel.add(label);
                    control_panel.add(bookNumber);
                    control_panel.add(labelKey);
                    control_panel.add(keyword);
                    control_panel.add(submitButton2);
                    submitButton2.addActionListener(eq -> {
                        RunMyProject.bookNumber = Integer.parseInt(bookNumber.getText());
                        RunMyProject.chosenBook = GutenbergSearch.getBooksByAuthor(savedAuthorName).get(RunMyProject.bookNumber-1);
                        RunMyProject.keyword = keyword.getText();
                        bookies.setPickBookPage(false);
                        bookies.setGeneratedTextPage(true);
                        control_panel.removeAll();
                        control_panel.revalidate();
                        control_panel.repaint();
                        JButton restart = new JButton("Restart!");
                        control_panel.add(restart);
                        submitButton2.addActionListener(v -> {
                            bookies.setGeneratedTextPage(false);
                            bookies.setActualProject(true);
                        });
                    });
                }
            });

            control_panel.remove(start);
            control_panel.add(labelOne);
            control_panel.add(authorName);
            control_panel.add(labelTwo);
            control_panel.add(textLength);
            control_panel.add(submitButton);

            control_panel.revalidate();
            control_panel.repaint();
        });
        control_panel.add(start);


        bookies.requestFocusInWindow();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RunMyProject());
    }
}
