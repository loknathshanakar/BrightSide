package god.codename.brightside;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Loknath Shankar on 5/8/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME="NEWS.db";
    public static final String TABLE_NAME = "AllNews";
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + HouseKeeping.TITLES + " TEXT ," + HouseKeeping.SUMMARYS + " TEXT ,"
                + HouseKeeping.CATEGORYS + " TEXT ," + HouseKeeping.SOURCES + " TEXT ,"
                + HouseKeeping.DATES + " DATETIME ," + HouseKeeping.NEWSIMAGES + " TEXT ,"
                + HouseKeeping.FULLTEXTS + " TEXT ," + HouseKeeping.NEWSIDS + " TEXT UNIQUE ,"
                + HouseKeeping.NEXTCALLS + " TEXT ," + HouseKeeping.NEWSLINKS + " TEXT ,"
                + HouseKeeping.AUTHORS + " TEXT ,"
                + HouseKeeping.FAVS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void UpdateNews(ContentValues contentValues,String newsId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, contentValues, HouseKeeping.NEWSIDS+"="+newsId, null);
    }

    void addNews(BasicModel news) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HouseKeeping.TITLES, news.getTitle()); // Contact Name
        values.put(HouseKeeping.SUMMARYS, news.getSummary()); // Contact Phone
        values.put(HouseKeeping.CATEGORYS, news.getCategory()); // Contact Phone
        values.put(HouseKeeping.SOURCES, news.getSource()); // Contact Phone
        values.put(HouseKeeping.DATES, news.getDate()); // Contact Phone
        values.put(HouseKeeping.NEWSIMAGES, news.getnewsImage()); // Contact Phone
        values.put(HouseKeeping.FULLTEXTS, news.getFullReview()); // Contact Phone
        values.put(HouseKeeping.NEWSIDS, news.getNewsID()); // Contact Phone
        values.put(HouseKeeping.NEXTCALLS, news.getNextCall()); // Contact Phone
        values.put(HouseKeeping.NEWSLINKS, news.getNewsLink()); // Contact Phone
        values.put(HouseKeeping.AUTHORS, news.getAuthor()); // Contact Phone
        values.put(HouseKeeping.FAVS, news.getFav()); // Contact Phone
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    void addNewsList(ArrayList<BasicModel> newsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<newsList.size();i++) {
            ContentValues values = new ContentValues();
            values.put(HouseKeeping.TITLES, newsList.get(i).getTitle()); // Contact Name
            values.put(HouseKeeping.SUMMARYS, newsList.get(i).getSummary()); // Contact Phone
            values.put(HouseKeeping.CATEGORYS, newsList.get(i).getCategory()); // Contact Phone
            values.put(HouseKeeping.SOURCES, newsList.get(i).getSource()); // Contact Phone
            values.put(HouseKeeping.DATES, newsList.get(i).getDate()); // Contact Phone
            values.put(HouseKeeping.NEWSIMAGES, newsList.get(i).getnewsImage()); // Contact Phone
            values.put(HouseKeeping.FULLTEXTS, newsList.get(i).getFullReview()); // Contact Phone
            values.put(HouseKeeping.NEWSIDS, newsList.get(i).getNewsID()); // Contact Phone
            values.put(HouseKeeping.NEXTCALLS, newsList.get(i).getNextCall()); // Contact Phone
            values.put(HouseKeeping.NEWSLINKS, newsList.get(i).getNewsLink()); // Contact Phone
            values.put(HouseKeeping.AUTHORS, newsList.get(i).getAuthor()); // Contact Phone
            values.put(HouseKeeping.FAVS, newsList.get(i).getFav()); // Contact Phone
            db.insert(TABLE_NAME, null, values);
        }
        db.close(); // Closing database connection
    }

    public ArrayList<BasicModel> getAllNews(){
        ArrayList <BasicModel> allNews=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, HouseKeeping.col, null, null, null, null, HouseKeeping.DATES+" DESC");

        if (cursor.moveToFirst()) {
            do {
                String TITLES=cursor.getString(HouseKeeping.TITLE);
                String SUMMARYS=cursor.getString(HouseKeeping.SUMMARY);
                String CATEGORYS=cursor.getString(HouseKeeping.CATEGORY);
                String SOURCES=cursor.getString(HouseKeeping.SOURCE);
                String DATES=cursor.getString(HouseKeeping.DATE);
                String NEWSIMAGES=cursor.getString(HouseKeeping.NEWSIMAGE);
                String FULLTEXTS=cursor.getString(HouseKeeping.FULLTEXT);
                String NEWSIDS=cursor.getString(HouseKeeping.NEWSID);
                String NEXTCALLS=cursor.getString(HouseKeeping.NEXTCALL);
                String NEWSLINKS=cursor.getString(HouseKeeping.NEWSLINK);
                String AUTHORS=cursor.getString(HouseKeeping.AUTHOR);
                String FAVS=cursor.getString(HouseKeeping.FAV);
                allNews.add(new BasicModel(TITLES,SUMMARYS,CATEGORYS,SOURCES,DATES,NEWSIMAGES,FULLTEXTS,NEWSIDS,NEXTCALLS,NEWSLINKS,AUTHORS,FAVS));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return (allNews);
    }

    public int getNewsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();
        return count;
    }
}
