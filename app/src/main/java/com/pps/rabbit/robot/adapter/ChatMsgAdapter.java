package com.pps.rabbit.robot.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pps.rabbit.robot.R;
import com.pps.rabbit.robot.module.ChatMsg;
import com.pps.rabbit.robot.module.ChatMsg.Type;

public class ChatMsgAdapter extends BaseAdapter {
	List<ChatMsg> chatMsgList = null;
	LayoutInflater layoutInflater = null;
	Context mContext = null;

	public ChatMsgAdapter() {
	}

	public ChatMsgAdapter(Context ctx, List<ChatMsg> list) {
		this.mContext = ctx;
		this.chatMsgList = list;
		this.layoutInflater = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
		return chatMsgList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMsg msg = chatMsgList.get(position);
		if (msg.getType() == Type.Income) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		ChatMsg msg = chatMsgList.get(position);

		if (convertView == null) {
			if (msg.getType() == Type.Income) {
				convertView = layoutInflater.inflate(R.layout.item_from_msg,
						null);
				viewHolder = new ViewHolder();
				viewHolder.timeTxt = (TextView) convertView
						.findViewById(R.id.id_form_msg_date);
				viewHolder.contentTxt = (TextView) convertView
						.findViewById(R.id.id_from_msg_info);
			} else {
				convertView = layoutInflater
						.inflate(R.layout.item_to_msg, null);
				viewHolder = new ViewHolder();
				viewHolder.timeTxt = (TextView) convertView
						.findViewById(R.id.id_to_msg_date);
				viewHolder.contentTxt = (TextView) convertView
						.findViewById(R.id.id_to_msg_info);
			}
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewHolder.timeTxt.setText(format.format(msg.getDate()));
		viewHolder.contentTxt.setText(msg.getContent());
		
		return convertView;
	}

	private class ViewHolder {
		TextView timeTxt;
		// TextView nameTxt;
		TextView contentTxt;
	}
}
