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

import io.objectbox.Box;

public class ContactDetailsActivity extends AppCompatActivity {

    private ActivityContactDetailsBinding binding;
    Toolbar toolbar;
    CollapsingToolbarLayout toolBarLayout;

    TextView tvPhoneCall, tvWhatsAppVoiceCall, tvWhatsAppVideoCall, tvWhatsAppMessage;
    ImageView imgProfile;

    boolean isFavContact;
    long contactId;

    Box<ContactDetails> detailsBox = ObjectBox.get().boxFor(ContactDetails.class);

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

        if(intent.hasExtra("CONTACT_ID")){
            contactId = intent.getLongExtra("CONTACT_ID", 0);
            ContactDetails details = detailsBox.get(contactId);

            String countryCode = "+254";

            isFavContact = details.isFavorite();

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_details, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem favoritesItem = menu.findItem(R.id.action_favorite);
        if(isFavContact){
            favoritesItem.setIcon(getDrawable(R.drawable.ic_baseline_star_24));

        }
        else{
            favoritesItem.setIcon(getDrawable(R.drawable.ic_baseline_star_border_24));
        }

        favoritesItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int item_selected = item.getItemId();

        switch (item_selected){
            case R.id.action_favorite:
                ContactDetails contactDetails = detailsBox.get(contactId);
                boolean current_favorite = contactDetails.isFavorite();
                contactDetails.setFavorite(!current_favorite);
                detailsBox.put(contactDetails);
//                invalidateOptionsMenu();
                supportInvalidateOptionsMenu();
                return true;

            case R.id.action_edit_contact:

                Intent intent  = new Intent(ContactDetailsActivity.this, EditContactActivity.class);
                intent.putExtra("CONTACT_ID", contactId);
                startActivity(intent);

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