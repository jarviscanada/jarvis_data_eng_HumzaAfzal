package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.math.BigInteger;

public class TwitterUtils {

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

  public static Tweet buildTweet(String text, double longitude, double latitude) {
    Coordinates tweetCoordinates = new Coordinates(longitude, latitude);
    return new Tweet(null, BigInteger.valueOf(0), null, text, tweetCoordinates, 0, 0, false, false);
  }

//  public static void main(String[] args) throws IOException {
////    Company company = toObjectFromJson(companyStr, Company.class);
////    System.out.println(toJson(company, true, false));
//  }

}
