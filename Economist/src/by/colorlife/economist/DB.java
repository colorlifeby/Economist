package by.colorlife.economist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DB {
	
	private final Context ctx;
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	  
	
	public DB(Context context){
		  ctx = context;}
	
	// открыть подключение
	  public void open() {
	    mDBHelper = new DBHelper(ctx, AppData.DB_NAME, null, AppData.DB_VERSION);
	    mDB = mDBHelper.getWritableDatabase();
	  }
	  
	  // закрыть подключение
	  public void close() {
	    if (mDBHelper!=null) mDBHelper.close();
	  }
	  
      public Cursor getAllCurrencyCursor() {
		return mDB.query(AppData.DB_CURRENCY, null, null, null, null, null, AppData.COLUMN_CURR_NAME);
	  }
	  
      
	
	public List<Currency> getAllCurrency() {
		  List<Currency> currencyList = new ArrayList<Currency>();
		  Cursor cursor = mDB.rawQuery(AppData.DB_CURRENCY_SELECT_ALL, null);
		  if(cursor.moveToFirst()) {
		   do {
		    Currency currency = new Currency(ctx);
		    currency.setId(Integer.parseInt(cursor.getString(0)));
		    currency.setDefault(Integer.parseInt(cursor.getString(1)));
		    currency.setFlag(cursor.getString(2));
		    currency.setName(cursor.getString(3));
		    currency.setISO(cursor.getString(4));
		    currency.setNumISO(Integer.parseInt(cursor.getString(5)));
		    currency.setShortName(cursor.getString(6));
		    currency.setDecimal(Integer.parseInt(cursor.getString(7)));
		    currency.setShowShortName(Integer.parseInt(cursor.getString(8)));
		    currency.setShortNameBefore(Integer.parseInt(cursor.getString(9)));
		    currency.setShortNameSpace(Integer.parseInt(cursor.getString(10)));
		    currency.setDelimiter(Integer.parseInt(cursor.getString(11)));
		    
		    currencyList.add(currency);
		   } while(cursor.moveToNext());
		  }
		  return currencyList;
		 }
	
	
	public Currency getCurrency(long id) {
		  Cursor cursor = mDB.query(AppData.DB_CURRENCY, 
				  new String[] 
				  { AppData.COLUMN_CURR_ID,
				    AppData.COLUMN_CURR_DEFAULT,
				    AppData.COLUMN_CURR_FLAG,
				    AppData.COLUMN_CURR_NAME,
				    AppData.COLUMN_CURR_ISO,
				    AppData.COLUMN_CURR_NUMISO,
				    AppData.COLUMN_CURR_SHORTNAME,
				    AppData.COLUMN_CURR_DECIMAL,
				    AppData.COLUMN_CURR_SHOWSHORTNAME,
				    AppData.COLUMN_CURR_SHORTNAMEBEFORE,
				    AppData.COLUMN_CURR_SHORTNAMESPACE,
				    AppData.COLUMN_CURR_DELIMITER},
		    AppData.COLUMN_CURR_ID + "=?", 
		    new String[] { String.valueOf(id) }, null, null, null);
		  
		  if(cursor != null) cursor.moveToFirst();
		  Currency currency = new Currency(
				  ctx,
				  Integer.parseInt(cursor.getString(0)), 
				  Integer.parseInt(cursor.getString(1)),
				  cursor.getString(2),
				  cursor.getString(3),
				  cursor.getString(4),
				  Integer.parseInt(cursor.getString(5)),
				  cursor.getString(6),
				  Integer.parseInt(cursor.getString(7)),
				  Integer.parseInt(cursor.getString(8)),
				  Integer.parseInt(cursor.getString(9)),
				  Integer.parseInt(cursor.getString(10)),
				  Integer.parseInt(cursor.getString(11)));
		  return currency;
		 }
	
	public int defautCurrencyCount() {
		Cursor c = mDB.query(AppData.DB_CURRENCY, null, AppData.COLUMN_CURR_DEFAULT +">0",null,null,null,null);
		if (c == null) {return 0;}
		else {
			return c.getCount();
		}
	}
	
	public int updateCurrency(Currency currency) {
		  int rr;
		  ContentValues cv = new ContentValues();
		  // снять у всех признак Основная валюта, 
		  // если в объекте, который сейчас прилетел на апгрейд
		  // установлен признак основной валюты.
		  if (currency.getDefault()>0) {
		  cv.put(AppData.COLUMN_CURR_DEFAULT, 0);
		  rr = mDB.update(AppData.DB_CURRENCY, cv, null, null);	  
		  cv.clear();
		  }
		  
		  cv.put(AppData.COLUMN_CURR_DEFAULT, currency.getDefault());
		  cv.put(AppData.COLUMN_CURR_FLAG, currency.getFlag());
		  cv.put(AppData.COLUMN_CURR_NAME, currency.getName());
		  cv.put(AppData.COLUMN_CURR_ISO, currency.getISO());
		  cv.put(AppData.COLUMN_CURR_NUMISO, currency.getNumISO());
		  cv.put(AppData.COLUMN_CURR_SHORTNAME, currency.getShortName());
		  cv.put(AppData.COLUMN_CURR_DECIMAL, currency.getDecimal());
		  cv.put(AppData.COLUMN_CURR_SHOWSHORTNAME, currency.getShowShortName());
		  cv.put(AppData.COLUMN_CURR_SHORTNAMEBEFORE, currency.getShortNameBefore());
		  cv.put(AppData.COLUMN_CURR_SHORTNAMESPACE, currency.getShortNameSpace());
		  cv.put(AppData.COLUMN_CURR_DELIMITER, currency.getDelimiter());
		  rr = mDB.update(AppData.DB_CURRENCY, cv, AppData.COLUMN_CURR_ID + "=?", new String[] { String.valueOf(currency.getId()) });
		  
		  if (defautCurrencyCount()==0) Toast.makeText(ctx,R.string.currency_defaut_not_selected , Toast.LENGTH_LONG).show();
		  
		  return rr;
		 }
	
	
	//====================================================================================================================================
	
	private class DBHelper extends SQLiteOpenHelper {

	    public DBHelper(Context context, String name, CursorFactory factory,
	        int version) {
	      super(context, name, factory, version);
	    }
	    
	    @Override
		public void onCreate(SQLiteDatabase db) {
	    	
//  CURRENCY --------------------------------------------------------------------------	    	
			db.execSQL(AppData.DB_CURRENCY_CREATE);
			ContentValues cv = new ContentValues();
	        String tmp = "";

	        try {
	          XmlPullParser xpp = ctx.getResources().getXml(R.xml.currency_data);;

	          while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
	            switch (xpp.getEventType()) {
	            case XmlPullParser.START_TAG:
	           	  tmp=xpp.getName(); 
	              break;
	            case XmlPullParser.TEXT:
	              cv.put(tmp, xpp.getText());
	              break;
	            case XmlPullParser.END_TAG:
	            	if (xpp.getName().equalsIgnoreCase("currency")) {
	            	db.insert(AppData.DB_CURRENCY, null, cv);	
	                }
	                break;
	            default:
	              break;
	            }
	            xpp.next();
	          }
	        } catch (XmlPullParserException e) {
	          e.printStackTrace();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	        
//  CURRATE -----------------------------------------------------------------------------
	        db.execSQL(AppData.DB_CURRATE_CREATE);
}

	    
	    
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if(AppData.DB_VERSION < newVersion) {
				/*db.execSQL(AppData.DB_CURRENCY_DROP);
				   onCreate(db);
				*/   
				  }
		}
	}
	
}
