package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Behaviours.abstractEntities.Interactable;
import com.al0ne.Engine.Utility;
import com.al0ne.Behaviours.Enums.Material;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 02/02/2017.
 *
 * Item interface
 */
public abstract class Item extends Interactable {

    //todo: add plural?
    protected double weight;
    protected int size;
    protected boolean unique;
    public int price;

    public Item(String id, String name, String description, double weight, Size size, Material material, Integer price) {
        super(id, name, description,
                "temp", material);
        this.weight = weight;
        this.type='i';
        this.size=Size.toInt(size);
        this.unique = false;
        this.canDrop = true;
        this.canTake=true;

        if(material != null){
            this.shortDescription = Utility.getArticle(Material.stringify(material))+
                    " "+Material.stringify(material)+" "+name.toLowerCase();
        } else {
            this.shortDescription = Utility.getArticle(name)+" "+name.toLowerCase();
        }


        if(price == null && material != null){
            int quality = Math.max(material.getToughness(), material.getDamage());
            this.price = ((int) ((material.getPrice()*2+quality)*weight))*2;
        } else if (material == null){
            this.price = 0;
        } else {
            this.price = price;
        }
    }

    public double getWeight() {
        return weight;
    }

    public void modifyWeight(double amt) {
        double temp = Math.round((weight+=amt)*100);
        weight = temp/100;
    }

    public void setUnique(){
        this.unique=true;
    }

    public boolean isUnique(){
        return unique;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void printLongDescription(Player player, Room room) {
        super.printLongDescription(player, room);
        printToLog("It's "+(Size.toString(this.size))+".");
        String m = Material.stringify(this.material);
        if(!m.equals("undefined")){
            printToLog("It's made of "+(Material.stringify(this.material))+".");
        }
    }

    public int getPrice() {
        return price;
    }

}
