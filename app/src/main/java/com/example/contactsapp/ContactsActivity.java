package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.contactsapp.adapters.ContactDetailsAdapter;
import com.example.contactsapp.adapters.ContactsAdapter;
import com.example.contactsapp.models.ContactDetails;
import com.example.contactsapp.utils.ObjectBox;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class ContactsActivity extends AppCompatActivity {
    private static String ACTION_BAR_TITLE = "Contact";
    private RecyclerView rvContacts;

    Box<ContactDetails> contactDetailsBox = ObjectBox.get().boxFor(ContactDetails.class);
    List<ContactDetails> contactList = new ArrayList<>();
    ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    @Override
    protected void onResume() {
        super.onResume();


        rvContacts = findViewById(R.id.rv_contacts);
        rvContacts.setNestedScrollingEnabled(true);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        contactList.addAll(contactDetailsBox.getAll());
        contactsAdapter = new ContactsAdapter(this, contactList);
        rvContacts.setAdapter(contactsAdapter);

        getSupportActionBar().setTitle(ACTION_BAR_TITLE);

    }
}