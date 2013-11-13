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

package twitter4j.examples.friendsandfollowers;

import java.util.Properties;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.PropertyConfiguration;

/**
 * Lists friends' ids
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class GetFriendsIDs {
	
	private static Properties props = new Properties();
	
    /**
     * Usage: java twitter4j.examples.friendsandfollowers.GetFriendsIDs [screen name]
     *
     * @param args message
     */
    public static void main(String[] args) {
        try {
        	
        	props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
    		props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
    		props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY, "WdM6eZhENPYSEbxvCXH8A");
            props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET, "Z3rUV2S9sgsfTKytdpipjugZykQe9F8TQPOyt9ldo");	
    		AccessToken token = new AccessToken(
    				"835028173-Tc6qHpE14zNWcxpp65aKkIzd3ggXRU2xKtjnKdzh",
    				"cGrAh8FDmfDwNlLEr2PCwzMJulzMCu0bPkxz5suMCc");
    		PropertyConfiguration conf = new PropertyConfiguration(props);
    		Twitter twitter = new TwitterFactory(conf).getInstance(token);
           
            long cursor = -1;
            IDs ids;
            System.out.println("Listing following ids.");
           // do {
              
                    ids = twitter.getFriendsIDs(73243474, cursor);
                
                for (long id : ids.getIDs()) {
                    System.out.println(id);
                }
            //} while ((cursor = ids.getNextCursor()) != 0);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get friends' ids: " + te.getMessage());
            System.exit(-1);
        }
    }
}
