package com.epopov.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class GetCardNamePrice {

  //private static final Logger logger = LoggerFactory.getLogger(GetCardNamePrice.class);
  private final String TERFIT_URL = "https://terfit.ru/cards-shop/cardsCredit.php";
  private final String BRATISLAVSKY_CLUB_CODE = "130";

  public Optional<JsonNode> getCardInfoByName(String cardName) throws Exception {
    Connection connection = Jsoup.newSession();
    connection.url(TERFIT_URL);
    connection.data("club", BRATISLAVSKY_CLUB_CODE);
    Document doc = connection.post();

    for (Element element : doc.getElementsByAttribute("data-card-radio")) {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode node = mapper.readTree(element.attr("data-card-radio"));
      if (node.get("name").asText().trim().equals(cardName)) {
        return Optional.of(node);
      }
    }
    return Optional.empty();
  }

  void printCardInfo(JsonNode node) {
    System.out.println("Стоимость карты \"" + node.get("name").asText().trim()
        + "\" сегодня: " + node.get("price").asInt());
  }




  public static void main(String[] args) throws Exception {
    new GetCardNamePrice().printCardInfo(new GetCardNamePrice().getCardInfoByName("Соло").get());
    new GetCardNamePrice().printCardInfo(new GetCardNamePrice().getCardInfoByName("Индивидуальная").get());
  }
}
