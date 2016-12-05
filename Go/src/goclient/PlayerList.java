package goclient;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;


/**
 * 
 * @author Marcin
 * JDialog opens list of current players
 * 
 * 
 */
public class PlayerList extends JDialog implements ActionListener {
	
	Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
	private static final long serialVersionUID = 1L;
	private JButton cancel, ok;
	JList<String> list;
	private DefaultListModel<String> listModel;
	private JScrollPane listScrollPane;
	
	/*
	 * Create List of Players GUI
	 */
	public PlayerList(){
		
		this.setBounds(300, 300, 300, 400);
		this.setLayout(null);
		
		listModel = new DefaultListModel<String>();
		cancel = new JButton("Cancel");
		ok = new JButton("OK");
		list = new JList<String>(listModel);
		listScrollPane = new JScrollPane(list);
		
		//Test Data
		listModel.addElement("Stefan ¯eromski");
		listModel.addElement("Steve Jobs");
		listModel.addElement("Pan Rektor");
		
		listScrollPane.setBounds(10, 10, 265,300);
		list.setSelectionMode(JList.VERTICAL_WRAP);
		ok.setBounds(100, 325, 90, 25);
		ok.addActionListener(this);
		
		add(ok);
		add(listScrollPane, BorderLayout.CENTER);
		add(cancel);
		
		this.setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand()=="OK"){
			//System.out.println("Po³¹cz z graczem " + listModel.getElementAt(list.getSelectedIndex()));
			
			//If socket doesnt exist, create it
			if(socket == null){ 
				
				try {
					socket = new Socket("localhost", 5556);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					out = new PrintWriter(socket.getOutputStream(), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
			}
			
			//test communication client -> server
			out.println(socket.toString());
		}
		
	}
	
	
	
	
	
}
