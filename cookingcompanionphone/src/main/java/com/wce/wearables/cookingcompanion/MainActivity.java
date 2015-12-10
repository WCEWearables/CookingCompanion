package com.wce.wearables.cookingcompanion;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, GoogleApiClient.ConnectionCallbacks {
    @Override
    public void onConnected(Bundle bundle) {



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in getActionBar
     */
    private CharSequence mTitle;

    private Boolean loaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);
        actionBar.setDisplayShowTitleEnabled(true);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        this.loaded = true;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (!this.loaded) {
            return;
        }

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        ActionBar actionBar = getSupportActionBar();


        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AccountFragment.newInstance())
                        .commit();
                actionBar.setTitle(R.string.title_section1);
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, BrowseFragment.newInstance())
                        .commit();
                actionBar.setTitle(R.string.title_section2);
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, FavoritesFragment.newInstance())
                        .commit();
                actionBar.setTitle(R.string.title_section3);
                break;
            default:
                actionBar.setTitle(mTitle);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
