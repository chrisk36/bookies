package org.cis1200.frontend;

import javax.swing.*;
import java.awt.*;

public class RunMyProject implements Runnable {
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
        frame.setSize(1000, 750);
        frame.setVisible(true);

        final JButton start = new JButton("Get Started");
        start.addActionListener(e -> {
            bookies.setActualProject(true);
            bookies.setIntroPage(false);

            start.setEnabled(false);

            JLabel labelOne = new JLabel("Enter author name:");
            JTextField authorName = new JTextField(20);
            JLabel labelTwo = new JLabel("Enter text length:");
            JTextField textLength = new JTextField(20);
            JButton submitButton = new JButton("Submit");

//            submitButton.addActionListener(ev -> {
//                String input = textField.getText();
//                JOptionPane.showMessageDialog(frame, "You entered: " + input);
//            });
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
