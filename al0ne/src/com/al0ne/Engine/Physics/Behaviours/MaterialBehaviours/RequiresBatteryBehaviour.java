package com.al0ne.Engine.Physics.Behaviours.MaterialBehaviours;

import com.al0ne.Engine.Physics.InteractableBehaviour;

public class RequiresBatteryBehaviour extends InteractableBehaviour {
    protected String batteryType;
    public RequiresBatteryBehaviour(String batteryType) {
        super("requiresbattery");
        this.batteryType = batteryType;
    }

    public String getBatteryType() {
        return batteryType;
    }
}
