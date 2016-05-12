package god.codename.brightside;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class DisplayNews extends AppCompatActivity {
    ViewPager mPager=null;
    SmartTabLayout viewPagerTab=null;
    Window window=null;
    Context context;
    public static MyAdapter ViewPagerAdapter;


    public static String Source;
    public static String Title;
    public static String Date;
    public static String Author;
    public static String FullText;
    public static String ImageURL;
    public static String NewsURL;

//TODO:Back on action bar not working
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View News");
        viewGroup=findViewById(android.R.id.content);
        context=this;
        /**Get passed parameters**/
        Bundle extras = getIntent().getExtras();
        Source=extras.getString(QuickView.SOURCE,"NULL");
        Title=extras.getString(QuickView.TITLE,"NULL");
        Date=extras.getString(QuickView.DATE,"NULL");
        Author=extras.getString(QuickView.AUTHOR,"NULL");
        FullText=extras.getString(QuickView.FULLTEXT,"NULL");
        ImageURL=extras.getString(QuickView.IMAGEURL,"NULL");
        NewsURL=extras.getString(QuickView.NEWSURL,"NULL");
        /**END**/
        /**View pager Related stuff**/
        ViewPagerAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setAdapter(ViewPagerAdapter);

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
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        /**END**/

    }

    Tracker mTracker;
    @Override
    public void onResume() {
        super.onResume();
        /**Track Application**/
        AnalyticsApplication application_new = (AnalyticsApplication) getApplication();
        mTracker = application_new.getDefaultTracker();
        mTracker.setScreenName("Read news screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        /**Reset Long Clicker**/

        new BrightnessHandler().RestoreBrightNess(context,DisplayNews.this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return(true);
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_news, menu);
        return true;
    }

    View viewGroup;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        float FontSize;
        switch (item.getItemId()) {
            case R.id.action_brightness:
                return (new BrightnessHandler().BrightnessHandler(context, this));

            case R.id.settings:
                Snackbar snackbar = Snackbar.make(viewGroup, "Settings coming soon!!", Snackbar.LENGTH_LONG);
                snackbar.show();
                break;
            case R.id.font_down:
                if(mPager.getCurrentItem()==0) {
                    FontSize = new SharedPreferenceRW().SharedPrefrenceReaderFloat(SharedPreferenceRW.FONTSIZE, 18f, context);
                    new SharedPreferenceRW().SharedPreferenceWriterFloat(SharedPreferenceRW.FONTSIZE, FontSize - 1, context);
                    ViewPagerAdapter = new MyAdapter(getSupportFragmentManager());
                    mPager.setAdapter(ViewPagerAdapter);
                }
                break;

            case R.id.font_up:
                if(mPager.getCurrentItem()==0) {
                    FontSize = new SharedPreferenceRW().SharedPrefrenceReaderFloat(SharedPreferenceRW.FONTSIZE, 18f, context);
                    new SharedPreferenceRW().SharedPreferenceWriterFloat(SharedPreferenceRW.FONTSIZE, FontSize + 1, context);
                    ViewPagerAdapter = new MyAdapter(getSupportFragmentManager());
                    mPager.setAdapter(ViewPagerAdapter);
                }
                break;
            case android.R.id.home:
                finish();
                return(true);
        default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
        return (true);
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
            return 2;
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return QuickView.newInstance(Source,Title,Date,Author,FullText,ImageURL,NewsURL);
            } else if (position == 1) {
                return FullView.newInstance(Source,Title,Date,Author,FullText,ImageURL,NewsURL);
            } else
                return BlankFragment.newInstance(10,"");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
                return ("Quick View");
            else if(position==1)
                    return ("Full View");
            else return ("This shouldnt be here");
        }
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
}
