package com.al0ne.ConcreteEntities.Items.ConcreteItems.Books;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Readable;

/**
 * Created by BMW on 07/05/2017.
 */
public class Note extends Readable{
    public Note(String about, String content) {
        super("note"+about, "Note about "+about,
                "a note written hastily on a piece of paper", Size.VSMALL, content, 0.01);
    }
}
