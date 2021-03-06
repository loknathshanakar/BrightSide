package god.codename.brightside;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


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
    private String Image_1_Desc;
    private float FontSize=0;

    public int NumberOfImages=0,NumberOfImageSubDesc=0;

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

        /**Set font sizes**/
        FontSize=new SharedPreferenceRW().SharedPrefrenceReaderFloat(SharedPreferenceRW.FONTSIZE,18f,getContext());
        Content_2_TextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,FontSize);
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

        /**Get and set unknown contents**/
        String s=FullText;
        try {
            Image_1_Desc = s.substring(s.indexOf("<imagedescx>") + "<imagedescx>".length(), s.indexOf("</imagedescx>"));
        }catch (IndexOutOfBoundsException e){/***/}
        FullText=FullText.replace("<imagedescx>"+Image_1_Desc+"</imagedescx>","");
        Glide.with(getActivity()).load(ImageURL).diskCacheStrategy(DiskCacheStrategy.ALL).into(ImageView_1);
        Image_1_Desc_TextView.setText(Image_1_Desc);
        Content_2_TextView.setText(Html.fromHtml(FullText.replace("$$$$","\r\n")));

        return(rv);
    }

    public int GetNumberOfImages(String fullText){
        //TODO:i made single image and full text for now
        int NoOfImages=0;
        int LastPosition=0;
        for(int i=0;i<=4;i++){
            if(FullText.indexOf("%%%%",LastPosition)>LastPosition){
                LastPosition=LastPosition+4;
                NoOfImages++;
            }
        }
        return (NoOfImages);
    }

    public int GetNumberOfImageSubDesc(String fullText){
        int NoOfImagesDesc=0;
        int LastPosition=0;
        for(int i=0;i<=4;i++){
            if(FullText.indexOf("@@@@@",LastPosition)>LastPosition){
                LastPosition=LastPosition+4;
                NoOfImagesDesc++;
            }
        }
        return (NoOfImagesDesc/2);
    }

    public void DisableImageViews(int numbers){
        if(numbers==0)
            return;
        if(numbers==1){
            ImageView_3.setVisibility(View.GONE);
        }
        if(numbers==2){
            ImageView_3.setVisibility(View.GONE);
            ImageView_2.setVisibility(View.GONE);
        }
        if(numbers==3){
            ImageView_3.setVisibility(View.GONE);
            ImageView_2.setVisibility(View.GONE);
            ImageView_1.setVisibility(View.GONE);
        }
    }

    public void DisableImageViewDesc(int numbers){
        if(numbers==0)
            return;
        if(numbers==1){
            Image_3_Desc_TextView.setVisibility(View.GONE);
        }
        if(numbers==2){
            Image_3_Desc_TextView.setVisibility(View.GONE);
            Image_2_Desc_TextView.setVisibility(View.GONE);
        }
        if(numbers==3){
            Image_3_Desc_TextView.setVisibility(View.GONE);
            Image_2_Desc_TextView.setVisibility(View.GONE);
            Image_1_Desc_TextView.setVisibility(View.GONE);
        }
    }
}
