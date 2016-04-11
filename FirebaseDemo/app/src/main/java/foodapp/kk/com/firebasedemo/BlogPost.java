package foodapp.kk.com.firebasedemo;

/**
 * Created by Sairam on 4/10/2016.
 */
public class BlogPost {
    private String author;
    private String title;

    public BlogPost(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public BlogPost() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}