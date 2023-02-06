package com.thephoenixzone.campusstudent;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    Context context;
    EditText etName, etEmail, etMobile, etAddress, etDepartment, etYear;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        prefManager = new PrefManager(this);
        context = this;
        etName = (EditText) findViewById(R.id.etUsername);
        etDepartment = (EditText) findViewById(R.id.etDepartment);
        etYear = (EditText) findViewById(R.id.etYear);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etName.setText(prefManager.getUserName());
        etEmail.setText(prefManager.getUserEmail());
        etMobile.setText(prefManager.getUserMobile());
        etAddress.setText(prefManager.getUserAddress());
        etDepartment.setText(prefManager.getUserPassword());
        etYear.setText(prefManager.getUserType());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}