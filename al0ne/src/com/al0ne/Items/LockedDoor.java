package com.al0ne.Items;

import com.al0ne.Items.Behaviours.Weapon;

/**
 * Created by BMW on 02/02/2017.
 */
public class LockedDoor extends Prop{
    public LockedDoor(String name, String description, String after, String key) {
        super(name, description, after);
        this.requiresItem=key;
    }

    public LockedDoor(String name, String key) {
        super(name, "A sturdy wooden door");
        this.requiresItem=key;
    }

    @Override
    public boolean usedWith(Item item){
        if(item.hasProperty("key")){
            if (item.getName().equals(requiresItem)){
                active=true;
                return true;
            } else{
                System.out.println("The key doesn't seem to fit.");
                return false;
            }
        } else if(( item.hasProperty("sharp") || item.hasProperty("blunt"))){
            Weapon temp = (Weapon) item;
            if (temp.getDamage() > 5){
                System.out.println("You break the door open");
                active = true;
                return true;
            } else {
                System.out.println("You try to break the door, but to no avail.");
                return false;
            }
        }
        else{
            System.out.println("The "+ item.getName()+" doesn't seem to fit in the keyhole.");
            return false;
        }
    }

    @Override
    public boolean used(){
        System.out.println("The door is locked.");
        return false;
    }
}
