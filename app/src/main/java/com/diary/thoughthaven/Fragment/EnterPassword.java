package com.diary.thoughthaven.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diary.thoughthaven.Activities.Dashboard;
import com.diary.thoughthaven.R;
import com.diary.thoughthaven.Utilities.PreferenceManager;


public class EnterPassword extends Fragment {


    private View view;
    private PreferenceManager preferenceManager;
    private EditText etPassword;
    private Button btnLogin;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_enter_password, container, false);
        Initialization();
        return view;

    }

    private void Initialization() {
        preferenceManager = new PreferenceManager(getContext());
        etPassword = (EditText) view.findViewById(R.id.etPasswordEnter);
        btnLogin = (Button) view.findViewById(R.id.btnEnterPassword);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidPassword(etPassword.getText().toString())) {
                    String password = preferenceManager.getPassword();
                    if (etPassword.getText().toString().toLowerCase().equals(password)) {
                        progressDialog.dismiss();
                        startActivity(new Intent(getContext(), Dashboard.class));
                        getActivity().finish();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
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

}
