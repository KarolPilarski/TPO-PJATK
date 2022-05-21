package zad1;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JPanel;

public class WikiPanel extends JPanel {

    private Stage stage;
    private WebView browser;
    private JFXPanel jfxPanel;
    private WebEngine webEngine;
    Service s;

    public WikiPanel(Service s){
        this.s=s;
        setPreferredSize(new Dimension(800,750));
        initComponents();
    }

    private void initComponents(){
        System.out.println(s.countryName);
        jfxPanel = new JFXPanel();
        createScene();

        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);
    }

    private void createScene() {
        Thread thread =new Thread() {

            @Override
            public void run() {

                stage = new Stage();

                stage.setResizable(true);

                Group root = new Group();
                Scene scene = new Scene(root,80,20);
                stage.setScene(scene);

                browser = new WebView();
                webEngine = browser.getEngine();
                webEngine.load("https://en.wikipedia.org/wiki/"+s.countryName);

                ObservableList<Node> children = root.getChildren();
                children.add(browser);

                jfxPanel.setScene(scene);
            }
        };
        thread.setDaemon(true);

        PlatformImpl.startup(thread);

    }
}