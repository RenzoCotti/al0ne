package com.al0ne.Items.ConcreteBehaviours.NoBehaviours;

import com.al0ne.Items.Behaviours.Cuttable;

/**
 * Created by BMW on 02/02/2017.
 */
public class NoCut implements Cuttable{
    private String isCut;
    public NoCut(String isCut) {
        this.isCut=isCut;
    }

    public NoCut() {
        this.isCut="You can't cut it.";
    }

    @Override
    public boolean isCut() {
        System.out.println(isCut);
        return false;
    }
}
