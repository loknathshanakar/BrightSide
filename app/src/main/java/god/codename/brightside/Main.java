package god.codename.brightside;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /** AdapterArrays for each fragment**/
    public static ArrayList <BasicModel> TheTitleAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> ComutingAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> InternetAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> MobileAndGearAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> BusinessAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> SecurityAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> RoboticsAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> CultureAndDesignAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> ScienceAndBioAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> EnergyAndTransportAdapterArray =new ArrayList<>();
    public static ArrayList <BasicModel> FavAdapterArray =new ArrayList<>();

    public static int TAB_COUNT=10;
    public static String NextCallLink="NULL";
    ViewPager mPager=null;
    SmartTabLayout viewPagerTab=null;
    Window window=null;
    public static ArrayList <String>TabNames=new ArrayList<>();
    public static boolean RedrawAdapter=false;
    Context context;
    public static MyAdapter ViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;
        db=new DBHelper(context);
        TabNames.add("The Daily");
        TabNames.add("Computing");
        TabNames.add("Internet");
        TabNames.add("Mobile & Gear");
        TabNames.add("Business");
        TabNames.add("Security");
        TabNames.add("Robotics");
        TabNames.add("Culture & Design");
        TabNames.add("Science & Biomedicine");
        TabNames.add("Energy & Transport");
        TabNames.add("Null");

        /**SOme aAction Bar Setup**/
        getSupportActionBar().setTitle("News & Reviews");
        /**VIEWPAGER**/
        ViewPagerAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setAdapter(ViewPagerAdapter);
        mPager.setOffscreenPageLimit(5);

        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(mPager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        SetTabAndStatusBarColors(0,window);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                SetTabAndStatusBarColors(position,window);
                if(RedrawAdapter) {
                    ViewPagerAdapter.notifyDataSetChanged();
                    RedrawAdapter=false;
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

    DBHelper db;
    CorePattern GetDetails=new CorePattern();
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Main.NextCallLink = GetDetails.GetNextCallLink(HouseKeeping.ReflectionFile);
    }
    public void SetTabAndStatusBarColors(int position,Window window){
        if(position==0) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab1_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab1_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab1_color));
        }
        if(position==1) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab2_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab2_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab2_color));
        }
        if(position==2) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab3_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab3_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab3_color));
        }
        if(position==3) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab4_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab4_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab4_color));
        }
        if(position==4) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab5_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab5_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab5_color));
        }
        if(position==5) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab1_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab1_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab1_color));
        }
        if(position==6) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab2_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab2_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab2_color));
        }
        if(position==7) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab3_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab3_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab3_color));
        }
        if(position==8) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab4_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab4_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab4_color));
        }
        if(position==9) {
            viewPagerTab.setBackgroundColor(getResources().getColor(R.color.tab5_color));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tab5_color)));
            if(window!=null)
                window.setStatusBarColor(getResources().getColor(R.color.tab5_color));
        }
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return TheTitleFragment.newInstance(position+1, TabNames.get(position));
            } else if (position == 1) {
                return ComputingFragment.newInstance(position+1, TabNames.get(position));
            } else if (position == 2) {
                return InternetFragment.newInstance(position+1, TabNames.get(position));
            } else if (position == 3) {
                return MobileAndGearFragment.newInstance(position+1, TabNames.get(position));
            } else if (position == 4) {
                return BusinessFragment.newInstance(position+1, TabNames.get(position));
            }else if (position == 5) {
                return SecurityFragment.newInstance(position+1, TabNames.get(position));
            }else if (position == 6) {
                return RoboticsFragment.newInstance(position+1, TabNames.get(position));
            }else if (position == 7) {
                return CultureAndDesignFragment.newInstance(position+1, TabNames.get(position));
            }else if (position == 8) {
                return ScienceAndBioMedicineFragment.newInstance(position+1, TabNames.get(position));
            }else if (position == 9) {
                return EnergyAndTransportFragment.newInstance(position+1, TabNames.get(position));
            }else
                return BlankFragment.newInstance(10, TabNames.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return (TabNames.get(position));
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
        switch (item.getItemId()) {
            case R.id.action_brightness:
                return (new BrightnessHandler().BrightnessHandler(context, this));

            case R.id.open_fav:
                Intent i=new Intent(Main.this,av.class);
                startActivity(i);
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
