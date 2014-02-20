package by.colorlife.economist;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrencyAdapter extends SimpleCursorAdapter {
	private int layout;

	public CurrencyAdapter(Context _context, 
			               int _layout, 
			               Cursor _cursor, 
			               String[] _from, 
			               int[] _to, int fl) {
		  super(_context, _layout, _cursor, _from, _to, fl);
		  
		  layout = _layout;
		  
		 }	 
	
	//связывает данные с view на которые указывает курсор
	 @Override
	 public void bindView(View view, Context _context, Cursor _cursor) {
	  int currDefault = Integer.parseInt(_cursor.getString(_cursor.getColumnIndex(AppData.COLUMN_CURR_DEFAULT)));
      String flag = _cursor.getString(_cursor.getColumnIndex(AppData.COLUMN_CURR_FLAG));	 
	  String name = _cursor.getString(_cursor.getColumnIndex(AppData.COLUMN_CURR_NAME));
	  String iso = _cursor.getString(_cursor.getColumnIndex(AppData.COLUMN_CURR_ISO));
	  
	  ImageView flagIV = (ImageView) view.findViewById(R.id.flagIV);
	  TextView nameTV = (TextView) view.findViewById(R.id.nameTV);
	  TextView isoTV = (TextView) view.findViewById(R.id.isoTV);
	  ImageView cbIV = (ImageView) view.findViewById(R.id.cbIV);
	  
	  flagIV.setImageResource(_context.getResources().getIdentifier(flag,"drawable",_context.getPackageName()));
	  nameTV.setText(name);
	  isoTV.setText(iso);
  
	  if (currDefault>0) {
		cbIV.setImageResource(R.drawable.cbyes);
	  }
	  else {
		 cbIV.setImageResource(R.drawable.cbno);
	  }

	 }
	 
	//сoздаёт нвоую view для хранения данных на которую указывает курсор
	 @Override
	 public View newView(Context _context, Cursor _cursor, ViewGroup parent) {
	  LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  View view = inflater.inflate(layout, parent, false);
	  return view;
	 }
}
