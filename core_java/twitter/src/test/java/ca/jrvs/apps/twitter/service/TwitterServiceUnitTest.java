package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterUtils;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  TwitterDao dao;

  @InjectMocks
  TwitterService service;

  @Test
  public void postTweet() {
    // test success
    when(dao.create(any())).thenReturn(new Tweet());
    service.postTweet(TwitterUtils.buildTweet("test", 50.0, 0.0));

    // test failure
    when(dao.create(any())).thenThrow(new RuntimeException("mock"));
    try {
      service.postTweet(TwitterUtils.buildTweet("test", 50.0, 0.0));
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  @Test
  public void showTweet() {
    // test success
    when(dao.findById(any())).thenReturn(new Tweet());
    service.showTweet("3242343223423423423", null);

    // test failure
    when(dao.findById(any())).thenThrow(new RuntimeException("mock"));
    try {
      service.showTweet("43232323232", null);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  @Test
  public void deleteTweets() {
    //test success
    when(dao.deleteById(any())).thenReturn(new Tweet());
    String[] tweets = {"543543543543"};
    service.deleteTweets(tweets);

    // test failure
    when(dao.deleteById(any())).thenThrow(new RuntimeException("mock"));
    try {
      service.deleteTweets(tweets);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }
}