package com.epopov.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface CardInfoService {

  void save(JsonNode node);
}
