package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.ClientAgent.ClientAgentListener;

public class MainActivity extends AppCompatActivity {
    
    private ClientAgent mClientAgent;
    private Button mSendingButton;
    private Button mReceivingButton;
    private Button mDisconnectButton;
    private EditText mHostnameEdit;
    private EditText mPortEdit;
    private EditText mIntervalEdit;
    private EditText mSaveEdit;
    private EditText mInterfaceEdit;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	mClientAgent = new ClientAgent();
	
	mSendingButton = (Button) findViewById(R.id.btn_sending);
	mReceivingButton = (Button) findViewById(R.id.btn_receiving);	
	mDisconnectButton = (Button) findViewById(R.id.btn_disconnect);
	
	mHostnameEdit = (EditText) findViewById(R.id.edit_hostname);
	mPortEdit = (EditText) findViewById(R.id.edit_port);
	mIntervalEdit = (EditText) findViewById(R.id.edit_interval);
	mSaveEdit = (EditText) findViewById(R.id.edit_save);
	mInterfaceEdit = (EditText) findViewById(R.id.edit_interface);	

	mHostnameEdit.setText(ConfigUtil.getHostname(this));
	mPortEdit.setText("" + ConfigUtil.getPort(this));
	mIntervalEdit.setText("" + ConfigUtil.getInterval(this));
	mSaveEdit.setText(ConfigUtil.getSaveName(this));
	mInterfaceEdit.setText(ConfigUtil.getInterface(this));
	
	mDisconnectButton.setOnClickListener(onButtonsClick);
	mSendingButton.setOnClickListener(onButtonsClick);
	mReceivingButton.setOnClickListener(onButtonsClick);
    }

    OnClickListener onButtonsClick = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_sending:
		    mClientAgent.startSending(mHostnameEdit.getText().toString(),
					      Integer.parseInt(mPortEdit.getText().toString()),
					      Integer.parseInt(mIntervalEdit.getText().toString()),
					      mSaveEdit.getText().toString(),
					      mInterfaceEdit.getText().toString(),
					      mTestingListener);
		    saveConfig();
		    break;
		case R.id.btn_receiving:
		    mClientAgent.startReceiving(mHostnameEdit.getText().toString(),
						Integer.parseInt(mPortEdit.getText().toString()),
						Integer.parseInt(mIntervalEdit.getText().toString()),
						mSaveEdit.getText().toString(),
						mInterfaceEdit.getText().toString(),		
						mTestingListener);
		    saveConfig();
		    break;
		case R.id.btn_disconnect:
		    mClientAgent.disconnect();
		    break;
		}		
	    }
	};

    public void saveConfig() {
	ConfigUtil.setHostname(this, mHostnameEdit.getText().toString());
	ConfigUtil.setPort(this, Integer.parseInt(mPortEdit.getText().toString()));
	ConfigUtil.setInterval(this, Integer.parseInt(mIntervalEdit.getText().toString()));
	ConfigUtil.setSaveName(this, mSaveEdit.getText().toString());
	ConfigUtil.setInterface(this, mInterfaceEdit.getText().toString());
    }
    
    ClientAgentListener mTestingListener = new ClientAgentListener() {
	    @Override
	    public void onTestingEnded() {
		MainActivity.this.runOnUiThread(new Runnable() {
			public void run() {
			    Toast.makeText(MainActivity.this, "Finished!!!", Toast.LENGTH_SHORT);
			}
		    });

	    }
	};
}
