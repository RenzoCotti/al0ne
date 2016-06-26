package com.al0ne;

import com.al0ne.Interactables.Door;

public class Main {

    public static void main(String[] args) {
        Door a = new Door("Knock knock", "Sorry, did you knock?");
        a.setToggled(false);
    }
}
