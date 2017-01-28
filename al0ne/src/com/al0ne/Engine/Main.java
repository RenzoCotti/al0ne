package com.al0ne.Engine;

import com.al0ne.Interactables.Items.Archetypes.Interactable;
import com.al0ne.Interactables.Items.Archetypes.Pickable;
import com.al0ne.Player;
import com.al0ne.Room;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {


        HashMap<String, Room> rooms = CreateRooms.create();

        Player Grog = new Player(rooms.get("cave1"), new ArrayList<Pickable>());

        Game game = new Game(Grog, rooms);



    }
}
