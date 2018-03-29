package com.al0ne.Engine.Editing;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by BMW on 27/05/2017.
 */
public class EditorInfo implements Serializable{
    private HashMap<String, EditingGame> games;
    private EditingGame currentEdit;

    public EditorInfo() {
        this.games = new HashMap<>();
    }


    public HashMap<String, EditingGame> getGames() {
        return games;
    }

    public void addGame(EditingGame g) {
        this.games.put(g.getCurrentEdit().getGameName(), g);
    }

    public EditingGame getCurrentEdit() {
        return currentEdit;
    }

    public void setCurrentEdit(EditingGame current) {
        this.currentEdit = current;
    }
}
