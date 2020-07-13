package com.example.myapplication;

import android.util.Log;

public class ClientAgent {
    
    private int sock;
    private static boolean ongoing = true;
    
    static {
	System.loadLibrary("native-lib");
    }

    private native int init(String hostname, int port);
    private native void sendData(int sock, String data);
    private native String recvData(int sock);
    private native void disconnect(int sock);
    
    // public void initConnection(String hostname, int port) {
    // 	sock = init(hostname, port);
    // 	// sendData(sock, "Hello World");
    // 	// disconnect(sock);
    // }

    public void startSending(final String hostname, final int port) {
	ongoing = true;
	Thread t = new Thread(new Runnable() {
		@Override
		public void run() {
		    sock = init(hostname, port);
		    byte[] bytes = new byte[1440];	
		    while (ongoing) {
			String sdata = new String(bytes);
			sendData(sock, sdata);
		    }
		    disconnect(sock);
		}
	    });
	t.start();
    }

    public void stopSending() {
	ongoing = false;
    }

    
}
