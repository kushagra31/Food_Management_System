package com.example.symbibro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private EditText email;
    private EditText password;
    private TextView textView3;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        myRef=FirebaseDatabase.getInstance().getReference();
        email =(EditText) findViewById(R.id.email);
        password =(EditText) findViewById(R.id.password);
        button =(Button) findViewById(R.id.button);
        textView3 =(TextView) findViewById(R.id.textView3);
        button.setOnClickListener(this);
        textView3.setOnClickListener(this);
        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()){
            finish();
            startActivity(new Intent(getApplicationContext(),Profileactivity2Activity.class));

        }
    }

    private void userLogin(){
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();
        if (TextUtils.isEmpty(email1)){
            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password1)){
            Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)  throws  NullPointerException{
                FirebaseUser user1 = task.getResult().getUser();
                String username = user1.getEmail();
                String uid= user1.getUid();
                String em = user1.getEmail();
                String cart="";
                int money= 500;


                if(mAuth.getCurrentUser().isEmailVerified())  {

                    User user = new User(username,em,cart,money);
                    myRef.child("Users").child(uid).setValue(user);
                        finish();
                        startActivity(new Intent(getApplicationContext(),Profileactivity2Activity.class));

                    }

            }
        });
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    @Override
    public void onClick(View view) {
        if (view== button){
            userLogin();

        }
        if(view== textView3){
            finish();
            startActivity(new Intent(this,MainActivity.class));

        }
    }
}
