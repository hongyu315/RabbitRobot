package com.pps.rabbit.robot.http;

import android.content.Context;

import com.google.gson.Gson;
import com.pps.rabbit.robot.R;
import com.pps.rabbit.robot.module.ChatMsg;
import com.pps.rabbit.robot.module.ChatMsg.Type;
import com.pps.rabbit.robot.module.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class HttpUtils {

	private final static String key = "a09930fb38fd08cfa338b98b90633c6b";
	private final static String API = "http://www.tuling123.com/openapi/api";
	
	private static String getUrl(String msg){
		String url = "";
		
		try {
			url = API + "?key=" + key + "&info=" + URLEncoder.encode(msg, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	private static String doGet(String msg) throws IOException{
		URL url = new URL(getUrl(msg));
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.connect();
		
		InputStream inputStream = con.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
		StringBuffer sb = new StringBuffer();
		String line = "";
		while((line = bufferedReader.readLine()) != null){
			sb.append(line);
		}
		
		bufferedReader.close();
		con.disconnect();
		
		return sb.toString();
	}

	public static ChatMsg sendMessage(Context ctx, String msg){
		ChatMsg chatMsg = new ChatMsg();
		Result result = new Result();
		Gson gson = new Gson();
		
		try {
			String resultStr = doGet(msg);
			result = gson.fromJson(resultStr, Result.class);
			chatMsg.setContent(result.getText());
		} catch (IOException e) {
			chatMsg.setContent(ctx.getString(R.string.unavablable));
			e.printStackTrace();
		}
		
		chatMsg.setDate(new Date());
		chatMsg.setType(Type.Income);
		
		return chatMsg;
	}
}
