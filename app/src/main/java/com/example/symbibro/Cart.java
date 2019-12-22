package com.example.symbibro;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Cart extends ListActivity
{
    private FirebaseAuth mAuth;
    DatabaseReference myRef,databaseReference;
    ArrayList<String> listItems=new ArrayList<String>();
    public Button placeorder;

    ArrayAdapter<String> adapter;
    List<Food> list = new ArrayList<>();


    public int getItemno1() {
        System.out.println(itemno1);
        return itemno1;
    }

    public void setItemno1(int itemno12) {
        itemno1 = itemno12;
        System.out.println(itemno1);

    }


    public static int itemno1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cart);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        String username = user1.getEmail();
        String uid= user1.getUid();
        myRef= FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("cart");
        databaseReference=FirebaseDatabase.getInstance().getReference();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
placeorder =(Button) findViewById(R.id.placeorder);

        final Button button5 =(Button) findViewById(R.id.button5);
        final ListView list1=(ListView) findViewById(android.R.id.list) ;
        System.out.println(itemno1);




        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {



                        String orderNumber = data.getValue().toString();
                        adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listItems);
                        adapter.add(orderNumber);
                        setListAdapter(adapter);



                }
            }


            public void addItems(String s) {
                listItems.add(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


placeorder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (view == placeorder) {
           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
           String uid =user.getUid();




            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if(data.getKey().equals(String.valueOf(itemno1))){

                            String orderNumber = data.getValue().toString();

                            String key = database.getReference("Orders").push().getKey();
                            System.out.println(key);
databaseReference.child("Orders").child(key).setValue(orderNumber);

                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            databaseReference.child("Users").child(uid).child("cart").removeValue();
            finish();








        }
    }
});
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == button5) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid =user.getUid();


                    databaseReference.child("Users").child(uid).child("cart").removeValue();









                }
            }
        });
    }

}
