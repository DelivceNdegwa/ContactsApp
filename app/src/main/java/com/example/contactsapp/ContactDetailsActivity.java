package com.example.contactsapp;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.contactsapp.models.ContactDetails;
import com.example.contactsapp.utils.ObjectBox;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactsapp.databinding.ActivityContactDetailsBinding;

public class ContactDetailsActivity extends AppCompatActivity {

    private ActivityContactDetailsBinding binding;
    Toolbar toolbar;
    CollapsingToolbarLayout toolBarLayout;

    TextView tvPhoneCall, tvWhatsAppVoiceCall, tvWhatsAppVideoCall, tvWhatsAppMessage;
    ImageView imgProfile;

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

        tvPhoneCall = findViewById(R.id.tv_phone_call);
        tvWhatsAppMessage = findViewById(R.id.tv_whatsapp_message);
        tvWhatsAppVideoCall = findViewById(R.id.tv_whatsapp_videocall);
        tvWhatsAppVoiceCall = findViewById(R.id.tv_whatsapp_voicecall);
        imgProfile = findViewById(R.id.profile_image);

        Intent intent = getIntent();

        Toast.makeText(this,
                String.valueOf(intent.getLongExtra("CONTACT_ID", 0)),
                Toast.LENGTH_LONG).show();


        if(intent.hasExtra("CONTACT_ID")){
            Log.d("TEST::::", "Success");
            ContactDetails details = ObjectBox.get().boxFor(ContactDetails.class)
                    .get(intent.getLongExtra("CONTACT_ID", 0));

            String countryCode = "+254";

            Glide.with(this)
                    .load(details.getProfileImage())
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(imgProfile);

            toolBarLayout.setTitle(details.getFirstName());
            tvPhoneCall.setText(countryCode+String.valueOf(details.getPhoneNumber()));
            tvWhatsAppVoiceCall.setText(String.format("Voice call  %s", countryCode+String.valueOf(details.getPhoneNumber())));
            tvWhatsAppVideoCall.setText(String.format("Video call  %s", countryCode+String.valueOf(details.getPhoneNumber())));
            tvWhatsAppMessage.setText(String.format("Message  %s", countryCode+String.valueOf(details.getPhoneNumber())));
        }
        else{
            Log.d("TEST::::", "Failed");
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