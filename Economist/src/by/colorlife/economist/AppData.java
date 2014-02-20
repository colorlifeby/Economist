package by.colorlife.economist;

public class AppData {
	

	  
	  public static final String LOG_TAG = "myLogs"; 
	
	  public static final String DB_NAME = "economist";
	  public static final int DB_VERSION = 1;
	  
// Калькулятор -------------------------------------------------------------------------------
	  public static final String calcFormat = "########################.######";
	  public static final int calcMaxLength = 33;
	  
// Валюта -------------------------------------------------------------------------------
	  public static final String DB_CURRENCY = "currency";
	  
	  public static final String COLUMN_CURR_ID = "_id";
	  public static final String COLUMN_CURR_DEFAULT = "curr_default";  
	  public static final String COLUMN_CURR_FLAG = "curr_flag";
	  public static final String COLUMN_CURR_NAME = "curr_name";
	  public static final String COLUMN_CURR_ISO = "curr_iso";
	  public static final String COLUMN_CURR_NUMISO = "curr_numiso";
	  public static final String COLUMN_CURR_SHORTNAME = "curr_shortname";
	  public static final String COLUMN_CURR_DECIMAL = "curr_decimal";
	  public static final String COLUMN_CURR_SHOWSHORTNAME = "curr_showshortname";
	  public static final String COLUMN_CURR_SHORTNAMEBEFORE = "curr_shortnamebefore";
	  public static final String COLUMN_CURR_SHORTNAMESPACE = "curr_shortnamespace";
	  public static final String COLUMN_CURR_DELIMITER = "curr_delimiter";
	  
	  public static final String DB_CURRENCY_CREATE = 
	    "create table " + DB_CURRENCY + "(" +
	      COLUMN_CURR_ID + " integer primary key autoincrement, " +
	      COLUMN_CURR_DEFAULT + " integer, " +
	      COLUMN_CURR_FLAG + " text, " +
	      COLUMN_CURR_NAME + " text, " +
	      COLUMN_CURR_ISO + " text, " +
	      COLUMN_CURR_NUMISO + " integer, " +
	      COLUMN_CURR_SHORTNAME + " text, " +
	      COLUMN_CURR_DECIMAL + " integer," +
	      COLUMN_CURR_SHOWSHORTNAME + " integer," +
	      COLUMN_CURR_SHORTNAMEBEFORE + " integer," +
	      COLUMN_CURR_SHORTNAMESPACE + " integer," +
	      COLUMN_CURR_DELIMITER + " integer" +
	    ");";
	  public static final String DB_CURRENCY_SELECT_ALL = 
			  "SELECT * FROM " + DB_CURRENCY;
	  public static final String DB_CURRENCY_DROP = 
			  "DROP TABLE IF EXISTS " + DB_CURRENCY;

	  
// Курсы валют ------------------------------------------------------------------------------
	
	  public static final String DB_CURRATE = "currate";
	  
	  public static final String COLUMN_RATE_ID = "_id";
	  public static final String COLUMN_RATE_CURRID1 = "rate_currid1";      // ссылка на _id 1-й валюты
	  public static final String COLUMN_RATE_CURRID2 = "rate_currid2";      // ссылка на _id 2-й валюты
	  public static final String COLUMN_RATE_DATE = "rate_date";		    // дата и время установки курса	 
	  public static final String COLUMN_RATE_VALUE = "rate_value";          // курс - сколько надо 1-й валюты, чтобы купить единицу второй.
	  public static final String COLUMN_RATE_TYPE = "rate_type";			// тип курса : продажа, покупка, НБ, ...
	  public static final String COLUMN_RATE_COMMENT = "rate_comment";      // комментарий к курсу. 
	  
	  public static final String DB_CURRATE_CREATE = 
			    "create table " + DB_CURRATE + "(" +
			      COLUMN_CURR_ID + " integer primary key autoincrement, " +
			      COLUMN_RATE_CURRID1 + " integer, " +
			      COLUMN_RATE_CURRID2 + " integer, " +
			      COLUMN_RATE_DATE + " integer, " +
			      COLUMN_RATE_VALUE + " double, " +
			      COLUMN_RATE_TYPE + " integer, " +
			      COLUMN_RATE_COMMENT + " text" +
			    ");";
	  

// Группы счетов ------------------------------------------------------------------------------
		
	  public static final String DB_ACCGROUP = "accgroup";
	  
	  public static final String COLUMN_ACCGR_ID = "_id";
	  public static final String COLUMN_ACCGR_ITOG = "accgr_itog";      		// включать в общий итог =0-нет, <>0 - да
	  public static final String COLUMN_ACCGR_SORT = "accgr_sort";      		// сортировка. число
	  public static final String COLUMN_ACCGR_NAME = "accgr_name";      		// Наименование группы
	   


	  public static final String DB_ACCGROUP_CREATE = 
			    "create table " + DB_ACCGROUP + "(" +
			      COLUMN_ACCGR_ID + " integer primary key autoincrement, " +
			      COLUMN_ACCGR_ITOG + " integer, " +
			      COLUMN_ACCGR_SORT + " integer, " +	      
			      COLUMN_ACCGR_NAME + " text" +
			    ");";

	  
// Счета ------------------------------------------------------------------------------
	  public static final String DB_ACCOUNT = "account";
	  
	  public static final String COLUMN_ACC_ID = "_id";
	  public static final String COLUMN_ACC_NAME = "acc_name";      	// Наименование счета	  
	  public static final String COLUMN_ACC_GROUP = "acc_group";        // группа счетов
	  public static final String COLUMN_ACC_CURRENCY = "acc_currency";  // Валюта счета	  
	  public static final String COLUMN_ACC_TYPE = "acc_type";  		// Тип счета
	  public static final String COLUMN_ACC_SUMMA = "acc_summa";      	// сумма на счете в валюте счета

	  public static final String DB_ACCOUNT_CREATE = 
			    "create table " + DB_ACCOUNT + "(" +
			      COLUMN_ACC_ID + " integer primary key autoincrement, " +
			      COLUMN_ACC_NAME + " text, " +			    		
			      COLUMN_ACC_GROUP + " integer, " +
			      COLUMN_ACC_CURRENCY + " integer, " +
			      COLUMN_ACC_TYPE + " integer, " +			      
			      COLUMN_ACC_SUMMA + " double" +			      
			    ");";
	  
//-------------------------------------------------------------------------------------------	  
		public static String getMoneyS(double f,int dec,String sym, int show, int before, int space, int delim){
			String res="", fex, sp="",s;
			if (delim>0)  {fex="%,.";} else {fex="%.";} // если нет разделителя разрядов
			fex = fex + String.valueOf(dec)+"f"; // сформировать формат
			s = String.format(fex, f); // получить саму строку по формату
			if (space>0) sp = " "; // всталять пробел между символом и валютой
			
			if (show>0) { //показывать символ валюты
			    if (before>0) { // если перед суммой 
			    	res = sym+sp+s;}
			    else {res = s+sp+sym;}
		   }
			else {
				return s;
			}
			return res;
		}
	  
}
