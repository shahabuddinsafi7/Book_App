package af.shahabuddin.book;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import af.shahabuddin.book.database.FavoriteDatabase;
import af.shahabuddin.book.fragments.AbuteFragment;
import af.shahabuddin.book.fragments.HomeFragment;
import af.shahabuddin.book.fragments.FavFragment;
import af.shahabuddin.book.fragments.SettingFragment;


public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView nav_view;
    private View mContentView;
    public static FavoriteDatabase favoriteDatabase;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

      favoriteDatabase = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, "myfavdb").allowMainThreadQueries().build();
        nav_view=findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                return false;
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frg_container, new HomeFragment())
                    .commit();
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        drawer = findViewById(R.id.drawer_layout);

        final View appMainView = findViewById(R.id.app_main_layout);
        drawerToggle =new

                ActionBarDrawerToggle(this,drawer, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close) {
                    int slidingDirection = +1;

                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            if (isRTL())
                                slidingDirection = -1;
                        }
                    }

                    @Override
                    public void onDrawerOpened (View drawerView){
                        super.onDrawerOpened(drawerView);

                    }

                    @Override
                    public void onDrawerClosed (View drawerView){
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerSlide (View drawerView,float slideOffset){
                        super.onDrawerSlide(drawerView, slideOffset);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            slidingAnimation(drawerView, slideOffset);
                        }
                    }

                    private void slidingAnimation (View drawerView,float slideOffset){
                        appMainView.setTranslationX(slideOffset * drawerView.getWidth() * slidingDirection);
                        drawer.bringChildToFront(drawerView);
                        drawer.requestLayout();
                    }
                }

        ;

        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()

        {
            @Override
            public boolean onNavigationItemSelected (MenuItem menuItem){
                int id = menuItem.getItemId();
                if (id == R.id.nav_home) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frg_container, new HomeFragment())
                            .commit();

                } else if (id == R.id.fav) {
                    Bundle bundle = new Bundle();
                    Fragment newFragment = new FavFragment();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frg_container, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if (id == R.id.settings) {
                    Bundle bundle = new Bundle();
                    Fragment newFragment = new SettingFragment();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frg_container, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if (id == R.id.about) {
                    Bundle bundle = new Bundle();
                    Fragment newFragment = new AbuteFragment();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frg_container, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else if (id == R.id.exit) {
                    finishAffinity();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }


    private boolean isRTL() {
        return getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frg_container);
            if (fragment instanceof HomeFragment) {
                if (doubleBackToExitPressedOnce) {
                    //int pid = android.os.Process.myPid();
                    //android.os.Process.killProcess(pid);
                    finish();
                }
                this.doubleBackToExitPressedOnce = true;

                Snackbar snackbar = Snackbar.make(drawer, "برای خروج دوباره بازگشت را لمس کنید", Snackbar.LENGTH_SHORT);
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                int snackbarTextId = R.id.snackbar_text;
                TextView textView = snackbarView.findViewById(snackbarTextId);
                textView.setTextColor(Color.WHITE);
                ViewCompat.setLayoutDirection(snackbarView,ViewCompat.LAYOUT_DIRECTION_RTL);

                    snackbarView.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
                snackbar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                super.onBackPressed();
            }
        }
    }

}