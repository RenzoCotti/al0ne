package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Player;
import com.al0ne.Behaviours.Item;
import com.al0ne.Behaviours.Room;
import com.al0ne.Engine.Size;
import com.al0ne.Entities.Items.Behaviours.Readable;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 15/03/2017.
 */
public class Scroll extends Readable{
    public Scroll(String id, String name, String description, String shortDescription, String content, double weight) {
        super(id, name, description, shortDescription, Size.SMALL, content, weight);
    }
}
