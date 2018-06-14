package com.diary.thoughthaven.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.diary.thoughthaven.Fragment.NotePad;
import com.diary.thoughthaven.Fragment.TodoList;
import com.diary.thoughthaven.R;
import   com.diary.thoughthaven.Fragment.Home;

public class Dashboard extends AppCompatActivity{


    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    public static int navItemIndex = 0;
    public static final String TAG_HOME = "Home";
    public static final String TAG_NOTEPAD = "NotePad";
    public static final String TAG_TODO = "TodoList";
    private boolean shouldLoadHomeFragOnBackPress = true;
    public static String CURRENT_TAG = TAG_HOME;
    private Handler mHandler;
    private String[] activityTitles;
    private TextView tvNavName;
    private View navHeader;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Initialization();
        setNavigationView();


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }


    }

    private void Initialization() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        mHandler = new Handler();
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_title);
        alertDialog = new AlertDialog.Builder(this);


    }

    private void setNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    navItemIndex = 0;
                    CURRENT_TAG = TAG_HOME;
                } else if (id == R.id.nav_notepad) {
                    navItemIndex = 1;
                    CURRENT_TAG = TAG_NOTEPAD;
                } else if (id == R.id.nav_todo) {
                    startActivity(new Intent(Dashboard.this,Todo.class));

                } else if (id == R.id.nav_personalise) {
//                    navItemIndex = 3;
//                    CURRENT_TAG = TAG_FIND_CAR;
                }  else if (id == R.id.nav_share) {
                    shareApp();
                } else if (id == R.id.nav_feedback) {
                    feedback();
                } else if (id == R.id.nav_log_out) {
                    logout();
                } else {
                    navItemIndex = 0;
                }

                drawer.closeDrawer(Gravity.START);
                // loadFragment on navigation item click
                loadHomeFragment();
                return true;
            }
        });
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                if (shouldLoadHomeFragOnBackPress) {
                    if (navItemIndex != 0) {
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        loadHomeFragment();
                    }
                }

                Home homeFrag = new Home();
                return homeFrag;

            case 1:
                NotePad notePad = new NotePad();
                return notePad;

            case 2:
                TodoList todoList = new TodoList();
                return todoList;

            default:
                return new Home();

        }
    }

    public void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();


        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            return;
        }

        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments

                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frameUser, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();

            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mRunnable != null) {
            mHandler.post(mRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void selectNavMenu() {

            navigationView.getMenu().getItem(navItemIndex).setChecked(true);

    }

    private void setToolbarTitle() {

            getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            moveTaskToBack(true);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {
            finish();
            startActivity(new Intent(Dashboard.this, Home.class));

        }

        return super.onOptionsItemSelected(item);
    }

    // logout the app
    private void logout() {

    }

    // give feedback on play store
    public void feedback() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
        startActivity(intent);
    }

    // when app is uploaded share via play store
    public void shareApp() {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Download the app from https://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(sharingIntent.createChooser(sharingIntent, "Share Via"));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                navigationView.getMenu().getItem(navItemIndex).setChecked(true);
                loadHomeFragment();

                return;
            }
        }

        if (navItemIndex == 0) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setTitle("Exit!")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    }
}
