package com.example.symbibro;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    List<Food> food;
    Context context;
    public int itemno1 ;
    DatabaseReference myRef,databaseReference;
    private FirebaseAuth mAuth;



    public MyAdapter(Context context, List<Food> food) {
        this.food = food;
        this.context=context;
    }

    public MyAdapter(){
     }

@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.foodlist,parent,false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        Food food1 = food.get(position);
        holder.txtid.setText(food1.menuId);
        holder.txtname1.setText(food1.name);
        holder.txtPrice.setText((food1).price);
        holder.txtdiscount.setText(food1.discount);


    mAuth= FirebaseAuth.getInstance();
    final DatabaseReference  databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
    myRef= FirebaseDatabase.getInstance().getReference().child("Foods");
    final DatabaseReference  firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(context, "Item Number "+position+" added to cart", Toast.LENGTH_SHORT).show();
                itemno1=position;
                Cart cart1 =new Cart();
                cart1.setItemno1(itemno1);
                final FirebaseUser user1 = mAuth.getCurrentUser();
                final String uid= user1.getUid();
                System.out.println(uid);
                firebaseDatabase.child(uid).child("TotalCartPrice");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if(data.getKey().equals(String.valueOf(itemno1))){

                                String orderNumber = data.getValue().toString();


                                String result = orderNumber.substring(7, 10) ;
                                int result1 = Integer.parseInt(result);

                                firebaseDatabase.child(uid).child("TotalCartPrice").setValue(result1);



                                CartPrice cartPrice = new CartPrice(orderNumber,result1);



                                databaseReference.child(uid).child("cart").child(String.valueOf(itemno1)).setValue(cartPrice);

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if(data.getKey().equals(user1.getUid())){

                                String result1 = data.child("TotalCartPrice").getValue().toString();
                                int sum= 0;
                               sum =sum+Integer.valueOf(result1);
                                firebaseDatabase.child(uid).child("TotalCartPrice").setValue(sum);
                                System.out.println(sum);








                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        }

@Override
public int getItemCount() {
        return food.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{
    private TextView txtid, txtname1,txtdiscount, txtPrice;
    Button button;
    public ViewHolder(View itemView) {
        super(itemView);

        txtname1=(TextView)itemView.findViewById(R.id.ShowFoodTextView);
        txtid=(TextView)itemView.findViewById(R.id.ShowMenuIdTextView);

        txtdiscount=(TextView)itemView.findViewById(R.id.ShowDiscountTextView);
        txtPrice=(TextView)itemView.findViewById(R.id.ShowPriceTextView);
        button =(Button) itemView.findViewById(R.id.button4);


    }
}
}