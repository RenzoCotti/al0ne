package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.AbstractEntities.Abstract.Item;
import com.al0ne.AbstractEntities.Player.Player;
import com.al0ne.AbstractEntities.Room;
import com.al0ne.AbstractEntities.Abstract.Interactable;

public class InteractionDestroy extends InteractionBehaviour {
    private Interactable toDestroy;
    public InteractionDestroy(Interactable toDestroy) {
        this.toDestroy = toDestroy;
    }

    @Override
    public void interactionEffect(Player player) {
        Room currentRoom = player.getCurrentRoom();
        String ID = toDestroy.getID();
        if(player.hasItemInInventory(ID)){
            Item item = (Item) player.getInventory().get(ID).getEntity();
            player.removeOneItem(item);
        } else{
            currentRoom.getEntities().remove(ID);
        }
    }
}
