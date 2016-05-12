package god.codename.brightside;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import in.srain.cube.views.GridViewWithHeaderAndFooter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CultureAndDesignFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "TabNumber";
    private static final String ARG_PARAM2 = "param2";

    private int TabNumber;
    private String TabName;

    public static CustomAdapter CultureAndDesignFragmentAdapter;
    CorePattern GetAdapter=new CorePattern();
    GridViewWithHeaderAndFooter newsView;
    Button LoadMore;
    int PositionToMaintain=-1;

    public CultureAndDesignFragment() {
        // Required empty public constructor
    }

    public static CultureAndDesignFragment newInstance(int param1, String param2) {
        CultureAndDesignFragment fragment = new CultureAndDesignFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TabNumber = getArguments().getInt(ARG_PARAM1);
            TabName = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rv=inflater.inflate(R.layout.fragment_the_title, container, false);

        newsView=(GridViewWithHeaderAndFooter)rv.findViewById(R.id.newsView) ;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View footerView = layoutInflater.inflate(R.layout.grid_footer, null);
        newsView.addFooterView(footerView);

        LoadMore=(Button)footerView.findViewById(R.id.loadMore);
        LoadMore.setText(R.string.load_culture);
        LoadMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Main.NextCallLink = new CorePattern().GetNextCallLink(HouseKeeping.ReflectionFile);
                View viewGroup = getActivity().findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(viewGroup, (CODES.ResponseCode(CODES.LOAD_MORE)), Snackbar.LENGTH_LONG);
                snackbar.show();
                new DownloadAndParseFile().execute(Main.NextCallLink);
            }
        });

        /**Set Colors and addition stuff**/
        SetColorsAndColumnNumber();
        /**End**/

        /**Click and long click handling**/
        newsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i=new Intent(getActivity(),DisplayNews.class);
                i.putExtra(QuickView.SOURCE,Main.CultureAndDesignAdapterArray.get(position).getSource());
                i.putExtra(QuickView.TITLE,Main.CultureAndDesignAdapterArray.get(position).getTitle());
                i.putExtra(QuickView.DATE,Main.CultureAndDesignAdapterArray.get(position).getDate());
                i.putExtra(QuickView.AUTHOR,Main.CultureAndDesignAdapterArray.get(position).getAuthor());
                i.putExtra(QuickView.FULLTEXT,Main.CultureAndDesignAdapterArray.get(position).getFullReview());
                i.putExtra(QuickView.IMAGEURL,Main.CultureAndDesignAdapterArray.get(position).getnewsImage());
                i.putExtra(QuickView.NEWSURL,Main.CultureAndDesignAdapterArray.get(position).getNewsLink());
                startActivity(i);
            }
        });

        newsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new ShareDialogViewer().ShowShareDialog(getActivity(),getContext(),Main.CultureAndDesignAdapterArray.get(position).getNewsLink(),Main.CultureAndDesignAdapterArray.get(position).getTitle(),"shared Via Science & Tech News","#S&T");
                return (true);
            }
        });

        /**Adapter get stuff**/
        Main.CultureAndDesignAdapterArray =GetAdapter.GetAdapterToSet(Main.CultureAndDesignAdapterArray,HouseKeeping.ReflectionFile,TabName);
        CultureAndDesignFragmentAdapter = new CustomAdapter(getContext(), Main.CultureAndDesignAdapterArray);
        newsView.setAdapter(CultureAndDesignFragmentAdapter);
        /**END**/

        return (rv);
    }


    private void SetColorsAndColumnNumber(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            newsView.setNumColumns(4);
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            newsView.setNumColumns(2);
        }

        if(TabNumber == 1 || TabNumber == 6) {
            newsView.setBackgroundColor(getResources().getColor(R.color.tab1_color));
        }
        if(TabNumber == 2 || TabNumber == 7) {
            newsView.setBackgroundColor(getResources().getColor(R.color.tab2_color));
        }
        if(TabNumber == 3 || TabNumber == 8) {
            newsView.setBackgroundColor(getResources().getColor(R.color.tab3_color));
        }
        if(TabNumber == 4 || TabNumber ==9) {
            newsView.setBackgroundColor(getResources().getColor(R.color.tab4_color));
        }
        if(TabNumber == 5 || TabNumber ==10) {
            newsView.setBackgroundColor(getResources().getColor(R.color.tab5_color));
        }
    }


    //UFD
    int ResponseCode =-1;
    int FileDownloadAPIStatusCode=0;
    String FileContents="";
    String FileOK="LoknathFileIsOkay";
    public class DownloadAndParseFile extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... DownloadLink) {
            if(DownloadLink[0].contains("NULL")){
                ResponseCode = CODES.NO_NEWS;
                return ("NULL");
            }
            try{
                Connection.Response resultImageResponse = Jsoup.connect(DownloadLink[0]).ignoreContentType(true).execute();
                FileDownloadAPIStatusCode=resultImageResponse.statusCode();
                if(FileDownloadAPIStatusCode==200){
                    FileContents=resultImageResponse.body();
                }else{
                    FileContents="";
                }
            }
            catch (IOException e){
                FileDownloadAPIStatusCode=-1;
                if(e instanceof SocketTimeoutException) {
                    ResponseCode = CODES.TIMEOUT;
                }
                else if(e instanceof MalformedURLException){
                    ResponseCode = CODES.MALFORMED_URL;
                }
                else if(e instanceof HttpStatusException){
                    ResponseCode = CODES.HTTP_STATUS;
                }
                else if(e instanceof UnsupportedMimeTypeException){
                    ResponseCode = CODES.UNSUPPORTED_MIME;
                }
                else
                    ResponseCode = CODES.IO_EXP;
            }
            return "NULL";
        }
        @Override
        protected void onPostExecute(String result) {
            if(FileContents.contains(FileOK) && (CODES.ResponseCode(ResponseCode)).contains(HouseKeeping.HEALTH_OK)){
                HouseKeeping.ReflectionFile.clear();
                FileContents = FileContents.replace(FileOK,"");
                HouseKeeping.ReflectionFile = new CorePattern().GetFileContents(FileContents,getContext());
                new CorePattern().UpdateAllAdaptersArrays(HouseKeeping.ReflectionFile);
                CultureAndDesignFragmentAdapter = new CustomAdapter(getContext(), Main.MobileAndGearAdapterArray);
                CultureAndDesignFragmentAdapter.notifyDataSetChanged();
                PositionToMaintain=newsView.getFirstVisiblePosition();
                newsView.setAdapter(CultureAndDesignFragmentAdapter);
                newsView.smoothScrollToPosition(PositionToMaintain);
            }else{
                View viewGroup = getActivity().findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(viewGroup, (CODES.ResponseCode(ResponseCode)), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }
    }
}