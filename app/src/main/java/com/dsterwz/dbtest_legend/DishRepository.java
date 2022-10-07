package com.dsterwz.dbtest_legend;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.dsterwz.dbtest_legend.models.Dish;
import com.dsterwz.dbtest_legend.models.FoodApi;
import com.dsterwz.dbtest_legend.models.database.DishDao;
import com.dsterwz.dbtest_legend.models.database.DishDatabase;

import java.util.List;

public class DishRepository {

    private DishDao dishDao;
    private FoodApi foodApi;
    //private ArrayList<String> dishesVersions;
    private LiveData<List<Dish>> allFoods;
    private LiveData<List<Dish>> allDrinks;
    private LiveData<List<Dish>> allSnacks;
    private LiveData<List<Dish>> allSauce;
    private LiveData<List<Dish>> allDishes;

    public DishRepository(Application application) {
        DishDatabase database = DishDatabase.getInstance(application);
        dishDao = database.dishDao();
        allFoods = dishDao.getAllFoods();
        allDrinks = dishDao.getAllDrinks();
        allSnacks = dishDao.getAllSnacks();
        allSauce = dishDao.getAllSauce();
        allDishes = dishDao.getAllDishes();
    }




    public void insert(Dish dish) {
        new InsertDishAsyncTask(dishDao).execute(dish);
    }

    public void update(Dish dish) {
        new UpdateDishAsyncTask(dishDao).execute(dish);
    }

    public void delete(Dish dish) {
        new DeleteDishAsyncTask(dishDao).execute(dish);
    }

    public void deleteAllDishes() {
        new DeleteAllDishesAsyncTask(dishDao).execute();
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



    private static class InsertDishAsyncTask extends AsyncTask<Dish, Void, Void> {
        private DishDao dishDao;

        private InsertDishAsyncTask(DishDao dishDao) {
            this.dishDao = dishDao;
        }

        @Override
        protected Void doInBackground(Dish... dishes) {
            dishDao.insert(dishes[0]);
            return null;
        }
    }

    private static class UpdateDishAsyncTask extends AsyncTask<Dish, Void, Void> {
        private DishDao dishDao;

        private UpdateDishAsyncTask(DishDao dishDao) {
            this.dishDao = dishDao;
        }

        @Override
        protected Void doInBackground(Dish... dishes) {
            dishDao.update(dishes[0]);
            return null;
        }
    }

    private static class DeleteDishAsyncTask extends AsyncTask<Dish, Void, Void> {
        private DishDao dishDao;

        private DeleteDishAsyncTask(DishDao dishDao) {
            this.dishDao = dishDao;
        }

        @Override
        protected Void doInBackground(Dish... dishes) {
            dishDao.delete(dishes[0]);
            return null;
        }
    }

    private static class DeleteAllDishesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DishDao dishDao;

        private DeleteAllDishesAsyncTask(DishDao dishDao) {
            this.dishDao = dishDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dishDao.deleteAllDishes();
            return null;
        }
    }


}

