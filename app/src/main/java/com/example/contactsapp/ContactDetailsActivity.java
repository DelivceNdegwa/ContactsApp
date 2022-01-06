package com.example.contactsapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.contactsapp.models.ContactDetails;
import com.example.contactsapp.utils.ObjectBox;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.contactsapp.databinding.ActivityContactDetailsBinding;

public class ContactDetailsActivity extends AppCompatActivity {

    private ActivityContactDetailsBinding binding;
    Toolbar toolbar;
    CollapsingToolbarLayout toolBarLayout;

    TextView tvPhoneCall, tvWhatsAppVoiceCall, tvWhatsAppVideoCall, tvWhatsAppMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContactDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("First Name");

        }


    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent();

        tvPhoneCall = findViewById(R.id.tv_phone_call);
        tvWhatsAppMessage = findViewById(R.id.tv_whatsapp_message);
        tvWhatsAppVideoCall = findViewById(R.id.tv_whatsapp_videocall);
        tvWhatsAppVoiceCall = findViewById(R.id.tv_whatsapp_voicecall);

        if(intent.hasExtra("CONTACT_ID")){
            ContactDetails details = ObjectBox.get().boxFor(ContactDetails.class)
                    .get(intent.getLongExtra("CONTACT_ID", 0));

            toolBarLayout.setTitle(details.getFirstName());
            tvPhoneCall.setText(String.valueOf(details.getPhoneNumber()));
            tvWhatsAppVoiceCall.setText(String.format("Voice call %s", String.valueOf(details.getPhoneNumber())));
            tvWhatsAppVideoCall.setText(String.format("Video call %s", String.valueOf(details.getPhoneNumber())));
            tvWhatsAppMessage.setText(String.format("Message %s", String.valueOf(details.getPhoneNumber())));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int item_selected = item.getItemId();

        switch (item_selected){
            case R.id.action_favorite:
                return true;

            case R.id.action_edit_contact:
                return true;

            case R.id.action_linked_contacts:
                return true;

            case R.id.action_delete_contact:
                return true;

            case R.id.action_share:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}