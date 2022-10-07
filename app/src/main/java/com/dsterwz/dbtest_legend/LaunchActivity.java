package com.dsterwz.dbtest_legend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dsterwz.dbtest_legend.models.Dish;
import com.dsterwz.dbtest_legend.models.DishesVersion;
import com.dsterwz.dbtest_legend.models.FoodApi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaunchActivity extends AppCompatActivity {

    private FoodApi foodApi;
    private DishRepository dishRepository;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        init();
        getSupportActionBar().hide();
    }

    private void init() {
        dishRepository = new DishRepository(getApplication());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://food.madskill.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        foodApi = retrofit.create(FoodApi.class);

        progressBar = findViewById(R.id.progress_bar);
        if (isOnline()) {
            progressBar.setVisibility(View.VISIBLE);
            getVersion();
            //getDishes();
            progressBar.setVisibility(View.GONE);
            Intent i = new Intent(LaunchActivity.this, OnBoardingActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Check out your internet connection, nigger!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getVersion() {
        Call<DishesVersion> call = foodApi.getVersion();
        call.enqueue(new Callback<DishesVersion>() {
            @Override
            public void onResponse(Call<DishesVersion> call, Response<DishesVersion> response) {
                if (response.isSuccessful()) {
                    DishesVersion versions = response.body();
                    for (String version : versions.getVersion()) {
                        getDishes(version);
                    }

                }
            }

            @Override
            public void onFailure(Call<DishesVersion> call, Throwable t) {

            }
        });

    }


    public void getDishes(String version) {
        Call<List<Dish>> call = foodApi.getDishes(version);
        call.enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful()) {
                    List<Dish> dishes = response.body();


                    for (Dish dish : dishes) {
                        dishRepository.insert(dish);
                    /*
                    String content = "";
                    content += "Code: " + response.code() + "\n";
                    content += "Dish ID: " + dish.getDishId() + "\n";
                    content += "Category: " + dish.getCategory() + "\n";
                    content += "Name Dish: " + dish.getNameDish() + "\n";
                    content += "Price: " + dish.getPrice() + "\n";
                    content += "Icon: " + dish.getIcon() + "\n";
                    content += "Version: " + dish.getVersion() + "\n\n";

                    anal.append(content);*/
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {

            }
        });
    }

    // ICMP 
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}