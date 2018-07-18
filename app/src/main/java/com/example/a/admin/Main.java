package com.example.a.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }
   public void a(View v){
       Intent a1 = new Intent(Main.this, Menu.class);
       startActivity(a1);
   }

    public void b(View v){
        Intent a1 = new Intent(Main.this, Review.class);
        startActivity(a1);

    }
}
