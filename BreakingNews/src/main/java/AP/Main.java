package AP;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Infrastructure infrastructure = new Infrastructure("7d5bc250ca254c4792a80a7712d3c473");
        infrastructure.displayNewsList();
        System.out.println("-------------------------");
        //getting newslist from infrastructure
        ArrayList<News> NewsList = infrastructure.getNewsList();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Choose the article you want to view (Enter -1 to exit) :");
            int ArticleNumber = scanner.nextInt();
            System.out.println("-------------------------");
            if(ArticleNumber == -1) {
                break;
            }
            NewsList.get(ArticleNumber-1).DisplayNews();
            System.out.println("-------------------------");
        }

    }
}