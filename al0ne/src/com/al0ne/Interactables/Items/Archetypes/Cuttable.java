package com.al0ne.Interactables.Items.Archetypes;

/**
 * Created by BMW on 28/01/2017.
 */
public abstract class Cuttable implements Interactable {

    private boolean toggled;
    private String description;

    public Cuttable(String description) {
        this.description = description;
    }

    @Override
    public void isToggled(Pickable item) {
        for (String s : item.getUses()){
            if (s.equals("sharp")){
                setToggled(true);
            }
        }
    }

    @Override
    public void setToggled(boolean toggled) {
        this.toggled=toggled;
    }

    @Override
    public boolean getToggled() {
        return toggled;
    }
}
