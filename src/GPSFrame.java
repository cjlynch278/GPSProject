import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class GPSFrame extends JFrame implements KeyListener{

	GPSPanel panel;
	public static int currentNode= 10 ;
	 
	
	public GPSFrame (int x, int y, int width, int height,int[][] graph) {
		
		int totalNodes = graph.length;
		
		setSize(width, height);
		setLocation(x, y);
		panel = new GPSPanel(graph);
		add(panel, BorderLayout.CENTER);
		setVisible(true);
		panel.makeObjects();
	
		
		JTextField typingArea = new JTextField(20);
		typingArea.addKeyListener(this);
		add(typingArea,BorderLayout.SOUTH);
		panel.enemyMovement(currentNode);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		currentNode = panel.moveNode(e.getKeyChar());
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	}