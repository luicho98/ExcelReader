/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Model;
import com.aspose.cells.Workbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author luisv
 */
public class DirectoryWatcherExample {

    public void metodito() throws IOException, InterruptedException {
        System.out.println("para thread");
        while (true) {
            //call method that read the directory and organize
            if (!Model.path.equals("")) {
                final File folder = new File(Model.path);
                organizeFilesForFolder(folder);
            } else {
                createMasterWorkSheet();
            }

            //this code below is the one that cares about new entries on directories
            WatchService watchService
                    = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(Model.path);
            System.out.println("My path " + Model.path);

            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE);
            //StandardWatchEventKinds.ENTRY_DELETE, // files are gonna be moved to other directories, so its not needed
            //StandardWatchEventKinds.ENTRY_MODIFY);  //the solution needs NEW FILES

            WatchKey key;
            boolean flag = true;
            while (flag && (key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println(Model.path);
                    System.out.println(
                            "Event kind:" + event.kind()
                            + ". File affected: " + event.context() + ".");
                }
                key.reset();

                System.out.println("tpstring " + path.getFileName().toString());
                if (!path.getFileName().toString().equals(Model.path)) {
                    flag = false;
                    System.out.println("flag");
                }
            }
        }
    }

    //checks the directory and move the files
    //FILES .xls*  -> I assume the * means other types, not just xls. -> xls xlsx xlsm xlsb
    public void organizeFilesForFolder(final File folder) {
        boolean in = false;
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                organizeFilesForFolder(fileEntry);
            } else {
                if (fileEntry.getName().endsWith(".xls") || fileEntry.getName().endsWith(".xlsx") || fileEntry.getName().endsWith(".xlsm") || fileEntry.getName().endsWith(".xlsb")) {
                    System.out.println(fileEntry.getName());
                    if (!(new File(Model.basePath + "\\" + "Processed" + "\\" + fileEntry.getName())).exists()) {

                        //with the master workbook, copy each sheet to the master file
                        try {
                            System.out.println(fileEntry.getAbsolutePath());
                            //get the master
                            Workbook master = new Workbook(Model.basePath + "\\" + "MASTER.xlsx");
                            Workbook fileToAdd = new Workbook(fileEntry.getAbsolutePath());

                            //aspose is gona leave sheets because needs license, the merge could be done with apache POI but I wanted to show the use of external jars
                            master.getWorksheets().removeAt("Evaluation Warning");
                            master.combine(fileToAdd);
                            master.save(Model.basePath + "\\" + "MASTER.xlsx");
                            in = true;
                            
                            //send to PROCESSED
                            fileEntry.renameTo(new File(Model.basePath + "\\" + "Processed" + "\\" + fileEntry.getName()));

                        } catch (Exception ex) {
                            Logger.getLogger(DirectoryWatcherExample.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                } else {
                    System.out.println(fileEntry.getName());
                    if (!(new File(Model.basePath + "\\" + "Not applicable" + "\\" + fileEntry.getName())).exists()) {
                        //send to NOT APPLICABLE
                        fileEntry.renameTo(new File(Model.basePath + "\\" + "Not applicable" + "\\" + fileEntry.getName()));
                    }
                }

            }
        }

        if (in) {
            //as I wrote, aspose leave the Evaluation Warning Sheet, so this is a way to avoid this
            //THIS IS ONLY FOR THE EXCERCISE, TO SHOW THE EASY USE OF THIS JAR TO MANAGE EXCEL FILES.
            //AVOID USING THIS CODE IN PRODUCTION. ASPOSE SELLS LICENSES.
            //SEE MORE ABOUT THEM IN https://www.aspose.com/
            //
            try {
                XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(Model.basePath + "\\" + "MASTER.xlsx"));
                int numberSheets = book.getNumberOfSheets();
                book.removeSheetAt((numberSheets - 1));
                FileOutputStream os = new FileOutputStream(Model.basePath + "\\" + "MASTER.xlsx");
                book.write(os);
            } catch (Exception ex) {
                Logger.getLogger(DirectoryWatcherExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //create worksheet if not exists on basepath
    private void createMasterWorkSheet() {
        String basePath = new File("").getAbsolutePath();
        File f = new File(basePath + "\\", "MASTER.xlsx");

        try {
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(DirectoryWatcherExample.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
