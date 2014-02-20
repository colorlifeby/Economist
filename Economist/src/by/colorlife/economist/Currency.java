package by.colorlife.economist;

import android.content.Context;


public class Currency {
	
	private final Context ctx;
	private int id;
  	private String curr_flag;
	private int curr_flag_id;
	private String curr_name;
	private String curr_shortname;
	private int	curr_showshortname;
	private int	curr_shortnamebefore;
	private int	curr_shortnamespace;
	private String curr_iso;
	private int curr_numiso;       
	private int curr_decimal;
	private int curr_delimiter;
	int curr_default;
	
	
	public Currency(Context _ctx)
	{	
		ctx = _ctx;
	}
	
	public Currency(Context _ctx,
						int _curr_default,
			         String _curr_flag,
			         String _curr_name,
			         String _curr_iso,
			         	int _curr_numiso,
			         String _curr_shortname,
			         	int _curr_decimal,
			        	int	_curr_showshortname,
			        	int	_curr_shortnamebefore,
			        	int	_curr_shortnamespace,
			        	int	_curr_delimiter) 
	{	
		ctx = _ctx;
		curr_default = _curr_default;
		curr_flag = _curr_flag;
		curr_flag_id = ctx.getResources().getIdentifier(curr_flag,"drawable",ctx.getPackageName());
		curr_iso = _curr_iso;
		curr_numiso = _curr_numiso;
		curr_name = _curr_name;
		curr_shortname = _curr_shortname;
		curr_decimal = _curr_decimal;
		curr_showshortname = _curr_showshortname;
		curr_shortnamebefore = _curr_shortnamebefore;
		curr_shortnamespace = _curr_shortnamespace; 
		curr_delimiter = _curr_delimiter;
		
	}

	public Currency(Context _ctx,
						int _id,
						int _curr_default,
					 String _curr_flag,
					 String _curr_name,
					 String _curr_iso,
					 	int _curr_numiso,
					 String _curr_shortname,
					 	int _curr_decimal,
			        	int	_curr_showshortname,
			        	int	_curr_shortnamebefore,
			        	int	_curr_shortnamespace,
			        	int	_curr_delimiter) 
					 	
{	
		ctx = _ctx;
		id = _id;
		curr_default = _curr_default;
		curr_flag = _curr_flag;
		curr_flag_id = ctx.getResources().getIdentifier(curr_flag,"drawable",ctx.getPackageName());
		curr_iso = _curr_iso;
		curr_numiso = _curr_numiso;
		curr_name = _curr_name;
		curr_shortname = _curr_shortname;
		curr_decimal = _curr_decimal;
		curr_showshortname = _curr_showshortname;
		curr_shortnamebefore = _curr_shortnamebefore;
		curr_shortnamespace = _curr_shortnamespace;
		curr_delimiter = _curr_delimiter;
		
}
	
	public void setId(int _id) {id = _id;}
	public int  getId() {return id;}
	
	public void     setDefault(int _curr_default) {curr_default = _curr_default;}
	public int  	getDefault() {return curr_default;}
	
	public void   setFlag(String _curr_flag) 
	{curr_flag = _curr_flag;
	 curr_flag_id = ctx.getResources().getIdentifier(curr_flag,"drawable",ctx.getPackageName());
	}
	public String getFlag() {return curr_flag;}
	
	public int getFlagId() {return curr_flag_id;}
	
	public void   setName(String _curr_name) {curr_name = _curr_name;}
	public String getName() {return curr_name;}
	
	public void   setISO(String _curr_iso) {curr_iso = _curr_iso;}
	public String getISO() {return curr_iso;}
	
	public void   setNumISO(int _curr_numiso) {curr_numiso = _curr_numiso;}
	public int 	  getNumISO() {return curr_numiso;}
	
	public void   setShortName(String _curr_shortname) {curr_shortname = _curr_shortname;}
	public String getShortName() {return curr_shortname;}
	
	public void setDecimal(int _curr_decimal) {curr_decimal = _curr_decimal;}
	public int  getDecimal() {return curr_decimal;}

	public void setShowShortName(int _curr_showshortname) {curr_showshortname = _curr_showshortname;}
	public int  getShowShortName() {return curr_showshortname;}
	
	public void setShortNameBefore(int _curr_shortnamebefore) {curr_shortnamebefore = _curr_shortnamebefore;}
	public int  getShortNameBefore() {return curr_shortnamebefore;}

	public void setShortNameSpace(int _curr_shortnamespace) {curr_shortnamespace = _curr_shortnamespace;}
	public int  getShortNameSpace() {return curr_shortnamespace;}

	public void setDelimiter(int _curr_delimiter) {curr_delimiter = _curr_delimiter;}
	public int  getDelimiter() {return curr_delimiter;}
	
}
