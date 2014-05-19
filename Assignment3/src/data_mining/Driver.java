package data_mining;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import data_mining.MagicString;
import data_mining.TickerData;
import data_mining.Industry;
public class Driver {public static Connection connSource, connDest;

public static void main(String[] args) {
	try {
		String[] connString = MagicString.getStockDBStrings();
		connSource = DriverManager.getConnection(connString[0].trim(), connString[1].trim(), connString[2].trim());
		connString = MagicString.getNewDBStrings();
		connDest = DriverManager.getConnection(connString[0].trim(), connString[1].trim(), connString[2].trim());
		System.out.println("Database connections established");
		
		Statement st = connDest.createStatement();
		try {
			st.executeUpdate("drop table Performance");
		}
		catch(SQLException ex) {}
			
		
	   System.out.println("Destination database cleared of tables");
		
		Statement stat1 = connDest.createStatement();
		stat1.executeUpdate("create table Performance (Industry char(30), Ticker char(6), StartDate char(10), EndDate char(10), TickerReturn char(12), IndustryReturn char(12))");
		System.out.println("Destination table ready");
		
		/* create the array list that keep the Industry */
		Statement stat = connSource.createStatement();
	    ArrayList <String> Industry = new ArrayList<String>();
		
		ResultSet rs = stat.executeQuery("select distinct(Industry) from company natural join pricevolume");
		while (rs.next())
		{
			String industry = rs.getString(1);
			Industry.add(0, industry);
		}
		ArrayList<TickerData> arraydata = new ArrayList<TickerData>();
		String S; 
		String T;
		 for (int i = 0; i < Industry.size(); i++) {	
				arraydata.clear();
				rs = stat.executeQuery("select Ticker, min(TransDate), max(TransDate) from company natural join pricevolume where Industry = " + quote(Industry.get(i)) + " group by Ticker order by Ticker");
				S = "0";
				T = "2014";
				int com;
				while (rs.next())
				{
				
					TickerData data = new TickerData();
					data.Ticker = rs.getString(1);
					data.Startdate = rs.getString(2);
					data.Enddate = rs.getString(3);
					arraydata.add(0, data);
					 com = S.compareTo(data.Startdate);
					 if (com < 0)
						 S = data.Startdate;
					 com = T.compareTo(data.Enddate);
					 if (com > 0)
						 T = data.Enddate;
				}
				//System.out.println("Data range for" + Industry.get(i) + S + T);
		ArrayList <Industry> data = new ArrayList<Industry>();
		PreparedStatement splitStat = connDest.prepareStatement("insert into Performance values (?, ?, ?, ?, ?, ?)");
		String firstdate = " "; String lastdate = " ";
		   for (int j = 0 ; j < arraydata.size(); j++) {
			   data.clear();
			int k = 0; 
			rs = stat.executeQuery("select Ticker, TransDate, OpenPrice, ClosePrice from company natural join pricevolume where Industry = 'Telecommunications Services' and Ticker = " + quote(arraydata.get(j).Ticker)+ " and TransDate >=" + quote(S) + " and TransDate <= " + quote(T));
			
			while (rs.next())
			{
				Industry dticker = new Industry();
				dticker.Ticker = rs.getString(1);
				dticker.Date = rs.getString(2);
				dticker.Opening = rs.getString(3);
				dticker.Closing = rs.getString(4);
			//	System.out.println(dticker.Ticker + " " + dticker.Date + " " + dticker.Opening);
				data.add(0, dticker);
				double tickerreturn = 0;
				tickerreturn = (Double.parseDouble(rs.getString(4))/ Double.parseDouble(rs.getString(3))) - 1;
				if (k == 0) {
					firstdate = dticker.Date;
					k++;
			} else if (k == 60) {
				k = 1; 
				lastdate = dticker.Date;
				System.out.println(firstdate + " " + lastdate);
				/*splitStat.setString(1, Industry.get(i));
				splitStat.setString(2, arraydata.get(j).Ticker);
				splitStat.setString(3, firstdate);
				splitStat.setString(4, lastdate);
				splitStat.setString(5, String.format("%10.7f", tickerreturn));
				splitStat.setString(6, "0.0");
				splitStat.execute();*/
			     } else {k++;}
				
				}
				
			}
		
			} 
	    
		
		
		connSource.close(); 
		connDest.close();
		System.out.println("Database connections closed");
		
	}
	catch (SQLException ex) {
		System.out.println("SQL exception");
		ex.printStackTrace();
		return;
	}
}
private static String quote(String str) {
	return "'" + str + "'";
}
}



	

