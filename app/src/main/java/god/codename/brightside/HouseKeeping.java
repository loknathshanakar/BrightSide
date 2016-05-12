package god.codename.brightside;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class HouseKeeping extends AppCompatActivity {

    Window window;
    /**Column names and index**/
    public static int TITLE=0;
    public static int SUMMARY=1;
    public static int CATEGORY=2;
    public static int SOURCE=3;
    public static int DATE=4;
    public static int NEWSIMAGE=5;
    public static int FULLTEXT=6;
    public static int NEWSID=7;
    public static int NEXTCALL=8;
    public static int NEWSLINK=9;
    public static int AUTHOR=10;
    public static int FAV=11;

    public static String TITLES="TITLE";
    public static String SUMMARYS="SUMMARY";
    public static String CATEGORYS="CATEGORY";
    public static String SOURCES="SOURCE";
    public static String DATES="DATE";
    public static String NEWSIMAGES="NEWSIMAGE";
    public static String FULLTEXTS="FULLTEXT";
    public static String NEWSIDS="NEWSID";
    public static String NEXTCALLS="NEXTCALL";
    public static String NEWSLINKS="NEWSLINK";
    public static String AUTHORS="AUTHOR";
    public static String FAVS="FAVS";

    public static final String col[] = {TITLES, SUMMARYS, CATEGORYS, SOURCES, DATES, NEWSIMAGES, FULLTEXTS, NEWSIDS, NEXTCALLS, NEWSLINKS, AUTHORS, FAVS};
    Context context;
    SharedPreferenceRW SPRW;
    TextView FeedbackTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_keeping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FeedbackTextView = (TextView)findViewById(R.id.serverresults);
        setSupportActionBar(toolbar);
        context=this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.nav_drawer_color));
        }
        SPRW=new SharedPreferenceRW();

        DBHelper db=new DBHelper(context);
        if(db.getNewsCount()>10){
            FeedbackTextView.setText(R.string.starting_app);
            ReflectionFile=db.getAllNews();
            new CountDownTimer(100,1000){
                public void onTick(long millisUntilFinished) {

                }
                public void onFinish(){
                    startActivity(new Intent(HouseKeeping.this,Main.class));
                    finish();
                    return;
                }
            }.start();
        }else{
            FeedbackTextView.setText("Connecting to primary server\r\n");
            new CallPrimaryAPI().execute();
        }
    }
    /**User Definded Functions Starts**/
    public static int TimeOut=1000;
    public static String PrimaryAPI="http://demo8304354.mockable.io/destination";
    public static String PrimaryAPIResponse="";
    public static String PrimaryAPIToken="";
    public static String PrimaryFileLink="";
    public static String primaryAPIHealth="";
    public static String HEALTH_OK="HEALTH_OK";
    public static int PrimaryAPIStatusCode=-1;
    public class CallPrimaryAPI extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            try{
                Connection.Response returnedDocument= Jsoup.connect(PrimaryAPI).ignoreContentType(true).timeout(TimeOut).execute();
                PrimaryAPIStatusCode=returnedDocument.statusCode();
                if(PrimaryAPIStatusCode==200){
                    PrimaryAPIResponse=returnedDocument.body();
                }else{
                    PrimaryAPIResponse="";
                }
            }
            catch (IOException e){
                if(e instanceof SocketTimeoutException) {
                    PrimaryAPIStatusCode = CODES.TIMEOUT;
                }
                else if(e instanceof MalformedURLException){
                    PrimaryAPIStatusCode = CODES.MALFORMED_URL;
                }
                else if(e instanceof HttpStatusException){
                    PrimaryAPIStatusCode = CODES.HTTP_STATUS;
                }
                else if(e instanceof UnsupportedMimeTypeException){
                    PrimaryAPIStatusCode = CODES.UNSUPPORTED_MIME;
                }
                else
                    PrimaryAPIStatusCode = CODES.IO_EXP;
            }
            return "null";
        }
        @Override
        protected void onPostExecute(String result) {
            if(PrimaryAPIStatusCode==200){
                boolean allOK=GetFileLinkAndToken(PrimaryAPIResponse);
                if(allOK){
                    FeedbackTextView.setText("Connecting to Secondary server\r\n");
                    new DownloadAndParseFile().execute(PrimaryFileLink);
                }else{
                    PrimaryAPIStatusCode=CODES.INVALID_TOKEN;
                    FeedbackTextView.setText("Primary API Health Check : "+ CODES.ResponseCode(PrimaryAPIStatusCode)+"\r\n");
                }
            }else{
                FeedbackTextView.setText(R.string.exit_notice);
                new CountDownTimer(1000,5000){
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish(){
                        finish();
                        return;
                    }
                }.start();
            }

        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... progress) {
        }
    }

    /** Download and parse file in here**/
    public static int FileDownloadAPIStatusCode=0;
    public static String FileContents="";
    public static String FileOK="LoknathFileIsOkay";
    public static ArrayList<BasicModel> ReflectionFile=new ArrayList<>();
    public class DownloadAndParseFile extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... DownloadLink) {
            try{
                Connection.Response resultImageResponse = Jsoup.connect(DownloadLink[0]).timeout(TimeOut).ignoreContentType(true).execute();
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
                    FileDownloadAPIStatusCode = CODES.TIMEOUT;
                }
                else if(e instanceof MalformedURLException){
                    FileDownloadAPIStatusCode = CODES.MALFORMED_URL;
                }
                else if(e instanceof HttpStatusException){
                    FileDownloadAPIStatusCode = CODES.HTTP_STATUS;
                }
                else if(e instanceof UnsupportedMimeTypeException){
                    FileDownloadAPIStatusCode = CODES.UNSUPPORTED_MIME;
                }
                else
                    FileDownloadAPIStatusCode = CODES.IO_EXP;
            }
            return "null";
        }
        @Override
        protected void onPostExecute(String result) {
            if(FileContents.contains(FileOK)){
                FileContents = FileContents.replace(FileOK,"");
                ReflectionFile = new CorePattern().GetFileContents(FileContents,context);
                if(ReflectionFile.size()>10){
                    FeedbackTextView.setText("File Ok,Starting APP\r\n");
                    SPRW.SharedPreferenceWriter(SharedPreferenceRW.TOKEN,HouseKeeping.PrimaryAPIToken,context);
                    startActivity(new Intent(HouseKeeping.this,Main.class));
                    finish();
                }else{
                    FeedbackTextView.setText(R.string.exit_notice);
                    new CountDownTimer(1000,5000){
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish(){
                            finish();
                            return;
                        }
                    }.start();
                }
            }
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }
    }

    private boolean GetFileLinkAndToken(String PrimaryAPIResponse){
        try {
            JSONObject jsonRootObject = new JSONObject(PrimaryAPIResponse).getJSONObject("primaryData");
            PrimaryFileLink=jsonRootObject.getString("fileurl");
            PrimaryAPIToken=jsonRootObject.getString("token");
            primaryAPIHealth=jsonRootObject.getString("apiHealth");
            if(primaryAPIHealth.compareTo(HEALTH_OK)==0)
                return (true);
            else {
                PrimaryAPIStatusCode=-1;
                return (false);
            }
        } catch (JSONException e) {
            return (false);
        }
    }
}
