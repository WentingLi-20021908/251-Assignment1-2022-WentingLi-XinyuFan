package org.ass1;// Java Program to create a text editor using java

import javax.swing.*;

class TextFrame extends JFrame {

    public TextFrame() {
        super();
        // Create a frame
        setTitle("Test Editor");

        final MenuBar menu = new MenuBar(this);
        setJMenuBar(menu);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
