import java.awt.Color;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
	public static void main(String[] args) {
		
		final JFrame obj=new JFrame();
		final JFrame obj1=new JFrame();
		
		obj1.setBounds(10, 10, 710, 600);
		obj1.setTitle("Choose The Difficulty");		
		obj1.setSize(400,100);
		obj1.setLocationRelativeTo(null);
		obj1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		final JButton btn = new JButton("Easy");
		btn.setFont(new Font("sans",Font.ITALIC, 20));
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gameplay gamePlay = new Gameplay(3, 7);
				obj.add(gamePlay);
				obj.setVisible(true);
				obj1.setVisible(false);
			}
		});
		panel.add(btn);
		
		final JButton btn1 = new JButton("Medium");
		btn1.setFont(new Font("sans",Font.ITALIC, 20));
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gameplay gamePlay = new Gameplay(4, 7);
				obj.add(gamePlay);
				obj.setVisible(true);
				obj1.setVisible(false);
			}
		});
		panel.add(btn1);
		
		final JButton btn2 = new JButton("Difficult");
		btn2.setFont(new Font("Sans",Font.ITALIC, 20));
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gameplay gamePlay = new Gameplay(5, 11);
				obj.add(gamePlay);
				obj.setVisible(true);
				obj1.setVisible(false);
			}
		});
		panel.add(btn2);
		
		obj1.setContentPane(panel);
		obj1.setVisible(true);
		
		obj.setBounds(10, 10, 710, 600);
		obj.setTitle("Game Play");		
		obj.setResizable(false);
		obj.setVisible(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
