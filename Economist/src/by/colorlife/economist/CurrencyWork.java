package by.colorlife.economist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class CurrencyWork extends FragmentActivity implements LoaderCallbacks<Cursor> {
	Context ctx;
	ListView lvData;
	CurrencyAdapter currAdapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currencywork);
        ctx = this;
       

     // формируем столбцы сопоставления
        String[] from = {AppData.COLUMN_CURR_NAME, AppData.COLUMN_CURR_ISO};
        int[] to = { R.id.nameTV, R.id.isoTV};
        
     // создааем адаптер и настраиваем список
        currAdapter = new CurrencyAdapter(this, R.layout.currencyitem, null, from, to, 0);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(currAdapter);
        lvData.setOnItemClickListener(new OnItemClickListener() {
            	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            		{
            		Intent intent = new Intent(CurrencyWork.this, CurrencyEdit.class);
            		intent.putExtra("currId", id);
            		startActivityForResult(intent,33);
            		}
        		});
     // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);
        
        registerForContextMenu(lvData);
        }
    
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (data == null) {return;}
	    int nu = data.getIntExtra("needupd",0);
	    //Toast.makeText(this, "Request=" + requestCode + "  ResultCode="+ resultCode+ "  Need update=" + String.valueOf(nu), Toast.LENGTH_LONG).show();
	    if (nu==1) getSupportLoaderManager().getLoader(0).forceLoad();
	  }

/*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/     
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
        ContextMenuInfo menuInfo) {
      // TODO Auto-generated method stub
      menu.add(0, 1, 0, R.string.currency_set_defaut);
    } 
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      // пункты меню для tvColor
    case 1:
    	// получаем из пункта контекстного меню данные по пункту списка 
        AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
        // извлекаем id записи
        Currency curr = EconomistMain.db.getCurrency(acmi.id);
        curr.setDefault(1);
        EconomistMain.db.updateCurrency(curr);
        getSupportLoaderManager().getLoader(0).forceLoad();
      break;
      }
      return super.onContextItemSelected(item);
    }
    
    @Override
	public void onBackPressed() {
		//super.onBackPressed();
	if (EconomistMain.db.defautCurrencyCount()==0) {
		Toast.makeText(ctx,R.string.currency_defaut_not_selected , Toast.LENGTH_LONG).show();
	}
	else {
		finish();
	}
	}
    
    
        
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
      return new MyCursorLoader(this,EconomistMain.db);
    }
    
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    	currAdapter.swapCursor(cursor);
    }
    
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
    
    static class MyCursorLoader extends CursorLoader {

        DB db;
        
        public MyCursorLoader(Context context, DB db) {
          super(context);
          this.db = db;
        }
        
        @Override
        public Cursor loadInBackground() {
          Cursor cursor = db.getAllCurrencyCursor();
          return cursor;
        }
      }
}
