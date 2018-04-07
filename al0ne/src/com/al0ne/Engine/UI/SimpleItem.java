package com.al0ne.Engine.UI;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by BMW on 23/04/2017.
 */
public class SimpleItem {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty amount;
    private final SimpleDoubleProperty weight;

    private final SimpleIntegerProperty value;
    private final SimpleIntegerProperty damage;
    private final SimpleIntegerProperty defense;

    public SimpleItem(String id, String name, int amount, double weight, int value, int defense, int damage) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
        this.weight = new SimpleDoubleProperty(weight);
        this.value = new SimpleIntegerProperty(value);
        this.damage = new SimpleIntegerProperty(damage);
        this.defense = new SimpleIntegerProperty(defense);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAmount() {
        return amount.get();
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public double getWeight() {
        return weight.get();
    }

    public SimpleDoubleProperty weightProperty() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }

    public int getValue() {
        return value.get();
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public int getDamage() {
        return damage.get();
    }

    public SimpleIntegerProperty damageProperty() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage.set(damage);
    }

    public int getDefense() {
        return defense.get();
    }

    public SimpleIntegerProperty defenseProperty() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense.set(defense);
    }
}
