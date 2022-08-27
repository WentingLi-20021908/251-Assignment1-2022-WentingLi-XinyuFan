package org.ass1;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.pkg.OdfElement;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class MenuBar extends JMenuBar implements ActionListener {

    private RSyntaxTextArea textarea;
    private JFrame frame;

    public MenuBar(final JFrame frame) {
        super();
        this.frame = frame;
        final ArrayList<String> fileItems = new ArrayList<>();
        fileItems.add("New");
        fileItems.add("Open");
        fileItems.add("Save");
        final JMenu fileMenu = createMenuItems("File", fileItems);

        final ArrayList<String> editItems = new ArrayList<>();
        editItems.add("Select text");
        editItems.add("Copy");
        editItems.add("Paste");
        editItems.add("Cut");
        final JMenu editMenu = createMenuItems("Edit", editItems);

        final ArrayList<String> manageItems = new ArrayList<>();
        manageItems.add("Print");
        final JMenu manageMenu = createMenuItems("Manage", manageItems);

        final ArrayList<String> viewItems = new ArrayList<>();
        viewItems.add("Time and Date");
        final JMenu viewMenu = createMenuItems("View", viewItems);

        final JMenu searchMenu = createMenuItems("Search", Collections.singletonList("Find word"));

        final ArrayList<String> helpItems = new ArrayList<>();
        helpItems.add("About");
        helpItems.add("Exit");
        final JMenu helpMenu = createMenuItems("Help", helpItems);

        final ArrayList<String> syntaxItems = new ArrayList<>();
        syntaxItems.add("Text");
        syntaxItems.add("Java");
        syntaxItems.add("Python");
        syntaxItems.add("Cpp");
        syntaxItems.add("Css");
        syntaxItems.add("JavaScript");
        final JMenu syntaxMenu = createMenuItems("Syntax", syntaxItems);

        this.add(fileMenu);
        this.add(editMenu);
        this.add(searchMenu);
        this.add(syntaxMenu);
        this.add(viewMenu);
        this.add(manageMenu);
        this.add(helpMenu);

        textarea = new RSyntaxTextArea();
        textarea.setName("mainTextArea");
        textarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
        final JScrollPane scrollPane = new JScrollPane(textarea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);
    }

    public JMenu createMenuItems(final String menuName, final List<String> items) {

        final JMenu menu = new JMenu(menuName);
        for (final String itemStr : items) {
            final JMenuItem item = new JMenuItem(itemStr);
            item.addActionListener(this);
            menu.add(item);
        }
        return menu;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        final String itemName = event.getActionCommand();

        if ("Cut".equals(itemName)) {
            textarea.cut();
        } else if ("Copy".equals(itemName)) {
            textarea.copy();
        } else if ("Paste".equals(itemName)) {
            textarea.paste();
        } else if ("Select text".equals(itemName)) {
            textarea.selectAll();
        } else if ("Save".equals(itemName)) {
            saveText();
        } else if ("Time and Date".equals(itemName)) {
            timeAndDate();
        } else if ("Print".equals(itemName)) {
            try {
                textarea.print();
            } catch (Exception pr) {
                JOptionPane.showMessageDialog(frame, pr.getMessage());
            }
        } else if ("Open".equals(itemName)) {
            openFile();
        } else if ("New".equals(itemName)) {
            textarea.setText("");
        } else if ("Exit".equals(itemName)) {
            frame.setVisible(false);
        } else if ("About".equals(itemName)) {
            showAbout();
        } else if ("Text".equals(itemName) || "Java".equals(itemName) || "JavaScript".equals(itemName)
                || "Cpp".equals(itemName) || "Python".equals(itemName) || "Css".equals(itemName)) {
            setSyntaxEditingStyle(itemName);
        } else if ("Find word".equals(itemName)) {
            searchText();
        }
    }

    private void searchText() {
        final String word = JOptionPane.showInputDialog(
                null,
                "Input word to search",
                ""
        );

        if (word != null && !word.isEmpty()) {
            final String text = textarea.getText();
            final Highlighter highlighter = textarea.getHighlighter();
            highlighter.removeAllHighlights();
            final HighlightPainter painter = new DefaultHighlightPainter(Color.YELLOW);
            int count = 0;
            int start = 0;
            int pos;
            while ((pos = text.indexOf(word, start)) != -1) {
                count++;
                try {
                    highlighter.addHighlight(pos, pos + word.length(), painter);
                } catch (BadLocationException e) {
                    JOptionPane.showMessageDialog(null, "Failed to highlight word", "Search word",
                            JOptionPane.ERROR_MESSAGE);
                }

                start = pos + word.length() + 1;
            }

            if (count == 0) {
                JOptionPane.showMessageDialog(null, "Word not found", "Search word", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAbout() {
        final StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("A Text editor").append("\n")
                .append("Team members: Andy Jack").append("\n");
        JOptionPane.showMessageDialog(null, msgBuilder.toString(), "About Text Editor", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setSyntaxEditingStyle(final String style) {
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
            default:
                textarea.setSyntaxEditingStyle(null);
                break;
        }
    }

    public void timeAndDate() {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        final Date date = new Date();
        textarea.setText(sdf.format(date) + "\n" + textarea.getText());
    }

    public void saveText() {
        final JFileChooser fileChooser = new JFileChooser();

        final int responseButton = fileChooser.showSaveDialog(null);

        if (responseButton == JFileChooser.APPROVE_OPTION) {

            final File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            final String fileName = file.getName();
            if ("pdf".equals(fileName.split("\\.")[1].toLowerCase())) {
                saveAsPDFFile(file);
            } else if ("odt".equals(fileName.split("\\.")[1].toLowerCase())) {
                saveAsODT(file);
            } else {
                try {
                    final FileWriter fileWriter = new FileWriter(file, false);
                    final BufferedWriter writer = new BufferedWriter(fileWriter);
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
        final JFileChooser chooserFile = new JFileChooser();
        final int readFile = chooserFile.showOpenDialog(null);
        if (readFile == JFileChooser.APPROVE_OPTION) {
            final File file = new File(chooserFile.getSelectedFile().getAbsolutePath());

            final String fileName = file.getName();
            if ("odt".equals(fileName.split("\\.")[1].toLowerCase())) {
                openODTFile(file);
            } else {
                openStandardFile(file);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Do not open file");
        }
    }

    public void saveAsODT(final File file) {
        try {
            final OdfTextDocument odt = OdfTextDocument.newTextDocument();
            odt.addText(textarea.getText());
            odt.save(file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error saving as ODT", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveAsPDFFile(final File file) {
        final Document document = new Document();
        try {
            final PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
            document.add(new Paragraph(textarea.getText()));
        } catch (DocumentException | IOException de) {
            JOptionPane.showMessageDialog(frame, de.getMessage(), "Error saving as PDF", JOptionPane.ERROR_MESSAGE);
        }

        document.close();
    }

    public void openODTFile(final File file) {
        try {
            final OdfTextDocument odt = OdfTextDocument.loadDocument(file);
            final OdfElement root = odt.getContentRoot();

            final OdfElement firstParagraph = OdfElement.findFirstChildNode(TextPElement.class, root);
            textarea.setText(firstParagraph.getTextContent());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error opening ODT File", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void openStandardFile(final File file) {
        try {
            final FileReader fileReader = new FileReader(file);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            String nextLine = bufferedReader.readLine();

            String fileLine;
            while ((fileLine = bufferedReader.readLine()) != null) {
                nextLine += "\n" + fileLine;
            }

            textarea.setText(nextLine);
        } catch (Exception evt) {
            JOptionPane.showMessageDialog(frame, evt.getMessage());
        }
    }

}