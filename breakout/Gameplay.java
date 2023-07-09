import java.util.*;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
public class Gameplay extends JPanel implements KeyListener, ActionListener 
{
	private boolean play = false;
	private int row_,col_;
	private int score = 0;

	private int totalBricks = row_ * col_;
	
	private Timer timer;
	private int delay=8;
	
	private int playerX = 310;
	
	private int ballposX = 350;
	private int ballposY = 530;
	private int ballXdir = -1;
	private int ballYdir = -3;
	private MapGenerator map;
	
	boolean extraPannel = false, shrinkPannel = false, extraBall = false, shrinkBall = false;
	
	public Gameplay(int row, int col)
	{	
		row_ = row;
		col_ = col;
		totalBricks = row * col;
		map = new MapGenerator(row, col);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
		timer.start();
	}
	int left = 30, right = 30, middle = 40;
	public void paint(Graphics g)
	{    		
		// background
		g.setColor(new Color(33, 105, 171));
		g.fillRect(1, 1, 692, 592);
		
		// drawing map
		map.draw((Graphics2D) g);
		
		// borders
		g.setColor(Color.black);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// the scores 		
		g.setColor(Color.orange);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 590,30);

		
		
		// the paddle
		if(extraPannel==true){

			g.setColor(Color.orange);
			g.fillRect(playerX, 550, 200, 8);
			left = 60; right = 60;middle = 80;//200
		}
		else if(shrinkPannel){

			g.setColor(Color.orange);
			g.fillRect(playerX, 550, 70, 8);
			left = 20; right = 20;middle = 30;//70
		}
		else{
			g.setColor(Color.orange);
			g.fillRect(playerX, 550, 100, 8);
			left = 30; right = 30;middle = 40;//100
		}
		
		
		// the ball
		if(extraBall){
			g.setColor(Color.yellow);
			g.fillOval(ballposX, ballposY, 30, 30);	
		}
		else if(shrinkBall){
			g.setColor(Color.yellow);
			g.fillOval(ballposX, ballposY, 10, 10);	
		}
		else{
			g.setColor(Color.yellow);
			g.fillOval(ballposX, ballposY, 20, 20);	
		}
	
		// when you won the game
		if(totalBricks <= 0)
		{
			 play = false;
             ballXdir = 0;
     		 ballYdir = 0;
			 extraPannel = false;
			 shrinkPannel = false;
			 extraBall = false;
			 shrinkBall = false;
             g.setColor(Color.white);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("You Won", 260,300);
             
             g.setColor(Color.white);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);  
		}
		
		// when you lose the game
		if(ballposY > 570)
        {
			 play = false;
             ballXdir = 0;
     		 ballYdir = 0;
			 extraPannel = false;
			 shrinkPannel = false;
			 extraBall = false;
			 shrinkBall = false;
             g.setColor(Color.white);
             g.setFont(new Font("sans",Font.BOLD, 30));
             g.drawString("Game Over! Scores: "+score, 190,300);
             
             g.setColor(Color.white);
             g.setFont(new Font("sans",Font.BOLD, 20));           
             g.drawString("Press Enter to Restart", 230,350);        
        }
		
		g.dispose();
	}	

	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{        
			if(playerX >= 600)
			{
				playerX = 600;
			}
			else
			{
				moveRight();
			}
        }
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{          
			if(playerX < 10)
			{
				playerX = 10;
			}
			else
			{
				moveLeft();
			}
        }		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!play)
			{
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -3;
				playerX = 310;
				score = 0;
				totalBricks = row_ * col_;
				map = new MapGenerator(row_, col_);
				
				repaint();
			}
        }		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void moveRight()
	{
		play = true;
		playerX+=20;	
	}
	
	public void moveLeft()
	{
		play = true;
		playerX-=20;	 	
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		int w = 20, h = 20;
		if(extraBall){
			w = 30; h = 30;
		}
		else if(shrinkBall){
			w = 10; h = 10;
		}
		timer.start();
		if(play)
		{	
			//碰撞到paddle		
			if(new Rectangle(ballposX, ballposY, w, h).intersects(new Rectangle(playerX, 550, left, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = -2;
			}
			else if(new Rectangle(ballposX, ballposY, w, h).intersects(new Rectangle(playerX + left+middle, 550, right, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = ballXdir + 1;
			}
			else if(new Rectangle(ballposX, ballposY, w, h).intersects(new Rectangle(playerX + left, 550, middle, 8)))
			{
				ballYdir = -ballYdir;
			}
			
			// check map collision with the ball		
			A: for(int i = 0; i<map.map.length; i++)
			{
				for(int j =0; j<map.map[0].length; j++)
				{				
					if(map.map[i][j] > 0)
					{
						//scores++;
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect;	
						if(extraBall){				
							ballRect = new Rectangle(ballposX, ballposY, 30, 30);
						}
						else if(shrinkBall){
							ballRect = new Rectangle(ballposX, ballposY, 10, 10);
						}
						else{
							ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						}
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{	
							score+=5;	
							totalBricks--;
							if(map.map[i][j] == 2){
								extraPannel = true;shrinkPannel = false;
								map.rand_break = true;
							}
							else if(map.map[i][j] == 3){
								shrinkPannel = true;extraPannel = false;
								map.rand_break = true;
							}
							else if(map.map[i][j] == 4){
								extraBall = true;shrinkBall = false;
								map.rand_break = true;
							}
							else if(map.map[i][j] == 5){
								shrinkBall = true;extraBall = false;
								map.rand_break = true;
							}							
							map.setBrickValue(0, i, j);
							// when ball hit right or left of brick
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)	
							{
								ballXdir = -ballXdir;
							}
							// when ball hits top or bottom of brick
							else
							{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
			}
			if(map.rand_break && totalBricks >= 1){
				System.out.println(totalBricks);
				map.rebuild();
				map.rand_break = false;
			}
	
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX < 0)
			{
				ballXdir = -ballXdir;
			}
			if(ballposY < 0)
			{
				ballYdir = -ballYdir;
			}
			if(ballposX > 670)
			{
				ballXdir = -ballXdir;
			}
					
			
			repaint();		
		}
	}
}
