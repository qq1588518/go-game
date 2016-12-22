package goclient.program;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import goclient.gui.GUIMediator;


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
	private JButton refresh, ok;
	JList<String> list;
	private JScrollPane listScrollPane;
	private GUIMediator parent;
	/*
	 * Create List of Players GUI
	 */
	public PlayerList(String playersList, GUIMediator guiMediator, String title){
		this.parent = guiMediator;
		this.setTitle(title);
		this.setBounds(300, 300, 300, 400);
		
		this.setResizable(false);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
        Dimension size= new Dimension(300, 400);
        Dimension insideSize = new Dimension(280, 350);
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));       
        
        setSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);		

		Box box = new Box(BoxLayout.PAGE_AXIS);
		refresh = new JButton("Refresh");
		ok = new JButton("OK");
		JLabel label = new JLabel("<html>Currently there are no players avaliable.<br>Please try again later.</html>");
				
		if (playersList.trim().equals(""))
		{
		    label.setSize(insideSize);
		    label.setMaximumSize(insideSize);
		    label.setAlignmentX(CENTER_ALIGNMENT);
		    ok.setEnabled(false);
		    box.add(label);
		}
		else
		{
	        list = new JList<String>(playersList.split(" "));
	        listScrollPane = new JScrollPane(list);
	        
	        listScrollPane.setBounds(10, 10, 265,300);
	        list.setSelectionMode(JList.VERTICAL_WRAP);
	        list.setSelectedIndex(0);
	        box.add(listScrollPane);
		}
		
		Dimension gap = new Dimension(300, 15);
		box.add(Box.createRigidArea(gap));
		
		
		ok.setSize(100, 50);
		refresh.setSize(100, 50);
		ok.addActionListener(this);
		refresh.addActionListener(this);
		
		Box bottom = new Box(BoxLayout.LINE_AXIS);
		
		bottom.add(ok);
		bottom.add(Box.createRigidArea(new Dimension(30, 15)));
		bottom.add(refresh);
		box.add(bottom);
		box.add(Box.createRigidArea(gap));
		add(box);
		this.setVisible(true);
	}
	
	public GUIMediator getGuiMediator(){
		return parent;
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try
		{
	        if(arg0.getActionCommand()=="OK")
	        {   
	             parent.getProgramManager().chooseOpponent(list.getSelectedValue());
	             this.setVisible(false);
	        }
	        else if(arg0.getActionCommand()=="Refresh")
	        {
	            parent.getProgramManager().askForList();
	            this.setVisible(false);
	        }		    
		}
		catch(ComponentException e)
		{
		    System.out.println(e.getMessage());
		}
		
	}
	
}