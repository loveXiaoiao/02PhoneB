package com.example.phonebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.phonebook.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

	private final Context context;
	private List<Contact> contacts = new ArrayList<Contact>();

	public ListViewAdapter(Context context, List<Contact> contacts) {
		this.context = context;
		if (contacts != null) this.contacts = contacts;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contacts.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 使用Vliew的对象itemView与R.layout.item关联
			convertView = inflater.inflate(R.layout.my_listitem, null);
			holder = new ViewHolder();
			holder.contactName = (TextView) convertView
					.findViewById(R.id.itemTitle);
			holder.contactAvatar = (ImageView) convertView
					.findViewById(R.id.itemImage);
			holder.phone = (TextView) convertView.findViewById(R.id.itemText);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Contact contact = contacts.get(position);
		holder.contactName.setText(contact.getName());
		holder.phone.setText(contact.getTel());
		if (contact.getPhoto() != null) {
			holder.contactAvatar.setImageBitmap(contact.getPhoto());
		} else {
			holder.contactAvatar.setImageResource(R.drawable.ic_launcher);
		}
		return convertView;
	}

	private class ViewHolder {
		public TextView contactName;
		public TextView phone;
		public ImageView contactAvatar;
	}

}
