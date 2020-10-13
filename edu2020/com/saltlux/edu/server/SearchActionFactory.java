package com.saltlux.edu.server;

import com.saltlux.nrpc.AbstractHandlers;

public class SearchActionFactory extends AbstractHandlers {
	
	public SearchActionFactory() {
		registerHandler(new HelloLucene());
	}
}
