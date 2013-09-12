package com.jonas.smssender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Send extends Activity {
	public static String CREATED_GROUP_NAME;
	// private EditText inputPhoneNumber;
	private EditText inputMsg;
	// private Button btnSelectMsg;
	private Button btnCreateGroup;
	private Button btnSend;

	// private static String messageContent = "";
	// private static String OrgNumber = "";
	// define all variables for database
	// private static final String DBNAME = "SMS";
	// private static final int VERSION = 1;
	private String groupName = "";
	// private static DBHelper dbhelper;
	// define a handler to handler thread message
	// private Handler sqlCreHandeler;
	// define a SharedPreferences to save settings
	// private SharedPreferences settings;
	// private boolean sqlCreated;
	private Spinner groupSpn;

	private final String TAG = "msgGroup";

	// private HashMap<String, String> tbNameMap;

	@Override
	protected void onResume() {
		super.onResume();

		// 取得回传的intent,判断是否包含需要的信息
		Intent intent = this.getIntent();
		// if (intent.hasExtra("Message")) {
		//
		// String newMsg = "";
		// Bundle bundle = this.getIntent().getExtras();
		// newMsg = bundle.getString("Message");
		// inputMsg.setText(newMsg);
		// }

		if (intent.hasExtra("contacts")) {
			Bundle bundle = this.getIntent().getExtras();
			ArrayList<String> list = bundle.getStringArrayList("contacts");
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File file = Environment.getExternalStorageDirectory();
				DocumentBuilderFactory docbfc = DocumentBuilderFactory
						.newInstance();
				File dataFile = new File(new File(file, "msgGroup"),
						"msgGroup.xml");
				try {
					DocumentBuilder docB;
					docB = docbfc.newDocumentBuilder();
					try {
						doc = docB.parse(dataFile);
					} catch (SAXException e) {
						doc = docB.newDocument();
						Element tempElement = doc.createElement("groups");
						doc.appendChild(tempElement);
					}

					Element root = doc.getDocumentElement();
					Element newGroup = doc.createElement("group");
					newGroup.setAttribute("name", CREATED_GROUP_NAME);
					for (Iterator<String> it = list.iterator(); it.hasNext();) {
						String data = it.next();
						String[] v = data.split(",");
						Element u = doc.createElement("u");
						u.setAttribute("uName", v[0]);
						u.setAttribute("phone", v[1]);
						u.setAttribute("nick", v[2]);
						newGroup.appendChild(u);
					}
					root.appendChild(newGroup);
					FileOutputStream fo;
					fo = new FileOutputStream(dataFile);
					XmlSerializer xs = XmlPullParserFactory.newInstance()
							.newSerializer();
					xs.setOutput(fo, "utf-8");
					xs.startDocument("UTF-8", true);
					createXMLString(root, xs);
					xs.endDocument();
					xs.flush();
					fo.flush();
					fo.close();
				} catch (FileNotFoundException e) {
				} catch (UnsupportedEncodingException e) {
				} catch (IOException e) {
				} catch (XmlPullParserException e) {
				} catch (ParserConfigurationException e) {
				}
			}
		}

	}

	private Document doc;

	private void createXMLString(Node root, XmlSerializer xs)
			throws IOException {
		String name = root.getNodeName();
		Log.d(TAG, "name:" + name);
		xs.startTag("", name);
		NamedNodeMap nnm = root.getAttributes();
		int size;
		int i;
		if (nnm != null) {
			size = nnm.getLength();
			for (i = 0; i < size; i++) {
				Node nd = nnm.item(i);
				// if(nd.getNodeType() == Node.TEXT_NODE){
				// continue;
				// }
				xs.attribute("", nd.getNodeName(), nd.getNodeValue());
			}
		}
		NodeList groups = root.getChildNodes();
		size = groups.getLength();
		for (i = 0; i < size; i++) {
			Node node = groups.item(i);
			createXMLString(node, xs);
		}
		xs.endTag("", name);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send);

		btnCreateGroup = (Button) this.findViewById(R.id.create_group);
		btnSend = (Button) this.findViewById(R.id.btnSend);
		inputMsg = (EditText) this.findViewById(R.id.InputMessage);
		ArrayList<String> arrList = new ArrayList<String>();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = Environment.getExternalStorageDirectory();
			File dirFile = new File(file, "msgGroup");
			if (!dirFile.exists() && !dirFile.mkdir()) {
				Log.e(TAG, "create dir error");
				this.finish();
			} else {
				Log.d(TAG, "dir ok");
			}
			File dataFile = new File(dirFile, "msgGroup.xml");
			if (!dataFile.exists()) {
				try {
					dataFile.createNewFile();
					FileWriter fw = new FileWriter(dataFile);
					fw.write("<groups></groups>");
					fw.close();
				} catch (IOException e) {
					Log.e(TAG, "create file error");
					this.finish();
				}
			}
			Log.d(TAG, "file ok");
			DocumentBuilderFactory docbfc = DocumentBuilderFactory
					.newInstance();

			try {
				DocumentBuilder docB = docbfc.newDocumentBuilder();
				doc = docB.parse(dataFile);
				NodeList ndls = doc.getDocumentElement().getElementsByTagName(
						"group");
				for (int i = 0; i < ndls.getLength(); i++) {
					arrList.add(ndls.item(i).getAttributes()
							.getNamedItem("name").getNodeValue());
				}
				Log.d(TAG, "length:" + ndls.getLength());
			} catch (ParserConfigurationException e1) {
			} catch (SAXException e) {
			} catch (IOException e) {
			}
		}

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrList);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		groupSpn = (Spinner) this.findViewById(R.id.groupIdSpinner);
		groupSpn.setAdapter(spinnerAdapter);
		groupSpn.setPrompt("select:");
		groupSpn.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				groupName = arg0.getItemAtPosition(arg2).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		btnCreateGroup.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Send.this, GroupName.class);
				startActivity(intent);
				Send.this.finish();
			}

		});
		// 短信发送部分
		btnSend.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO groupName
				NodeList list = doc.getDocumentElement().getElementsByTagName(
						"group");
				int len = list.getLength();
				Node node = null;
				for (int index = 0; index < len; index++) {
					node = list.item(index);
					if (groupName.equals(node.getAttributes()
							.getNamedItem("name").getNodeValue())) {
						break;
					}
				}
				sendList = node.getChildNodes();
				sendIndex = 0;
				ramMsg = inputMsg.getText().toString();
				pintent = PendingIntent.getBroadcast(Send.this, 0, new Intent(
						"SMS_SENT"), 0);
				SMSReceiver smsreceiver = new SMSReceiver();
				IntentFilter receiverFilter = new IntentFilter("SMS_SENT");
				registerReceiver(smsreceiver, receiverFilter);
				sendMsg(getNextNode());
			}

			// }

		});
	}

	private NodeList sendList;
	private int sendIndex;
	private String ramMsg;
	private PendingIntent pintent;

	private Node getNextNode() {
		Node node = null;
		int len = sendList.getLength();
		do {
			node = null;
			if (sendIndex > len) {
				break;
			}
			node = sendList.item(sendIndex);
			sendIndex++;
		} while (node != null && node.getNodeType() != Node.ELEMENT_NODE);
		return node;
	}

	private void sendMsg(Node node) {
		if (node == null) {
			sendList = null;
			pintent = null;
			return;
		}
		NamedNodeMap attrs = node.getAttributes();
		String phoneNum = attrs.getNamedItem("phone").getNodeValue();
		SmsManager smsManager = SmsManager.getDefault();
		String nick = "";
		if (attrs.getNamedItem("nick") != null) {
			nick = attrs.getNamedItem("nick").getNodeValue();
		}
		String smsContent = ramMsg.replace("xxx", nick);

		smsManager.sendTextMessage(phoneNum, null, smsContent, pintent, null);
		Toast.makeText(getApplicationContext(),
				"to:" + phoneNum + "\n->" + smsContent, Toast.LENGTH_SHORT)
				.show();
		sendIndex++;
	}

	public class SMSReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {

			int resultCode = getResultCode();
			CharSequence result = null;
			switch (resultCode) {
			case Activity.RESULT_OK:
				result = "send ok";
				break;
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				result = "send failed";
				break;
			case SmsManager.RESULT_ERROR_NO_SERVICE:
				result = "send failed no service";
				break;
			case SmsManager.RESULT_ERROR_NULL_PDU:
				result = "send fail no pud";
				break;
			case SmsManager.RESULT_ERROR_RADIO_OFF:
				result = "send fail radio off";
				break;
			}
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT)
					.show();
			sendMsg(getNextNode());
		}
	}

	public class SmsSender implements Runnable {
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	// 监听BACK按钮，显示退出对话框
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Builder exitDialog = new AlertDialog.Builder(Send.this);
			exitDialog.setTitle("确定退出");
			exitDialog.setMessage("真的要退出吗？");
			exitDialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Send.this.getIntent().setFlags(
									Intent.FLAG_ACTIVITY_CLEAR_TOP);
							Send.this.finish();
						}

					});
			exitDialog.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			exitDialog.show();

			break;
		}
		return super.onKeyDown(keyCode, event);

	}

	// override run() method to implement init database and send Msg to main
	// Thread
	// class sqlThread extends Thread {
	//
	// @Override
	// public void run() {
	// dbhelper = new DBHelper(getApplicationContext(), DBNAME, null,
	// VERSION);
	// // SQLiteDatabase db = dbhelper.getWritableDatabase();
	// Message toMain = sqlCreHandeler.obtainMessage();
	//
	// toMain.obj = "Database has been created";
	//
	// sqlCreHandeler.sendMessage(toMain);
	// }
	//
	// }

}
