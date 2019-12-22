package com.example.symbibro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    Button button3;
    private FirebaseAuth mAuth;
    @Override protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        Button button3= (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view== button3 ){

            mAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
