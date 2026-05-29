package com.example.coffehub;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewActivity extends AppCompatActivity {

    private static final String TAG = "ViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        retrieveFirebase();
    }

    private void retrieveFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("subscribers");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Firebase read successful. Records found: " + dataSnapshot.getChildrenCount());

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Pojo subscriber = childSnapshot.getValue(Pojo.class);
                    if (subscriber != null) {
                        Log.d(TAG, "Subscriber: "
                                + subscriber.getLname() + ", "
                                + subscriber.getLid() + ", "
                                + subscriber.getLemail());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Firebase read failed", error.toException());
            }
        });
    }
}
