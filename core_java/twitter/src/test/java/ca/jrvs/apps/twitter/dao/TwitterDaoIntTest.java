package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterUtils;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao dao;

  @Before
  public void setupDao() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);
    this.dao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() throws Exception {
    String text = "Test create method in TwitterDao " + System.currentTimeMillis();
    double lat = 30;
    double lon = 30;
    Tweet postTweet = TwitterUtils.buildTweet(text, lon, lat);
    Tweet tweet = dao.create(postTweet);

    assertEquals(text, tweet.getText());

    assertNotNull(tweet.getCoordinates());
    assertEquals(lat, tweet.getCoordinates().getLatitude(), 0.00001);
    assertEquals(lon, tweet.getCoordinates().getLongitude(), 0.00001);

  }

  @Test
  public void findById() {
    String text = "Test findById method in TwitterDao " + System.currentTimeMillis();
    double lat = 30;
    double lon = 30;
    Tweet postTweet = TwitterUtils.buildTweet(text, lon, lat);
    Tweet testTweet = dao.create(postTweet);

    String idString = testTweet.getIdString();
    Tweet tweet = dao.findById(idString);

    assertEquals(testTweet.getId(), tweet.getId());
    assertEquals(testTweet.getCreatedAt(), tweet.getCreatedAt());
    assertEquals(testTweet.getText(), tweet.getText());
    assertEquals(testTweet.getCoordinates().getLatitude(), tweet.getCoordinates().getLatitude(),
        0.000001);
    assertEquals(testTweet.getCoordinates().getLongitude(), tweet.getCoordinates().getLongitude(),
        0.000001);
  }

  @Test
  public void deleteById() {
    String text = "Test deleteById in TwitterDao " + System.currentTimeMillis();
    double lat = 30;
    double lon = 30;
    Tweet postTweet = TwitterUtils.buildTweet(text, lon, lat);
    Tweet testTweet = dao.create(postTweet);

    String idString = testTweet.getIdString();
    Tweet tweet = dao.findById(idString);

    assertEquals(testTweet.getId(), tweet.getId());
    assertEquals(testTweet.getCreatedAt(), tweet.getCreatedAt());
    assertEquals(testTweet.getText(), tweet.getText());
    assertEquals(testTweet.getCoordinates().getLatitude(), tweet.getCoordinates().getLatitude(),
        0.000001);
    assertEquals(testTweet.getCoordinates().getLongitude(), tweet.getCoordinates().getLongitude(),
        0.000001);
  }
}