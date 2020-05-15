package com.example.profileinformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class firstactivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    AppBarLayout appBarLayout;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    GoogleSignInClient mGoogleSignInClient;
    NavigationView navigationView;
    FirebaseAuth firebaseAuth;
    CardView profilecardview;
    Fragment fragment;
    FirebaseFirestore fstore;
    ImageView Image;
    String userid;
    TextView name;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstactivity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        name = findViewById(R.id.fullname);
        Image = findViewById(R.id.photo);
        profilecardview = findViewById(R.id.profilecardview);
        fstore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        userid=intent.getStringExtra("UID");
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            Uri photo = account.getPhotoUrl();
            String Name = account.getDisplayName();
            name.setText("Hi " + Name);
            Glide.with(this).load(photo).into(Image);
        }

        navigationView.setCheckedItem(R.id.Addbooking);


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        } else {


            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                startActivity(new Intent(getApplicationContext(),firstactivity.class));
                break;
            case R.id.Addbooking:
                name.setVisibility(View.GONE);
                toolbar.setTitle("Add Booking");
                drawerLayout.closeDrawer(GravityCompat.START);
                fragment=new Fragment_addBookings();
                profilecardview.setVisibility(View.GONE);
                counter(fragment);

                break;
            case R.id.Searchbookings:
                drawerLayout.closeDrawer(GravityCompat.START);

                startActivity(new Intent(this, Searchbookings.class));
                break;
            case R.id.profile:
                Bundle bundle = new Bundle();
                bundle.putString("UID", userid);
                fragment=new Fragment_Profile();
                fragment.setArguments(bundle);
                toolbar.setTitle("Profile");
                profilecardview.setVisibility(View.GONE);


                drawerLayout.closeDrawer(GravityCompat.START);
                counter(fragment);
                break;
            case R.id.update:
                toolbar.setTitle("Update Booking");
                profilecardview.setVisibility(View.GONE);
                fragment=new Fragment_UpdateBookings();
                drawerLayout.closeDrawer(GravityCompat.START);
                counter(fragment);
                break;
            case R.id.delete:
                toolbar.setTitle("Delete");
                drawerLayout.closeDrawer(GravityCompat.START);
                fragment=new Fragment_Delete();
                profilecardview.setVisibility(View.GONE);
                counter(fragment);



                break;


        }
        return false;
    }

    public void counter(Fragment fragment) {
        new CountDownTimer(800, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();

            }


        }.start();
    }
    public String getUID()
    {
        return userid;
    }
}








