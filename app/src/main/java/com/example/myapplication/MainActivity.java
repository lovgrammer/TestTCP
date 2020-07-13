package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    
    private ClientAgent mClientAgent;
    private Button mConnectButton;
    private Button mDisconnectButton;
    private EditText mHostnameEdit;
    private EditText mPortEdit;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	mClientAgent = new ClientAgent();
	mConnectButton = (Button) findViewById(R.id.btn_connect);
	mDisconnectButton = (Button) findViewById(R.id.btn_disconnect);
	mHostnameEdit = (EditText) findViewById(R.id.edit_hostname);
	mPortEdit = (EditText) findViewById(R.id.edit_port);
	mDisconnectButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		    switch(v.getId()) {
		    case R.id.btn_disconnect:
			mClientAgent.stopSending();
			break;
		    }
		}
	    });	
	mConnectButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		    switch(v.getId()) {
		    case R.id.btn_connect:
			mClientAgent.startSending(mHostnameEdit.getText().toString(),
						  Integer.parseInt(mPortEdit.getText().toString()));
			break;
		    }
		}
	    });
    }
}
