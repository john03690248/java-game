import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
public class Operation implements KeyListener
{
  int[] temp = {7,8,9,10,13,14,15,16,19,20,21,22,25,26,27,28};
  int[] undovalue = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
  boolean undonumflag = true;
  Block[] block;
  JPanel panel;
  public boolean up,down,left,right;
  int moveFlag;
  int numBack = 3;
  int sumScore = 0;
  int maxScore = 0;
  int undoSumScore = 0;
  int undoMaxScore = 0;
  int button = 0;
  int countPress = 0;
  int button1 = 0;
  int button2 = 0;
  boolean hamFlag=true;
  int hammerChances=0;
  boolean numFlag;
  int numHammer=3;
  Clip main_bgm;
  public Operation(JFrame frame) 
  {
    this.panel = (JPanel)frame.getContentPane();
    block = new Block[36];
    numFlag = true;
    moveFlag = 0;
    up=true;down=true;left=true;right=true;
    addBlock();
    block[12].setValue(1);
    for (int i = 0; i < 2; i++)
      appearBlock();
    frame.addKeyListener(this);
    block[12].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){
		if (button2 == 0) {
		button2 = 1;
		hammer(12);}
		}});
	block[17].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){Undo();}});
  }
 
  private void addBlock() 
  {
    for (int i = 0; i < 36; i++)
    {
      block[i] = new Block();
      block[i].setHorizontalAlignment(JLabel.CENTER);
      block[i].setOpaque(true);
      panel.add(block[i]);  
    }
	block[0].setText("Score:");
	block[0].setForeground(Color.white);
	block[0].setFont(new java.awt.Font("Arial", 1, 25));
	block[0].setBackground(new Color(114, 85, 68));
	String text = String.valueOf(sumScore);
	block[1].setText(text);
	block[1].setForeground(Color.white);
	block[1].setFont(new java.awt.Font("Arial", 1, 25));
	block[1].setBackground(new Color(114, 85, 68));
	block[2].setBackground(new Color(114, 85, 68));
	block[3].setText("Current:");
	block[3].setForeground(Color.white);
	block[3].setFont(new java.awt.Font("Arial", 1, 25));
	block[3].setBackground(new Color(114, 85, 68));
	block[4].setBackground(new Color(114, 85, 68));
	block[5].setBackground(new Color(114, 85, 68));
	block[6].setBackground(new Color(114, 85, 68));
	block[11].setBackground(new Color(114, 85, 68));
	block[12].setBackground(new Color(114, 85, 68));
	block[17].setText("Back");
	block[17].setForeground(Color.white);
	block[17].setFont(new java.awt.Font("Arial", 1, 25));
	block[17].setBackground(new Color(114, 85, 68));
	text = String.valueOf(numHammer);
	block[18].setText("Remaining: " + text);
	block[18].setForeground(Color.white);
	block[18].setFont(new java.awt.Font("Arial", 1, 25));
	block[18].setBackground(new Color(114, 85, 68));
	text = String.valueOf(numBack);
	block[23].setText("Remaining: " + text);
	block[23].setForeground(Color.white);
	block[23].setFont(new java.awt.Font("Arial", 1, 25));
	block[23].setBackground(new Color(114, 85, 68));
	block[24].setBackground(new Color(114, 85, 68));
	block[29].setBackground(new Color(114, 85, 68));
	block[30].setText("Exit");
	block[30].setForeground(Color.white);
	block[30].setFont(new java.awt.Font("Arial", 1, 25));
	block[30].setBackground(new Color(114, 85, 68));
	block[30].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){exit();}});
	block[31].setBackground(new Color(114, 85, 68));
	block[32].setText("Mute");
	block[32].setForeground(Color.white);
	block[32].setFont(new java.awt.Font("Arial", 1, 25));
	block[32].setBackground(new Color(114, 85, 68));
	block[32].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){Mute();}});
	block[33].setText("Unmute");
	block[33].setForeground(Color.white);
	block[33].setFont(new java.awt.Font("Arial", 1, 25));
	block[33].setBackground(new Color(114, 85, 68));
	block[33].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){UnMute();}});
	block[34].setBackground(new Color(114, 85, 68));
	block[35].setText("Restart");
	block[35].setForeground(Color.white);
	block[35].setFont(new java.awt.Font("Arial", 1, 25));
	block[35].setBackground(new Color(114, 85, 68));
	block[35].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){reStart();}});
	try{
    main_bgm = AudioSystem.getClip();
    InputStream is = Fun2048.class.getClassLoader().getResourceAsStream("background.wav");
    AudioInputStream ais = AudioSystem.getAudioInputStream(is);
    main_bgm.open(ais);
    main_bgm.loop(Clip.LOOP_CONTINUOUSLY);
    }
    catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
    e.printStackTrace();
	}
  } 
  
  public void appearBlock() 
  {
    while (numFlag)
    {
      int index = (int) (Math.random() * 16);
      if (block[temp[index]].getValue() == 0)
      {
        if (Math.random() < 0.5)
        {
          block[temp[index]].setValue(2);
        }
        else
        {
          block[temp[index]].setValue(4);
        }
		if (block[temp[index]].getValue() > maxScore) {
			maxScore = block[temp[index]].getValue();
			String text = String.valueOf(maxScore);
			block[4].setText(text);
			block[4].setForeground(Color.white);
			block[4].setFont(new java.awt.Font("Arial", 1, 25));
		}
        break;
      }
    }
  }
 
  public void judgeAppear()
  {
    int sum = 0;
    for (int i = 0; i < 16; i++) 
    {
      if (block[temp[i]].getValue() != 0)
      {
        sum++;
      }
    }
    if (sum == 16)
      numFlag = false;
 
  }
 
  public int Find(int i,int j,int a,int b)
  {
    while( i<b && i>=a )
    {
       if(block[i].getValue()!=0)
       {
        return i;
       }
       i=i+j;
    }
    return -1;
  }
  
  public void upBlock()
  {
    int i=0,j=0;int t=0;int valueJ=0;int valueI=0;int index=0;
    for(i=temp[0];i<=temp[3];i++)
    {
      index=i;
      for(j=i+6;j<29;j+=6)
      {  
        valueJ=0; valueI=0;
        if(block[index].getValue()==0)
        {
          t=Find(index,6,0,29);
          if(t!=-1)
          {
            block[index].setValue(block[t].getValue());
            block[t].setValue(0);
          }
          else
          {
            break;
          }
        }
        valueI=block[index].getValue();
        if(block[j].getValue()==0)
        {
          t=Find(j,6,0,29);
          if(t!=-1)
          {
            block[j].setValue(block[t].getValue());
            block[t].setValue(0);
          }
          else
          {
            break;
          }
        }
        valueJ=block[j].getValue();
        if(valueI==valueJ&&valueI!=0&&valueJ!=0)
        {
          block[index].setValue(valueI+valueJ);
		  sumScore += block[index].getValue();	  
          block[j].setValue(0);
          numFlag = true;
		  if (valueI + valueJ > maxScore) {
			maxScore = valueI + valueJ;
		    String text = String.valueOf(maxScore);
		    block[4].setText(text);
		    block[4].setForeground(Color.white);
		    block[4].setFont(new java.awt.Font("Arial", 1, 25));
		  }
        }
        index=j;
      }
       
    }
	try{
    Clip bgm = AudioSystem.getClip();
    InputStream is = Operation.class.getClassLoader().getResourceAsStream("sound.wav");
    AudioInputStream ais = AudioSystem.getAudioInputStream(is);
    bgm.open(ais);
    bgm.start();

    }
    catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
    e.printStackTrace();
    }
  }
  public void downBlock() {
 
    int i=0,j=0;int t=0;int valueJ=0;int valueI=0;int index=0;
    for(i=temp[12];i<29;i++)
    {
      index=i;
      for(j=i-6;j>=7;j-=6)
      {  
        valueJ=0; valueI=0;
        if(block[index].getValue()==0)
        {
          t=Find(index,-6,7,29);
          if(t!=-1)
          {
            block[index].setValue(block[t].getValue());
            block[t].setValue(0);
          }
          else
          {
            break;
          }
        }
        valueI=block[index].getValue();
        if(block[j].getValue()==0)
        {
          t=Find(j,-6,7,29);
          if(t!=-1)
          {
            block[j].setValue(block[t].getValue());
            block[t].setValue(0);
          }
          else
          {
            break;
          }
        }
        valueJ=block[j].getValue();
        if(valueI==valueJ&&valueI!=0&&valueJ!=0)
        {
          block[index].setValue(valueI+valueJ);
		  sumScore += block[index].getValue();	
          block[j].setValue(0);
          numFlag = true;
		  if (valueI + valueJ > maxScore) {
			maxScore = valueI + valueJ;
		    String text = String.valueOf(maxScore);
		    block[4].setText(text);
		    block[4].setForeground(Color.white);
		    block[4].setFont(new java.awt.Font("Arial", 1, 25));
		  }
        }
        index=j;
      }
    }
	try{
    Clip bgm = AudioSystem.getClip();
    InputStream is = Operation.class.getClassLoader().getResourceAsStream("sound.wav");
    AudioInputStream ais = AudioSystem.getAudioInputStream(is);
    bgm.open(ais);
    bgm.start();

    }
    catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
    e.printStackTrace();
    }
  }
  public void rightBlock() 
  {
    int i=0,j=0;int t=0;int valueJ=0;int valueI=0;int index=0;
    for(i=temp[3];i<29;i+=6)
    {
      index=i;
      for(j=i-1;j>i-4;j--)
      {  
        valueJ=0; valueI=0;
        if(block[index].getValue()==0)
        {
          t=Find(index,-1,i-3,index+1);
          if(t!=-1)
          {
            block[index].setValue(block[t].getValue());
            block[t].setValue(0);
          }
          else
          {
            break;
          }
        }
        valueI=block[index].getValue();
        if(block[j].getValue()==0)
        {
          t=Find(j,-1,i-3,j+1);
          if(t!=-1)
          {
            block[j].setValue(block[t].getValue());
            block[t].setValue(0);
          }
          else
          {
            break;
          }
        }
        valueJ=block[j].getValue();
        if(valueI==valueJ&&valueI!=0&&valueJ!=0)
        {
          block[index].setValue(valueI+valueJ);
		  sumScore += block[index].getValue();
          block[j].setValue(0);
          numFlag = true;
		  if (valueI + valueJ > maxScore) {
			maxScore = valueI + valueJ;
		    String text = String.valueOf(maxScore);
		    block[4].setText(text);
		    block[4].setForeground(Color.white);
		    block[4].setFont(new java.awt.Font("Arial", 1, 25));
		  }
        }
        index=j;
      }  
    }
	try{
    Clip bgm = AudioSystem.getClip();
    InputStream is = Operation.class.getClassLoader().getResourceAsStream("sound.wav");
    AudioInputStream ais = AudioSystem.getAudioInputStream(is);
    bgm.open(ais);
    bgm.start();

    }
    catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
    e.printStackTrace();
    }
  }
  public void leftBlock() 
  {
    int i=0,j=0;int t=0;int valueJ=0;int valueI=0;int index=0;
    for(i=temp[0];i<29;i+=6)
    {
      index=i;
      for(j=i+1;j<i+4;j++)
      {  
        valueJ=0; valueI=0;
        if(block[index].getValue()==0)
        {
          t=Find(index,1,index,i+4);
          if(t!=-1)
          {
            block[index].setValue(block[t].getValue());
            block[t].setValue(0);
          }
          else
          {
            break;
          }
        }
        valueI=block[index].getValue();
        if(block[j].getValue()==0)
        {
          t=Find(j,1,j,i+4);
          if(t!=-1)
          {
            block[j].setValue(block[t].getValue());
            block[t].setValue(0);
          }
          else
          {
            break;
          }
        }
        valueJ=block[j].getValue();
        if(valueI==valueJ&&valueI!=0&&valueJ!=0)
        {
          block[index].setValue(valueI+valueJ);
		  sumScore += block[index].getValue();	
          block[j].setValue(0);
          numFlag = true;
		  if (valueI + valueJ > maxScore) {
			maxScore = valueI + valueJ;
		    String text = String.valueOf(maxScore);
		    block[4].setText(text);
		    block[4].setForeground(Color.white);
		    block[4].setFont(new java.awt.Font("Arial", 1, 25));
		  }
        }
        index=j;
      }  
    }
	try{
    Clip bgm = AudioSystem.getClip();
    InputStream is = Operation.class.getClassLoader().getResourceAsStream("sound.wav");
    AudioInputStream ais = AudioSystem.getAudioInputStream(is);
    bgm.open(ais);
    bgm.start();

    }
    catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
    e.printStackTrace();
    }
  }
  public void hit(int k){
      if(hammerChances<4&&hamFlag==true){
       block[k].setValue(0);
       hamFlag=false;
	   button2 = 0;
      }
  }
  
  public void hammer(int i){
	if (button2 == 1) {
		hamFlag=true;
		if(hammerChances<3&&hamFlag==true){
		block[7].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(7);}});
		block[8].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(8);}});
		block[9].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(9);}});
		block[10].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(10);}});
		block[13].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(13);}});
		block[14].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(14);}});
		block[15].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(15);}});
		block[16].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(16);}});
		block[19].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(19);}});
		block[20].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(20);}});
		block[21].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(21);}});
		block[22].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(22);}});
		block[25].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(25);}});
		block[26].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(26);}});
		block[27].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(27);}});
		block[28].addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e){hit(28);}});
		String text = String.valueOf(numHammer);
		numHammer--;
		text = String.valueOf(numHammer);
		block[18].setText("Remaining: " + text);   
		}
		hammerChances++;  
	}
	try{
    Clip bgm = AudioSystem.getClip();
    InputStream is = Operation.class.getClassLoader().getResourceAsStream("hammer.wav");
    AudioInputStream ais = AudioSystem.getAudioInputStream(is);
    bgm.open(ais);
    bgm.start();

    }
    catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
    e.printStackTrace();
    }
  }
  
  public void over() 
  {
    if (numFlag ==false&& up==false&&down==false&&left==false&&right==false) 
    {
	  button = 1;
      block[temp[4]].setText("G");
      block[temp[5]].setText("A");
      block[temp[6]].setText("M");
      block[temp[7]].setText("E");
      block[temp[8]].setText("O");
      block[temp[9]].setText("V");
      block[temp[10]].setText("E");
      block[temp[11]].setText("R"); 
	  try{
		Clip bgm = AudioSystem.getClip();
		InputStream is = Operation.class.getClassLoader().getResourceAsStream("gameover.wav");
		AudioInputStream ais = AudioSystem.getAudioInputStream(is);
		bgm.open(ais);
		bgm.start();
		}
		catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
		e.printStackTrace();
		}
    }
  }
   
  public void win() 
  { 
	button = 1;
    block[temp[4]].setText("Y");
    block[temp[5]].setText("O");
    block[temp[6]].setText("U");
    block[temp[9]].setText("W");
    block[temp[10]].setText("I");
    block[temp[11]].setText("N");
	block[temp[4]].setBackground(new Color(252, 184, 0));
	block[temp[5]].setBackground(new Color(252, 184, 0));
	block[temp[6]].setBackground(new Color(252, 184, 0));
	block[temp[9]].setBackground(new Color(252, 184, 0));
	block[temp[10]].setBackground(new Color(252, 184, 0));
	block[temp[11]].setBackground(new Color(252, 184, 0));
	try{
	Clip bgm = AudioSystem.getClip();
	InputStream is = Operation.class.getClassLoader().getResourceAsStream("win.wav");
	AudioInputStream ais = AudioSystem.getAudioInputStream(is);
	bgm.open(ais);
	bgm.start();
	}
	catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
	e.printStackTrace();
	}
  }
  public void reStart()
  {
	numBack = 3;
	String text = String.valueOf(numBack);
	block[23].setText("Remaining: " + text);
	countPress = 0;
	button = 0;
	block[temp[15]].setFont(new Font("font", Font.PLAIN, 40));
    numFlag=true;
    moveFlag=0;
	sumScore = 0;
	maxScore = 0;
	text = String.valueOf(sumScore);
	block[1].setText(text);
    up=true;down=true;left=true;right=true;
    for(int i=0;i<16;i++)
      block[temp[i]].setValue(0);
    for (int i = 0; i < 2; i++)
      appearBlock();
	hamFlag=true;
    hammerChances=0;
	numHammer=3;
	text = String.valueOf(numHammer);
	block[18].setText("Remaining: " + text); 
	try{
    Clip bgm = AudioSystem.getClip();
    InputStream is = Operation.class.getClassLoader().getResourceAsStream("restart.wav");
    AudioInputStream ais = AudioSystem.getAudioInputStream(is);
    bgm.open(ais);
    bgm.start();

    }
    catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
    e.printStackTrace();
    }
  }
  public void keyPressed(KeyEvent e) 
  {
	if(button == 1)
		return;
	button1 = 0;
	setundo();
    switch (e.getKeyCode()) {
    case KeyEvent.VK_UP:
	  countPress++;
      if(up){
      upBlock();}
      judgeAppear();
      appearBlock();
      over();
       
      if(numFlag==false)
      {
        up=false;
      }
      else
      {
        up=true;down=true;left=true;right=true;
      }
      break;
    case KeyEvent.VK_DOWN:
	  countPress++;
      if(down){
      downBlock();}
      judgeAppear();
      appearBlock();
      over();
      if(numFlag==false)
      {
        down=false;
      }
      else
      {
        up=true;down=true;left=true;right=true;
      }
      break;
    case KeyEvent.VK_LEFT:
	  countPress++;
      if(left){
      leftBlock();}
      judgeAppear();
      appearBlock();
      over();
       
      if(numFlag==false)
      {
        left=false;
      }
      else
      {
        up=true;down=true;left=true;right=true;
      }
      break;
    case KeyEvent.VK_RIGHT:
	  countPress++;
      if(right){
      rightBlock();}
      judgeAppear();
      appearBlock();
      over();
       
      if(numFlag==false)
      {
        right=false;
      }
      else
      {
        up=true;down=true;left=true;right=true;
      }
      break;
    }
	String text = String.valueOf(sumScore);
	block[1].setText(text);
	if (maxScore == 2048)
		win();
  }
  public void exit(){
    System.exit(0);
  }
  public void setundo(){
    for(int i = 0; i < 16; i++){
      undovalue[i] = block[temp[i]].getValue();
    }
    undonumflag = numFlag;
	undoMaxScore = maxScore;
	undoSumScore = sumScore;
  }
  public void Undo(){
	if(button == 0 && countPress != 0 && numBack > 0 && button1 == 0) {
		for(int i = 0; i < 16; i++){
			block[temp[i]].setValue(undovalue[i]);
		}
		numFlag = undonumflag;
		maxScore = undoMaxScore;
		sumScore = undoSumScore;
		String text = String.valueOf(maxScore);
		block[4].setText(text);
		block[4].setForeground(Color.white);
		block[4].setFont(new java.awt.Font("Arial", 1, 25));
		text = String.valueOf(sumScore);
		block[1].setText(text);
		block[1].setForeground(Color.white);
		block[1].setFont(new java.awt.Font("Arial", 1, 25));
		setundo();
		numBack--;
		text = String.valueOf(numBack);
		block[23].setText("Remaining: " + text);
		
	}
	button1 = 1;
	try{
	Clip bgm = AudioSystem.getClip();
	InputStream is = Operation.class.getClassLoader().getResourceAsStream("back.wav");
	AudioInputStream ais = AudioSystem.getAudioInputStream(is);
	bgm.open(ais);
	bgm.start();

	}
	catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
	e.printStackTrace();
	}
  }
  public void Mute(){
    main_bgm.stop();
  }
  public void UnMute(){
    main_bgm.loop(Clip.LOOP_CONTINUOUSLY);
  }
  public void keyTyped(KeyEvent e) {
 
  }
  public void keyReleased(KeyEvent e) {
 
  }
}