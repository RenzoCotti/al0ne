package com.al0ne.Engine.Utility;

import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.Engine.Main;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Armor;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Helmet;
import com.al0ne.ConcreteEntities.Items.Types.Wearable.Weapon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 22/04/2017.
 */
public class Utility {

    public static int randomNumber (int max){
        return (int)(Math.random() * (max - 1) + 1);
    }

    public static boolean randomGreaterThan (int threshold){
        return randomNumber(100) > threshold;
    }

    public static void dumpToFile(String fileName, String content){
        FileOutputStream fop = null;
        ObjectOutputStream oos = null;
        File file;

        Main.game.setNotes(Main.notes.getText());

        try {
            file = new File(fileName+".txt");
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

    public static void removeFile(String fileName){
        try {
            File f = new File(fileName);
            f.delete();
        } catch (Exception x) {
            System.out.println("Cant remove file");
        }
    }

    //this function makes a string from the begin-th element of the array to the end-th
    public static String stitchFromTo(String[] input, int begin, int end) {
        String temp = "";
        for (int i = begin; i != end; i++) {
            temp += input[i] + " ";
        }

        temp = temp.substring(0, temp.length() - 1);
        return temp;
    }

    //this function looks for token in input
    public static int checkForToken(String[] input, String token) {
        for (int i = 0; i != input.length; i++) {
            if (input[i].equals(token)) {
                return i;
            }
        }
        return -1;
    }

    public static double twoDecimals(double number){
       return Math.round(number*100.0)/100.0;
    }

    public static Material getRandomMaterial(int kind){
        int total = Material.getMaterials(kind).size();
        int random = randomNumber(total);

        int i = 0;
        for(Material m : Material.getMaterials(kind)){
            i++;
            if(i==random){
                return m;
            }
        }
        return null;
    }

    public static String getArticle(String s){
        char l = s.toLowerCase().charAt(0);
        if (l == 'a' || l == 'e' || l == 'i' || l == 'o' || l == 'u'){
            return "an";
        }
        return "a";
    }

    //these functions return the needed string
    //for the given body part
    //used for PlayUI
    public static String getHelmetString(){
        Helmet h = Main.player.getHelmet();
        if(h == null) return "None.";
        return h.getName();
    }

    public static String getArmorString(){
        Armor a = Main.player.getArmor();
        if(a == null) return "None.";
        return a.getName();
    }

    public static String getWeaponString(){
        Weapon w = Main.player.getWeapon();
        if(w == null) return "None.";
        return w.getName();
    }

    public static String getOffHandString(){
        Item s = Main.player.getOffHand();
        if(s == null) return "None.";
        return s.getName();
    }

    public static String capitalise(String s){
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static int parseNumber(String[] temp){
        int amt = 1;
        try {
            amt = Integer.parseInt(temp[1]);
        } catch (NumberFormatException ex){
            if (temp[1].equals("all")) {
                return -1;
            }
        }
        return amt;
    }

    public static String getAllString (String[] temp, int amt){
        String item;
        if(amt == -1){
            //take all shoes
            item = Utility.stitchFromTo(temp, 2, temp.length);
        } else if(amt == 1){
            //take shoes
            item = Utility.stitchFromTo(temp, 1, temp.length);
        } else {
            //take 12 shoes
            item = Utility.stitchFromTo(temp, 2, temp.length);
        }
        return item;
    }

}
