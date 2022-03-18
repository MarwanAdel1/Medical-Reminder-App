package com.example.medicalreminder.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.medicalreminder.R;
import com.example.medicalreminder.screens.add_dependent_screen.AddDependentFragment;
import com.example.medicalreminder.screens.add_dependent_screen.DependentActivity;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicationActivityScreen;
import com.example.medicalreminder.screens.addmedfriend.MedfriendActivity;
import com.example.medicalreminder.screens.home_screen.HomeFragment;
import com.example.medicalreminder.screens.user_profile.Profile;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TabBar extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;
    TextView profile_name;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView navUsername;
    Toolbar toolbar;
    ActionBar actionBar;
    ExtendedFloatingActionButton add_medication_button;
    ExtendedFloatingActionButton add_dose_button;
    ExtendedFloatingActionButton add_health_tracker_button;
    boolean addBtnClicked = false;
    SharedPreferences readUserData;
//    Intent getIntent;
    //              Firebase database object to access firebase's realtime database.
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //         get user data from shared preferences after login
        readUserData = getSharedPreferences("user_file", MODE_PRIVATE);


//        getIntent = getIntent();

        //      UI References
        tab = findViewById(R.id.buttom_tab_layout);
        viewPager = findViewById(R.id.pager);
        drawerLayout = findViewById(R.id.full_drawable_layout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.nav_toolBar);
        add_medication_button = findViewById(R.id.add_medication_btn);
        add_health_tracker_button = findViewById(R.id.add_health_tracker_btn);
        add_dose_button = findViewById(R.id.add_dose_btn);
        profile_name = findViewById(R.id.profile_name);
        //          to set profile name of the user
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.profile_name);
//        navUsername.setText(readUserData.getString("name", "No Name Exist"));
        ImageView navUserImage = (ImageView) headerView.findViewById(R.id.profile_img);
        TextView navEditeProfile = (TextView) headerView.findViewById(R.id.edit_profile);

//----------------------------------- Start of Handling Extended floating action buttons-----------------------------------------------

        add_medication_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TabBar.this, AddMedicationActivityScreen.class));
                add_medication_button.setVisibility(View.INVISIBLE);
                add_health_tracker_button.setVisibility(View.INVISIBLE);
                add_dose_button.setVisibility(View.INVISIBLE);
                Toast.makeText(TabBar.this, "Add Medication", Toast.LENGTH_SHORT).show();
            }
        });

        add_health_tracker_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_medication_button.setVisibility(View.INVISIBLE);
                add_health_tracker_button.setVisibility(View.INVISIBLE);
                add_dose_button.setVisibility(View.INVISIBLE);
                Toast.makeText(TabBar.this, "Add Health Tracker", Toast.LENGTH_SHORT).show();
            }
        });

        add_dose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_medication_button.setVisibility(View.INVISIBLE);
                add_health_tracker_button.setVisibility(View.INVISIBLE);
                add_dose_button.setVisibility(View.INVISIBLE);
                Toast.makeText(TabBar.this, "Add Dose", Toast.LENGTH_SHORT).show();
            }
        });

        navEditeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TabBar.this, Profile.class));
            }
        });

//----------------------------------- End of Handling Extended floating action buttons-----------------------------------------------

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        // methods to display the icon in the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_account_circle_24);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("");
        setListeners();

        if(!databaseReference.child(readUserData.getString("email","")).equals("")) {
            databaseReference.child(readUserData.getString("email", "")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    actionBar.setSubtitle(snapshot.child("name").getValue(String.class));
                    System.out.println("value: " + snapshot.child("name").getValue(String.class));
                    navUsername.setText(snapshot.child("name").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            actionBar.setSubtitle(readUserData.getString("name","no name"));
            navUsername.setText(readUserData.getString("name","no name"));
        }


// -------------------------------------------- Start of Tab Layout (Home, Medication, Settings)---------------------

        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
// -------------------------------------------- End of Tab Layout (Home, Medication, Settings)---------------------
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        databaseReference.child(readUserData.getString("email","")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                actionBar.setSubtitle(snapshot.child("name").getValue(String.class));
                System.out.println("value: " + snapshot.child("name").getValue(String.class));
                navUsername.setText(snapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //----------------------------------------------- Start of Action Bar -----------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification_icon:
                Toast.makeText(this, "notification", Toast.LENGTH_SHORT).show();
                return true;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        Toast.makeText(this, "no item", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

//----------------------------------------------- End of Action Bar -----------------------------------------------------------


//--------------------------------------------- Adapter Class with Tab layout (Home, Medications, Settings) ------------------------
    static class Adapter extends FragmentStatePagerAdapter {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;

                case 1:
                    fragment = new MedicineFragment();
                    break;

                case 2:
                    fragment = new SettingsFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

//---------------------------------------------- End of Adapter Class --------------------------------------------------------


//------------------------------------------------------ Start of Add floating action button ----------------------------------

    public void press(View view) {
        add_medication_button.setVisibility(View.VISIBLE);
        add_health_tracker_button.setVisibility(View.VISIBLE);
        add_dose_button.setVisibility(View.VISIBLE);

    }

//----------------------------------------------------- End of Add floating action button -----------------------------------


    private void setListeners() {
        navigationView.setNavigationItemSelectedListener(
                (new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();
                        Fragment dfragment = null;
                        if (menuItem.toString().equals("Add Dependent")) {
                            dfragment = new AddDependentFragment();

                            Intent intent = new Intent(TabBar.this, DependentActivity.class);
                            startActivity(intent);
                            Toast.makeText(TabBar.this, "Add Dependent", Toast.LENGTH_SHORT).show();

                        } else if (menuItem.toString().equals("Invite Medfriend")) {
                            // dfragment = new;
                            Intent intent = new Intent(TabBar.this, MedfriendActivity.class);
                            startActivity(intent);

                            Toast.makeText(TabBar.this, "Invite Medfriend", Toast.LENGTH_SHORT).show();

                        }
                        //Toast.makeText(TabBar.this, menuItem.toString(), Toast.LENGTH_SHORT).show();
                        // add code here to update the UI based on the item selected
                        // for example swap UI fragment here
                        return true;
                    }
                }));
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}