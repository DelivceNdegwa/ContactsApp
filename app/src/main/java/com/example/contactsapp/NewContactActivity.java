package com.example.contactsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

public class NewContactActivity extends AppCompatActivity {
    public static String ACTION_TITLE = "Create new contact";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact_form);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(ACTION_TITLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_contact_save, menu);
        return true;
    }

    public void saveContact(){

    }

    public void validateContactDetails(){

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_save_contact){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}