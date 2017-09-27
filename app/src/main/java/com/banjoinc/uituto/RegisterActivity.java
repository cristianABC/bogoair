package com.banjoinc.uituto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
    EditText phoneTxt, pwdTxt, nameTxt, emailTxt;
    Button registerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phoneTxt = (MaterialEditText) findViewById(R.id.phonetxt);
        pwdTxt = (MaterialEditText)findViewById(R.id.pwdtxt);
        nameTxt = (MaterialEditText)findViewById(R.id.nametxt);
        emailTxt = (MaterialEditText)findViewById(R.id.emailtxt);

        registerbtn = (Button)findViewById(R.id.registerbutton);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("User");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         if(phoneTxt.getText().toString().equals("") || pwdTxt.getText().toString().equals("")  ||
                                nameTxt.getText().toString().equals("")  || emailTxt.getText().toString().equals("") ){
                            Toast.makeText(RegisterActivity.this, "Complete all the fields", Toast.LENGTH_SHORT).show();
                        }else if(!emailTxt.getText().toString().contains("@")){
                             Toast.makeText(RegisterActivity.this, "Please enter a valid mail", Toast.LENGTH_SHORT).show();
                         }
                        else{
                            if(dataSnapshot.hasChild(phoneTxt.getText().toString())){
                                 Toast.makeText(RegisterActivity.this, "Phone:"+phoneTxt.getText().toString()+" already exists",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                User u = new User(nameTxt.getText().toString(), emailTxt.getText().toString(),pwdTxt.getText().toString(),0,0);
                                myRef.child(phoneTxt.getText().toString()).setValue(u);
                                Toast.makeText(RegisterActivity.this, "Register succesfull",Toast.LENGTH_SHORT).show();
                                finish();

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
}
