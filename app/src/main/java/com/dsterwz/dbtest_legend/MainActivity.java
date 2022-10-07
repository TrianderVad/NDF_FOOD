package com.dsterwz.dbtest_legend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dsterwz.dbtest_legend.models.Dish;
import com.dsterwz.dbtest_legend.views.DishAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DishAdapter dishAdapter;
    private DishViewModel dishViewModel;
    private RadioGroup dishCategory;
    private RadioGroup bottomNavigation;

    private FlexboxLayoutManager layoutManager;
    private EditText editTextSearchBar;
    private EditText editTextAddress;
    private TextView textViewResults;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        //getDishes();
    }

    private void init() {

        //dishViewModel.getDishes();
        //dishRepository = new DishRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.search_bar);
        textViewResults = findViewById(R.id.text_view_results);
        editTextAddress = findViewById(R.id.edit_text_address);
        dishCategory = findViewById(R.id.categories_button_view);
        bottomNavigation = findViewById(R.id.bottom_navigation_radio_group);
        editTextSearchBar = findViewById(R.id.edit_text_search_bar);


        layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dishAdapter = new DishAdapter();
        recyclerView.setAdapter(dishAdapter);

        dishAdapter.setOnItemClickListener(new DishAdapter.OneItemClickListener() {
            @Override
            public void onItemClick(Dish dish) {
                editTextSearchBar.setText("");
                /*getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container_view, ExpandDishDialogFragment.class, null)
                        .commit();*/
                /*Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);*/
            }
        });

        dishViewModel = new ViewModelProvider(this).get(DishViewModel.class);
        dishViewModel.getAllFoods().observe(this, new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
                dishAdapter.setDishes(dishes);
            }
        });


        dishCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_button_foods:
                        dishViewModel.getAllFoods().observe(MainActivity.this, new Observer<List<Dish>>() {
                            @Override
                            public void onChanged(List<Dish> dishes) {
                                dishAdapter.setDishes(dishes);
                            }
                        });
                        break;
                    case R.id.radio_button_drinks:
                        dishViewModel.getAllDrinks().observe(MainActivity.this, new Observer<List<Dish>>() {
                            @Override
                            public void onChanged(List<Dish> dishes) {
                                dishAdapter.setDishes(dishes);
                            }
                        });
                        break;
                    case R.id.radio_button_snacks:
                        dishViewModel.getAllSnacks().observe(MainActivity.this, new Observer<List<Dish>>() {
                            @Override
                            public void onChanged(List<Dish> dishes) {
                                dishAdapter.setDishes(dishes);
                            }
                        });
                        break;
                    case R.id.radio_button_sauce:
                        dishViewModel.getAllSauce().observe(MainActivity.this, new Observer<List<Dish>>() {
                            @Override
                            public void onChanged(List<Dish> dishes) {
                                dishAdapter.setDishes(dishes);
                            }
                        });
                        break;
                }
            }
        });

        bottomNavigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_button_history:
                    case R.id.radio_button_profile:
                    case R.id.radio_button_cart:
                        Intent intent = new Intent(MainActivity.this, OneItemActivity.class);
                        startActivity(intent);

                }
            }
        });


        editTextSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dishAdapter.getDishFilter().filter(editable.toString());
            }
        });

        /*dishViewModel.insert(new Dish(1,
                "Foods",
                "Zalupa",
                20,
                "jopa.jpg",
                "1.02"));*/

    }
/*
    public void onClickFoods(View view) {
        dishViewModel = new ViewModelProvider(this).get(DishViewModel.class);
        dishViewModel.getAllFoods().observe(this, new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
                dishAdapter.setDishes(dishes);
            }
        });
    }

    public void onClickDrinks(View view) {
        dishViewModel = new ViewModelProvider(this).get(DishViewModel.class);
        dishViewModel.getAllDrinks().observe(this, new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
                dishAdapter.setDishes(dishes);
            }
        });
    }

    public void onClickSnacks(View view) {
        dishViewModel = new ViewModelProvider(this).get(DishViewModel.class);
        dishViewModel.getAllSnacks().observe(this, new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
                dishAdapter.setDishes(dishes);
            }
        });
    }

    public void OnClickSauce(View view) {
        dishViewModel = new ViewModelProvider(this).get(DishViewModel.class);
        dishViewModel.getAllSauce().observe(this, new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
                dishAdapter.setDishes(dishes);
            }
        });
    }*/


    public void onClickSearchBarOpen(View view) {
        dishCategory.setVisibility(View.INVISIBLE);
        textViewResults.setVisibility(View.VISIBLE);
        editTextSearchBar.setText("");
        dishViewModel.getAllDishes().observe(this, new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
                dishAdapter.setDishes(dishes);
            }
        });
        toolbar.setVisibility(View.VISIBLE);
    }


    public void OnClickSearchBarClose(View view) {
        editTextSearchBar.setText("");
        textViewResults.setVisibility(View.INVISIBLE);
        dishCategory.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.INVISIBLE);
    }
}