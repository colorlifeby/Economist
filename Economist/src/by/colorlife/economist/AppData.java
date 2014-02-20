package by.colorlife.economist;

public class AppData {
	

	  
	  public static final String LOG_TAG = "myLogs"; 
	
	  public static final String DB_NAME = "economist";
	  public static final int DB_VERSION = 1;
	  
// ����������� -------------------------------------------------------------------------------
	  public static final String calcFormat = "########################.######";
	  public static final int calcMaxLength = 33;
	  
// ������ -------------------------------------------------------------------------------
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

	  
// ����� ����� ------------------------------------------------------------------------------
	
	  public static final String DB_CURRATE = "currate";
	  
	  public static final String COLUMN_RATE_ID = "_id";
	  public static final String COLUMN_RATE_CURRID1 = "rate_currid1";      // ������ �� _id 1-� ������
	  public static final String COLUMN_RATE_CURRID2 = "rate_currid2";      // ������ �� _id 2-� ������
	  public static final String COLUMN_RATE_DATE = "rate_date";		    // ���� � ����� ��������� �����	 
	  public static final String COLUMN_RATE_VALUE = "rate_value";          // ���� - ������� ���� 1-� ������, ����� ������ ������� ������.
	  public static final String COLUMN_RATE_TYPE = "rate_type";			// ��� ����� : �������, �������, ��, ...
	  public static final String COLUMN_RATE_COMMENT = "rate_comment";      // ����������� � �����. 
	  
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
	  

// ������ ������ ------------------------------------------------------------------------------
		
	  public static final String DB_ACCGROUP = "accgroup";
	  
	  public static final String COLUMN_ACCGR_ID = "_id";
	  public static final String COLUMN_ACCGR_ITOG = "accgr_itog";      		// �������� � ����� ���� =0-���, <>0 - ��
	  public static final String COLUMN_ACCGR_SORT = "accgr_sort";      		// ����������. �����
	  public static final String COLUMN_ACCGR_NAME = "accgr_name";      		// ������������ ������
	   


	  public static final String DB_ACCGROUP_CREATE = 
			    "create table " + DB_ACCGROUP + "(" +
			      COLUMN_ACCGR_ID + " integer primary key autoincrement, " +
			      COLUMN_ACCGR_ITOG + " integer, " +
			      COLUMN_ACCGR_SORT + " integer, " +	      
			      COLUMN_ACCGR_NAME + " text" +
			    ");";

	  
// ����� ------------------------------------------------------------------------------
	  public static final String DB_ACCOUNT = "account";
	  
	  public static final String COLUMN_ACC_ID = "_id";
	  public static final String COLUMN_ACC_NAME = "acc_name";      	// ������������ �����	  
	  public static final String COLUMN_ACC_GROUP = "acc_group";        // ������ ������
	  public static final String COLUMN_ACC_CURRENCY = "acc_currency";  // ������ �����	  
	  public static final String COLUMN_ACC_TYPE = "acc_type";  		// ��� �����
	  public static final String COLUMN_ACC_SUMMA = "acc_summa";      	// ����� �� ����� � ������ �����

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
			if (delim>0)  {fex="%,.";} else {fex="%.";} // ���� ��� ����������� ��������
			fex = fex + String.valueOf(dec)+"f"; // ������������ ������
			s = String.format(fex, f); // �������� ���� ������ �� �������
			if (space>0) sp = " "; // �������� ������ ����� �������� � �������
			
			if (show>0) { //���������� ������ ������
			    if (before>0) { // ���� ����� ������ 
			    	res = sym+sp+s;}
			    else {res = s+sp+sym;}
		   }
			else {
				return s;
			}
			return res;
		}
	  
}
