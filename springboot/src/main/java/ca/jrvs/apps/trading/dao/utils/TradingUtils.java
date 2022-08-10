package ca.jrvs.apps.trading.dao.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class TradingUtils {

  public static String toJson(Object object, boolean prettyJson, boolean includeNullValues)
      throws JsonProcessingException {

    ObjectMapper m = new ObjectMapper();
    if (!includeNullValues) {
      m.setSerializationInclusion(Include.NON_NULL);
    }
    if (prettyJson) {
      m.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return m.writeValueAsString(object);
  }

  public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
    ObjectMapper m = new ObjectMapper();
    return (T) m.readValue(json, clazz);
  }

  public static <T> T toObjectFromJson(String json, TypeReference clazz) throws IOException {
    ObjectMapper m = new ObjectMapper();
    return (T) m.readValue(json, clazz);
  }

}
