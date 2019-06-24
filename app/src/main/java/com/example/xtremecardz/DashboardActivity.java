package com.example.xtremecardz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xtremecardz.Utils.CustomTypefaceSpan;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        init();
        setDrawerAction();
    }

    private void init(){
        Typeface ubuntuRegular  = Typeface.createFromAsset(getAssets(), "fonts/ProximaNova-Thin.ttf");
        Typeface ubuntuSemibold = Typeface.createFromAsset(getAssets(), "fonts/ProximaNova-Thin.ttf");
        Typeface googleSansReg  = Typeface.createFromAsset(getAssets(), "fonts/GoogleSans-Regular.ttf");
        Typeface ubuntuExtrabold   = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-Bold.ttf");

        TextView titleRow       = findViewById(R.id.titleDashboard);
        Button newCardButton    = findViewById(R.id.newCardDesign);
        Button createDesign     = findViewById(R.id.createdDesign);
        Button templateDesign   = findViewById(R.id.templateDesign);
        Button setUpAccount     = findViewById(R.id.setUpAccount);
        //Button myCardsRow       = findViewById(R.id.myCardsRow);
        Button uploadDesign     = findViewById(R.id.uploadDesign);

        titleRow.setTypeface(googleSansReg);

        newCardButton.setTypeface(ubuntuExtrabold);
        createDesign.setTypeface(ubuntuExtrabold);
        templateDesign.setTypeface(ubuntuExtrabold);
        setUpAccount.setTypeface(ubuntuExtrabold);
        //myCardsRow.setTypeface(ubuntuExtrabold);
        uploadDesign.setTypeface(ubuntuExtrabold);

        createDesign.setOnClickListener(this);
    }

    private void setDrawerAction(){
        drawerLayout    = findViewById(R.id.drawer_layout);
        drawerToggle    = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView  = findViewById(R.id.nav_view);
        setNavigationViewItemTypeface(navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id          = menuItem.getItemId();
        drawerLayout    = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationViewItemTypeface(NavigationView navigationView){
        Menu menu   = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++){
            MenuItem menuItem   = menu.getItem(i);
            SubMenu subMenu     = menuItem.getSubMenu();
            if(subMenu != null && subMenu.size() > 0){
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem();
                    applyToSubMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(menuItem);
        }
    }

    private void applyFontToMenuItem(MenuItem menuItem){
        Typeface font   = Typeface.createFromAsset(getAssets(), "fonts/GoogleSans-Bold.ttf");
        SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(mNewTitle);
    }

    private void applyToSubMenuItem(MenuItem menuItem){
        Typeface font   = Typeface.createFromAsset(getAssets(), "fonts/GoogleSans-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(mNewTitle);
    }

    private void clickListenerAction(){

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.createdDesign){
            Intent intent   = new Intent(getBaseContext(), CreateDesignActivity.class);
            startActivity(intent);
        }
    }
}
