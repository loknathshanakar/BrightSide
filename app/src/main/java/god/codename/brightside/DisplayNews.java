package god.codename.brightside;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View News");
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
