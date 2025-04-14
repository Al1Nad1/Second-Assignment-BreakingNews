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

    private final String APIKEY;
    private final String URL;
    private final String JSONRESULT;
    private ArrayList<News> newsList = new ArrayList<>();

    public Infrastructure(String APIKEY) {
        this.APIKEY = APIKEY;
        this.URL = "https://newsapi.org/v2/everything?q=tesla&from=2025-02-18&sortBy=publishedAt&apiKey=";
        this.JSONRESULT = getInformation();

        if (this.JSONRESULT != null) {
            parseInformation();
        } else {
            System.out.println("Failed to fetch news data.");
        }
    }

    // Returns the newsList to be used in another class
    public ArrayList<News> getNewsList() {
        return newsList;
    }

    // Fetches information from API and returns JSON as a string
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
                System.out.println("HTTP Request Failed! Code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("!! Exception (API Request): " + e.getMessage());
        }
        return null;
    }

    // Parses JSON response and maps it to News objects
    private void parseInformation() {
        try {
            JSONObject obj = new JSONObject(JSONRESULT);
            JSONArray array = obj.getJSONArray("articles");

            int newsCount = Math.min(array.length(), 20); // Ensuring we don’t go out of bounds

            for (int i = 0; i < newsCount; i++) {
                JSONObject article = array.getJSONObject(i);
                News news = new News();

                news.title = article.optString("title", "No Title Available");
                news.author = article.optString("author", "Unknown Author");
                news.description = article.optString("description", "No Description Available");
                news.publishedAt = article.optString("publishedAt", "No Date Available");
                news.Url = article.optString("url", "#");

                JSONObject sourceObj = article.optJSONObject("source");
                news.SourceName = (sourceObj != null) ? sourceObj.optString("name", "Unknown Source") : "Unknown Source";

                newsList.add(news);
            }
        } catch (Exception e) {
            System.out.println("!! Exception (Parsing JSON): " + e.getMessage());
        }
    }

    // Displays a list of news articles, allowing user interaction
    public void displayNewsList() {
        if (newsList.isEmpty()) {
            System.out.println("No news available to display.");
            return;
        }

        System.out.println("News of the Day:");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.println("-------------------------");
            System.out.println((i + 1) + ") " + newsList.get(i).title);
        }
    }
}