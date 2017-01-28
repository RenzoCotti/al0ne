package com.al0ne.Interactables.Items.Archetypes;

/**
 * Created by BMW on 26/06/2016.
 */
public interface Interactable {

    void isToggled(Pickable item);

    void setToggled(boolean toggled);

    boolean getToggled();

}
