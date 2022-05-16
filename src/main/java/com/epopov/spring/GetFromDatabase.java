package com.epopov.spring;

import com.epopov.util.GetCardNamePrice;
import com.fasterxml.jackson.databind.JsonNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.epopov.service.CardInfoService;

@Component
public class GetFromDatabase implements CommandLineRunner {

  private final DataSource dataSource;
  private final CardInfoService cardInfoService;

  public GetFromDatabase(DataSource dataSource, CardInfoService cardInfoService) {
    this.dataSource = dataSource;
    this.cardInfoService = cardInfoService;
  }

  @Override
  public void run(String... args) throws Exception {
    JsonNode soloCardNode = new GetCardNamePrice().getCardInfoByName("Соло").get();
    JsonNode individualCardNode = new GetCardNamePrice().getCardInfoByName("Индивидуальная").get();
    cardInfoService.save(soloCardNode);
    cardInfoService.save(individualCardNode);

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      connection = dataSource.getConnection();
      statement = connection.prepareStatement("select * from price_changelog");
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        System.out.println(resultSet.getInt("card_price"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (statement != null) {
          statement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
