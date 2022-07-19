package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterUtils;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;


public class TwitterDao implements CrdDao<Tweet, String> {

  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy";

  private static final String QUERY_SYMBOL = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  @Override
  public Tweet create(Tweet tweet) {
    try {
      String status = tweet.getText();
      String longitude = String.valueOf(tweet.getCoordinates().getLongitude());
      String latitude = String.valueOf(tweet.getCoordinates().getLatitude());
      String encodedStatus = URLEncoder.encode(status, StandardCharsets.UTF_8.toString());
      String uriString =
          API_BASE_URI + POST_PATH + QUERY_SYMBOL + "status" + EQUAL + encodedStatus + AMPERSAND
              + "long" + EQUAL
              + longitude + AMPERSAND + "lat" + EQUAL + latitude;

      HttpResponse response = httpHelper.httpPost(URI.create(uriString));
      return parseResponseBody(response, HTTP_OK);
    } catch (Exception e) {
      System.out.println("Cannot Encode!");
      throw new RuntimeException(e);
    }
  }

  @Override
  public Tweet findById(String s) {
    String uriString = API_BASE_URI + SHOW_PATH + QUERY_SYMBOL + "id" + EQUAL + s;

    HttpResponse response = httpHelper.httpGet(URI.create(uriString));

    return parseResponseBody(response, HTTP_OK);
  }

  @Override
  public Tweet deleteById(String s) {
    String uriString = API_BASE_URI + DELETE_PATH + "/" + s + ".json";

    HttpResponse response = httpHelper.httpPost(URI.create(uriString));

    return parseResponseBody(response, HTTP_OK);
  }

  public Tweet parseResponseBody(HttpResponse response, int expectedStatusCode) {
    Tweet tweet;
    int status = response.getStatusLine().getStatusCode();
    if (status != expectedStatusCode) {
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        System.out.println("Response has no entity!");
      }
      throw new RuntimeException("Unexpected HTTP status:" + status);
    }

    String jsonString;
    try {
      jsonString = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert entity to String", e);
    }

    try {
      tweet = TwitterUtils.toObjectFromJson(jsonString, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Failed to make json string to tweet object", e);
    }
    return tweet;
  }

}
