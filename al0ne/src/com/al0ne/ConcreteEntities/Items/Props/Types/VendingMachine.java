package com.al0ne.ConcreteEntities.Items.Props.Types;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Enums.Material;
import com.al0ne.AbstractEntities.Pairs.Pair;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Prop;
import com.al0ne.Engine.Utility.GameChanges;

import java.util.ArrayList;
import java.util.HashMap;

import static com.al0ne.Engine.Main.printToLog;

public class VendingMachine extends Prop{
    private HashMap<String, Pair> stock = new HashMap<>();
    public VendingMachine(String name, String description, Material m) {
        super(name, description, m);
    }

    public VendingMachine() {
        super("vending machine", "A vending machine made out of metal", Material.IRON);
    }

    public HashMap<String, Pair> getStock() {
        return stock;
    }

    public void addItem(Item item, int cnt) {
        this.stock.put(item.getID(), new Pair(item, cnt));
    }


    public boolean hasItemInStock(String item){
        return getStock().get(item) != null;
    }


    public Pair getItemPair(String s){
        return getStock().get(s);
    }

    public void printItemList(){
        printToLog("Item\t\tPrice");
        printToLog("__________________");
        for (Pair p : getStock().values()){
            Item i = (Item) p.getEntity();
            printToLog(i.getName()+"(x"+p.getCount()+")\t"+i.getPrice());
        }
    }

    @Override
    public void printLongDescription(Player player) {
        super.printLongDescription(player);
        printItemList();
    }

    public void buy(Player player, String toBuy){
        if (hasItemInStock(toBuy)){
            Pair item = getItemPair(toBuy);
            if (GameChanges.hasEnoughMoney(player, ((Item)item.getEntity()).getPrice())){
                GameChanges.removeAmountMoney(player, ((Item)item.getEntity()).getPrice());

                Pair pairInv = player.getItemPair(item.getEntity().getID());
                if(pairInv != null){
                    pairInv.modifyCount(1);
                } else {
                    if (!player.simpleAddItem((Item) item.getEntity(), 1)){
                        player.getCurrentRoom().addEntity(item.getEntity(), 1);
                    }
                }
                if (item.getCount() == 0){
                    getStock().remove(item.getEntity().getID());
                } else {
                    item.modifyCount(-1);
                }
                printToLog("You purchased a "+item.getEntity().getName());
            } else{
                printToLog("You don't have enough money.");
            }
        } else{
            printToLog("That item is not available.");
        }
    }
}
