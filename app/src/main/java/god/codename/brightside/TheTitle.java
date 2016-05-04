package god.codename.brightside;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TheTitle extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "TabNumber";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int TabNumber;
    private String mParam2;

    TextView tv;
    GridView newsView;

    public TheTitle() {
        // Required empty public constructor
    }

    public static TheTitle newInstance(int param1, String param2) {
        TheTitle fragment = new TheTitle();
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
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rv=inflater.inflate(R.layout.fragment_the_title, container, false);

        newsView=(GridView)rv.findViewById(R.id.newsView) ;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            newsView.setNumColumns(4);
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            newsView.setNumColumns(2);
        }

        if(TabNumber == 1 || TabNumber == 6)
            newsView.setBackgroundColor(getResources().getColor(R.color.tab1_color));
        if(TabNumber == 2 || TabNumber == 7)
            newsView.setBackgroundColor(getResources().getColor(R.color.tab2_color));
        if(TabNumber == 3 || TabNumber == 8)
            newsView.setBackgroundColor(getResources().getColor(R.color.tab3_color));
        if(TabNumber == 4)
            newsView.setBackgroundColor(getResources().getColor(R.color.tab4_color));
        if(TabNumber == 5)
            newsView.setBackgroundColor(getResources().getColor(R.color.tab5_color));
        CustomAdapter customAdapter = new CustomAdapter(getContext(), getListItemData());
        newsView.setAdapter(customAdapter);

        return (rv);
    }
    private List<ItemObject> getListItemData(){
        List<ItemObject> listViewItems = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        for(int i=0;i<100;i++) {
                listViewItems.add(new ItemObject("Title 1",formattedDate,"The Title","NULL"));
        }

        return listViewItems;
    }
}
