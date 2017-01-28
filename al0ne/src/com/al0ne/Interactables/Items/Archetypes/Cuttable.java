package com.al0ne.Interactables.Items.Archetypes;

/**
 * Created by BMW on 28/01/2017.
 */
public abstract class Cuttable extends Interactable {

    public Cuttable(String name, String description) {
        super(name, description);
    }

    @Override
    public void isInteractedWith(Pickable item) {

        if (isToggled()) {
            printDescription();
            return;
        }

        for (String s : item.getUses()){
            if (s.equals("sharp")){
                setToggled(true);
                System.out.println(getName()+" is cut using the "+ item.getName());
                return;
            }
        }
        System.out.println(item.getName()+" is not sharp enough.");
    }

    @Override
    public void printDescription(){
        if (isToggled()){
            System.out.println("The "+ getName() + " has been cut");
        } else {
            System.out.println(getDescription());
        }
    }
}
