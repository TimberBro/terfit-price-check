package parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class GetDocument {

  public static void main(String[] args) throws Exception {

    String blogUrl = "https://terfit.ru/cards-shop/cardsCredit.php";
    Connection connection = Jsoup.newSession();

    connection.url(blogUrl);
    connection.data("club", "130");
    Document doc = connection.post();

    for (Element element : doc.getElementsByAttribute("data-card-radio")) {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readTree(element.attr("data-card-radio"));

      if (node.get("name").asText().trim().equals("Соло") || node.get("name").asText().trim()
          .equals("Индивидуальная")) {
        System.out.println("Стоимость карты \"" + node.get("name").asText().trim()
            + "\" сегодня: " + node.get("price").asInt());
      }
    }
  }
}
