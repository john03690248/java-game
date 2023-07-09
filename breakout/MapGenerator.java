import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator 
{
	public int map[][];
	public int brickWidth;
	public int brickHeight;

	public boolean rand_break = false;//if there is no special block on the map
	public int rand_row, rand_col, glo_row, glo_col;//record random row and col
	public int rand_flag = 0;//record which special function

	public MapGenerator (int row, int col)
	{
		map = new int[row][col];	
		glo_row = row; glo_col = col;
		rand_row = (int)(Math.random() * row);
		rand_col = (int)(Math.random() * col);
		while(rand_flag <= 1){
			rand_flag = (int)(Math.random() * 100);	
			rand_flag %= 6;
		}
		for(int i = 0; i<map.length; i++)
		{		
			for(int j =0; j<map[0].length; j++)
			{
				map[i][j] = 1;
			}			
		}
		setBrickValue(rand_flag, rand_row, rand_col);
		brickWidth = 540/col;
		brickHeight = 150/row;
	}	
	
	public void draw(Graphics2D g)
	{
		for(int i = 0; i<map.length; i++)
		{
			for(int j =0; j<map[0].length; j++)
			{
				if(map[i][j] == 2){
					g.setColor(Color.red);
					g.fillRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);
					
					// this is just to show separate brick, game can still run without it
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);	
				}
				else if(map[i][j] == 3){
					g.setColor(Color.green);
					g.fillRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);
					
					// this is just to show separate brick, game can still run without it
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);	
				}
				else if(map[i][j] == 4){
					g.setColor(Color.yellow);
					g.fillRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);
					
					// this is just to show separate brick, game can still run without it
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);	
				}
				else if(map[i][j] == 5){
					g.setColor(Color.DARK_GRAY);
					g.fillRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);
					
					// this is just to show separate brick, game can still run without it
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);	
				}
				else if(map[i][j]!=0)//if(map[i][j] > 0)
				{
					g.setColor(Color.white);
					g.fillRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);
					
					// this is just to show separate brick, game can still run without it
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRoundRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight, 20, 20);				
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col)
	{
		map[row][col] = value;
	}

	public void rebuild(){
		rand_row = (int)(Math.random() * glo_row);
		rand_col = (int)(Math.random() * glo_col);
		int lastflag = -1;
        while(map[rand_row][rand_col] == 0){
            rand_row = (int)(Math.random() * glo_row);
            rand_col = (int)(Math.random() * glo_col);
        }
        while(rand_flag != lastflag){
            lastflag = rand_flag;
            rand_flag = (int)(Math.random() * 100);
            rand_flag %= 6;
        }
        setBrickValue(rand_flag, rand_row, rand_col);
	}
}
