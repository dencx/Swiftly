package com.belaevsherbaty.swiftly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private String login, password;
    private String URL = "https://swiftly2021.000webhostapp.com/login";

    SharedPreferences sPref;
    final String AUTH_DATA = "id_session";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login = password = "";
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);

    }

    public void login(View view) {
        login = etLogin.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if(!login.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("res", response);
                    if (!response.equals("failure")) {
                        saveSessionId(response);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        Toast.makeText(Login.this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
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

    public void getRegisterActivity(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }
    private void saveSessionId(String sessionId) {
        sPref = getSharedPreferences("auth_data", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(AUTH_DATA, sessionId);
        ed.commit();

    }

}
