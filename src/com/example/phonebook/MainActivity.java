package com.example.phonebook;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.phonebook.entity.Contact;
import com.example.phonebook.service.ContactService;

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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ListView listView;
	List<Contact> contacts = new ArrayList<Contact>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("PhoneBook");
		contacts = new ContactService(this).getContacts();
		ListViewAdapter adapter = new ListViewAdapter(this,contacts);
		adapter.setData(contacts);
		listView = (ListView) this.findViewById(R.id.MyListItem);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// 调用系统方法拨打电话
				Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + contacts.get(position).getTel()));
				startActivity(dialIntent);
			}
		});

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
