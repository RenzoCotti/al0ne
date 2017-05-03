package com.al0ne.Entities.Items.ConcreteItems;

import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Items.Behaviours.Readable;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 15/03/2017.
 */
public class Scroll extends Readable{
    public Scroll(String id, String name, String description, String content, double weight) {
        super(id, name, description, Size.SMALL, content, weight);
    }
}
