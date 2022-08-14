package org.ass1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu extends JMenuBar implements ActionListener {

    JTextArea textarea;
    JFrame frame;
    public Menu( JFrame frame) {
        this.frame = frame;
        ArrayList<String> fileItems =new ArrayList<>();
        fileItems.add("New");
        fileItems.add("Open");
        fileItems.add("Save");
        fileItems.add("Select text");
        fileItems.add("Copy");
        fileItems.add("Paste");
        fileItems.add("Cut");
        JMenu mf =createMenuItems("File",fileItems);

        ArrayList<String> manageItems =new ArrayList<>();
        manageItems.add("Print");
        JMenu mm =createMenuItems("Manage",manageItems);

        ArrayList<String> viewItems =new ArrayList<>();
        viewItems.add("Time and Date");
        JMenu mv =createMenuItems("View",viewItems);

        JMenu ms = new JMenu("Search");

        ArrayList<String> helpItems =new ArrayList<>();
        helpItems.add("About");
        helpItems.add("Exit");
        JMenu mh =createMenuItems("Help",helpItems);
        this.add(mf);
        this.add(ms);
        this.add(mv);
        this.add(mm);
        this.add(mh);

        textarea = new JTextArea();
        frame.add(textarea);
    }
    public JMenu createMenuItems(String menuName, ArrayList<String> items){

        JMenu mb = new JMenu(menuName);
        for (int i = 0; i < items.size(); i++) {
            JMenuItem item =  new JMenuItem(items.get(i));
            item.addActionListener(this);
            mb.add(item);
        }
        return mb;
    }
    public void actionPerformed(ActionEvent e)
    {
        String itemName = e.getActionCommand();

        if (itemName.equals("Cut")) {
            textarea.cut();
        }
        else if (itemName.equals("Copy")) {
            textarea.copy();
        }
        else if (itemName.equals("Paste")) {
            textarea.paste();
        }
        else if (itemName.equals("Save")) {

        }
        else if (itemName.equals("Time and Date")){
            timeAndDate();
        }
        else if (itemName.equals("Print")) {
            try {
                textarea.print();
            }
            catch (Exception pr) {
                JOptionPane.showMessageDialog(frame, pr.getMessage());
            }
        }
        else if (itemName.equals("Open")) {

        }
        else if (itemName.equals("New")) {
            textarea.setText("");
        }
        else if (itemName.equals("Exit")) {
            frame.setVisible(false);
        }
        else if (itemName.equals("About")){

        }
    }
    public void timeAndDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        Date date =new Date();
        textarea.setText(sdf.format(date));
    }






}
