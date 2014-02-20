package by.colorlife.economist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class CurrencyEdit extends Activity{
	Currency curr;
	long currId;
	DB cDB;
	int cbDefault;
	int cbShowShortName;
	int cbShortNameBefore;
	int cbShortNameSpace;
	int cbDelimiter;
	
	ImageView edCurrFlagIV;
	ImageView edCurrFlag1IV;
	EditText edCurrNameET;
	EditText edCurrShortNameET;
	CheckBox edCurrShowShortNameCB;
	CheckBox edCurrShortNameBeforeCB;
	CheckBox edCurrShortNameSpaceCB;
	CheckBox edCurrDelimiterCB;
	EditText edCurrISOET;
	EditText edCurrNumISOET;
	TextView edCurrDecimalTV;
	CheckBox edCurrDefaultCB;
	TextView edCurrExampleTV;
	SeekBar  edCurrDecimalSB;
	
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.currencyedit);
	    
	    edCurrFlagIV = (ImageView) findViewById(R.id.edCurrFlagIV);
	    edCurrFlag1IV = (ImageView) findViewById(R.id.edCurrFlag1IV);
	    edCurrNameET = (EditText) findViewById(R.id.edCurrNameET);
	    edCurrShortNameET = (EditText) findViewById(R.id.edCurrShortNameET);
		edCurrShowShortNameCB = (CheckBox) findViewById(R.id.showShortNameCB);
		edCurrShortNameBeforeCB = (CheckBox) findViewById(R.id.shortNameBeforeCB);
		edCurrShortNameSpaceCB = (CheckBox) findViewById(R.id.shortNameSpaceCB);
		edCurrDelimiterCB = (CheckBox) findViewById(R.id.DelimiterCB);
	    edCurrISOET = (EditText) findViewById(R.id.edCurrISOET);
	    edCurrNumISOET = (EditText) findViewById(R.id.edCurrNumISOET);
	    edCurrDecimalTV = (TextView) findViewById(R.id.edCurrDecimalTV);
	    edCurrDefaultCB = (CheckBox) findViewById(R.id.edCurrDefaultCB);
	    edCurrDecimalSB = (SeekBar) findViewById(R.id.edCurrDecimalSB);
	    
	    edCurrExampleTV = (TextView) findViewById(R.id.edCurrExampleTV);
	    
	    Intent intent = getIntent();
        currId = intent.getLongExtra("currId",-1);
        //cDB = new DB(this);
	    curr = EconomistMain.db.getCurrency(currId);
	    
	    edCurrFlagIV.setImageResource(curr.getFlagId());
	    edCurrFlag1IV.setImageResource(curr.getFlagId());
	    edCurrNameET.setText(curr.getName());
	    edCurrShortNameET.setText(curr.getShortName());
	    edCurrISOET.setText(curr.getISO());
	    edCurrNumISOET.setText(String.valueOf(curr.getNumISO()));
	    edCurrDecimalTV.setText(String.valueOf(curr.getDecimal()));
	    if (curr.getDefault()>0) {edCurrDefaultCB.setChecked(true);} else {edCurrDefaultCB.setChecked(false);}
	    if (curr.getShowShortName()>0) {edCurrShowShortNameCB.setChecked(true);} else {edCurrShowShortNameCB.setChecked(false);}
	    if (curr.getShortNameBefore()>0) {edCurrShortNameBeforeCB.setChecked(true);} else {edCurrShortNameBeforeCB.setChecked(false);}
	    if (curr.getShortNameSpace()>0) {edCurrShortNameSpaceCB.setChecked(true);} else {edCurrShortNameSpaceCB.setChecked(false);}
	    if (curr.getDelimiter()>0) {edCurrDelimiterCB.setChecked(true);} else {edCurrDelimiterCB.setChecked(false);}
	    edCurrDecimalSB.setProgress(curr.getDecimal());
	    
	    
	    redrawExample();
	    
	    edCurrDecimalSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
	        public void onProgressChanged(SeekBar seekBark, int progress, boolean fromUser) {
	        edCurrDecimalTV.setText(String.valueOf(progress));
	        redrawExample();}
	        public void onStopTrackingTouch(SeekBar seekBar) {}
	        public void onStartTrackingTouch(SeekBar seekBar) {}
	        });
	    
	    
	    //К полю редатирования символа валюты подключаем слушателей на изменение текста
	    edCurrShortNameET.addTextChangedListener(new TextWatcher(){
	        @Override
	        public void afterTextChanged(Editable s) {
	        	//Log.d(AppData.LOG_TAG, "afterTextChanged --- "+s.toString());
	        	redrawExample();}
	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) { }
	    });

}
	
	public void onClick(View view) {
		redrawExample();
	}
	
	public void  edCurrRateBTClick (View view) {
		
	}
	
	
	public void redrawExample() {
    	edCurrExampleTV.setText(AppData.getMoneyS(Double.parseDouble(getString(R.string.currency_example_data)),
    			Integer.parseInt(edCurrDecimalTV.getText().toString()),
				edCurrShortNameET.getText().toString(),
				edCurrShowShortNameCB.isChecked()?1:0,
				edCurrShortNameBeforeCB.isChecked()?1:0,
				edCurrShortNameSpaceCB.isChecked()?1:0,
				edCurrDelimiterCB.isChecked()?1:0));	        	
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		openQuitDialog();
	}

	private void openQuitDialog() {
		// Проверяем правильность заполнения полей
		if (edCurrNameET.getText().toString().isEmpty()) {
		  Toast.makeText(this, String.format(getString(R.string.currency_wrongfield),getString(R.string.currency_name)), Toast.LENGTH_SHORT).show();
		  return;
		}
		if (edCurrISOET.getText().toString().isEmpty()) {
			  Toast.makeText(this, String.format(getString(R.string.currency_wrongfield),getString(R.string.currency_iso)), Toast.LENGTH_SHORT).show();
			  return;
		}
		if (edCurrNumISOET.getText().toString().isEmpty()) {
			  Toast.makeText(this, String.format(getString(R.string.currency_wrongfield),getString(R.string.currency_numiso)), Toast.LENGTH_SHORT).show();
			  return;
		}

		
		// проверяем, были ли внесены изменения		
			cbDefault=0; if (edCurrDefaultCB.isChecked()) cbDefault=1;
			cbShowShortName=0; if (edCurrShowShortNameCB.isChecked()) cbShowShortName=1;
			cbShortNameBefore=0; if (edCurrShortNameBeforeCB.isChecked()) cbShortNameBefore=1;
			cbShortNameSpace=0; if (edCurrShortNameSpaceCB.isChecked()) cbShortNameSpace=1;
			cbDelimiter = edCurrDelimiterCB.isChecked()?1:0;
			
		if (edCurrNameET.getText().toString().equalsIgnoreCase(curr.getName()) && 
			edCurrShortNameET.getText().toString().equalsIgnoreCase(curr.getShortName()) &&
			edCurrISOET.getText().toString().equalsIgnoreCase(curr.getISO()) &&
			edCurrNumISOET.getText().toString().equalsIgnoreCase(String.valueOf(curr.getNumISO())) &&
			edCurrDecimalTV.getText().toString().equalsIgnoreCase(String.valueOf(curr.getDecimal())) &&
			cbShowShortName == curr.getShowShortName() &&
			cbShortNameBefore == curr.getShortNameBefore() &&
			cbShortNameSpace == curr.getShortNameSpace() &&
			cbDefault == curr.getDefault() &&
			cbDelimiter == curr.getDelimiter() ){
			Intent intent = new Intent();
		    intent.putExtra("needupd", 3);
		    setResult(RESULT_OK, intent);
			finish();
			return;
		}
		
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				CurrencyEdit.this);
		quitDialog.setTitle(R.string.currency_saverequest);

		quitDialog.setPositiveButton(R.string.yes, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Сохраняем
		    
				curr.setName(edCurrNameET.getText().toString());
				curr.setShortName(edCurrShortNameET.getText().toString());
				curr.setISO(edCurrISOET.getText().toString());
				curr.setNumISO(Integer.parseInt(edCurrNumISOET.getText().toString()));
				curr.setDecimal(Integer.parseInt(edCurrDecimalTV.getText().toString()));
				curr.setDefault(cbDefault);
				curr.setShowShortName(cbShowShortName);
				curr.setShortNameBefore(cbShortNameBefore);
				curr.setShortNameSpace(cbShortNameSpace);
				curr.setDelimiter(cbDelimiter);
				EconomistMain.db.updateCurrency(curr);
				
				Intent intent = new Intent();
			    intent.putExtra("needupd", 1);
			    setResult(RESULT_OK, intent);
				finish();
			}
		});

		quitDialog.setNegativeButton(R.string.no, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
			    intent.putExtra("needupd", 2);
			    setResult(RESULT_OK, intent);
				finish();
			}
		});

		quitDialog.show();
	}

	
}
