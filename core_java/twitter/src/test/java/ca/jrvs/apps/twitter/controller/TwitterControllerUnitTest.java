package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  @Mock
  TwitterService service;

  @InjectMocks
  TwitterController controller;

  @Test
  public void postTweet() {
    // test success
    String text = "test";
    String coordinates = "50.0:50.0";
    String[] testInput = {"post", text, coordinates};
    when(service.postTweet(any())).thenReturn(new Tweet());
    controller.postTweet(testInput);

    // test failure
    when(service.postTweet(any())).thenThrow(new RuntimeException("mock"));
    try {
      controller.postTweet(testInput);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  @Test
  public void showTweet() {
    // test success
    String[] testInput = {"show", "432432423423423423"};
    when(service.showTweet(any(), any())).thenReturn(new Tweet());
    controller.showTweet(testInput);

    // test fail
    when(service.showTweet(any(), any())).thenThrow((new RuntimeException("mock")));
    try {
      controller.showTweet(testInput);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  @Test
  public void deleteTweet() {
    // test success
    String[] ids = {"delete", "432423423423"};
    when(service.deleteTweets(any())).thenReturn(new ArrayList<Tweet>());
    controller.deleteTweet(ids);

    // test fail
    when(service.deleteTweets(any())).thenThrow(new RuntimeException("mock"));
    try {
      controller.deleteTweet(ids);
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }
}