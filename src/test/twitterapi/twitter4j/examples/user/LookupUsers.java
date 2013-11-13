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

package twitter4j.examples.user;

import java.util.Properties;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.PropertyConfiguration;

/**
 * Looks up users.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class LookupUsers {
	
	private static Properties props = new Properties();
	
    /**
     * Usage: java twitter4j.examples.user.LookupUsers [screen name[,screen name..]]
     *
     * @param args message
     */
    public static void main(String[] args) {
    	
    	props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
		props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
		props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY, "WdM6eZhENPYSEbxvCXH8A");
        props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET, "Z3rUV2S9sgsfTKytdpipjugZykQe9F8TQPOyt9ldo");	
		AccessToken token = new AccessToken(
				"835028173-Tc6qHpE14zNWcxpp65aKkIzd3ggXRU2xKtjnKdzh",
				"cGrAh8FDmfDwNlLEr2PCwzMJulzMCu0bPkxz5suMCc");
		PropertyConfiguration conf = new PropertyConfiguration(props);
		
    	
       /* if (args.length < 1) {
            System.out.println(
                    "Usage: java twitter4j.examples.user.LookupUsers [screen name[,screen name..]]");
            System.exit(-1);
        }*/
		
		//long[] SeedsIdArr={269691987,1007224032,1007276611,979338925,1007244240,988716427,999833587,979697214,1007295210,986023082,1007278273,985993465,1007284285,986268912,1007263549,1007273042,1007261364,1007281382,1005809539,1005288272,1006446860,1007277361,1007273977,983408172,1000102142,1007271631,1007262235,999865303,1007265636,1007258688,1007269482,1006183352,1007276136,1007266340,999627937,983990532,991196732,1004806519,1005089677,1007893122,1007289811,1007250830,994741045,988228382,999132738,1005720456,1007405899,1000015644,1007769324,990253500,1007280949,1007769990,984111487,1007780024,1007769894,1007769330,1007771082,1007941232,1007788981,999637886,1007780156,1007770374,1004712301,1007779850,986677592,1007787049,984328213,1007808530,1007831473,990867949,997000620,980058674,1007821117,994616695,1005682178,986324340,989123827,1007828929,1004642208,1007823140,1007825455,983867810,1007808894,1007802834,1007804244,1007817212,1007822732,1007827501,1007813228,999739878,1007828023,993307867,1007831112,998776850,995364438,1007844812,980065056,1007832546,1007842514,1007844152};
		
		long[] SeedsIdArr={103848976};
		
        try {
        	Twitter twitter = new TwitterFactory(conf).getInstance(token);
            ResponseList<User> users = twitter.lookupUsers(SeedsIdArr);
            for (User user : users) {
            	
            	System.out.println(user);
//                if (user.getStatus() != null) {
//                    System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
//                } else {
//                    // the user is protected
//                    System.out.println("@" + user.getScreenName());
//                }
            }
            System.out.println("Successfully looked up users [" + SeedsIdArr + "].");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to lookup users: " + te.getMessage());
            System.exit(-1);
        }
    }
}
