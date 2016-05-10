package god.codename.brightside;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class FullView extends Fragment {

    private String Source;
    private String Title;
    private String Date;
    private String Author;
    private String FullText;
    private String ImageURL;
    private String NewsURL;


    private static float mDownX;
    private static float mDownY;
    static boolean isOnClick;
    private final static float SCROLL_THRESHOLD = 10;
    WebView mWebView;
    public FullView() {
        // Required empty public constructor
    }

    public static FullView newInstance(String Source, String Title,String Date,String Author,String FullText,String ImageURL,String NewsURL) {
        FullView fragment = new FullView();
        Bundle args = new Bundle();
        args.putString(QuickView.SOURCE, Source);
        args.putString(QuickView.TITLE, Title);
        args.putString(QuickView.DATE, Date);
        args.putString(QuickView.AUTHOR, Author);
        args.putString(QuickView.FULLTEXT, FullText);
        args.putString(QuickView.IMAGEURL, ImageURL);
        args.putString(QuickView.NEWSURL, NewsURL);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Source = getArguments().getString(QuickView.SOURCE);
            Title = getArguments().getString(QuickView.TITLE);
            Date = getArguments().getString(QuickView.DATE);
            Author = getArguments().getString(QuickView.AUTHOR);
            FullText = getArguments().getString(QuickView.FULLTEXT);
            ImageURL = getArguments().getString(QuickView.IMAGEURL);
            NewsURL = getArguments().getString(QuickView.NEWSURL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rv=inflater.inflate(R.layout.fragment_full_view, container, false);
        mWebView = (WebView) rv.findViewById(R.id.webView);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.loadUrl(NewsURL);
            mWebView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent ev) {
                    WebView.HitTestResult hr = ((WebView) v).getHitTestResult();
                    switch (ev.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            mDownX = ev.getX();
                            mDownY = ev.getY();
                            isOnClick = true;
                            break;
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            if (isOnClick && hr != null){
                                if (hr.getType() == WebView.HitTestResult.IMAGE_TYPE){}
                                //new ImageViewer().setURI(hr.getExtra()).show(getFragmentManager(), "#show");
                                return true;
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (isOnClick && (Math.abs(mDownX - ev.getX()) > SCROLL_THRESHOLD
                                    || Math.abs(mDownY - ev.getY()) > SCROLL_THRESHOLD)) {
                                isOnClick = false;
                            }
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
        return (rv);
    }
}
