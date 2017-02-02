package com.al0ne.Items.ConcreteBehaviours.YesBehaviours;

import com.al0ne.Items.Behaviours.Cuttable;

/**
 * Created by BMW on 02/02/2017.
 */
public class CanCut implements Cuttable{
    private String isCut;
    public CanCut(String isCut) {
        this.isCut=isCut;
    }

    public CanCut() {
        this.isCut="You cut it.";
    }

    @Override
    public boolean isCut() {
        System.out.println(isCut);
        return true;
    }
}
