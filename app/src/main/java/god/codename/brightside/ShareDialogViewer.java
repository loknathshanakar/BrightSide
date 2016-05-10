package god.codename.brightside;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Loknath Shankar on 5/9/2016.
 */
public class ShareDialogViewer {
    public void ShowShareDialog(final Context context){
        final Item[] items = {
                new Item("Facebook", R.drawable.ic_menu_camera),
                new Item("Twitter", R.drawable.ic_menu_camera),
                new Item("Whats app", R.drawable.ic_menu_camera),
                new Item("Email", R.drawable.ic_menu_camera),
                new Item("Open in browser", R.drawable.ic_menu_camera),
                new Item("Copy link", R.drawable.ic_menu_camera),
                new Item("Share by other means", R.drawable.ic_menu_camera),//no icon for this one
        };
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setIcon(R.drawable.ic_menu_camera);
        //builderSingle.setTitle(newsTitle);
        final ArrayAdapter<Item> arrayAdapter = new ArrayAdapter<Item>(context, android.R.layout.select_dialog_item,android.R.id.text1,items){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView)v.findViewById(android.R.id.text1);
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);
                int dp5 = (int) (5 * context.getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);

                return v;
            }
        };
        builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                        /*if (ShareDialog.canShow(ShareLinkContent.class)) {
                                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                                    .setContentDescription("Shared via Evernews")
                                                    .setContentTitle(newsTitle + " #EVERNEWS")
                                                    .setContentUrl(Uri.parse(newsLink))
                                                    .build();
                                            shareDialog.show(linkContent);
                                        }*/
                                break;
                            case 1:
                                // tweet();
                                break;
                            case 2:
                                // sharebyWhatsapp();
                                break;
                            case 3:
                                //sharebyMail();
                                break;
                            case 4:
                                //openInBrowser();
                                break;
                            case 5:
                                //ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                //ClipData clip = ClipData.newPlainText(newsTitle, newsLink);
                                //clipboard.setPrimaryClip(clip);
                                //Toast.makeText(getContext() == null ? context : context, "Link copied to clipboard", Toast.LENGTH_SHORT).show();
                                break;
                            case 6:
                                //shareByOther();
                                break;
                        }
                    }
                });
        builderSingle.show();
    }
}
