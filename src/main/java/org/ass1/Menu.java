package org.ass1;

import javax.swing.*;
import java.util.ArrayList;

public class Menu extends JMenuBar {

    public Menu() {
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
    }

    public JMenu createMenuItems(String menuName, ArrayList<String> items){

        JMenu mb = new JMenu(menuName);
        for (int i = 0; i < items.size(); i++) {
            mb.add( new JMenuItem(items.get(i)));
        }
        return mb;
    }
}
