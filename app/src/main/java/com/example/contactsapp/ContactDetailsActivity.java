package com.example.contactsapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.contactsapp.models.ContactDetails;
import com.example.contactsapp.utils.ObjectBox;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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
    ImageButton btnMessage, btnPhoneCall;

    private static final int REQUEST_SEND_SMS = 0;

    boolean isFavContact;
    long contactId;

    String phone;
    boolean current_favorite;

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
        btnMessage = findViewById(R.id.btn_message);
        btnPhoneCall = findViewById(R.id.btn_phone_call);

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

            phone = tvPhoneCall.getText().toString().trim();

            btnMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendTextMessage("");
                }
            });

            btnPhoneCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makePhoneCall();
                }
            });
        }
        else{
            Toast.makeText(this, "Could not load contact", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ContactDetailsActivity.this, ContactsActivity.class);
            startActivity(i);
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
        boolean result = true;
        switch (item_selected){
            case R.id.action_favorite:
                ContactDetails contactDetails = detailsBox.get(contactId);
                current_favorite = contactDetails.isFavorite();

//                contactDetails.setFavorite(!current_favorite);
//                detailsBox.put(contactDetails);
////              invalidateOptionsMenu();
//                supportInvalidateOptionsMenu();

                if(current_favorite){
                    current_favorite = false;

                }
                else{
                    current_favorite = true;
                }

                contactDetails.setFavorite(current_favorite);
                detailsBox.put(contactDetails);

                invalidateOptionsMenu();

                break;

            case R.id.action_edit_contact:

                Intent intent  = new Intent(ContactDetailsActivity.this, EditContactActivity.class);
                intent.putExtra("CONTACT_ID", contactId);
                startActivity(intent);
                finish();

                break;

            case R.id.action_linked_contacts:

                break;

            case R.id.action_delete_contact:
                ContactDetails contactDeleteDetails = detailsBox.get(contactId);
                showPromptDialog(contactDeleteDetails);
                break;

            case R.id.action_share:
                break;

            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem favoritesItem = menu.findItem(R.id.action_favorite);
        ContactDetails contactDetails = detailsBox.get(contactId);
        boolean current_fav = contactDetails.isFavorite();
        if(current_fav){
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

    public void makePhoneCall(){

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone));
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }

    }

    public void sendTextMessage(String message){
        Uri attachment = Uri.parse("smsto:"+phone);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void showPromptDialog(ContactDetails contactDetails){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteContact(contactDetails);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete "+contactDetails.getFirstName()+"?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void deleteContact(ContactDetails contactDetails) {
        detailsBox.remove(contactDetails);
        Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ContactDetailsActivity.this, ContactsActivity.class);
        startActivity(intent);
        finish();
    }

}