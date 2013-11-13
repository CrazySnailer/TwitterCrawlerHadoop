This is a Twitter Crawler Which was written by CrazySnialer in June 2013!


		  <!-- The configuration of maven-assembly-plugin -->
		<plugin>
	    	<groupId>org.apache.maven.plugins</groupId>
	   		 <artifactId>maven-assembly-plugin</artifactId>
	   		  <!-- The configuration of the plugin -->
		    <configuration>
				<finalName>TweetsCrawler</finalName>
				
				 <!-- Specifies the configuration file of the assembly plugin -->
				<descriptors>
				<descriptor>src/main/assembly/package.xml</descriptor>
				</descriptors>	
			</configuration>
	    
	      <executions>
	        <execution>
		        <id>make-assembly</id>
		        	<phase>package</phase>
			        <goals>
			          <goal>single</goal>
			        </goals>
	        </execution>
          </executions>
       </plugin>
