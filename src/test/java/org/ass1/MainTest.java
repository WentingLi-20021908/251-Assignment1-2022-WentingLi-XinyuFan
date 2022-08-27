package org.ass1;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.fixture.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.junit.*;
import org.mockito.Mockito;

public class MainTest {
    private static FrameFixture frameFixture;

    @BeforeClass
    public static void setUp() throws Exception {
        frameFixture = new FrameFixture(new TextFrame());
        frameFixture.show();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        frameFixture.cleanUp();
    }

    @Test
    public void testVisible() throws Exception {
        frameFixture.requireVisible();
    }

    @Test
    public void testCreateMenu() {
        JFrame frame = Mockito.mock(JFrame.class);
        MenuBar menu = new MenuBar(frame);
        JMenu m1 = menu.createMenuItems("test", Arrays.asList("m1"));
        Assert.assertNotNull(m1);
    }

    @Test
    public void testEnterOnEditor() throws Exception {
        Component textArea = findComponent("mainTextArea");
        Assert.assertNotNull(textArea);

        JTextComponentFixture textAreaFixture = new JTextComponentFixture(frameFixture.robot, (JTextComponent) textArea);
        textAreaFixture.enterText("test");

        Assert.assertEquals(((JTextComponent) textArea).getText(), "test");

        clickMenuItem("New");
        Assert.assertEquals(((JTextComponent) textArea).getText(), "");
    }

    private void clickMenuItem(String itemName) {
        Component menuItem = findComponent(itemName);
        Assert.assertNotNull(menuItem);

        JMenuItemFixture menuItemFixture = new JMenuItemFixture(frameFixture.robot, (JMenuItem) menuItem);
        menuItemFixture.click();
    }

//    @Test
    public void testAbout() throws IOException {
        clickMenuItem("About");
        JOptionPane optionPane = (JOptionPane) frameFixture.robot.finder().find(new ComponentMatcher() {
            @Override
            public boolean matches(Component component) {
                if (component instanceof JOptionPane) {
                    return true;
                }

                return false;
            }
        });
        Assert.assertNotNull(optionPane);
    }

    private Component findComponent(String name) {
        return frameFixture.robot.finder().find(new ComponentMatcher() {
            @Override
            public boolean matches(Component component) {
                String componentName = component.getName();
                if (component instanceof AbstractButton theMenu) {
                    componentName = theMenu.getText();
                }
                if (componentName != null && componentName.equals(name)) {
                    return true;
                }

                return false;
            }
        });
    }
}