package com.example.phonebook.service;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.example.phonebook.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactService {

	private final Context context;

	public ContactService(Context context) {
		this.context = context;
	}

	public List<Contact> getContacts() {
		List<Contact> contacts = new ArrayList<Contact>();

		// 获得通讯录信息 ，URI是ContactsContract.Contacts.CONTENT_URI
		Cursor cursor = context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			Contact contact = new Contact();
			// 获得通讯录中每个联系人的ID
			String displayName = cursor.getString(cursor
					.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

//            // 获得通讯录中联系人的名字
//			String name = cursor
//					.getString(cursor
//							.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
//
//			String hasPhone = cursor
//					.getString(cursor
//							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//			if (hasPhone.equalsIgnoreCase("1"))
//				hasPhone = "true";
//			else
//				hasPhone = "false";
//			// 如果有电话，根据联系人的ID查找到联系人的电话，电话可以是多个
//			String phoneNumber = "";
//			Cursor phone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
//            while(phone.moveToNext())
//            {
//            	phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            }
//
//			Long id = Long.parseLong(contactId);
//			Uri uri = ContentUris.withAppendedId(
//					ContactsContract.Contacts.CONTENT_URI, id);
//			InputStream input = ContactsContract.Contacts
//					.openContactPhotoInputStream(context.getContentResolver(),
//							uri);
//			Bitmap photo = BitmapFactory.decodeStream(input);
//			contact.setPhoto(photo);
			contact.setName(displayName);
			contact.setTel(phoneNumber);
			contacts.add(contact);
		}
		cursor.close();

		return contacts;
	}

}
