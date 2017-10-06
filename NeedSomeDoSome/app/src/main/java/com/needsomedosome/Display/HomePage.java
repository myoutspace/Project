package com.example.julien.needsomedosome.Display;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.julien.needsomedosome.Display.Control.CreateGroupPage;
import com.example.julien.needsomedosome.Display.Control.CreateUserPage;
import com.example.julien.needsomedosome.Display.Control.DeleteGroupPage;
import com.example.julien.needsomedosome.Display.Control.DeleteUserPage;
import com.example.julien.needsomedosome.R;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void CreateUserBtn(View v){
        startActivity(new Intent(HomePage.this, CreateUserPage.class));
    }

    public void CreateGroupBtn(View v){
        startActivity(new Intent(HomePage.this, CreateGroupPage.class));
    }

    public void DeleteUserBtn(View v){
        startActivity(new Intent(HomePage.this, DeleteUserPage.class));
    }

    public void DeleteGroupBtn(View v){
        startActivity(new Intent(HomePage.this, DeleteGroupPage.class));
    }

}
