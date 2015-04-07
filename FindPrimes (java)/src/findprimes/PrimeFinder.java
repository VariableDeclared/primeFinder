/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findprimes;
import java.sql.*;

import java.util.HashSet;
import java.util.Timer;
/**
 *
 * @author pete
 */
public class PrimeFinder implements Runnable {
    int currentPrime;
    boolean killFlag;   
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String DBURL = "jdbc:mysql://localhost/primes";
    final String userName = "primes";
    final String password = "primes123";
    HashSet<PrimeFinderListener> listeners;
    long time_lapsed;
    Connection conn;
    Statement stmt;
    /**
     * Stops or sets the starting flag for the prime finder
     * @param running false to stop running
     */
    public void setRunning(boolean running)
    {
        this.killFlag = !running;
    }
    
    public void run()
    {
        this.findPrime();
    }
    
    public void addListener(PrimeFinderListener listener)
    {
        this.listeners.add(listener);
    }
    public PrimeFinder()
    {
        currentPrime = 0;
        this.killFlag = false;
        listeners = new HashSet<PrimeFinderListener>();
    }
    public int getCurrentPrime()
    {
        return this.currentPrime;
    }
    public void findPrime()
    {
        try{
            this.killFlag = false;
            
            Class.forName(DRIVER);
            
            sendMessage("Connecting to the primes database.");
            //connect to the DB.
            conn = DriverManager.getConnection(DBURL, userName, password);

            //Create the statement
            stmt = conn.createStatement();
            String sql =  "select prime_id, prime from prime order by prime desc limit 1";
           
            ResultSet set = stmt.executeQuery(sql);
                
            if(set.next() == false)
                throw new Exception("Error executing query - no result returned");
            
            currentPrime = set.getInt("prime");
            //start finding primes...
           
            while(!killFlag)
            {
            sendMessage("Last prime that found is: " + currentPrime);
            Thread.sleep(1000);
            sendMessage("Finding primes...");
            long tBeforePSearch = System.currentTimeMillis();
            int count = currentPrime+1;
            while(!isPrime(count))
            {   
                sendMessage("Incrementing count " + count);
                Thread.sleep(200);
                count += 1;
            }
            long tAfterPSearch = System.currentTimeMillis();
            time_lapsed = tAfterPSearch - tBeforePSearch;
            sendMessage("Found Prime! - " + count + " \n Time lapsed: " + time_lapsed + 
                    "ms");
            Thread.sleep(1000);
            currentPrime = count;
            sql = "insert into prime(prime) values(" +count + ");";
            //Thread.sleep(200);
            stmt.execute(sql);
            ResultSet pk = stmt.executeQuery("select prime_id from prime where"
                    + " prime = " + currentPrime);
            pk.next();
            int numPK = pk.getInt("prime_id");
            
            
            sql = "insert into time_lapsed(ms, prime_id) values("+
                    time_lapsed+ ","+numPK+")";
            
            stmt.execute(sql);
            
            
            
            }
            
            stmt.close();
            conn.close();
        }
        catch(Exception ex)
        {
            sendMessage("An error occured starting the application \n"
            + ex.getMessage());
            //System.exit(-1);
        }
    }
    private void sendMessage(String message)
    {
        for(PrimeFinderListener ear : listeners)
        {
            ear.messageListener(message);
        }
    }
    
     private boolean isPrime(int prime)
        {
            boolean foundPrime = true;
            for(int i = 0; i < prime; i++)
            {
                if(i != prime && i > 1)
                    if((prime % i) == 0)
                        foundPrime = false ;
            }           
            return foundPrime;
        }
}
