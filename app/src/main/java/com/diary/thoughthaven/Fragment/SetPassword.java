package com.diary.thoughthaven.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.diary.thoughthaven.Activities.Passcode;
import com.diary.thoughthaven.R;
import com.diary.thoughthaven.Utilities.PreferenceManager;

public class SetPassword extends Fragment {

    private View view;
    private EditText etPassword;
    private EditText etConfPassword;
    private Button btnSetPassword;
    private PreferenceManager preferenceManager;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_set_password, container, false);
        Initialization();
        return view;
    }

    private void Initialization() {
        preferenceManager=new PreferenceManager(getContext());
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etConfPassword = (EditText) view.findViewById(R.id.etConfPassword);
        btnSetPassword = (Button) view.findViewById(R.id.btnSetPasscode);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait..");


        btnSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidPassword(etPassword.getText().toString())) {
                    etPassword.setError("Invalid password minimum 6 characters");
                } else if (!isValidConfirm(etPassword.getText().toString(), etConfPassword.getText().toString())) {
                    etConfPassword.setError("Password mismatch");
                } else {

                    preferenceManager.setPassword(etPassword.getText().toString().toLowerCase());
                    preferenceManager.setPasswordFlag(true);
                    progressDialog.dismiss();

                    AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                    alert.setTitle("Successful")
                            .setIcon(getResources().getDrawable(R.drawable.ic_done_black_24dp))
                            .setMessage("Password saved successfully you can login now.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Passcode.navItemIndex = 0;
                                    Passcode.CURRENT_TAG_PASSWORD = Passcode.TAG_ENTER;
                                    ((Passcode) getActivity()).loadHomeFragment();
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


}
