package god.codename.brightside;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Loknath Shankar on 5/9/2016.
 */
public class ShareDialogViewer {
    public Context context;
    public Activity activity;
    public String newsTitle;
    public String newsLink;
    public String contentDiscription;
    public String Tags;
    public void ShowShareDialog(final Activity activity,final Context context, final String newsLink, final String newsTitle, final String contentDiscription,String Tags){
        this.context=context;
        this.activity=activity;
        this.newsLink=newsLink;
        this.newsTitle=newsTitle;
        this.contentDiscription=contentDiscription;
        this.Tags=Tags;
        final Item[] items = {
                new Item("Facebook", R.drawable.facebook),
                new Item("Twitter", R.drawable.twitter),
                new Item("Whats app", R.drawable.whatsapp),
                new Item("Email", R.drawable.email),
                new Item("Open in browser", R.drawable.chrome),
                new Item("Copy link", R.drawable.copy),
                new Item("Share by other means", R.drawable.share),//no icon for this one
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
                                FacebookSdk.sdkInitialize(context);
                                CallbackManager callbackManager = CallbackManager.Factory.create();
                                        ShareDialog shareDialog=new ShareDialog(activity);
                                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                                    .setContentDescription(contentDiscription)
                                                    .setContentTitle(newsTitle)
                                                    .setContentUrl(Uri.parse(newsLink))
                                                    .build();
                                            shareDialog.show(linkContent);
                                        }
                                break;
                            case 1:
                                 tweet();
                                break;
                            case 2:
                                 sharebyWhatsapp();
                                break;
                            case 3:
                                sharebyMail();
                                break;
                            case 4:
                                openInBrowser();
                                break;
                            case 5:
                                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText(newsTitle, newsLink);
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(context == null ? context : context, "Link copied to clipboard", Toast.LENGTH_SHORT).show();
                                break;
                            case 6:
                                shareByOther();
                                break;
                        }
                    }
                });
        builderSingle.show();
    }

    public void sharebyWhatsapp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, newsTitle + "\n\n" + newsLink + "\n\n"+Tags);
        try {
            context.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,"Whatsapp have not been installed.",Toast.LENGTH_LONG).show();
        }
    }


    public void tweet() {
        try {
            String tweetUrl =
                    String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                            urlEncode(newsTitle+"\n-Shared via @"+Tags+"Tweets\n "), urlEncode(newsLink)+"\r\n"+activity.getResources().getString(R.string.play_store_link));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

            List<ResolveInfo> matches = ((context == null ? context : context)).getPackageManager().queryIntentActivities(intent, 0);
            boolean exists = false;
            for (ResolveInfo info : matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                    exists = true;
                    intent.setPackage(info.activityInfo.packageName);
                }
            }
            if (!exists) {
                Toast.makeText(context == null ? context : context, "Twitter not found", Toast.LENGTH_SHORT).show();
                Intent tweet = new Intent(Intent.ACTION_VIEW);
                tweet.setData(Uri.parse("http://twitter.com/?status=" + Uri.encode(newsLink)));//where message is your string message
                activity.startActivity(tweet);
            }
            else {
                if (context == null)
                    context.startActivity(intent);
                else context.startActivity(intent);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf("TAG", "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

    public void openInBrowser() {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(newsLink));
            if (context == null)
                activity.startActivity(i);
            else
                (context).startActivity(i);
        }catch (Exception e){e.printStackTrace();}
    }

    public void shareByOther() {
        try {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, newsLink);
            activity.startActivity(Intent.createChooser(sharingIntent, activity.getResources().getString(R.string.share_using)));
        }catch (Exception e){e.printStackTrace();}
    }

    public void sharebyMail() {
        try {
            Intent gmail = new Intent(Intent.ACTION_VIEW);
            gmail.setType("plain/text");
            gmail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
            gmail.putExtra(Intent.EXTRA_SUBJECT, newsTitle);
            gmail.putExtra(Intent.EXTRA_TEXT, newsLink);
            if (context != null) {
                context.startActivity(gmail);
                return;
            }
            activity.startActivity(gmail);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context == null ? context : context, "Gmail client not found \nUse Share by other ", Toast.LENGTH_SHORT).show();
        }

    }

    public void copyToClipBoard() {
        try {
            ClipboardManager clipboard = (ClipboardManager) ((context == null ? activity : context).getSystemService(Context.CLIPBOARD_SERVICE));
            ClipData clip = ClipData.newPlainText("link", newsLink);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context == null ? context : context, "Link copied to clipboard", Toast.LENGTH_SHORT).show();
        }catch (Exception e){e.printStackTrace();}
    }
}
