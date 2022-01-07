package com.example.contactsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.contactsapp.models.ContactDetails;
import com.example.contactsapp.utils.ObjectBox;
import com.example.contactsapp.utils.Common;
import com.google.android.material.textfield.TextInputEditText;


import java.io.File;

import io.objectbox.Box;

public class EditContactActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] saveLocations = {"Gmail", "Safaricom", "Airtel", "Phone"};
    String  profileImageUri = "android.resource://com.example.contactsapp/drawable/ic_baseline_account_circle_24";

    TextInputEditText inputFname, inputLname, inputPhoneNumber, inputEmail;
    String saveLocation, firstName, lastName, userEmail, phoneNumber;

    ImageView imgTakeProfile, profileImage;
    ConstraintLayout imageContainer;

    Box<ContactDetails> detailsBox = ObjectBox.get().boxFor(ContactDetails.class);
    long contactId;

    Common commonVariables = new Common();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_form);

        inputFname = findViewById(R.id.input_fname);
        inputLname = findViewById(R.id.input_lname);
        inputPhoneNumber = findViewById(R.id.input_phonenumber);
        inputEmail = findViewById(R.id.input_email);
        imgTakeProfile = findViewById(R.id.img_take_profile);
        imageContainer = findViewById(R.id.img_container);
        profileImage = findViewById(R.id.profile_image);

    }

    @Override
    protected void onResume() {
        super.onResume();

        commonVariables.setActionTitle("Edit contact");
        getSupportActionBar().setTitle(commonVariables.getActionTitle());

        Intent intent = getIntent();
        if(intent.hasExtra("CONTACT_ID")){
            contactId = intent.getLongExtra("CONTACT_ID", 0);
            ContactDetails contactDetails = detailsBox.get(contactId);

            inputFname.setText(contactDetails.getFirstName());
            inputLname.setText(contactDetails.getLastName());
            inputPhoneNumber.setText(String.format("0%s", String.valueOf(contactDetails.getPhoneNumber())));
            inputEmail.setText(contactDetails.getUserEmail());

            Glide.with(this)
                    .load(contactDetails.getProfileImage())
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(profileImage);

            imageContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageChooser();
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_contact_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_save_contact){
            firstName = inputFname.getText().toString().trim();
            lastName = inputLname.getText().toString().trim();
            phoneNumber = inputPhoneNumber.getText().toString();
            userEmail = inputEmail.getText().toString();

            validateContactDetails();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void validateContactDetails(){
        if(firstName.isEmpty()){
            inputFname.setError("First name should not be empty");
        }
        else if(String.valueOf(phoneNumber).isEmpty()){
            inputPhoneNumber.setError("Phone number field cannnot be empty");
        }

        else{
            editDetails();
        }

    }

    public void editDetails(){
        ContactDetails details = new ContactDetails();
        details.setFirstName(firstName);
        details.setLastName(lastName);
        details.setPhoneNumber(Integer.parseInt(phoneNumber));
        details.setUserEmail(userEmail);
        details.setFavorite(false);
        details.setProfileImage(profileImageUri);

        long id = detailsBox.put(details);

        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditContactActivity.this, ContactDetailsActivity.class);
        intent.putExtra("CONTACT_ID", id);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select an image"), commonVariables.SELECT_IMAGE);
    }

    public void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"fname_" +
                String.valueOf(System.currentTimeMillis()) + ".jpg"));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, commonVariables.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == commonVariables.SELECT_IMAGE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    profileImageUri = String.valueOf(selectedImageUri);
                    profileImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

}