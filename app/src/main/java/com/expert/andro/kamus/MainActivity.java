package com.expert.andro.kamus;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.expert.andro.kamus.fragment.EngIdFragment;
import com.expert.andro.kamus.fragment.IdEngFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_ID_ENG = "id_eng";
    private static final String TAG_ENG_ID = "eng_id";
    private static String CURRENT_TAG = TAG_ID_ENG;

    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_ID_ENG;
            loadHomeFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragOnBackPress){
            if (navItemIndex != 0){
                navItemIndex = 0;
                CURRENT_TAG = TAG_ID_ENG;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null){
            drawer.closeDrawers();
            return;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
                fragmentTransaction.commit();
            }
        };

        if (runnable != null){
            mHandler.post(runnable);
        }
        drawer.closeDrawers();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex){
            case 0:
                // id-eng
                IdEngFragment idEngFragment = new IdEngFragment();
                return idEngFragment;
            case 1:
                // eng-id
                EngIdFragment engIdFragment = new EngIdFragment();
                return engIdFragment;
            default:
                return new IdEngFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_id_eng:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_ID_ENG;
                        break;
                    case R.id.nav_eng_id:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_ENG_ID;
                        break;
                    default:
                        navItemIndex = 0;
                }

                if (item.isChecked()){
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                item.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
