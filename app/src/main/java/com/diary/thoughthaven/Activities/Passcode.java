package com.diary.thoughthaven.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.diary.thoughthaven.Fragment.EnterPassword;
import com.diary.thoughthaven.Fragment.SetPassword;
import com.diary.thoughthaven.R;
import com.diary.thoughthaven.Utilities.PreferenceManager;

public class Passcode extends AppCompatActivity {

    public static int navItemIndex;
    public static final String TAG_ENTER = "EnterPassword";
    public static final String TAG_SET= "SetPassword";
    private boolean shouldLoadHomeFragOnBackPress = true;
    public static String CURRENT_TAG_PASSWORD ;
    private PreferenceManager preferenceManager;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        mHandler = new Handler();
        preferenceManager=new PreferenceManager(this);

        if (preferenceManager.isPasswordSet())
        {
            navItemIndex=0;
            CURRENT_TAG_PASSWORD = TAG_ENTER;
        }else {
            navItemIndex=1;
            CURRENT_TAG_PASSWORD = TAG_SET;
        }

        loadHomeFragment();
    }

    public void loadHomeFragment() {
        setToolbarTitle();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.framePassword, fragment, CURRENT_TAG_PASSWORD);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mRunnable != null) {
            mHandler.post(mRunnable);
        }

        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:

                EnterPassword enterPassword = new EnterPassword();
                return enterPassword;

            case 1:
                SetPassword setPassword = new SetPassword();
                return setPassword;

            default:
                return new EnterPassword();

        }
    }



    private void setToolbarTitle() {
        if (navItemIndex == 0) {
            getSupportActionBar().setTitle("Enter Password");
        } else {
            getSupportActionBar().setTitle("Set Password");
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Passcode.this,Home.class));
        finish();
    }

}
