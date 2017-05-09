package com.al0ne.Entities.NPCs;

import com.al0ne.Behaviours.NPC;
import com.al0ne.Behaviours.Pairs.Subject;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Pairs.Pair;

import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/03/2017.
 */
public class Shopkeeper extends NPC {
    private String list;

    public Shopkeeper(String id, String name, String description, String shortDescription, String intro,
                      int maxHealth, int attack, int dexterity, int armor, int damage) {
        super(id, name, description,shortDescription, intro, maxHealth, attack, dexterity, armor, damage);
        inventory = new HashMap<>();
        list="Items: ";
        isShopkeeper=true;
    }

    public Shopkeeper(String id, String name, String description, String intro) {
        super(id, name, description, intro);
        inventory = new HashMap<>();
        list="I sell these items: \n";
        isShopkeeper=true;
    }

    public Shopkeeper(String id, String name, String description, String shortDescription, String intro) {
        super(id, name, description, shortDescription, intro);
        inventory = new HashMap<>();
        list="I sell these items: \n";
        isShopkeeper=true;
    }

    @Override
    public boolean simpleAddItem(Item item, Integer price) {
        boolean result =  super.simpleAddItem(item, price);
        list+=item.getName()+" - "+price+" coins\n";
        addSubject("items", new Subject(list));
        return result;
    }

    public void buy(Player player, String toBuy){
        if (hasItemInInventory(toBuy)){
            Pair item = getItemPair(toBuy);
            if (player.hasEnoughMoney(item.getCount())){
                player.removeAmountMoney(item.getCount());
                //todo: need to sort out prices

                Pair pairInv = player.getItemPair(item.getEntity().getID());
                if(pairInv != null){
                    pairInv.setCount(pairInv.getCount()+1);
                } else {
                    if (!player.simpleAddItem((Item) item.getEntity(), 1)){
                        player.getCurrentRoom().addEntity((Item) item.getEntity(), 1);
                    }
                }
                printToLog("\"There you go!\"");
                printToLog("You received 1 "+item.getEntity().getName());

            } else{
                printToLog("\"You don't have enough money\"");
            }
        } else{
            printToLog("\"I don't seem to have that item with me.\"");
        }
    }
}
