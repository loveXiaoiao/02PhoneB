package com.example.phonebook;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ListView listView;
	List<String> titles = new ArrayList<String>();// 姓名
	List<String> texts = new ArrayList<String>();// 电话
	List<Bitmap> resIds = new ArrayList<Bitmap>();// 头像

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("PhoneBook");
		getContactInfo();
		listView = (ListView) this.findViewById(R.id.MyListItem);
		listView.setAdapter(new ListViewAdapter(titles, texts, resIds));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// 调用系统方法拨打电话
				Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + texts.get(position)));
				startActivity(dialIntent);
			}
		});

	}

	protected void getContactInfo() {

		// 获得通讯录信息 ，URI是ContactsContract.Contacts.CONTENT_URI
		Cursor cursor = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			// 获得通讯录中每个联系人的ID
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));

			// 获得通讯录中联系人的名字
			String name = cursor
					.getString(cursor
							.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

			String hasPhone = cursor
					.getString(cursor
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
			if (hasPhone.equalsIgnoreCase("1"))
				hasPhone = "true";
			else
				hasPhone = "false";
			// 如果有电话，根据联系人的ID查找到联系人的电话，电话可以是多个
			String phoneNumber = "";
			if (Boolean.parseBoolean(hasPhone)) {
				Cursor phones = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contactId, null, null);
				while (phones.moveToNext()) {
					phoneNumber = phones
							.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				}
			}
			resIds.add(getPersonPhoto(contactId));
			texts.add(phoneNumber);
			titles.add(name);
		}
		cursor.close();

	}

	// 通过联系人id获取头像
	public Bitmap getPersonPhoto(String PersonID) {
		String photo_id = null;
		String[] projection1 = new String[] { ContactsContract.Contacts.PHOTO_ID };
		String selection1 = ContactsContract.Contacts._ID + " = " + PersonID;
		Cursor cur1 = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, projection1, selection1,
				null, null);
		if (cur1.getCount() > 0) {
			cur1.moveToFirst();
			photo_id = cur1.getString(0);
		}
		String[] projection = new String[] { ContactsContract.Data.DATA15 };

		String selection = "ContactsContract.Data._ID = " + photo_id;

		Cursor cur = getContentResolver().query(
				ContactsContract.Data.CONTENT_URI, projection, selection, null,
				null);
		cur.moveToFirst();
		if (cur.getCount() < 0 || cur.getCount() == 0) {
			return null;
		}
		byte[] contactIcon = cur.getBlob(0);
		if (contactIcon == null) {
			return null;
		} else {
			InputStream inputStream = new ByteArrayInputStream(contactIcon);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			cur1.close();
			return bitmap;
		}

	}

	public class ListViewAdapter extends BaseAdapter {
		View[] itemViews;

		public ListViewAdapter(List<String> itemTitles, List<String> itemTexts,
				List<Bitmap> reIds) {
			itemViews = new View[itemTitles.size()];

			for (int i = 0; i < itemViews.length; i++) {
				itemViews[i] = makeItemView(itemTitles.get(i),
						itemTexts.get(i), reIds.get(i));
			}
		}

		@Override
		public int getCount() {
			return itemViews.length;
		}

		@Override
		public View getItem(int position) {
			return itemViews[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		private View makeItemView(String strTitle, String strText, Bitmap resId) {
			LayoutInflater inflater = (LayoutInflater) MainActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// 使用View的对象itemView与R.layout.item关联
			View itemView = inflater.inflate(R.layout.my_listitem, null);

			// 通过findViewById()方法实例R.layout.item内各组件
			TextView title = (TextView) itemView.findViewById(R.id.itemTitle);
			title.setText(strTitle);
			TextView text = (TextView) itemView.findViewById(R.id.itemText);
			text.setText(strText);
			ImageView image = (ImageView) itemView.findViewById(R.id.itemImage);

			// 如果是空就给一个默认头像
			if (resId != null) {
				image.setImageBitmap(resId);
			} else {
				image.setImageResource(R.drawable.ic_launcher);
			}
			return itemView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				return itemViews[position];
			return convertView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
