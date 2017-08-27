package com.al0ne.Engine.Physics.InteractionResult;

import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Room;
import com.al0ne.Behaviours.abstractEntities.Interactable;

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
