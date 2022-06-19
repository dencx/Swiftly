package com.belaevsherbaty.swiftly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText etName, etLogin, etPassword, etReenterPassword;
    private TextView tvNameValidate, tvLoginValidate, tvPasswordValidate;
    private String URL = "https://swiftly2021.000webhostapp.com/register";
    private String name, login, password, reenterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        etName = findViewById(R.id.etName);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etReenterPassword = findViewById(R.id.etReenterPassword);

        tvNameValidate = findViewById(R.id.tvNameValidate);
        tvLoginValidate = findViewById(R.id.tvLoginValidate);
        tvPasswordValidate = findViewById(R.id.tvPasswordValidate);

        name = login = password = reenterPassword = "";
    }

    public void register(View view) {
        name = etName.getText().toString().trim();
        login = etLogin.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        reenterPassword = etReenterPassword.getText().toString().trim();

        tvPasswordValidate.setText("");
        tvNameValidate.setText("");
        tvLoginValidate.setText("");

        if (!password.equals(reenterPassword)) {
            tvPasswordValidate.setText("Паролі не співпадають");
        } else if (password.length() < 5) {
            tvPasswordValidate.setText("Пароль менше 5 символів");
        } else if (name.length() < 2) {
            tvNameValidate.setText("Ім'я менше 2 символів");
        } else if (login.length() < 5) {
            tvLoginValidate.setText("Логін менше 5 символів");
        } else if (!name.equals("") && !login.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(Register.this, "Успішно зареєстровано!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        Toast.makeText(Register.this, "Користувач з таким логіном існує!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("name", name);
                    data.put("login", login);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show();
        }
    }
    public void getLoginActivity(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
