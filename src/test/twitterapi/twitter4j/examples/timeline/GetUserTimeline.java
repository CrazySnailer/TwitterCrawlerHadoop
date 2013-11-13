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

package twitter4j.examples.timeline;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.PropertyConfiguration;

import java.util.List;
import java.util.Properties;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class GetUserTimeline {
    /**
     * Usage: java twitter4j.examples.timeline.GetUserTimeline
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        // gets Twitter instance with default credentials
    	 Properties props = new Properties();
         props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
 		props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
 		props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY,
 				"PUrgpzGjVb8bBlj5THzzTw");
 		props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET,
 				"MmfW4bqClQDX2BAarc2B0dctVHK9Fm8BcIiiz4Vf4");
 		AccessToken token = new AccessToken(
 				"393264915-y0shGxOSWHPid1QAMveVMefef322rp9Q91JlTV9A",
 				"kHaTs1fPmjNkjzUGIRSsgU9WZnjXVZItSxFaIr31I");
 		PropertyConfiguration conf = new PropertyConfiguration(props);
        Twitter twitter = new TwitterFactory(conf).getInstance(token);
        try {
            List<Status> statuses;
            long user;

                user =103848976;
                statuses = twitter.getUserTimeline(user);
            
     
            for (Status status : statuses) {
                System.out.println(status);
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
}
