package br.com.fiap.app;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ClientFactory {

	
	public Twitter getConn(){
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("E5jxZu2Mdl3YPAVQwSKbAlG16");
		builder.setOAuthConsumerSecret("A0KFOtOOY0OuaXfIvIuGjSCrmM2K5hDyBCueRXoqHV2gfVfdZP");
		Configuration configuration = builder.build();

		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		AccessToken accessToken = loadAccessToken();
		twitter.setOAuthAccessToken(accessToken);
		return twitter;
	}
	
	protected AccessToken loadAccessToken() {
		String token = "51522438-pZsjGmFgIDb1lSUzkktNXxfF2t49HZRKKYQ6J4Hx2";
		String tokenSecret = "b7mW9b4nwQ8vh9r0WkQjJzwrzWb3RD1IE7rBeScBsaVAR";
		return new AccessToken(token, tokenSecret);
	}
	
}
