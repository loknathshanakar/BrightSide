package god.codename.brightside;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

public class FavoritesActivity extends AppCompatActivity {

    Context context;
    GridViewWithHeaderAndFooter newsView;
    public static CustomAdapter favAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_av);
        context=this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favorites");
        viewGroup = findViewById(android.R.id.content);
        /**Adapter get stuff**/
        newsView=(GridViewWithHeaderAndFooter)findViewById(R.id.newsView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.tab2_color));
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            newsView.setNumColumns(4);
            newsView.setBackgroundColor(getResources().getColor(R.color.tab2_color));
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            newsView.setNumColumns(2);
            newsView.setBackgroundColor(getResources().getColor(R.color.tab2_color));
        }
        Main.FavAdapterArray = new CorePattern().GetFavList(HouseKeeping.ReflectionFile);
        if(Main.FavAdapterArray.size()>0) {
            favAdapter = new CustomAdapter(context, Main.FavAdapterArray);
            newsView.setAdapter(favAdapter);
        }else{
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View headerView = layoutInflater.inflate(R.layout.text_box, null);
            newsView.addHeaderView(headerView);
            TextView TV=(TextView) headerView.findViewById(R.id.feedbackTextView);
            TV.setText(R.string.no_favs);
            favAdapter = new CustomAdapter(context, Main.FavAdapterArray);
            newsView.setAdapter(favAdapter);
        }
        /**END**/

        /**Click and long click handling**/
        newsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(context,"Position "+position,Toast.LENGTH_LONG).show();
            }
        });

        newsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new ShareDialogViewer().ShowShareDialog(FavoritesActivity.this,context,Main.FavAdapterArray.get(position).getNewsLink(),Main.FavAdapterArray.get(position).getTitle(),"shared Via Science & Tech News","#S&T");
                return (true);
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_fav, menu);
        return true;
    }
    View viewGroup;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_brightness:
                return (new BrightnessHandler().BrightnessHandler(context, this));
            case R.id.settings:
                Snackbar snackbar = Snackbar.make(viewGroup, "Settings coming soon!!", Snackbar.LENGTH_LONG);
                snackbar.show();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
