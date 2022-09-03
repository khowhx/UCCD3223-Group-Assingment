package com.edu.shoppinglistapplication;

public class ShoppingList {
    private int id;

    private String name;
    private String cty;
    private String dateAdded;

    public ShoppingList(String name, String cty, String dateAdded, int id) {
        this.id = id;
        this.name = name;
        this.cty = cty;
        this.dateAdded = dateAdded;
    }

    public ShoppingList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCty() {
        return cty;
    }

    public void setCty(String cty) {
        this.cty = cty;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cty='" + cty + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                '}';
    }
}
