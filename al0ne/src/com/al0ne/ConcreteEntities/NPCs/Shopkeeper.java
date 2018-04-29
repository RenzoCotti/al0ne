package com.al0ne.ConcreteEntities.NPCs;

import com.al0ne.AbstractEntities.NPC;
import com.al0ne.AbstractEntities.Pairs.Subject;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.Engine.Utility.GameChanges;

import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 14/03/2017.
 */
public class Shopkeeper extends NPC {
    private String list;

    public Shopkeeper(String name, String description, String intro,
                      int maxHealth, int attack, int dexterity, int armor, int damage) {
        super(name, description, intro, maxHealth, attack, dexterity, armor, damage);
        inventory = new HashMap<>();
        list="Items: ";
        isShopkeeper=true;
    }

    public Shopkeeper(String name, String description, String intro) {
        super(name, description, intro);
        inventory = new HashMap<>();
        list="I sell these items: \n";
        isShopkeeper=true;
    }

    @Override
    public boolean simpleAddItem(Item item, Integer amt) {
        boolean result =  super.simpleAddItem(item, amt);
        list+=item.getName()+" - "+item.getPrice()+" coins\n";
        addSubject("items", new Subject(list));
        return result;
    }

    public void buy(Player player, String toBuy){
        if (hasItemInInventory(toBuy)){
            Pair item = getItemPair(toBuy);
            if (GameChanges.hasEnoughMoney(player, ((Item)item.getEntity()).getPrice())){
                GameChanges.removeAmountMoney(player, ((Item)item.getEntity()).getPrice());
                //todo: need to sort out prices

                Pair pairInv = player.getItemPair(item.getEntity().getID());
                if(pairInv != null){
                    pairInv.modifyCount(1);
                } else {
                    if (!player.simpleAddItem((Item) item.getEntity(), 1)){
                        player.getCurrentRoom().addEntity(item.getEntity(), 1);
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
