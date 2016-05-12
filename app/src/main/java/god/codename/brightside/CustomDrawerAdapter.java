package god.codename.brightside;

/**
 * Created by Loknath Shankar on 5/11/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomDrawerAdapter extends ArrayAdapter<NavDrawerModel> implements View.OnClickListener {

    Context context;
    ArrayList<NavDrawerModel> drawerItemList;
    int layoutResID;
    private Activity activity;

    public CustomDrawerAdapter(Context context, int layoutResourceID, ArrayList<NavDrawerModel> listItems,Activity activity) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
        this.activity=activity;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view.findViewById(R.id.parent_title);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.parent_icon);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        NavDrawerModel dItem = this.drawerItemList.get(position);
        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getResId()));
        drawerHolder.ItemName.setText(dItem.getName());
        view.setOnClickListener(new OnItemClickListener( position ));
        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView icon;
    }

    @Override
    public void onClick(View v) {
    }
    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            Main sct = (Main)activity;
            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
            sct.onItemClick(mPosition);
        }
    }
}