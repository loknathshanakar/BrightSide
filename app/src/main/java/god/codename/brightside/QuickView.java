package god.codename.brightside;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuickView extends Fragment {

    public static final String SOURCE = "SOURCE";
    public static final String TITLE = "TITLE";
    public static final String DATE = "DATE";
    public static final String AUTHOR = "AUTHOR";
    public static final String FULLTEXT = "FULLTEXT";
    public static final String IMAGEURL = "IMAGEURL";
    public static final String NEWSURL = "NEWSURL";

    private String Source;
    private String Title;
    private String Date;
    private String Author;
    private String FullText;
    private String ImageURL;
    private String NewsURL;

    public QuickView() {
        // Required empty public constructor
    }

    public static QuickView newInstance(String Source, String Title,String Date,String Author,String FullText,String ImageURL,String NewsURL) {
        QuickView fragment = new QuickView();
        Bundle args = new Bundle();
        args.putString(SOURCE, Source);
        args.putString(TITLE, Title);
        args.putString(DATE, Date);
        args.putString(AUTHOR, Author);
        args.putString(FULLTEXT, FullText);
        args.putString(IMAGEURL, ImageURL);
        args.putString(NEWSURL, NewsURL);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Source = getArguments().getString(SOURCE);
            Title = getArguments().getString(TITLE);
            Date = getArguments().getString(DATE);
            Author = getArguments().getString(AUTHOR);
            FullText = getArguments().getString(FULLTEXT);
            ImageURL = getArguments().getString(IMAGEURL);
            NewsURL = getArguments().getString(NEWSURL);
        }
    }

    TextView SourceTextView,TitleTextView,DateTextView,AuthorTextView,Content_1_TextView,Content_2_TextView,Content_3_TextView,Content_4_TextView,Image_1_Desc_TextView,Image_2_Desc_TextView,Image_3_Desc_TextView,Disclaimer_TextView;
    ImageView ImageView_1,ImageView_2,ImageView_3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rv=inflater.inflate(R.layout.quick_watch, container, false);
        /**Lets Get All Ids first**/
        SourceTextView=(TextView)rv.findViewById(R.id.source);
        TitleTextView=(TextView)rv.findViewById(R.id.title);
        DateTextView=(TextView)rv.findViewById(R.id.date);
        AuthorTextView=(TextView)rv.findViewById(R.id.author);
        Content_1_TextView=(TextView)rv.findViewById(R.id.partContent1);
        Content_2_TextView=(TextView)rv.findViewById(R.id.partContent2);
        Content_3_TextView=(TextView)rv.findViewById(R.id.partContent3);
        Content_4_TextView=(TextView)rv.findViewById(R.id.partContent4);
        Image_1_Desc_TextView=(TextView)rv.findViewById(R.id.image1_desc);
        Image_2_Desc_TextView=(TextView)rv.findViewById(R.id.image2_desc);
        Image_3_Desc_TextView=(TextView)rv.findViewById(R.id.image3_desc);
        Disclaimer_TextView=(TextView)rv.findViewById(R.id.discmailer);
        ImageView_1=(ImageView)rv.findViewById(R.id.image1);
        ImageView_2=(ImageView)rv.findViewById(R.id.image2);
        ImageView_3=(ImageView)rv.findViewById(R.id.image3);
        /**END**/

        /**Set all know contents**/
        SourceTextView.setText(Source);
        TitleTextView.setText(Title);
        DateTextView.setText(Date);
        if(!Author.contains("by"))
            Author="by "+Author;
        AuthorTextView.setText(Author);
        Disclaimer_TextView.setText(R.string.disclaimer);
        /**END**/
        return(rv);
    }

}
