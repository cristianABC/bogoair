package com.banjoinc.uituto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cuboid.cuboidcirclebutton.CuboidButton;

public class Home extends AppCompatActivity {

    CuboidButton profileBtn, giftBtn, complainBtn, routeBtn, searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profileBtn = (CuboidButton)findViewById(R.id.profilebutton);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this,"profile",Toast.LENGTH_SHORT).show();
            }
        });

        giftBtn = (CuboidButton)findViewById(R.id.giftbutton);
        giftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this,"gift",Toast.LENGTH_SHORT).show();
            }
        });


        complainBtn = (CuboidButton)findViewById(R.id.complainbutton);
        complainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent c = new Intent(Home.this, ComplainActivity.class);
                startActivity(c);
            }
        });

        routeBtn = (CuboidButton)findViewById(R.id.routebutton);
        routeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(Home.this, RouteActivity.class);
                startActivity(r);
            }
        });

        searchBtn = (CuboidButton)findViewById(R.id.searchbutton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(Home.this, SearchActivity.class);
                startActivity(s);
            }
        });
    }

}
