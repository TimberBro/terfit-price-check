package com.epopov.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.sql.DataSource;
import org.springframework.stereotype.Component;

@Component("cardInfoService")
public class CardInfoServiceImpl implements CardInfoService {

  private final DataSource dataSource;

  public CardInfoServiceImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void save(JsonNode node) {
    String insertQuery = "INSERT INTO price_changelog (club_id, create_date, card_name, card_price) VALUES (130, ?, ?, ?)";
    java.sql.Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = dataSource.getConnection();
      statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, Date.valueOf(LocalDate.now()).toString());
      statement.setString(2, node.get("name").asText().trim());
      statement.setInt(3, node.get("price").asInt());
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
