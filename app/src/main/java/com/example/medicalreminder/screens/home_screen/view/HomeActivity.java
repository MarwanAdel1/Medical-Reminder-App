package com.example.medicalreminder.screens.home_screen.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.medicalreminder.R;
import com.example.medicalreminder.local_data.LocalLoginUserData;
import com.example.medicalreminder.model.RegisterationRepo;
import com.example.medicalreminder.network_data.FirebaseAccess;
import com.example.medicalreminder.screens.SplashScreen;
import com.example.medicalreminder.screens.add_dependent_screen.AddDependentFragment;
import com.example.medicalreminder.screens.add_dependent_screen.DependentActivity;
import com.example.medicalreminder.screens.add_medication_screen.view.AddMedicationActivityScreen;
import com.example.medicalreminder.screens.addmedfriend.MedfriendActivity;
import com.example.medicalreminder.screens.addmedfriend.MyFriendsAdapter;
import com.example.medicalreminder.screens.addmedfriend.OnMedfriendClickListener;
import com.example.medicalreminder.screens.addmedfriend.RequestModel;
import com.example.medicalreminder.screens.addmedfriend.RequestsActivity;
import com.example.medicalreminder.screens.home_screen.presenter.HomePresenter;
import com.example.medicalreminder.screens.home_screen.presenter.HomePresenterInterface;
import com.example.medicalreminder.screens.login_screen.view.LoginScreenActivity;
import com.example.medicalreminder.screens.user_profile.view.UserProfileActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeActivityInterface , OnMedfriendClickListener {

    TabLayout tab;
    ViewPager viewPager;
    TextView profile_name;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView navUsername;
    Toolbar toolbar;
    TextView navEditeProfile;
    ImageView navUserImage;
    ActionBar actionBar;
    ExtendedFloatingActionButton add_medication_button;
    ExtendedFloatingActionButton add_dose_button;
    ExtendedFloatingActionButton add_health_tracker_button;


    MyFriendsAdapter senderFriendsAdapter ;
    ArrayList<RequestModel> modellist ;
    private FirebaseFirestore firestore ;
    private FirebaseAuth firebaseAuth;
    RecyclerView headerRecycleNavDrawar ;


    HomePresenterInterface presenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        initUI();
        MyMedFriends();
        setActionBar();
        setViewPager();
        presenterInterface = new HomePresenter(this, RegisterationRepo.getInstance(this , FirebaseAccess.getInstance(), LocalLoginUserData.getInstance(this)));
        presenterInterface.getUserName(this);

    //----------------------------------- Start of Handling Extended floating action buttons-----------------------------------------------

        add_medication_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddMedicationActivityScreen.class));
                add_medication_button.setVisibility(View.INVISIBLE);
                add_health_tracker_button.setVisibility(View.INVISIBLE);
                add_dose_button.setVisibility(View.INVISIBLE);
            }
        });

        add_health_tracker_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_medication_button.setVisibility(View.INVISIBLE);
                add_health_tracker_button.setVisibility(View.INVISIBLE);
                add_dose_button.setVisibility(View.INVISIBLE);
            }
        });

        add_dose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_medication_button.setVisibility(View.INVISIBLE);
                add_health_tracker_button.setVisibility(View.INVISIBLE);
                add_dose_button.setVisibility(View.INVISIBLE);
            }
        });

        navEditeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
            }
        });

    //----------------------------------- End of Handling Extended floating action buttons-----------------------------------------------
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenterInterface.getUserName(this);
    }

    private void initUI(){
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
        navUserImage = (ImageView) headerView.findViewById(R.id.profile_img);
        navEditeProfile = (TextView) headerView.findViewById(R.id.edit_profile);

        ////////////////

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        headerRecycleNavDrawar = headerView.findViewById(R.id.headerRecycleNavDrawar);
        modellist = new ArrayList<>();

    }

    //--------------------------------- Start of action bar methods -------------------------------------------------------
    private void setActionBar(){
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        // methods to display the icon in the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_account_circle_24);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("");
        setListeners();
    }
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
                            Intent intent = new Intent(HomeActivity.this, DependentActivity.class);
                            startActivity(intent);

                        } else if (menuItem.toString().equals("Invite Medfriend")) {
                            Intent intent = new Intent(HomeActivity.this, MedfriendActivity.class);
                            startActivity(intent);
                        } else if (menuItem.toString().equals("Requests")){
                            startActivity(new Intent(HomeActivity.this , RequestsActivity.class)); // senderRequestsActivity
                        }else if(menuItem.toString().equals("Logout")) {
                            presenterInterface.logout();
                            startActivity(new Intent(HomeActivity.this, LoginScreenActivity.class));
                        }
                        return true;
                    }
                }));
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {}

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {}

            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

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
    //--------------------------------- End of action bar methods -------------------------------------------------------

    //---------------------------------- Start of Add floating action button ----------------------------------
    public void press(View view) {
        add_medication_button.setVisibility(View.VISIBLE);
        add_health_tracker_button.setVisibility(View.VISIBLE);
        add_dose_button.setVisibility(View.VISIBLE);
    }


    //---------------------------------- End of Add floating action button -----------------------------------

    // -------------------------------------------- Start of Tab Layout (Home, Medication, Settings)---------------------
    //--------------- Adapter Class with Tab layout (Home, Medications, Settings) ---------------
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
    //--------------------- End of Adapter Class -----------------------
        void setViewPager(){
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
       }
    // -------------------------------------------- End of Tab Layout (Home, Medication, Settings)---------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onSuccess(String name) {
        actionBar.setSubtitle(name);
        navUsername.setText(name);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void MyMedFriends(){

        senderFriendsAdapter = new MyFriendsAdapter( this, modellist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()); // , LinearLayoutManager.HORIZONTAL , false
        headerRecycleNavDrawar.setHasFixedSize(true);
        headerRecycleNavDrawar.setLayoutManager(layoutManager);
        headerRecycleNavDrawar.setAdapter(senderFriendsAdapter);


        // get my meds
        firestore.collection("MedFriends")
                .document("MyFriends") // senderfriends   ///nargesnagy21@gmail.com
                .collection( firebaseAuth.getCurrentUser().getEmail()) // my current user    firebaseAuth.getCurrentUser().getEmail()
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null ){
                            Log.d("TAG", "Error : "+error.getMessage());
                        }
                        for (DocumentChange doc : value.getDocumentChanges()){

                            RequestModel model = doc.getDocument().toObject(RequestModel.class);
                            modellist.add(model);
                            senderFriendsAdapter.notifyDataSetChanged();

                            // if(model.getReciverName().equals("heba")) {
                            // senderNametext.setText(model.getReciverName());
                            //}

                        }
                    }
                });

    }


    @Override
    public void onMedFriendClick(RequestModel model) {
        Toast.makeText(getApplicationContext(), "do", Toast.LENGTH_SHORT).show();
    }
}
