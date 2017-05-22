package me.waltster.Jem;

import com.rollbar.Rollbar;

public class ErrorHandler {
	private static final String SERVER_POST_ACCESS_TOKEN = "a46083a83e7441a9b53e242571cec93a";
    private static final String ENVIRONMENT = "production";
    private static Rollbar r;
    
    public static void init(){
    	r = new Rollbar(SERVER_POST_ACCESS_TOKEN, ENVIRONMENT);
    	r.handleUncaughtErrors();
    }
}
