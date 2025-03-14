package AP;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
public class Infrastructure {

    private final String URL;
    private final String APIKEY;
    private final String JSONRESULT;
    private ArrayList<News> newsList = new ArrayList<News>();


    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=2025-02-18&sortBy=publishedAt&apiKey=";
        this.JSONRESULT = getInformation();
        parseInformation();
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }


    private String getInformation() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + APIKEY))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new IOException("HTTP error code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("!!Exception 1: " + e.getMessage());
        }
        return null;
    }

    private void parseInformation() {

        try {
            JSONObject obj = new JSONObject(JSONRESULT);
            JSONArray array = obj.getJSONArray("articles");
            for (int i = 0; i < 20; i++) {
                JSONObject article = array.getJSONObject(i);
                News news = new News();
                news.title = article.getString("title");
                news.author = article.getString("author");
                news.description = article.getString("description");
                news.publishedAt = article.getString("publishedAt");
                news.Url = article.getString("url");
                JSONObject newsObj = article.getJSONObject("source");
                news.SourceName = newsObj.getString("name");
                newsList.add(news);
            }
        }
        catch (Exception e) {
            System.out.println("!!Exception : " + e.getMessage());
        }
    }
    //shows the news from 1 to 20
    public void displayNewsList() {

        System.out.println("The Breaking News Of The Day");
        for(int i = 0; i < 20; i++) {
            System.out.println("-------------------------");
            System.out.println(i+1 + ") " + newsList.get(i).title);
        }
    }

}