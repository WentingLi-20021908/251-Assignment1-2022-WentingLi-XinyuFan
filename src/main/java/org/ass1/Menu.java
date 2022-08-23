package org.ass1;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.pkg.OdfElement;

public class Menu extends JMenuBar implements ActionListener {

    RSyntaxTextArea textarea;
    JFrame frame;

    public Menu(JFrame frame) {
        this.frame = frame;
        ArrayList<String> fileItems = new ArrayList<>();
        fileItems.add("New");
        fileItems.add("Open");
        fileItems.add("Save");
        JMenu mf = createMenuItems("File", fileItems);

        ArrayList<String> editItems = new ArrayList<>();
        editItems.add("Select text");
        editItems.add("Copy");
        editItems.add("Paste");
        editItems.add("Cut");
        JMenu me = createMenuItems("Edit", editItems);

        ArrayList<String> manageItems = new ArrayList<>();
        manageItems.add("Print");
        JMenu mm = createMenuItems("Manage", manageItems);

        ArrayList<String> viewItems = new ArrayList<>();
        viewItems.add("Time and Date");
        JMenu mv = createMenuItems("View", viewItems);

        JMenu ms = new JMenu("Search");

        ArrayList<String> helpItems = new ArrayList<>();
        helpItems.add("About");
        helpItems.add("Exit");
        JMenu mh = createMenuItems("Help", helpItems);

        ArrayList<String> syntaxItems = new ArrayList<>();
        syntaxItems.add("Text");
        syntaxItems.add("Java");
        syntaxItems.add("Python");
        syntaxItems.add("Cpp");
        syntaxItems.add("Css");
        syntaxItems.add("JavaScript");
        JMenu mSyn = createMenuItems("Syntax", syntaxItems);

        this.add(mf);
        this.add(me);
        this.add(ms);
        this.add(mSyn);
        this.add(mv);
        this.add(mm);
        this.add(mh);

        textarea = new RSyntaxTextArea();
        textarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        JScrollPane scrollPane = new JScrollPane(textarea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);
    }

    public JMenu createMenuItems(String menuName, ArrayList<String> items) {

        JMenu mb = new JMenu(menuName);
        for (int i = 0; i < items.size(); i++) {
            JMenuItem item = new JMenuItem(items.get(i));
            item.addActionListener(this);
            mb.add(item);
        }
        return mb;
    }

    public void actionPerformed(ActionEvent e) {
        String itemName = e.getActionCommand();

        if (itemName.equals("Cut")) {
            textarea.cut();
        } else if (itemName.equals("Copy")) {
            textarea.copy();
        } else if (itemName.equals("Paste")) {
            textarea.paste();
        } else if (itemName.equals("Select text")) {
            textarea.selectAll();
        } else if (itemName.equals("Save")) {
            saveText();
        } else if (itemName.equals("Time and Date")) {
            timeAndDate();
        } else if (itemName.equals("Print")) {
            try {
                textarea.print();
            } catch (Exception pr) {
                JOptionPane.showMessageDialog(frame, pr.getMessage());
            }
        } else if (itemName.equals("Open")) {
            openFile();
        } else if (itemName.equals("New")) {
            textarea.setText("");
        } else if (itemName.equals("Exit")) {
            frame.setVisible(false);
        } else if (itemName.equals("About")) {

        } else if (itemName.equals("Text") || itemName.equals("Java") || itemName.equals("JavaScript")
                || itemName.equals("Cpp") || itemName.equals("Python") || itemName.equals("Css")) {
            setSyntaxEditingStyle(itemName);
        }
    }

    private void setSyntaxEditingStyle(String style) {
        switch (style) {
            case "Text":
                textarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
                break;
            case "Java":
                textarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
                break;
            case "JavaScript":
                textarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
                break;
            case "Cpp":
                textarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                break;
            case "Css":
                textarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS);
                break;
            case "Python":
                textarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
                break;
        }
    }

    public void timeAndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        Date date = new Date();
        textarea.setText(sdf.format(date) + "\n" + textarea.getText());
    }

    public void saveText() {
        JFileChooser fc = new JFileChooser();

        int responseButton = fc.showSaveDialog(null);

        if (responseButton == JFileChooser.APPROVE_OPTION) {

            File file = new File(fc.getSelectedFile().getAbsolutePath());

            String fileName = file.getName();
            if (fileName.split("\\.")[1].toLowerCase().equals("pdf")) {
                saveAsPDFFile(file);
            } else if (fileName.split("\\.")[1].toLowerCase().equals("odt")) {
                saveAsODT(file);
            } else {
                try {
                    FileWriter fileWriter = new FileWriter(file, false);
                    BufferedWriter writer = new BufferedWriter(fileWriter);
                    writer.write(textarea.getText());

                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, e.getMessage());
                }
            }

        } else {
            JOptionPane.showMessageDialog(frame, "cancelled save");
        }

    }

    public void openFile() {
        JFileChooser chooserFile = new JFileChooser();
        int readFile = chooserFile.showOpenDialog(null);
        if (readFile == JFileChooser.APPROVE_OPTION) {
            File file = new File(chooserFile.getSelectedFile().getAbsolutePath());

            String fileName = file.getName();
            if (fileName.split("\\.")[1].toLowerCase().equals("odt")) {
                openODTFile(file);
            } else {
                openStandardFile(file);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Do not open file");
        }
    }

    public void saveAsODT(File file) {
        OdfTextDocument odt = null;
        try {
            odt = OdfTextDocument.newTextDocument();
            odt.addText(textarea.getText());
            odt.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAsPDFFile(File file) {
        Document document = new Document();
        try {
            final PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
            document.add(new Paragraph(textarea.getText()));
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }

        document.close();
    }

    public void openODTFile(File file) {
        try {
            OdfTextDocument odt = OdfTextDocument.loadDocument(file);
            OdfElement root = odt.getContentRoot();

            OdfElement firstParagraph = OdfElement.findFirstChildNode(TextPElement.class, root);
            textarea.setText(firstParagraph.getTextContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openStandardFile(File file) {
        try {
            String fileLine = "", nextLine = "";

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            nextLine = bufferedReader.readLine();

            while ((fileLine = bufferedReader.readLine()) != null) {
                nextLine += "\n" + fileLine;
            }

            textarea.setText(nextLine);
        } catch (Exception evt) {
            JOptionPane.showMessageDialog(frame, evt.getMessage());
        }
    }

}
