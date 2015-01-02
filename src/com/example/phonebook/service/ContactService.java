package com.example.phonebook.service;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import com.example.phonebook.entity.Contact;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactService {

	private final Context context;

	public ContactService(Context context) {
		this.context = context;
	}

	public List<Contact> getContacts() {
		List<Contact> contacts = new ArrayList<Contact>();
		String[] PHOTO_BITMAP_PROJECTION = new String[] {
			    ContactsContract.CommonDataKinds.Photo.PHOTO
			};
		// 获得通讯录信息 ，URI是ContactsContract.Contacts.CONTENT_URI
		Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			Contact contact = new Contact();
			// 获得通讯录中每个联系人的ID
			String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
           // 获得通讯录中联系人的名字
			String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			//获取通讯录电话
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			Long id = Long.parseLong(contactId);
			//获取联系人头像
			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),uri);
			Bitmap photo = BitmapFactory.decodeStream(input);
//			Bitmap photo = loadContactPhoto(context.getContentResolver(), id);
			contact.setPhoto(photo);
			contact.setName(displayName);
			contact.setTel(phoneNumber);
			contacts.add(contact);
		}
		cursor.close();

		return contacts;
	}
	public static Bitmap loadContactPhoto(ContentResolver cr, long  id) {
	    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
	    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
	    if (input == null) {
	        return null;
	    }
	    return BitmapFactory.decodeStream(input);
	}
	public Bitmap fetchThumbnail(int thumbnailId) {
		 String[] PHOTO_BITMAP_PROJECTION = new String[] {
		    ContactsContract.CommonDataKinds.Photo.PHOTO
		};
	     Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, thumbnailId);
	     Cursor cursor = context.getContentResolver().query(uri, PHOTO_BITMAP_PROJECTION, null, null, null);

	    try {
	        Bitmap thumbnail = null;
	        if (cursor.moveToFirst()) {
	            final byte[] thumbnailBytes = cursor.getBlob(0);
	            if (thumbnailBytes != null) {
	                thumbnail = BitmapFactory.decodeByteArray(thumbnailBytes, 0, thumbnailBytes.length);
	            }
	        }
	        return thumbnail;
	    }
	    finally {
	        cursor.close();
	    }
	}

}
