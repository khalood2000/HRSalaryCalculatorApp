package com.example.hremployeesalarycalculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReadScreen extends AppCompatActivity {
    TextView id1, name1, post1, salary1, contact1;
    Button v_view, v_back, v_clear;
    EditText enterID;
    private FirebaseDatabase firedb;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_screen);

        enterID= (EditText) findViewById(R.id.enterID);
        id1 = (TextView) findViewById(R.id.id1);
        name1=(TextView) findViewById(R.id.name1);
        post1= (TextView) findViewById(R.id.post1);
        salary1= (TextView) findViewById(R.id.salary1);
        contact1= (TextView) findViewById(R.id.contact1);
        v_view= (Button) findViewById(R.id.v_view);
        v_back= (Button) findViewById(R.id.v_back);
        v_clear= (Button) findViewById(R.id.v_clear);


        firedb = FirebaseDatabase.getInstance();
        myRef = firedb.getReference();

        v_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userVal = enterID.getText().toString().trim();
                myRef = firedb.getInstance().getReference().child("EmployeeRecords");
                Query queries=myRef.orderByChild("userID").equalTo(userVal);
                queries.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                           UserInfo userInfo = dataSnapshot.child(userVal).getValue(UserInfo.class);
                            String id12= userInfo.getUserID();
                            if(id12.equals(userVal)){
                                id1.setText(userInfo.getUserID());
                                name1.setText(userInfo.getUserName());
                                post1.setText(userInfo.getUserPost());
                                salary1.setText(userInfo.getUserSalary());
                                contact1.setText(userInfo.getUserContact());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        v_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReadScreen.this, MainActivity.class));
                finish();
            }
        });

        v_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterID.setText("");
                id1.setText("");
                name1.setText("");
                contact1.setText("");
                salary1.setText("");
                post1.setText("");
                Toast.makeText(ReadScreen.this, "Screen Fields are Clear", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReadScreen.this, MainActivity.class));
        finish();
    }
}