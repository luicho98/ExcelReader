/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Views.DemoJFileChooser;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author luisv
 */
public class Controller {

    JFrame frame;
    DemoJFileChooser panel;
    Thread thread;
    public static String path = "";

    public Controller() {

        frame = new JFrame("");
        panel = new DemoJFileChooser();
        frame.addWindowListener(
                new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }
        );
        frame.getContentPane().add(panel, "Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }

    public void threadSearchFile() throws IOException, InterruptedException {
        
        DirectoryWatcherExample filew = new DirectoryWatcherExample();
        filew.metodito();
    }

}
