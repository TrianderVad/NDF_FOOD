package com.dsterwz.dbtest_legend.models.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dsterwz.dbtest_legend.models.Dish;

@Database(entities = {Dish.class}, version = 1)
public abstract class DishDatabase extends RoomDatabase {

    private static DishDatabase instance;

    public abstract DishDao dishDao();

    public static synchronized DishDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            DishDatabase.class, "dishes_table")
                    .fallbackToDestructiveMigration()
                    //.addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

/*
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DishDao dishDao;


        private PopulateDbAsyncTask(DishDatabase db) {
            dishDao = db.dishDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dishDao.insert(new Dish(5,
                    "Foods",
                    "\u042d\u0441\u043a\u0430\u043b\u043e\u043f \u0441 \u043e\u0442\u0432\u0430\u0440\u043d\u044b\u043c \u043a\u0430\u0440\u0442\u043e\u0444\u0435\u043b\u0435\u043c",
                    1720,
                    "esca.jpeg",
                    "1.01"));
            return null;
        }
    }
//*/
}