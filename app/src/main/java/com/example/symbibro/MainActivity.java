package com.example.symbibro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView3;
    private Button button;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        button = (Button) findViewById(R.id.button);
        email = (EditText)  findViewById(R.id.email);
        password = (EditText)  findViewById(R.id.password);
        textView3= (TextView) findViewById(R.id.textView3);
        button.setOnClickListener(this);
        textView3.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }
    public void registerButton(){
        String email1= email.getText().toString().trim();
        String password1=password.getText().toString().trim();
        if (TextUtils.isEmpty(email1)){
            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password1)){
            Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email1,password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(MainActivity.this,"Registered link send to email",Toast.LENGTH_SHORT).show();
                                        email.setText("");
                                        password.setText("");
                                        return;
                                    }
                                }
                            });
                        }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view== button){
            registerButton();
        }
        if (view== textView3){
           startActivity(new Intent(this,LoginActivity.class));
    }
}
}