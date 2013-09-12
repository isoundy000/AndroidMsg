package com.jonas.smssender;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class Contact extends Activity {

	private ArrayList<ContactItem> listItem;
	private ListView listContact;
	private ArrayList<String> phoneData;
//	private int positon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		listContact = (ListView) this.findViewById(R.id.listContact);

		listItem = new ArrayList<ContactItem>();
		phoneData = new ArrayList<String>();
		Cursor cur = getContentResolver().query(
				Phone.CONTENT_URI,
				null,
				Phone.TYPE + "=" + Phone.TYPE_MOBILE,
				null, null);
		while (cur.moveToNext()) {
			ContactItem item = new ContactItem(cur.getString(cur.getColumnIndex(Phone.DISPLAY_NAME)),cur.getString(cur.getColumnIndex(Phone.NUMBER)));
			listItem.add(item);

		}
		cur.close();
		final MyAdapter adapter = new MyAdapter(this, listItem);
		listContact.setAdapter(adapter);
		listContact.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listContact.setFocusable(true);
		listContact.setItemsCanFocus(true);

		// remove "-" from Phonenumber
		listContact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				CheckBox cb = (CheckBox) arg0.getChildAt(0).findViewById(
						R.id.cbListItem);
				if (adapter.checkstate.get(arg2)){
					phoneData.remove(listItem.get(arg2).toString());
					cb.setChecked(false);
				} else {
					phoneData.add(listItem.get(arg2).toString());
					cb.setChecked(true);
				}
				adapter.checkstate.put(arg2, cb.isChecked());
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, 0, R.string.OK);
		menu.add(0, 1, 1, R.string.Cancel);
		// return super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// return super.onOptionsItemSelected(item);
		int item_id = item.getItemId();
		Bundle selectedContact = new Bundle();
		Intent intent = new Intent();
		switch (item_id) {
		case 0:
			selectedContact.putStringArrayList("contacts", phoneData);
			intent.putExtras(selectedContact);
			intent.setClass(Contact.this, Send.class);
			startActivity(intent);
			Contact.this.finish();
			break;
		case 1:
			intent.setClass(Contact.this, Send.class);
			startActivity(intent);
			Contact.this.finish();
			break;
		}
		return true;
	}

	public class MyAdapter extends BaseAdapter {

		private Context context;

		private ArrayList<ContactItem> list;

		private LayoutInflater mInflater;

		private HashMap<Integer, Boolean> checkstate;

		public MyAdapter(Context ct, ArrayList<ContactItem> lt) {

			context = ct;

			list = lt;

			mInflater = LayoutInflater.from(context);

			checkstate = new HashMap<Integer, Boolean>();
			for (int i = 0; i < lt.size(); i++) {
				checkstate.put(i, false);
			}

		}

		public final class ViewHolder {

			public TextView tvname;

			public TextView tvphone;

			public CheckBox checkbox;
		}

		@Override
		public int getCount() {

			// TODO Auto-generated method stub

			// ����List��size

			if (list != null) {

				return list.size();

			} else {

				return 0;

			}

		}

		@Override
		public Object getItem(int position) {

			// TODO Auto-generated method stub

			if (list != null) {

				// ����ĳ��λ�õ�Map<String,Object>

				return list.get(position);

			} else {

				return null;

			}
		}

		@Override
		public long getItemId(int position) {

			// TODO Auto-generated method stub

			// ���ص�ǰλ��

			return position;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// TODO Auto-generated method stub

			// ����ĳ��λ�õ�View

			ViewHolder holder = null;

			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.listsytle, null);

				holder.tvname = (TextView) convertView
						.findViewById(R.id.topTextView);

				holder.tvphone = (TextView) convertView
						.findViewById(R.id.bottomTextView);

				holder.checkbox = (CheckBox) convertView
						.findViewById(R.id.cbListItem);
				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();

			}

			holder.tvname.setText(list.get(position).uName);

			holder.tvphone.setText(list.get(position).phoneNum);

			holder.checkbox.setChecked(checkstate.get(position));

			return convertView;

		}
	}
}
class ContactItem{
	public ContactItem(String name,String phone){
		this(name,phone,name);
	}
	public ContactItem(String name,String phone,String nick){
		uName = name;
		phoneNum = phone;
		this.nick = nick;
	}
	public String uName;
	public String phoneNum;
	public String nick;
	public String toString(){
		return uName+","+phoneNum+","+nick;
	}
	
	public String getShowName(){
		return uName+"("+nick+")";
	}
}
