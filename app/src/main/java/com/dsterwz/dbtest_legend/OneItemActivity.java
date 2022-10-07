package com.dsterwz.dbtest_legend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OneItemActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 5;

    private TextView textViewTitle;
    private TextView textViewPrice;

    private ViewPager2 viewPager2;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_item);
        init();
        getSupportActionBar().hide();
    }

    private void init() {
        viewPager2 = findViewById(R.id.pager_one_dish);
        pagerAdapter = new ScreenSlidePageAdapter(this);
        viewPager2.setAdapter(pagerAdapter);

        textViewTitle = findViewById(R.id.text_view_title);
        textViewPrice = findViewById(R.id.text_view_price);
    }

    private class ScreenSlidePageAdapter extends FragmentStateAdapter {
        public ScreenSlidePageAdapter(OneItemActivity oneItemActivity) {
            super(oneItemActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Intent intent = getIntent();

            String supaDishName = intent.getStringExtra("Title");
            if (supaDishName == null) supaDishName = "Заглушка";
            if (supaDishName.length() >= 18 && supaDishName.length() < 25)
                textViewTitle.setTextSize(26);
            else if (supaDishName.length() >= 25) textViewTitle.setTextSize(22);
            else textViewTitle.setTextSize(30);

            textViewTitle.setText(supaDishName);
            textViewPrice.setText(String.valueOf(intent.getIntExtra("Price", 0)));
            //intent.getIntExtra("Icon", R.drawable.fatass);

            if (position <= 4) {
                return new DishImageFragment();
            } else {
                return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    public void onClickAddToCart(View view) {
        //flex
        finish();
    }

    public void onClickBack(View view) {
        finish();
    }
}