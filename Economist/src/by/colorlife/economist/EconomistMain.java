package by.colorlife.economist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;



public class EconomistMain extends FragmentActivity {

	public double calcValue;
	public static DB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.economistmain);
		
        db = new DB(this);
        db.open();
        calcValue = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
        return true;
	}

	
	protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
      }

	public void accStart(View view){
		Intent intent = new Intent(this, AccountWork.class);
		startActivity(intent);
	}


	public void currStart(View view){
		Intent intent = new Intent(this, CurrencyWork.class);
		startActivity(intent);
	}
	
	public void calcStart(View view){
		Intent intent = new Intent(this, CalculatorWork.class);
		intent.putExtra("calcValue", calcValue);
		startActivityForResult(intent,33);
	}

}
