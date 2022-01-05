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

public class DeleteScreen extends AppCompatActivity {
    Button v_view, v_back, v_clear, delete;
    String e_id,e_name, e_contact, e_salary, e_post;
    EditText enterID;
    TextView id1, name1, post1, salary1, contact1;
    private FirebaseDatabase firedb;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_screen);

        id1 = (TextView) findViewById(R.id.id3);
        name1=(TextView) findViewById(R.id.name3);
        post1= (TextView) findViewById(R.id.post3);
        salary1= (TextView) findViewById(R.id.salary3);
        contact1= (TextView) findViewById(R.id.contact3);
        enterID= (EditText) findViewById(R.id.enterID2);
        v_view= (Button) findViewById(R.id.v_view2);
        v_back= (Button) findViewById(R.id.v_back2);
        v_clear= (Button) findViewById(R.id.v_clear2);
        delete= (Button) findViewById(R.id.delete);

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
                        }else{
                            Toast.makeText(DeleteScreen.this, "There is no employee with this ID", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(DeleteScreen.this, MainActivity.class));
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
                Toast.makeText(DeleteScreen.this, "Screen Fields are Clear", Toast.LENGTH_SHORT).show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 final String userVal = enterID.getText().toString().trim();
                myRef = firedb.getInstance().getReference().child("EmployeeRecords").child(userVal);
                myRef.removeValue();
                Toast.makeText(DeleteScreen.this, "This employee Record is deleted", Toast.LENGTH_SHORT).show();
                name1.setText("");
                enterID.setText("");
                contact1.setText("");
                id1.setText("");
                post1.setText("");
                salary1.setText("");

            }
        });
}
}