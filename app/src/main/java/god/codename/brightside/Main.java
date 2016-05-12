package god.codename.brightside;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
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
        TabNames.add(getResources().getString(R.string.the_daily));
        TabNames.add(getResources().getString(R.string.Computing));
        TabNames.add(getResources().getString(R.string.Internet));
        TabNames.add(getResources().getString(R.string.Mobile));
        TabNames.add(getResources().getString(R.string.Business));
        TabNames.add(getResources().getString(R.string.Security));
        TabNames.add(getResources().getString(R.string.Robotics));
        TabNames.add(getResources().getString(R.string.Culture));
        TabNames.add(getResources().getString(R.string.Science));
        TabNames.add(getResources().getString(R.string.Energy));
        TabNames.add(getResources().getString(R.string.Favorites));

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

        /**Drawer stuff**/
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        viewGroup = findViewById(android.R.id.content);
        mDrawerList = (ListView) findViewById(R.id.nav_drawer_list);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View headerView = layoutInflater.inflate(R.layout.nav_header_main, null);
        mDrawerList.addHeaderView(headerView);
        ArrayList <NavDrawerModel> DrawerArrayList=new ArrayList<>();
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(10),R.drawable.fav_drawer,10));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(0),R.drawable.top_news,0));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(1),R.drawable.computing,1));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(2),R.drawable.internet_news,2));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(3),R.drawable.mobileandgear,3));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(4),R.drawable.business,4));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(5),R.drawable.security_news,5));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(6),R.drawable.robotics,6));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(7),R.drawable.design,7));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(8),R.drawable.science,8));
        DrawerArrayList.add(new NavDrawerModel(TabNames.get(9),R.drawable.energy,9));
        DrawerArrayList.add(new NavDrawerModel("Settings",R.drawable.ic_action_settings,11));
        DrawerArrayList.add(new NavDrawerModel("Share",R.drawable.share,12));
        DrawerArrayList.add(new NavDrawerModel("Feedback",R.drawable.feedback,13));
        DrawerArrayList.add(new NavDrawerModel("About",R.drawable.about,14));
        adapter = new CustomDrawerAdapter(this, R.layout.drawer_list_single_item, DrawerArrayList,this);
        mDrawerList.setAdapter(adapter);
    }

    View viewGroup;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    ListView mDrawerList;
    CustomDrawerAdapter adapter;
    public void onItemClick(int mPosition) {
        if(mPosition>=0) {
            if (mPosition == 0) {
                drawer.closeDrawers();
                Intent i=new Intent(Main.this,FavoritesActivity.class);
                startActivity(i);
            }else if(mPosition==11){
                drawer.closeDrawers();
                Snackbar snackbar = Snackbar.make(viewGroup, "Settings coming soon!!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }else if(mPosition==12){
                drawer.closeDrawers();
                new ShareDialogViewer().ShowShareDialog(Main.this,context,getResources().getString(R.string.play_store_link),"Hey check out this news app","News application wich updates you on \""+getResources().getString(R.string.tagline)+"\"","");
            }else if(mPosition==13){
                drawer.closeDrawers();
                Snackbar snackbar = Snackbar.make(viewGroup, "Feedback coming soon!!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }else if(mPosition==14){
                LayoutInflater linf = LayoutInflater.from(context);
                View inflator = linf.inflate(R.layout.nav_header_main, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                builder.setView(inflator).show();
            }
            else{
                mPager.setCurrentItem(mPosition - 1);
                drawer.closeDrawers();
            }
        }
    }
    DBHelper db;
    CorePattern GetDetails=new CorePattern();
    Tracker mTracker;
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Main.NextCallLink = GetDetails.GetNextCallLink(HouseKeeping.ReflectionFile);

        /**Track Application**/
        AnalyticsApplication application_new = (AnalyticsApplication) getApplication();
        mTracker = application_new.getDefaultTracker();
        mTracker.setScreenName("App main screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        /**Reset Long Clicker**/

        new BrightnessHandler().RestoreBrightNess(context,Main.this);
    }


    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent i = context.getPackageManager().getLaunchIntentForPackage( context.getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar snackbar = Snackbar.make(viewGroup, "Please click BACK again to exit", Snackbar.LENGTH_LONG);
        snackbar.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
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
                Intent i=new Intent(Main.this,FavoritesActivity.class);
                startActivity(i);
            case R.id.settings:
                Snackbar snackbar = Snackbar.make(viewGroup, "Settings coming soon!!", Snackbar.LENGTH_LONG);
                snackbar.show();
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
