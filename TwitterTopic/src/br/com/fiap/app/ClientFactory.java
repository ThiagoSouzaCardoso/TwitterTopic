package br.com.fiap.app;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ClientFactory {

	
	public Twitter getConn(){
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("");
		builder.setOAuthConsumerSecret("");
		Configuration configuration = builder.build();

		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		AccessToken accessToken = loadAccessToken();
		twitter.setOAuthAccessToken(accessToken);
		return twitter;
	}
	
	protected AccessToken loadAccessToken() {
		String token = "";
		String tokenSecret = "";
		return new AccessToken(token, tokenSecret);
	}
	
}
