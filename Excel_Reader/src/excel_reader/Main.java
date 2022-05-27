/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel_reader;

import Controller.Controller;
import Views.DemoJFileChooser;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author luisv
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String s[]) throws IOException {
        try {
            Controller cont = new Controller();
            cont.threadSearchFile();
            System.out.println("para thread");
        } catch(Exception e)  {
            System.out.println("Error type: "+e);
        }

/*
    JFrame frame = new JFrame("");
        DemoJFileChooser panel = new DemoJFileChooser();
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
        System.out.println("ahh");
        */
    }
}
