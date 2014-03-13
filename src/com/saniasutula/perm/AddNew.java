package com.saniasutula.perm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNew extends Activity{
	
	
	public final static String THIEF = "com.saniasutula";
	EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new);
        et = (EditText)findViewById(R.id.editText1);
        et.setText("Name");
    }
    
    public void onClick(View v){
    	Intent answerInent = new Intent();
    	answerInent.putExtra(THIEF, et.getText().toString());
    	setResult(RESULT_OK, answerInent);
    	et.setText("");
    	finish();
    }
}
