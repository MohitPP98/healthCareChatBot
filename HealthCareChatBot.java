import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HealthcareChatbot {
  private static final String API_KEY = "your_api_key_here";

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String userInput;

    System.out.println("Welcome to the Healthcare Chatbot!");
    System.out.println("How can I help you today?");

    while (true) {
      userInput = sc.nextLine();

      if (userInput.equalsIgnoreCase("quit")) {
        break;
      } else if (userInput.equalsIgnoreCase("symptoms")) {
        System.out.println("What symptoms are you experiencing?");
      } else if (userInput.startsWith("diagnosis")) {
        String symptom = userInput.split(" ")[1];
        String diagnosis = getDiagnosis(symptom);
        System.out.println("Based on your symptom of " + symptom + ", I think you might have: " + diagnosis);
      } else {
        System.out.println("I'm sorry, I don't understand what you're asking. Please try again.");
      }
    }

    System.out.println("Thank you for using the Healthcare Chatbot. Have a great day!");
  }

  private static String getDiagnosis(String symptom) {
    String diagnosis = "";
    try {
      URL url = new URL("https://api.infermedica.com/v2/diagnosis?symptoms=[" + symptom + "]&sex=female&age=30&evidence=[]&extras=hide");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("App-Id", API_KEY);
      conn.setRequestProperty("App-Key", API_KEY);

      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      br.close();
      conn.disconnect();

      // Parse the JSON response to extract the diagnosis
      // ...

      return diagnosis;
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      return "";
    }
  }
}
