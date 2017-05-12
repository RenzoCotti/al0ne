package com.al0ne.Behaviours;

import com.al0ne.Behaviours.Enums.Material;
import com.al0ne.Behaviours.Pairs.Pair;
import com.al0ne.Engine.Utility;
import com.al0ne.Entities.Items.Behaviours.Weapons.*;
import com.al0ne.Entities.Items.Behaviours.Wearable.*;
import com.al0ne.Entities.Items.ConcreteItems.Coin.SilverCoin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by BMW on 13/05/2017.
 */
public class LootTable implements Serializable{
    private ArrayList<Item> loot;

    public LootTable() {
        this.loot = new ArrayList<>();
        for (Material m : Material.getMetals()){
            loot.add(new Sallet(m));
            loot.add(new GreatHelm(m));
            loot.add(new Barbute(m));
            loot.add(new ChainMail(m));
            loot.add(new PlateArmor(m));
            loot.add(new ScaleArmor(m));
        }

        for (Material m : Material.getMaterials(true)){
            loot.add(new Armor(m));
            loot.add(new Shield(m));
            loot.add(new Helmet(m));
        }

        for (Material m : Material.getMaterials(false)){
            loot.add(new Sword(m));
            loot.add(new Mace(m));
            loot.add(new Spear(m));
            loot.add(new Dagger(m));
            loot.add(new Axe(m));
        }

        long seed = System.nanoTime();
        Collections.shuffle(loot, new Random(seed));
    }

    public ArrayList<Pair> getLoot(int value){
        int total = 0;

        ArrayList<Pair> lootDropped = new ArrayList<>();


        int maxItems = 0;
        for (int begin = Utility.randomNumber(loot.size()); begin < loot.size(); begin++){
            Item currentItem = loot.get(begin);

            if(total+currentItem.getPrice() <= value){
                lootDropped.add(new Pair(currentItem, 1));
                total+= currentItem.getPrice();
                maxItems++;

                int chanceToContinue = (int)((((double)currentItem.getPrice())
                        /(double)value)*100);
                System.out.println("must roll above: "+chanceToContinue);

                int random = Utility.randomNumber(currentItem.getPrice());
                System.out.println("rolled: "+random);
                if(random > 100 - chanceToContinue || maxItems == 2){
                    break;
                }
            }
        }
        for(Pair p : Utility.toCoins(value-total)){
            if(p.getCount() > 0){
                lootDropped.add(p);
            }
        }

        long seed = System.nanoTime();
        Collections.shuffle(loot, new Random(seed));

        return lootDropped;
    }

}
