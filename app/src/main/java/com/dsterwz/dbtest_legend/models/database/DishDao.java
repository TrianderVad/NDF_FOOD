package com.dsterwz.dbtest_legend.models.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.dsterwz.dbtest_legend.models.Dish;

import java.util.List;

@Dao
public interface DishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Dish dish);

    @Update
    void update(Dish dish);

    @Delete
    void delete(Dish dish);

    //если нужна "необычная" команда, то пропысываем SQL-инструкцию в Query
    @Query("DELETE FROM dishes_table")
    void deleteAllDishes();

    @Query("SELECT * FROM dishes_table WHERE category = 'Foods' ORDER BY dishId DESC")
    LiveData<List<Dish>> getAllFoods();

    @Query("SELECT * FROM dishes_table WHERE category = 'Drinks' ORDER BY dishId DESC")
    LiveData<List<Dish>> getAllDrinks();

    @Query("SELECT * FROM dishes_table WHERE category = 'Snacks' ORDER BY dishId DESC")
    LiveData<List<Dish>> getAllSnacks();

    @Query("SELECT * FROM dishes_table WHERE category = 'Sauce' ORDER BY dishId DESC")
    LiveData<List<Dish>> getAllSauce();

    @Query("SELECT * FROM dishes_table ORDER BY dishId DESC")
    LiveData<List<Dish>> getAllDishes();

   /* @Query("SELECT dishId FROM dishes_table WHERE dishId = :dishID")
    Integer getDishID(int dishID);*/
}
