package boat.golden.outlife;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import boat.golden.outlife.fragments.home_page;
import boat.golden.outlife.fragments.profile;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment=new home_page();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        Boolean islogin=sharedPreferences.getBoolean("IsLogin",false);
        if (!islogin)
        {
        Intent i=new Intent(this,Login_main.class);
        //startActivity(i);
             }








        mDrawerLayout = findViewById(R.id.drawerlayout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        int id=menuItem.getItemId();
                        if (id==R.id.home)
                        {
                            fragment=new home_page();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                        }
                            else if (id==R.id.profie)
                        {
                            fragment=new profile();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                        }

                        return true;
                    }
                });






    }
}
