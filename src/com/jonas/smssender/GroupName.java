package com.jonas.smssender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GroupName extends Activity {
	private EditText nameText;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupname);
		nameText = (EditText)findViewById(R.id.group_name);
		Button btn = (Button)findViewById(R.id.btn_next);
		btn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				Send.CREATED_GROUP_NAME = GroupName.this.nameText.getText().toString();
				if(Send.CREATED_GROUP_NAME != null &&Send.CREATED_GROUP_NAME.length()>1){
					Intent intent = new Intent();
					intent.setClass(GroupName.this, Contact.class);
					startActivity(intent);
					GroupName.this.finish();
				}
			}
		});
	}
}
