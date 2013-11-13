/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter4j.examples.myTest;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.PropertyConfiguration;

import java.util.List;
import java.util.Properties;

/**
 * Shows up to 100 of the first retweets of a given tweet.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class GetRetweets {
    /**
     * Usage: java twitter4j.examples.tweets.GetRetweets [status id]
     *
     * @param args message
     */
	
	
    public static void main(String[] args) {
      /*  if (args.length < 1) {
            System.out.println("Usage: java twitter4j.examples.tweets.GetRetweets [status id]");
            System.exit(-1);
        }*/
        Properties props = new Properties();
        props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
		props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
		props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY,
				"eEKgNmj5LZM1Y0s9w256A");
		props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET,
				"IZzKrkFVd2dsJkaKQCUFA5vHFI8cDjthj7hccXnM");
		AccessToken token = new AccessToken(
				"1346773026-x9FAnE5qFB3xjm47OU2puDSQiduMS7WDc3fdUX7",
				"EOQC5Ejfx8CVy4y1ZBFgQbrSaQltsesULNm0rNHPDQ");
		PropertyConfiguration conf = new PropertyConfiguration(props);
        //System.out.println("Showing up to 100 of the first retweets of the status id - [" + args[0] + "].");
        try {
        	Twitter twitter = new TwitterFactory(conf).getInstance(token);
            List<Status> statuses = twitter.getRetweets(1346773026);
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
            System.out.println("done.");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get retweets: " + te.getMessage());
            System.exit(-1);
        }
    }
}
