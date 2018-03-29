package com.al0ne.ConcreteEntities.Items.ConcreteItems.Books;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Readable;

import static com.al0ne.Engine.Main.printToLog;

/**
 * Created by BMW on 15/03/2017.
 */
public class Scroll extends Readable{
    public Scroll(String id, String name, String description, String content, double weight) {
        super(id, name, description, Size.SMALL, content, weight);
    }
}
