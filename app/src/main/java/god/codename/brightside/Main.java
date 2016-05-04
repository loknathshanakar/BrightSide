package god.codename.brightside;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static int TAB_COUNT=8;
    ViewPager mPager=null;
    SmartTabLayout viewPagerTab=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**VIEWPAGER**/
        MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setAdapter(mAdapter);


        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(mPager);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position==0) {
                    viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab1_color));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab1_color)));
                }
                if(position==1) {
                    viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab2_color));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab2_color)));
                }
                if(position==2) {
                    viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab3_color));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab3_color)));
                }
                if(position==3) {
                    viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab4_color));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab4_color)));
                }
                if(position==4) {
                    viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab5_color));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab5_color)));
                }
                if(position==5) {
                    viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab1_color));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab1_color)));
                }
                if(position==6) {
                    viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab2_color));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab2_color)));
                }
                if(position==7) {
                    viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab3_color));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab3_color)));
                }
                if(position==8) {
                    viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab4_color));
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab4_color)));
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        /**END**/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return TheTitle.newInstance(1, "2");
            } else if (position == 1) {
                return BlankFragment.newInstance(2, "2");
            } else if (position == 2) {
                return BlankFragment.newInstance(3, "2");
            } else if (position == 3) {
                return BlankFragment.newInstance(4, "2");
            } else if (position == 4) {
                return BlankFragment.newInstance(5, "2");
            }else if (position == 5) {
                return BlankFragment.newInstance(6, "2");
            }else if (position == 6) {
                return BlankFragment.newInstance(7, "2");
            }else {
                return BlankFragment.newInstance(8, "2");
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return ("The Daily");
            else if(position==1)
                return ("Computing");
            else if(position==2)
                return ("Mobile");
            else if(position==3)
                return ("Robotics");
            else if(position==4)
                return ("Business");
            else if(position==5)
                return ("Business");
            else if(position==6)
                return("Bio Medicine");
            else
                return("Energy");
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
