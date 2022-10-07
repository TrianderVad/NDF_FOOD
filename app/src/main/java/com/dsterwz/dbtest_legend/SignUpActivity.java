package com.dsterwz.dbtest_legend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dsterwz.dbtest_legend.models.FoodApi;
import com.dsterwz.dbtest_legend.models.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private FoodApi foodApi;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private EditText editTextFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        getSupportActionBar().hide();
    }

    private void init() {
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextPasswordConfirm = findViewById(R.id.edit_text_password_confirm);
        editTextFullName = findViewById(R.id.edit_text_full_name);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://food.madskill.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        foodApi = retrofit.create(FoodApi.class);
    }


    private boolean checkEmail(String email) {
        return email.matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,3})$");
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

    private void registerUser() {
        User user = new User(
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString(),
                editTextFullName.getText().toString()
        );

        Call<User> call = foodApi.registerUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                    return;
                }

                User userResponse = response.body();
                Toast.makeText(SignUpActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onClickRegister(View view) {
        if (!isOnline()) {
            Toast.makeText(this, "Check Inet, eblan!", Toast.LENGTH_SHORT).show();
        } else if (!editTextEmail.getText().toString().isEmpty() &&
                !editTextPassword.getText().toString().isEmpty() &&
                !editTextFullName.getText().toString().isEmpty() &&
                checkEmail(editTextEmail.getText().toString()) &&
                editTextPassword.getText().toString().
                        equals(editTextPasswordConfirm.getText().toString())) registerUser();
        else {
            Toast.makeText(this, "Idiot!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCancel(View view) {
        Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(i);
    }
}