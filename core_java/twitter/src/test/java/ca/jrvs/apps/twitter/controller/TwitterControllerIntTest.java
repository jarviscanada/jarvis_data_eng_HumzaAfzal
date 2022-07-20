package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {

  private TwitterController twitterController;

  @Before
  public void setupTwitterController() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);
    TwitterDao dao = new TwitterDao(httpHelper);
    TwitterService service = new TwitterService(dao);
    this.twitterController = new TwitterController(service);
  }

  @Test
  public void postTweet() {
    String text = "test postTweet method in TwitterController " + System.currentTimeMillis();
    String coordinates = "50.0:50.0";
    String[] testInput = {"post", text, coordinates};
    Tweet postTweet = twitterController.postTweet(testInput);

    assertNotNull(postTweet);
    assertEquals(postTweet.getText(), text);
  }

  @Test
  public void showTweet() {
    String text = "test showTweet method in TwitterController " + System.currentTimeMillis();
    String coordinates = "50.0:50.0";
    String[] postInput = {"post", text, coordinates};
    Tweet postTweet = twitterController.postTweet(postInput);
    String idString = postTweet.getIdString();

    String[] testInput = {"show", idString};
    Tweet showTweet = twitterController.showTweet(testInput);

    assertNotNull(showTweet);
    assertEquals(postTweet.getId(), showTweet.getId());

  }

  @Test
  public void deleteTweet() {
    String text = "test deleteTweet method in TwitterController " + System.currentTimeMillis();
    String coordinates = "50.0:50.0";
    String[] postInput = {"post", text, coordinates};
    Tweet postTweet = twitterController.postTweet(postInput);
    String idString = postTweet.getIdString();

    String[] testInput = {"delete", idString};
    List<Tweet> deleteTweets = twitterController.deleteTweet(testInput);

    assertNotNull(deleteTweets);

    Tweet deleteTweet = deleteTweets.get(0);

    assertNotNull(deleteTweet);
    assertEquals(postTweet.getId(), deleteTweet.getId());
  }
}