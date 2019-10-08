package com.example.bookreader;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView favList;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        favList = view.findViewById(R.id.fav_list);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users/Public/6dsZEQ2k2SUqjcF8PL4yh62gtXz2").child("favorites");

        final List<String> favs = new ArrayList<>();


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i("favs",dataSnapshot.getValue().toString());

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String fav = snapshot.getValue(String.class);
                    Log.i("fav",fav);
                    favs.add(fav);
                    favList.append(fav+"\n");

                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Log.i("favs",favs.toString());




        return view;

    }

}
