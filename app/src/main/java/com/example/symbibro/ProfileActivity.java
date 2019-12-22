package com.example.symbibro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView textView4;
    private Button button2;
    private RecyclerView recyclerView;
    FirebaseDatabase database ;
    DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setText("Welcome"+ user.getEmail());
        button2= (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds :dataSnapshot.getChildren()){

        }
    }
    @Override
    public void onClick(View view) {
        if(view == button2){
            mAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

    }

}
