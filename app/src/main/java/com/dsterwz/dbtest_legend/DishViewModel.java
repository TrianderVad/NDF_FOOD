package com.dsterwz.dbtest_legend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dsterwz.dbtest_legend.models.Dish;

import java.util.List;

public class DishViewModel extends AndroidViewModel {

    private DishRepository repository;
    private LiveData<List<Dish>> allFoods;
    private LiveData<List<Dish>> allDrinks;
    private LiveData<List<Dish>> allSnacks;
    private LiveData<List<Dish>> allSauce;
    private LiveData<List<Dish>> allDishes;


    public DishViewModel(@NonNull Application application) {
        super(application);
        repository = new DishRepository(application);
        allFoods = repository.getAllFoods();
        allDrinks = repository.getAllDrinks();
        allSnacks = repository.getAllSnacks();
        allSauce = repository.getAllSauce();
        allDishes = repository.getAllDishes();
    }

    public void insert(Dish dish) {
        repository.insert(dish);
    }

    public void update(Dish dish) {
        repository.update(dish);
    }

    public void delete(Dish dish) {
        repository.delete(dish);
    }

    public void deleteAllDishes() {
        repository.deleteAllDishes();
    }

    public LiveData<List<Dish>> getAllFoods() {
        return allFoods;
    }

    public LiveData<List<Dish>> getAllDrinks() {
        return allDrinks;
    }

    public LiveData<List<Dish>> getAllSnacks() {
        return allSnacks;
    }

    public LiveData<List<Dish>> getAllSauce() {
        return allSauce;
    }

    public LiveData<List<Dish>> getAllDishes() {
        return allDishes;
    }
}
