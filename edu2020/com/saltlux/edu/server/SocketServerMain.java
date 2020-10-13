package com.saltlux.edu.server;

import com.saltlux.nrpc.AbstractHandlers;
import com.saltlux.nrpc.RESTServer;

public class SocketServerMain {

	private static RESTServer rserver = null;

	public void startSocket(int port, int thread, AbstractHandlers factory) throws Exception {

		rserver = new RESTServer(port, factory, true);
		boolean isOk = rserver.start(thread << 1, thread);
		if (isOk) {
			System.out.println("NLU REST Server Start ... Port : " + port);
		} else {
			throw new Exception("Socket Create failure: REST Server port=" + port);
		}
	}

	public static void main(String[] args) throws Exception {
		SocketServerMain runner = new SocketServerMain();
		runner.startSocket(8888, 10, new SearchActionFactory());
	}
}
