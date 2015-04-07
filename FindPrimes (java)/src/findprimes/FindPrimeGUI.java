/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findprimes;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import java.sql.*;
/**
 * 
 * @author Peter J De Sousa
 * @version 1.4
 * 
 */
public class FindPrimeGUI extends JFrame implements ActionListener, PrimeFinderListener {
    JButton startButton, stopButton;
    JTextArea display;
    PrimeFinder finder;
    Thread worker;
    
    public void messageListener(String message)
    {
        display.setText(message);
    }
    public FindPrimeGUI()
    {
        
        super();
        
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0,0,500,500);
        init();
    }
 
  
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if(source.equals(startButton) && !worker.isAlive())
        {
            worker = new Thread(finder);
            
            worker.start();
            
        }
        if(source.equals(stopButton))
            finder.setRunning(false);
    }
    
    private void init()
    {
        
        this.finder = new PrimeFinder();
        this.worker = new Thread(finder);
        finder.addListener(this);
        startButton = new JButton();
        stopButton = new JButton();
        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        this.add(stopButton);
        this.add(startButton);
        display = new JTextArea();
        display.setBounds(100,100, 100,100);
        this.add(display);
        display.setEditable(false);
        startButton.setBounds(0,390, 250, 100);
        stopButton.setBounds(250, 390, 250, 100);
        startButton.setText("Start");
        stopButton.setText("Stop!");
    }
    
}
