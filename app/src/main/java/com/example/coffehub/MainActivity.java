package com.example.coffehub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText name, id, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking XML IDs with Java
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        email = findViewById(R.id.email);
    }

    // SAVE BUTTON
    public void save(View view) {

        String lname = name.getText().toString().trim();
        String lid = id.getText().toString().trim();
        String lemail = email.getText().toString().trim();

        if (lname.isEmpty() || lid.isEmpty() || lemail.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            Log.w(TAG, "Firebase save blocked: one or more fields are empty");
            return;
        }

        // Save to Firebase
        fireBase(lname, lid, lemail);

        // Send Email
        sendmail(lname, lid, lemail);
    }

    // EMAIL INTENT
    private void sendmail(String lname, String lid, String lemail) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{"bundichristopher639@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                "New Subscriber");
        intent.putExtra(Intent.EXTRA_TEXT, "Subscriber Details\n\n" + "Name: " + lname + "\n" +
                "ID Number: " + lid + "\n" +
                "Email: " + lemail);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    // FIREBASE METHOD
    public void fireBase(String lname, String lid, String lemail) {
        Pojo item = new Pojo(lname, lid, lemail);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("subscribers").child(lid);

        // Save data
        myRef.setValue(item)
                .addOnSuccessListener(unused -> {
                    Log.d(TAG, "Firebase save successful for ID: " + lid);
                    Toast.makeText(this,
                            "Saved Successfully",
                            Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(error -> {
                    Log.e(TAG, "Firebase save failed for ID: " + lid, error);
                    Toast.makeText(this,
                            "Save Failed: " + error.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
//        myRef.push().setValue(
//                "Name: " + lname +
//                        ", ID: " + lid +
//                        ", Email: " + lemail
//        );
    }

    // CANCEL BUTTON
    public void cancel(View view) {

        name.setText("");
        id.setText("");
        email.setText("");

        Toast.makeText(this,
                "Data Cancelled",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitems, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.setting)
        {
            Toast.makeText(this, "Setting selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        else  if(item.getItemId()==R.id.view)
        {
            Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
            startActivity(intent);
            return true;
        }
        else if(item.getItemId()==R.id.aboutus)
        {
            Toast.makeText(this, "About Us selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}
