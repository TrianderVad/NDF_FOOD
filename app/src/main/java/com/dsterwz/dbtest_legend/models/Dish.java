package com.dsterwz.dbtest_legend.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dishes_table")
public class Dish {

    //@PrimaryKey(autoGenerate = true)
    //private int id;

    //@ColumnInfo(name = "id")
    @PrimaryKey
    private int dishId;

    private String category;

    @ColumnInfo(name = "dish_name")
    private String nameDish;

    private int price;

    private String icon;

    private String version;

    public Dish(int dishId, String category, String nameDish, int price, String icon, String version) {
        this.dishId = dishId;
        this.category = category;
        this.nameDish = nameDish;
        this.price = price;
        this.icon = icon;
        this.version = version;
    }

    /*public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }*/

    public int getDishId() {
        return dishId;
    }

    public String getCategory() {
        return category;
    }

    public String getNameDish() {
        return nameDish;
    }

    public int getPrice() {
        return price;
    }

    public String getIcon() {
        return icon;
    }

    public String getVersion() {
        return version;
    }
}
