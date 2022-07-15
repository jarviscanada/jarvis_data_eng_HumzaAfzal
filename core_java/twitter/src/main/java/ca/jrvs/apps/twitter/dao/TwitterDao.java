package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import ca.jrvs.apps.twitter.dao.helper.JsonParser;


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
    String status = tweet.getText();
    String longitude = String.valueOf(tweet.getCoordinates().getLongitude());
    String latitude = String.valueOf(tweet.getCoordinates().getLatitude());
    String uri = API_BASE_URI + POST_PATH + QUERY_SYMBOL + status + AMPERSAND + "longitude" + EQUAL
        + longitude + AMPERSAND + "latitude" + EQUAL + latitude;
    HttpResponse response = httpHelper.httpPost(URI.create(uri));
    return parseResponseBody(response, HTTP_OK);
  }

  private Tweet parseResponseBody(HttpResponse response, int expectedStatusCode) {
    Tweet tweet;
    int status = response.getStatusLine().getStatusCode();
    if (status != expectedStatusCode) {
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      }
      catch (IOException e) {
        System.out.println("Response has no entity!");
      }
      throw new RuntimeException("Unexpected HTTP status:" + status);
    }

    String jsonString;
    try {
      jsonString = EntityUtils.toString(response.getEntity());
    }
    catch (IOException e) {
      throw new RuntimeException("Failed to convert entity to String", e);
    }

    try {
      tweet = JsonParser.toObjectFromJson(jsonString, Tweet.class);
    }
    catch (IOException e) {
      throw new RuntimeException("Failed to make json string to tweet object", e);
    }
    return tweet;
  }

  @Override
  public Tweet findById(String s) {
    return null;
  }

  @Override
  public Tweet deleteById(String s) {
    return null;
  }
}
