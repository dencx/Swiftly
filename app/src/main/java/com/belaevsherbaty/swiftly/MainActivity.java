package com.belaevsherbaty.swiftly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String URL = "http://swiftly2021.000webhostapp.com/logout";

    SharedPreferences sPref;
    final String AUTH_DATA = "id_session";
    final String FILE_NAME = "auth_data";
    final String FILE_PATH = "/data/data/com.belaevsherbaty.swiftly/shared_prefs/auth_data.xml";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File f = new File(FILE_PATH);
        if (!f.exists()) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    public void logout(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res", response);
                if (response.equals("logout")) {
                    Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                } else if (response.equals("failure")) {
                    Toast.makeText(MainActivity.this, "Logout failed", Toast.LENGTH_SHORT).show();
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
                data.put("id_session", readSessionId());

                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        removeSessionId();
    }

    public void removeSessionId(){

        File f = new File(FILE_PATH);
        f.delete();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private String readSessionId() {
        sPref = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String sessionId = sPref.getString(AUTH_DATA, "");

        return sessionId;
    }





}