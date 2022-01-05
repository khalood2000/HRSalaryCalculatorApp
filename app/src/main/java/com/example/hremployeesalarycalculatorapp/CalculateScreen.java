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

public class CalculateScreen extends AppCompatActivity {
    Button v_view, v_back, v_clear, calculate, save;
    EditText enterID, new_Sal;
    TextView p_salary1, id4, salary4;
    private FirebaseDatabase firedb;
    private DatabaseReference myRef;
    String l_name, l_contact, l_post,s, u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_screen);

        enterID= (EditText) findViewById(R.id.enterID4);
        new_Sal= (EditText) findViewById(R.id.new_sal);
        p_salary1= (TextView) findViewById(R.id.p_salary1);
        id4= (TextView) findViewById(R.id.id4);
        salary4= (TextView) findViewById(R.id.salary4);
        v_view= (Button) findViewById(R.id.v_view4);
        v_clear= (Button) findViewById(R.id.v_clear4);
        v_back= (Button) findViewById(R.id.v_back4);
        calculate= (Button) findViewById(R.id.calculate4);
        save= (Button) findViewById(R.id.save);

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
                                id4.setText(userInfo.getUserName());
                                p_salary1.setText(userInfo.getUserSalary());
                               l_name= userInfo.getUserName();
                               l_contact= userInfo.getUserContact();
                                l_post= userInfo.getUserPost();
                            }
                        }else{
                            Toast.makeText(CalculateScreen.this, "There is no employee with this ID", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(CalculateScreen.this, MainActivity.class));
                finish();
            }
        });

        v_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterID.setText("");
                id4.setText("");
                new_Sal.setText("");
                p_salary1.setText("");
                salary4.setText("");
                Toast.makeText(CalculateScreen.this, "Screen Fields are Clear", Toast.LENGTH_SHORT).show();
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u_id=enterID.getText().toString().trim();

                String c_sal=p_salary1.getText().toString();
                Float c_sal1 = Float.valueOf(c_sal).floatValue();

                String per= new_Sal.getText().toString();
                Float percnt_1= Float.valueOf(per).floatValue();

                Float cal_percent= c_sal1/100 *percnt_1;
                Float new_cal_sal= c_sal1 + cal_percent;
                s=String.valueOf(new_cal_sal);
                salary4.setText(s);
                Toast.makeText(CalculateScreen.this, "Salary Calculated", Toast.LENGTH_SHORT).show();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo userInfo = new UserInfo(u_id,l_name, l_post,l_contact,s);
                myRef.child(u_id).setValue(userInfo);
                Toast.makeText(CalculateScreen.this, "New Salary is Saved", Toast.LENGTH_SHORT).show();
                new_Sal.setText("");


            }
        });

    }
}