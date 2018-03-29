package com.al0ne.ConcreteEntities.Items.ConcreteItems.Books;

import com.al0ne.AbstractEntities.Enums.Size;
import com.al0ne.ConcreteEntities.Items.Types.Readable;

public class Book extends Readable{

    public Book(String about, String content) {
        super("book"+about, "book",
                "a book about "+about, Size.SMALL, content, 0.5);
    }
}
