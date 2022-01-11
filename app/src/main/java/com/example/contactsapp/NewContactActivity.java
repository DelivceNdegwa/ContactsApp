package com.example.contactsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.contactsapp.models.ContactDetails;
import com.example.contactsapp.utils.Common;
import com.example.contactsapp.utils.ObjectBox;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.objectbox.Box;

public class NewContactActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String  profileImageUri = "android.resource://com.example.contactsapp/drawable/ic_baseline_account_circle_24";

    String[] saveLocations = {"Gmail", "Safaricom", "Airtel", "Phone"};
    TextInputEditText inputFname, inputLname, inputPhoneNumber, inputEmail;
    String saveLocation, firstName, lastName, userEmail, phoneNumber;;

    ImageView imgTakeProfile, profileImage;
    ConstraintLayout imageContainer;

    Box<ContactDetails> detailsBox = ObjectBox.get().boxFor(ContactDetails.class);
    Common commonVariables = new Common();

    /*
    Uri uri = Uri.parse("android.resource://your.package.here/drawable/image_name");
    InputStream stream = getContentResolver().openInputStream(uri);

    public NewContactActivity() throws FileNotFoundException {
    }
    * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact_form);

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
        commonVariables.setActionTitle("Create a new contact");
        getSupportActionBar().setTitle(commonVariables.getActionTitle());

        Spinner saveLocationSpinner = findViewById(R.id.spinner_saving);
        saveLocationSpinner.setOnItemSelectedListener(this);

        ArrayAdapter saveLocationAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                saveLocations);
        saveLocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        saveLocationSpinner.setAdapter(saveLocationAdapter);

        imageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        saveLocation = saveLocations[i];
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



    public void validateContactDetails(){
        if(firstName.isEmpty()){
            inputFname.setError("First name should not be empty");
        }
        else if(String.valueOf(phoneNumber).isEmpty()){
            inputPhoneNumber.setError("Phone number field cannnot be empty");
        }

        else{
            saveContact();
        }

    }


    public void saveContact(){

        ContactDetails details = new ContactDetails();
        details.setFirstName(firstName);
        details.setLastName(lastName);
        details.setPhoneNumber(Integer.parseInt(phoneNumber));
        details.setUserEmail(userEmail);
        details.setFavorite(false);
        details.setProfileImage(profileImageUri);

        long id = detailsBox.put(details);

        Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NewContactActivity.this, ContactDetailsActivity.class);
        intent.putExtra("CONTACT_ID", id);
        startActivity(intent);
        finish();

    }

}