package com.example.symbibro.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.symbibro.Cart;
import com.example.symbibro.Food;
import com.example.symbibro.LoginActivity;
import com.example.symbibro.MyAdapter;
import com.example.symbibro.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private HomeViewModel homeViewModel;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    Button CartButton;

    RecyclerView.Adapter adapter;
     List<Food> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        myRef=FirebaseDatabase.getInstance().getReference().child("Foods");

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);


        if (mAuth.getCurrentUser()==null){
            getActivity().finish();
            Intent intent = new Intent(root.getContext(), LoginActivity.class);
            startActivity(intent);
        }

        final Button button3= (Button) root.findViewById(R.id.button3);
CartButton =(Button) root.findViewById(R.id.CartButton);



        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));




        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Food food = dataSnapshot.getValue(Food.class);

                    list.add(food);
                }

               adapter= new MyAdapter(root.getContext(), list);

                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

CartButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), Cart.class);
        startActivity(intent);
    }
});






        return root;

    }
}

