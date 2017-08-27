package com.al0ne.Engine.Physics.Behaviours.MaterialBehaviours;

import com.al0ne.Engine.Physics.Behaviour;

public class RequiresBatteryBehaviour extends Behaviour {
    protected String batteryType;
    public RequiresBatteryBehaviour(String batteryType) {
        super("requiresbattery");
        this.batteryType = batteryType;
    }

    public String getBatteryType() {
        return batteryType;
    }
}
