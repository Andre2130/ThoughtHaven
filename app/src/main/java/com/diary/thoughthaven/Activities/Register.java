package com.diary.thoughthaven.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.diary.thoughthaven.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import thebat.lib.validutil.ValidUtils;

public class Register extends AppCompatActivity {


    private EditText etPassword;
    private EditText etConfirm;
    private EditText etEmail;
    private Button btnSignUp;
    private ValidUtils validUtils;
    private TextView alreadyRegistered;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private void Initialization() {

        etEmail = (EditText) findViewById(R.id.etEmailRegister);
        etPassword = (EditText) findViewById(R.id.etPasswordRegister);
        etConfirm = (EditText) findViewById(R.id.etConfirmRegister);
        btnSignUp = (Button) findViewById(R.id.btnSinUpRegister);
        alreadyRegistered = (TextView) findViewById(R.id.alreadyRegistered);

        alertDialog = new AlertDialog.Builder(Register.this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Signing up please wait");
        validUtils = new ValidUtils();
        auth = FirebaseAuth.getInstance();

    }

    private void createUser() {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    progressDialog.dismiss();
                    AlertDialog.Builder alert = new AlertDialog.Builder(Register.this);
                    alert.setTitle("Successful")
                            .setIcon(getResources().getDrawable(R.drawable.ic_done_black_24dp))
                            .setMessage("Your account is created successfully you can login now...")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();
                                    finish();
                                }
                            })
                            .show();

                } else {

                    progressDialog.dismiss();
                    AlertDialog.Builder alert = new AlertDialog.Builder(Register.this);
                    alert.setTitle("Operation Failed")
                            .setMessage("The email address you have entered is already registered please login.")
                            .setIcon(getResources().getDrawable(R.drawable.ic_warning_black_24dp))
                            .setPositiveButton("Retry", null)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                }
            }
        });
    }


    private boolean isValidPassword(String password) {
        boolean flag = true;

        if (password.isEmpty() || password.length() < 6) {
            flag = false;
        }

        return flag;
    }

    private boolean isValidConfirm(String password, String con) {
        boolean flag = true;

        if (con.isEmpty() || !con.equals(password) || con.length() < 6) {
            flag = false;
        }

        return flag;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
