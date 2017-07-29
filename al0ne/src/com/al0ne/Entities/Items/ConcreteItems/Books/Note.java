package com.al0ne.Entities.Items.ConcreteItems.Books;

import com.al0ne.Behaviours.Enums.Size;
import com.al0ne.Entities.Items.Types.Readable;

/**
 * Created by BMW on 07/05/2017.
 */
public class Note extends Readable{
    public Note(String id, String name, String description, Size size, String content, double weight) {
        super(id, name, description, size, content, weight);
    }

    public Note(String about, String content) {
        super("note"+about, "Note about "+about,
                "a note written hastily on a piece of paper", Size.VSMALL, content, 0.01);
    }
}
