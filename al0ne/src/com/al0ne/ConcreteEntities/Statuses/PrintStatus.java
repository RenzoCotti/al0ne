package com.al0ne.ConcreteEntities.Statuses;

import com.al0ne.AbstractEntities.Abstract.Status;
import com.al0ne.AbstractEntities.Abstract.WorldCharacter;

import static com.al0ne.Engine.Main.printToLog;

public class PrintStatus extends Status{

    public PrintStatus(String resolve) {
        super("print", 0, "", resolve);
    }

    @Override
    public boolean resolveStatus(WorldCharacter character) {
        printToLog(onResolve);
        return true;
    }
}
