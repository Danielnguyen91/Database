package stockinvestment;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
public static Connection conn;
	
	public static void main(String[] args) {
		try {
			Scanner connStrSource = new Scanner(new File("connString.txt"));
			String[] connString = connStrSource.nextLine().trim().split("\\s+");
			connStrSource.close();
			conn = DriverManager.getConnection(connString[0].trim(), connString[1].trim(), connString[2].trim());
			System.out.println("Database connection established");
			Scanner keyboard = new Scanner(System.in);
			System.out.print("Enter a ticker symbol: ");
			String ticker = keyboard.nextLine().trim();
			while (!ticker.matches("")) {
				processTicker(ticker);
				System.out.print("\nEnter a ticker symbol: ");
				ticker = keyboard.nextLine().trim();
			}
			conn.close();
			System.out.println("Database connection closed");
			keyboard.close();
		}
		catch (FileNotFoundException ex) {
			System.out.println("File connString.txt not found");
			return;
		}
		catch (SQLException ex) {
			System.out.println("SQL exception");
			ex.printStackTrace();
			return;
		}
	}
	private static void processTicker(String ticker) {
		 class TickerData {
				public String date;
				public double open;
				public double close;
				
			}
		
		ArrayList<TickerData> dataArray = new ArrayList<TickerData>();
		Double prevop = 1.0;
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from company where ticker = " + quote(ticker));
			if (!rs.next())
				System.out.println(ticker + " not found in database");
			else {
				System.out.println(rs.getString(2).trim());
				System.out.println("Adjusting data for splits");
			
			 rs = stat.executeQuery("select * from pricevolume where ticker = " + quote(ticker) + " order by transDate DESC");
			 if (rs.next()) {
				 boolean done = false;
				 while (!done) {
					TickerData data = new TickerData();
					data.date = rs.getString(2).trim();
					data.open = Double.parseDouble(rs.getString(3).trim());
					data.close = Double.parseDouble(rs.getString(6));
					dataArray.add(0, data);
				    double ratio = Math.abs((data.close/ prevop) - 2.0);
					double ratio2 = Math.abs((data.close/ prevop) - 3.0);
					double ratio3 = Math.abs((data.close/ prevop) - 1.5);
					
					if (ratio < 0.13)
					{
					    System.out.println(String.format("2:1 split on  %s; %7.2f --> %7.2f",data.date, data.close, prevop));
					}
					   
					else if (ratio2 < 0.13)
					{
					 
						System.out.println(String.format("3:1 split on  %s; %7.2f --> %7.2f",data.date, data.close, prevop));
					}
					else if (ratio3 < 0.13)
					{
						System.out.println(String.format("3:2 split on  %s; %7.2f --> %7.2f",data.date, data.close, prevop));
					}
					prevop = Double.parseDouble(rs.getString(3).trim());
				    if (!rs.next()) { done = true;}
					
				}
				
				System.out.println();
				System.out.println("Executing investment strategy");
				double avg = 0.0;
				int executed = 0;
				double netgain = 0.0;
				int share = 0;
				if (dataArray.size() >= 51)
				{
					for (int i = 0; i < (dataArray.size() - 2); i++)
					{
						double op = dataArray.get(i).open; 	//opening value at day x
						double cp = dataArray.get(i).close;	//closing value at day x 
					    if (i <= 49) {
					    	avg = ((avg * i) + cp)/(i + 1); 
					    }
					    else if ((cp < avg) && (((op - cp)/op) >= 0.03))
					    {
					    	//buy shares
					    	share += 100;
					    	netgain -= (100 * dataArray.get(i+1).open) + 8.00;
					    	avg = ((((avg * 50) - dataArray.get(i - 50).close) + cp)/50);
					    	executed++;
					    	
					    }
					    else if ((share >= 100) && (op > avg) && 
					    		(((op - dataArray.get(i - 1).close)/ dataArray.get(i-1).close) >= 0.01))
					    {
					    	// sell shares
					    	share -= 100;
					    	netgain += (100 * ((op + cp)/2)) - 8.00;
					    	executed++;
					    	avg = ((((avg * 50) - dataArray.get(i - 50).close) + cp)/50);
					    	
					    	
					    }
					    else {
					    	avg = ((((avg * 50) - dataArray.get(i -50).close) + cp)/50);
					    }
					}
				}
				netgain += (share * dataArray.get(dataArray.size()- 1).open);
				System.out.println(String.format("Transactions executed: %d", executed));
				System.out.println(String.format("Net gain: %7.2f", netgain));
				System.out.println(share);
			}			     
		}
		}
		catch (SQLException ex) {
			System.out.println("SQL exception in processTicker");
		}
	}
	
	private static String quote(String str) {
		return "'" + str + "'";
	}
	
}


