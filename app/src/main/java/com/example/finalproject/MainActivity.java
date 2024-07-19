package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalproject.random.RandomFragment;
import com.google.android.material.navigation.NavigationView;
import com.example.finalproject.Them.DongGopThongTinFragment;
import com.example.finalproject.TimKiem.TimkiemdacsanFragment;
import com.example.finalproject.Yeuthich.DanhsachyeuthichFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_random);
            displayFragment(new RandomFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_find) {
            selectedFragment = new TimkiemdacsanFragment(); // Assuming you have this fragment
        } else if (id == R.id.nav_favoritelist) {
            selectedFragment = new DanhsachyeuthichFragment(); // Assuming you have this fragment
        } else if (id == R.id.nav_add) {
            selectedFragment = new DongGopThongTinFragment(); // Assuming you have this fragment
        } else if (id == R.id.nav_random) {
            selectedFragment = new RandomFragment();
        }

        if (selectedFragment != null) {
            displayFragment(selectedFragment);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool Application");
        intent.putExtra(Intent.EXTRA_TEXT, "Your Application Link Here");
        startActivity(Intent.createChooser(intent, "Share Via"));
        return super.onOptionsItemSelected(item);
    }
}
