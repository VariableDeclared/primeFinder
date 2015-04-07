/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findprimes;
import java.sql.*;




/**
 *
 * @author pete
 */
public class FindPrimes 
{
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DBURL = "jdbc:mysql://localhost";
    static final String userName = "primes";
    static final String password = "primes123";
    static Connection conn;
    static Statement stmt;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
        // TODO code application logic here
            
            FindPrimeGUI appGUI = new FindPrimeGUI();
            
            
      
        }
  
                    
       
        catch(Exception ex)
        {
            System.out.println("Error connecting to the database...");
        }
    }
//     static boolean isPrime(int prime)
//        {
//            boolean foundPrime = false;
//            for(int i = 0; i < prime; i++)
//            {
//                if(i != prime && i < 1)
//                    if((i % prime) == 0)
//                        foundPrime = true;
//            }           
//            return foundPrime;
//        }
}
