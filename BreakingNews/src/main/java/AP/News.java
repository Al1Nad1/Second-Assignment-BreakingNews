package AP;

public class News {
    String title;
    String author;
    String description;
    String publishedAt;
    String SourceName;
    String Url;
    public void DisplayNews()
    {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Description: " + description);
        System.out.println("Published At: " + publishedAt);
        System.out.println("Source Name: " + SourceName);
        System.out.println("Url: " + Url);
    }
}