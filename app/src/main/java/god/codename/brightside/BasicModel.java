package god.codename.brightside;

/**
 * Created by lokanath on 5/4/2016.
 */

public class BasicModel {
    private String title;
    private String summary;
    private String date;
    private String category;
    private String newsImage;
    private String fullText;
    private String newsID;
    private String source;
    private String nextCall;
    private String newsLink;
    private String author;
    private String fav;


    public BasicModel(String title,String summary, String category,String source, String date ,String newsImage, String fullText, String newsID, String nextCall,String newsLink,String author, String fav) {
        this.title = title;
        this.newsImage = newsImage;
        this.category=category;
        this.date=date;
        this.source=source;
        this.summary=summary;
        this.fullText=fullText;
        this.newsID=newsID;
        this.nextCall=nextCall;
        this.newsLink=newsLink;
        this.author=author;
        this.fav=fav;
    }

    public String getSource() {
        return source;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getnewsImage() {
        return newsImage;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getFullReview(){return fullText;}

    public String getNewsID(){return newsID;}

    public String getNextCall(){return nextCall;}

    public String getNewsLink(){return newsLink;}

    public String getAuthor(){return author;}

    public String getFav(){return fav;}

    public void setnewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFullReview(String fullText) {
        this.fullText = fullText;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public void setNextCall(String nextCall) {
        this.nextCall = nextCall;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public void setSummary() {
        this.summary = summary;
    }

    public void setSource() {
        this.source = source;
    }

    public void setNewsLink() {
        this.newsLink = newsLink;
    }

    public void setAuthor() {
        this.author = author;
    }
}