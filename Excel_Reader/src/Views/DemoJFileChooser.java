/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

/**
 *
 * @author luisv
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import Model.Model;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DemoJFileChooser extends JPanel
        implements ActionListener {

    JButton go;

    JFileChooser chooser;
    String choosertitle;
    JLabel folderSelected = new JLabel("");
    JLabel text = new JLabel("Please choose a folder!");

    public DemoJFileChooser() {
        go = new JButton("Choose a folder");
        go.addActionListener(this);
        add(go);
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //it just shows the folders

        chooser.setAcceptAllFileFilterUsed(false);
        this.add(text);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //this removes the last label draw, the directory/folder label 
        if (this.getComponentCount() > 2) {
            //this.remove(2);
            this.remove(folderSelected);
            //System.out.println(this.getComponentCount());
        }
        this.revalidate();
        this.repaint();

        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//      System.out.println("Base Directory: " 
//         +  chooser.getCurrentDirectory());
            System.out.println("Folder: "
                    + chooser.getSelectedFile());
            folderSelected = new JLabel(chooser.getSelectedFile().getAbsolutePath());
            //Controller.Controller.path=chooser.getSelectedFile().getAbsolutePath();
            String oldPath = Model.path;
            Model.path = chooser.getSelectedFile().getAbsolutePath() + "\\";

            //this method force the watcher to run again, this makes the watcher to get notice about a change and look for the new path
            cleanWatcher(oldPath);
        } else {
            System.out.println("No Selection ");
            folderSelected = new JLabel("Please choose a folder!");
            //Controller.Controller.path="";
            Model.path = "";
        }

        this.add(folderSelected);
        this.revalidate();
        this.repaint();

        //if a directoy is chosen, it will get into it and do the stuff needed
//        if(flag){
//            WatchForFile fileWatcher = new WatchForFile();
//            fileWatcher.WatchMyFolder(chooser.getSelectedFile().getAbsolutePath());
//            
//        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    //timeout on watcher already avoid an app block but the watcher holds the path to check
    //the watcher waits for actions, this method is to avoid staying there and provoque a run to check path change
    public void cleanWatcher(String oldpath) {

        File f;
        if (oldpath.equals("")) {
            //getting absolute path of the first run of the watcher, then triggering the watcher to change its path
            String basePath = new File("").getAbsolutePath();
            f = new File(basePath + "\\", "cleanWatcher.txt");

            //if oldpath is null it means its the first run, so lets create the folders to save files found.
            createFolders(basePath);
            Model.basePath=basePath;
        } else {
            f = new File(oldpath + "\\", "cleanWatcher.txt");
            System.out.println("oldpath -" + oldpath + '-');
            System.out.println("NOTHING path");
        }

        try {
            String basePath = new File("").getAbsolutePath();
            System.out.println("basepath" + basePath);
            if (!f.exists()) {
                System.out.println("created");
                f.createNewFile();
                f.delete();
            } else {
                System.out.println("NOTHING created");
            }

        } catch (IOException ex) {
            Logger.getLogger(DemoJFileChooser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //create folders-> Processed / Not applicable
    private void createFolders(String basePath) {
        File f1 = new File(basePath + "\\" + "Processed");
        if (!f1.exists()) {
            f1.mkdir();
        } else {
            System.out.println("processed created");
        }
        File f2 = new File(basePath + "\\" + "Not applicable");
        if (!f2.exists()) {
            f2.mkdir();
        } else {
            System.out.println("notApl created");
        }
    }
}
