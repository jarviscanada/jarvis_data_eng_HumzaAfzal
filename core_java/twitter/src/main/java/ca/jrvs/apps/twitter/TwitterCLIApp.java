package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterUtils;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterCLIApp {

  public static final String USAGE = "Usage: TwitterCLIApp post|show|delete [options]";

  private Controller controller;

  @Autowired
  public TwitterCLIApp(Controller controller) {
    this.controller = controller;
  }

//  public static void main(String[] args) {
//    String consumerKey = System.getenv("consumerKey");
//    String consumerSecret = System.getenv("consumerSecret");
//    String accessToken = System.getenv("accessToken");
//    String tokenSecret = System.getenv("tokenSecret");
//
//    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
//        tokenSecret);
//    TwitterDao dao = new TwitterDao(httpHelper);
//    TwitterService service = new TwitterService(dao);
//    TwitterController controller = new TwitterController(service);
//    TwitterCLIApp app = new TwitterCLIApp(controller);
//
//    app.run(args);
//  }

  public void run(String[] args) {
    if (args.length < 2) {
      throw new RuntimeException((USAGE));
    }

    switch (args[0].toLowerCase()) {
      case "post":
        printTweet(controller.postTweet(args));
        break;
      case "show":
        printTweet(controller.showTweet(args));
        break;
      case "delete":
        controller.deleteTweet(args).forEach(this::printTweet);
        break;
      default:
        throw new IllegalArgumentException(USAGE);
    }
  }

  public void printTweet(Tweet tweet) {
    try {
      System.out.println(TwitterUtils.toJson(tweet, true, true));
    }
    catch (JsonProcessingException e) {
      throw new RuntimeException("Unable to convert tweet object to string", e);
    }
  }

}