package abdullah.mansour.egynews.Models;

public class NewsModel
{
    String title,imageurl;

    public NewsModel() {
    }

    public NewsModel(String title, String imageurl) {
        this.title = title;
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
