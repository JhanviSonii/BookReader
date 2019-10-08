package com.example.bookreader;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;


public class DrawerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private TextView headerTitle;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);


        fragmentManager = getSupportFragmentManager();
        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_white_18dp);


        if (headerTitle==null){
            Log.i("test","null");
        }

        View header=navigationView.getHeaderView(0);
        headerTitle=(TextView) (header.findViewById(R.id.nav_header_title));
       // headerTitle.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        navigationView.setItemIconTintList(null);

        fragmentManager.beginTransaction().replace(R.id.drawer_fragments_container, new HomeFragment(), null).commit();





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                System.out.println("id"+id);
                switch (id)
                {
                    /*case R.id.book_sell:

                        SellBookFragment fragment = new SellBookFragment();
                        fragmentManager.beginTransaction().replace(R.id.drawer_fragments_container, fragment, null).commit();
                        drawerLayout.closeDrawers();
                        break;*/


                    case R.id.book_explore:
                        RecyclerViewBookFragment fragment1 = new RecyclerViewBookFragment();
                        fragmentManager.beginTransaction().replace(R.id.drawer_fragments_container, fragment1, null).commit();
                        drawerLayout.closeDrawers();
                        break;


                    /*case R.id.logout_menu:
                        FirebaseAuth.getInstance().signOut();
                        drawerLayout.closeDrawers();
                        DrawerActivity.this.finish();
                        Intent intent= new Intent(DrawerActivity.this,LoginSignUpContainer.class);
                        startActivity(intent);

                        break;*/


                }


                return true;
            }
        });

    }

    //FirebaseRemoteConfig



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;


        }
        return super.onOptionsItemSelected(item);

    }


}

