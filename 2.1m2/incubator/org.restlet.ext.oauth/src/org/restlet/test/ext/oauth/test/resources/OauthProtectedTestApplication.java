package org.restlet.test.ext.oauth.test.resources;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.ext.oauth.protectedresource.RemoteAuthorizer;
import org.restlet.routing.Router;
import org.restlet.test.ext.oauth.provider.AuthorizationServerTest;

public class OauthProtectedTestApplication extends Application {
	@Override
	public synchronized Restlet createInboundRoot() {
		Context ctx = getContext();
		Router router = new Router(ctx);
		
		RemoteAuthorizer auth = new RemoteAuthorizer(
				AuthorizationServerTest.prot+"://localhost:"
				+AuthorizationServerTest.serverPort+
			"/oauth/validate",
			AuthorizationServerTest.prot+"://localhost:"+
			AuthorizationServerTest.serverPort+"/oauth/authorize"
			);
		auth.setNext(DummyResource.class);
		router.attach("/protected",auth);
		
		RemoteAuthorizer auth2 = new RemoteAuthorizer(
				AuthorizationServerTest.prot+"://localhost:"
				+AuthorizationServerTest.serverPort+
			"/oauth/validate",
			AuthorizationServerTest.prot+"://localhost:"+
			AuthorizationServerTest.serverPort+"/oauth/authorize"
			);
		auth2.setNext(ScopedDummyResource.class);
		router.attach("/scoped",auth2);
		
		return router;
	}

}
