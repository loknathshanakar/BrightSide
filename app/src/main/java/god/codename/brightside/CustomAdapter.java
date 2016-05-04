package god.codename.brightside;

/**
 * Created by lokanath on 5/4/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<ItemObject> listStorage;
    private Context context;

    public CustomAdapter(Context context, List<ItemObject> customizedListView) {
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

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load("NULL")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .thumbnail(0.1f).fitCenter()
                .placeholder(R.drawable.tech_placeholder)
                .error(R.drawable.tech_placeholder)
                .into(listViewHolder.thumbnail);
        listViewHolder.source.setText(listStorage.get(position).getCategory());
        listViewHolder.title.setText(listStorage.get(position).getTitle());
        listViewHolder.date.setText(listStorage.get(position).getDate());
        return convertView;
    }

    static class ViewHolder {
        ImageView thumbnail;
        TextView source;
        TextView title;
        TextView date;
    }

}
