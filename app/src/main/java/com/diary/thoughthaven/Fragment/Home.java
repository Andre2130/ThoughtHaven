package com.diary.thoughthaven.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.diary.thoughthaven.Activities.Dashboard;
import com.diary.thoughthaven.Activities.Todo;
import com.diary.thoughthaven.R;


public class Home extends Fragment {


    private View view;
    private CardView cvNotePad;
    private CardView cvTodo;
    private Button btnShare;
    private Button btnRate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Initialization();
        return view;
    }

    private void Initialization() {

        cvNotePad = (CardView) view.findViewById(R.id.cvNotePad);
        cvTodo = (CardView) view.findViewById(R.id.cvTodo);
        btnShare = (Button) view.findViewById(R.id.btnShareApp);
        btnRate = (Button) view.findViewById(R.id.btnRateApp);

        cvNotePad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dashboard.navItemIndex = 1;
                Dashboard.CURRENT_TAG = Dashboard.TAG_NOTEPAD;
                ((Dashboard) getActivity()).loadHomeFragment();
            }
        });

        cvTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Todo.class));

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback();
            }
        });
    }


    public void feedback() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getContext().getPackageName()));
        startActivity(intent);
    }

    public void shareApp() {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Download the app from https://play.google.com/store/apps/details?id=" + getContext().getPackageName());
        startActivity(sharingIntent.createChooser(sharingIntent, "Share Via"));
    }


}
