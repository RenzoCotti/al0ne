package com.al0ne.Engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 22/04/2017.
 */
public class Utility {

    public static int randomNumber (int max){
        return (int)(Math.random() * (max - 1) + 1);
    }

    public static void dumpToFile(String fileName, String content){
        FileOutputStream fop = null;
        ObjectOutputStream oos = null;
        File file;

        Main.game.setNotes(Main.notes.getText());

        try {
            if (fileName != null){
                file = new File(fileName+".txt");
            } else{
                file = new File("./"+fileName+".save");
            }
            fop = new FileOutputStream(file);
            oos = new ObjectOutputStream(fop);

            // if file doesnt exists, then create it
//            if (!file.exists()) {
//                printToLog("File already exists. Specify a non existing file name.");
//                return;
//            }

            // get the content in bytes
            oos.writeObject(content);

            oos.flush();
            oos.close();

            printToLog("Dump successful");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fop != null) {
                try {
                    fop.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
