package god.codename.brightside;

/**
 * Created by lokanath on 5/4/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private ArrayList<BasicModel> listStorage;
    private Context context;
    public CustomAdapter(Context context, ArrayList<BasicModel> customizedListView) {
        this.context = context;
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.single_news_item, parent, false);
            listViewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.newsImage);
            listViewHolder.source = (TextView) convertView.findViewById(R.id.category);
            listViewHolder.title = (TextView) convertView.findViewById(R.id.title);
            listViewHolder.date = (TextView) convertView.findViewById(R.id.date);
            listViewHolder.bookmark =(ImageView) convertView.findViewById(R.id.bookmark);
            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(listStorage.get(position).getnewsImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .placeholder(R.drawable.tech_placeholder)
                .error(R.drawable.tech_placeholder)
                .into(listViewHolder.thumbnail);
        listViewHolder.source.setText(listStorage.get(position).getCategory());
        listViewHolder.title.setText(listStorage.get(position).getTitle());
        listViewHolder.date.setText(listStorage.get(position).getDate());

        if(listStorage.get(position).getFav().compareTo("false")==0){
            listViewHolder.bookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_bookmark_unfilled));
        }else{
            listViewHolder.bookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_bookmark_filled));
        }

        listViewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBHelper db=new DBHelper(context);
                ArrayList <BasicModel> ReflectionFile=db.getAllNews();
                String favStatus=GetFavStatusBasedOnID(listStorage.get(position).getNewsID(),ReflectionFile);
                if(favStatus.contains("false")) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(HouseKeeping.FAVS, "true");
                    db.UpdateNews(contentValues, listStorage.get(position).getNewsID());
                    listViewHolder.bookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_bookmark_filled));
                    HouseKeeping.ReflectionFile = db.getAllNews();
                    new CorePattern().UpdateAllAdaptersArrays(HouseKeeping.ReflectionFile);
                }
                if(favStatus.contains("true")) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(HouseKeeping.FAVS, "false");
                    db.UpdateNews(contentValues, listStorage.get(position).getNewsID());
                    listViewHolder.bookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_bookmark_unfilled));
                    HouseKeeping.ReflectionFile = db.getAllNews();
                    new CorePattern().UpdateAllAdaptersArrays(HouseKeeping.ReflectionFile);
                }
            }
        });
        return convertView;
    }

    public String GetFavStatusBasedOnID(String newsID,ArrayList <BasicModel>ReflectionFile){
        for(int i=0;i<ReflectionFile.size();i++){
            if(ReflectionFile.get(i).getNewsID().compareTo(newsID)==0){
                return(ReflectionFile.get(i).getFav());
            }
        }
        return ("false");
    }
    static class ViewHolder {
        ImageView thumbnail;
        ImageView bookmark;
        TextView source;
        TextView title;
        TextView date;
    }

}
