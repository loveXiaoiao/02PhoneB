package com.example.phonebook;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.example.phonebook.entity.Contact;
import com.example.phonebook.service.ContactService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	ListView listView;
	List<Contact> contacts = new ArrayList<Contact>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("PhoneBook");
		contacts = new ContactService(this).getContacts();
		ListViewAdapter adapter = new ListViewAdapter(this, contacts);
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
