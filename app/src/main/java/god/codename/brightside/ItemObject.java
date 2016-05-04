package god.codename.brightside;

/**
 * Created by lokanath on 5/4/2016.
 */

public class ItemObject {
    private String title;
    private String date;
    private String category;
    private String image;


    public ItemObject(String title,String date,String category, String image) {
        this.title = title;
        this.image = image;
        this.category=category;
        this.date=date;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public void setImage(String image) {
        this.image = image;
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
}