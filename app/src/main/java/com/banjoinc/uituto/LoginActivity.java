package com.banjoinc.uituto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.banjoinc.uituto.Entities.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {
    EditText phoneTxt, pwdTxt;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneTxt = (MaterialEditText)findViewById(R.id.phonetxt);
        pwdTxt = (MaterialEditText)findViewById(R.id.pwdtxt);

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        final DatabaseReference users = firebaseDatabase.getReference("User");
        loginBtn= (Button)findViewById(R.id.loginbutton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User");



                myRef.addValueEventListener(new ValueEventListener() {
                    boolean log = false;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String phone = phoneTxt.getText().toString();
                        String password = pwdTxt.getText().toString();

                        if(dataSnapshot.hasChild(phone)){
                            User u = dataSnapshot.child(phone).getValue(User.class);

                            if(u.getPassword().equals(password)){
                               Toast.makeText(LoginActivity.this,"Logged in succesfully", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(LoginActivity.this,Home.class);
                                startActivity(in);
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"Error: wrong password", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(LoginActivity.this,"User: "+phone+" doesn´t exists", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(LoginActivity.this,text, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        });


    }

    public void redirect(){
        Intent i = new Intent(LoginActivity.this, Home.class);
        startActivity(i);
    }
}
