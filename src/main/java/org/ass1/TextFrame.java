package org.ass1;// Java Program to create a text editor using java

import javax.swing.*;

class TextFrame extends JFrame {

    // Frame
    JFrame frame;

    TextFrame()
    {
        // Create a frame
        frame = new JFrame("Test Editor");

        Menu menu = new Menu(frame);
        frame.setJMenuBar(menu);


        frame.setSize(500, 500);
        frame.show();
    }



}
