package com.pps.rabbit.robot;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.rabbit.robot.adapter.ChatMsgAdapter;
import com.pps.rabbit.robot.http.HttpUtils;
import com.pps.rabbit.robot.module.ChatMsg;
import com.pps.rabbit.robot.module.ChatMsg.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity implements
		OnClickListener {

	ImageButton backBtn;
	Button backToGameBtn, sendBtn;
	EditText inputEdit;
	TextView titleTxt;
	ListView listView;

	String inputStr;

	ChatMsgAdapter adapter = null;
	List<ChatMsg> chatMsgList = new ArrayList<ChatMsg>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initViews();
		setValues();
	}

	private void setValues() {
		titleTxt.setText(getString(R.string.app_name));

		ChatMsg msg = new ChatMsg();
		msg.setDate(new Date());
		msg.setContent(getString(R.string.say_hi));
		msg.setType(Type.Income);

		chatMsgList.add(msg);

		adapter = new ChatMsgAdapter(this, chatMsgList);
		listView.setAdapter(adapter);
		listView.setSelection(chatMsgList.size() - 1);
	}

	private void initViews() {
		backBtn = (ImageButton) findViewById(R.id.ppsgame_slider_back_pre);
		backToGameBtn = (Button) findViewById(R.id.ppsgame_slider_back_game);
		sendBtn = (Button) findViewById(R.id.id_send_msg);
		inputEdit = (EditText) findViewById(R.id.id_input_msg);
		titleTxt = (TextView) findViewById(R.id.ppsgame_slider_title);
		listView = (ListView) findViewById(R.id.id_listview_msgs);

		backBtn.setOnClickListener(this);
		backToGameBtn.setOnClickListener(this);
		sendBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ppsgame_slider_back_pre:
		case R.id.ppsgame_slider_back_game:
			finish();
			break;
		case R.id.id_send_msg:
			onSendBtnClick();
		default:
			break;
		}
	}

	private void onSendBtnClick() {
		inputStr = inputEdit.getText().toString().trim();
		inputEdit.setText("");
		
		if (TextUtils.isEmpty(inputStr)) {
			Toast.makeText(MainActivity.this, getString(R.string.no_empty_input), Toast.LENGTH_SHORT).show();
			return;
		}
		
		ChatMsg msg = new ChatMsg();
		msg.setContent(inputStr);
		msg.setDate(new Date());
		msg.setType(Type.Outcome);
		chatMsgList.add(msg);
		adapter.notifyDataSetChanged();
		listView.setSelection(chatMsgList.size() - 1);
	
		talkToRabbit(inputStr);
	}
	
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ChatMsg chatMsg = (ChatMsg) msg.obj;
			chatMsgList.add(chatMsg);
			adapter.notifyDataSetChanged();
			listView.setSelection(chatMsgList.size() - 1);
		}
	};

	private void talkToRabbit(final String word) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				ChatMsg msg = HttpUtils.sendMessage(MainActivity.this,word);
				Message m = Message.obtain();
				m.obj = msg;
				handler.sendMessage(m);
			}
		}).start();
	}

}
