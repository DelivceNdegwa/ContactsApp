package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.contactsapp.adapters.ContactDetailsAdapter;
import com.example.contactsapp.adapters.ContactsAdapter;
import com.example.contactsapp.models.ContactDetails;
import com.example.contactsapp.utils.ObjectBox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.objectbox.Box;

public class ContactsActivity extends AppCompatActivity {
    private static String ACTION_BAR_TITLE = "Contacts";
    private RecyclerView rvContacts;

    Box<ContactDetails> contactDetailsBox = ObjectBox.get().boxFor(ContactDetails.class);
    List<ContactDetails> contactList = new ArrayList<>();
    ContactsAdapter contactsAdapter;
    FloatingActionButton btnNewContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        rvContacts = findViewById(R.id.rv_contacts);
        rvContacts.setNestedScrollingEnabled(true);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));

        btnNewContact = findViewById(R.id.btn_new_contact);
        btnNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsActivity.this, NewContactActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(ACTION_BAR_TITLE);
        contactList.clear();
        contactList.addAll(contactDetailsBox.getAll());

        Collections.sort(contactList, new Comparator<ContactDetails>() {
            @Override
            public int compare(ContactDetails contactDetails, ContactDetails t1) {
                return contactDetails.getFirstName().compareTo(t1.getFirstName());
            }

        });

        contactsAdapter = new ContactsAdapter(this, contactList);
        rvContacts.setAdapter(contactsAdapter);

        contactsAdapter.notifyDataSetChanged();

    }
}