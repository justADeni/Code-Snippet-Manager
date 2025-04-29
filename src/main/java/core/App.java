package core;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatXcodeDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTAtomOneDarkIJTheme;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import core.panels.ControlPanel;
import core.panels.GroupsTabbedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App extends JFrame {

    public ControlPanel controlPanel;
    public GroupsTabbedPanel tabbedPanel;
    public DataManager dataManager;

    private App() {
        this.setTitle("Code Snippet Manager");
        this.setSize(500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
    }

    private void init() {
        controlPanel = new ControlPanel(this);
        tabbedPanel = new GroupsTabbedPanel(this);

        tabbedPanel.init();
        controlPanel.init();

        dataManager = new DataManager(tabbedPanel);
        dataManager.loadData();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataManager.saveData();
            }
        });
    }

    private void addComponent() {
        controlPanel.addComponent();
        tabbedPanel.addComponent();

        this.setVisible(true);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacDarkLaf());

        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.init();
            app.addComponent();
        });
    }
}