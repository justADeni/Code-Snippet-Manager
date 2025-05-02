package core;

import core.customisation.GithubItem;
import core.customisation.ThemeMenu;
import core.highlight.GeneralTheme;
import core.io.DataManager;
import core.io.SettingsManager;
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
        this.setSize(600, 400);
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

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new ThemeMenu(this));
        menuBar.add(new GithubItem());
        setJMenuBar(menuBar);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataManager.saveData();
                SettingsManager.save();
            }
        });
    }

    private void addComponent() {
        controlPanel.addComponent();
        tabbedPanel.addComponent();

        this.setVisible(true);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        SettingsManager.load();
        UIManager.setLookAndFeel(GeneralTheme.load());

        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.init();
            app.addComponent();
        });
    }

}