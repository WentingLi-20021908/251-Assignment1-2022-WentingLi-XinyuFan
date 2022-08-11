package org.ass1;// Java Program to create a text editor using java

import javax.swing.*;

class TextFrame extends JFrame {

    // Frame
    JFrame frame;

    TextFrame()
    {
        // Create a frame
        frame = new JFrame("Test Editor");
        Menu menu = new Menu();
        frame.setJMenuBar(menu);
        JTextArea jTextArea = new JTextArea();
        frame.add(jTextArea);
        frame.setSize(500, 500);
        frame.show();
    }



}
