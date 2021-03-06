package wasada;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * author Jacob Demmith
 **/
public class AsteroidsGame extends Applet implements Runnable, KeyListener
{
static final long serialVersionUID = 1L;
Thread thread;
Dimension dim;
Image img;
Graphics g;
long endTime, startTime, framePeriod;
Player ship;
JLabel instruct;
boolean paused, shooting;
Shot[] shots;
Computer[] asteroids; 
double astRadius, minAstVel,maxAstVel; 
int level,counter, killCount = 0, experienceCount = 0, astNumHits,astNumSplit, numAsteroids, numShots, playerLife;
JButton button = new JButton("Hello");
Random gen = new Random();
Color userColor, playersChosenColor;
MenuBar menuBar;
Menu menu, playerColorMenu, playerColor, computerDifficulty;
private MenuItem normalComputer, saveGameMenu, loadGameMenu, hardComputer, yellowPlayer, greenPlayer, redPlayer;
private String mode = "Normal", fileLocation = "C://Temp//WASADA.txt";
private LifeBar lifeBar;
private SaveGame saveGame;
ObjectOutputStream oOS;
ObjectInputStream oIS;


/**
 * This initializes my variable with values
 */
public void init()
{
menuBar = new MenuBar();
playersChosenColor = Color.red;

lifeBar = new LifeBar();
playerLife = 100;
resize(500,500);
endTime=0;
startTime=0;
framePeriod=25;
addKeyListener(this);
dim=getSize();
img=createImage(dim.width, dim.height);
g=img.getGraphics();
thread=new Thread(this);
thread.start();
shots=new Shot[41]; 
numAsteroids=0;
level=0; 
astRadius=16; 
minAstVel=.5;
maxAstVel=5;
astNumHits=1;
astNumSplit=2;
button.setBounds(200,200,200,200);
button.addActionListener(new ButtonListener());
userColor = Color.blue;
getFrame();
}

public void getFrame()
{
	Object f = getParent();
	while(!(f instanceof Frame))
	{
		f = ((Component) f).getParent();
	}
	Frame frame = (Frame) f;
	
	menuBar.add(menu = new Menu("Menu"));
	
	menu.add(playerColor = new Menu("Player Color"));
	
	playerColor.add(redPlayer = new MenuItem("Red"));
	playerColor.add(greenPlayer = new MenuItem("Green"));
	playerColor.add(yellowPlayer = new MenuItem("Yellow"));
	
	menu.add(computerDifficulty = new Menu("Computer Difficulty"));
	computerDifficulty.add(normalComputer = new MenuItem("Normal"));
	computerDifficulty.add(hardComputer = new MenuItem("Hard"));
	
	menu.add(saveGameMenu = new MenuItem("Save Game"));
	
	menu.add(loadGameMenu = new MenuItem("Load Game"));
	
	redPlayer.addActionListener(new Menuhandler());
	yellowPlayer.addActionListener(new Menuhandler());
	greenPlayer.addActionListener(new Menuhandler());
	
	normalComputer.addActionListener(new Menuhandler());
	hardComputer.addActionListener(new Menuhandler());
	
	saveGameMenu.addActionListener(new Menuhandler());
	loadGameMenu.addActionListener(new Menuhandler());
	
	frame.setMenuBar(menuBar);
	frame.setSize(new Dimension(507,575));
	frame.setResizable(false);
	}

/**
 * This methods sets up the next level with more asteroids.
 */
public void setUpNextLevel()
{
level++;
ship=new Player(250,250,0,.35,.8,.2,10);
numShots=0; 
paused=true;
playerLife = 100;
shooting=false;
asteroids=new Computer[level*(int)Math.pow(astNumSplit,astNumHits-1)+1];
numAsteroids=level;
for(int i=0;i<numAsteroids;i++)
asteroids[i]=new Computer(RandomGenX(),RandomGenY(),astRadius,astNumHits,astNumSplit);
}

public int RandomGenX()
{
int x = gen.nextInt(500);
	if(x>150 && x< 350)
	{
		x += 200;
	}
	return x;
}

public int RandomGenY()
{
int y = gen.nextInt(500);
	if(y >150 && y< 350)
	{
		y += 200;
	}
	return y;
}


/**
 * This method will paint my background, asteroids, ship, and shots.
 */
public void paint(Graphics gfx)
{
counter = 0;
while(counter == 0)
{
	g.setColor(Color.black);
	g.fillRect(0,0,500,500);
	for(int i=0;i<numShots;i++) 
	shots[i].draw(g);
	for(int i=0;i<numAsteroids;i++)
	asteroids[i].draw(g,userColor);
	ship.draw(g, playersChosenColor); 
	g.setColor(Color.cyan); 
	g.drawString("Level " + level,20,20);
	counts();
	lifeBar.draw(g, playerLife);
	gfx.drawImage(img,0,0,this);
	counter++;
}
	if(ship.isActive() && !paused)
	{
g.setColor(Color.black);
g.fillRect(0,0,500,500);
for(int i=0;i<numShots;i++) 
shots[i].draw(g);
for(int i=0;i<numAsteroids;i++)
asteroids[i].draw(g,userColor);
ship.draw(g, playersChosenColor); 
g.setColor(Color.cyan); 
g.drawString("Level " + level,20,20);
counts();
lifeBar.draw(g, playerLife);
gfx.drawImage(img,0,0,this);
	}
}

/**
 * Repaints everything to be updated.		
 */
public void update(Graphics gfx)
{
paint(gfx);
}

/**
 * 
 */
public void run()
{
for(;;)
{
startTime=System.currentTimeMillis();
if(numAsteroids<=0)
setUpNextLevel();

if(!paused)
{
ship.move(dim.width,dim.height); 

for(int i=0;i<numShots;i++)
{
shots[i].move(dim.width,dim.height);
if(shots[i].getLifeLeft()<=0)
{
deleteShot(i);
i--; 
}
}
updateAsteroids();
if(shooting && ship.canShoot())
{
shots[numShots]=ship.shoot();
numShots++;
}
}
repaint();
try
{
endTime=System.currentTimeMillis();
if(framePeriod-(endTime-startTime)>0)
Thread.sleep(framePeriod-(endTime-startTime));
}
catch(InterruptedException e)
{}
}
}

/**
 * deletes the shots.
 */
private void deleteShot(int index)
{
numShots--;
for(int i=index;i<numShots;i++)
shots[i]=shots[i+1];
shots[numShots]=null;
}

/**
 * removes asteroids after last hit.
 */
private void deleteAsteroid(int index)
{
try
{
numAsteroids--;
for(int i=index;i<numAsteroids;i++)
asteroids[i]=asteroids[i+1];
asteroids[numAsteroids]=null;
}
catch(ArrayIndexOutOfBoundsException excpetion)
{}
}

/**
 * Adds asteroids
 */
private void addAsteroid(Computer ast)
{
asteroids[numAsteroids]=ast;
numAsteroids++;
}


private void updateAsteroids()
{
for(int i=0;i<numAsteroids;i++)
{
asteroids[i].move((int)ship.getX(),(int)ship.getY());
	
if(mode.equals("Hard")) {
	asteroids[i].move((int)ship.getX(),(int)ship.getY());
}
if(asteroids[i].shipCollision(ship))
{
	if(playerLife > 0)
	{
		playerLife -= 1;
	}
	else 
	{
level-=2; 
numAsteroids=0;
experienceCount -= 13;
playerLife = 100;
	}
}
for(int j=0;j<numShots;j++)
{
if(asteroids[i].shotCollision(shots[j]))
{
deleteShot(j);
killCount++;

if(mode.equals("Hard"))
{
experienceCount += 40;
}
if(mode.equals("Normal")) {
	experienceCount += 25;
}


if(asteroids[i].getHitsLeft()>1)
{
for(int k=0;k<asteroids[i].getNumSplit();k++)
addAsteroid(
asteroids[i].createSplitAsteroid(minAstVel,maxAstVel));
}

deleteAsteroid(i);
j=numShots; 
i--; 
}
}
}
}

public void keyPressed(KeyEvent e)
{
if(e.getKeyCode()==KeyEvent.VK_ENTER)
{
if(!ship.isActive() && !paused)
{
ship.setActive(true);
}

else
{
paused=!paused; 
if(paused)
{
ship.setActive(false);

}
else
ship.setActive(true);
}
}
else if(paused || !ship.isActive()) 
return;

else if(e.getKeyCode()==KeyEvent.VK_UP)
ship.setAccelerating(true);

else if(e.getKeyCode()==KeyEvent.VK_LEFT)
ship.setTurningLeft(true);

else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
ship.setTurningRight(true);

else if(e.getKeyCode()==KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_CONTROL)
shooting=true; 
}
public void keyReleased(KeyEvent e)
{
if(e.getKeyCode()==KeyEvent.VK_UP)
ship.setAccelerating(false);

else if(e.getKeyCode()==KeyEvent.VK_LEFT)
ship.setTurningLeft(false);

else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
ship.setTurningRight(false);

else if(e.getKeyCode()==KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_CONTROL)
shooting=false;
}

public void keyTyped(KeyEvent e)
{}

private class ButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button)
		{
		remove(button);
		}
		
	}
	
}

public class Menuhandler implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == redPlayer) {
			playersChosenColor = Color.RED;
		}
		if(e.getSource() == greenPlayer) {
			playersChosenColor = Color.GREEN;
		}
		if(e.getSource() == yellowPlayer) {
			playersChosenColor = Color.YELLOW;
		}
		
		
		
		
		
		if(e.getSource() == normalComputer) {
			mode = "Normal";
		}
		
		if(e.getSource() == hardComputer) {
			mode = "Hard";
		}
		
		
		
		
		
		if(e.getSource() == saveGameMenu) {
			saveGame = new SaveGame(killCount, experienceCount);

			try {
				oOS = new ObjectOutputStream(new FileOutputStream(fileLocation));
				
				oOS.writeObject(saveGame);
				
				oOS.flush();
				oOS.close();
			} catch (IOException ex) {
				System.out.println("There was an error while trying to save the game.");
			}
		}
			
			
			
			if(e.getSource() == loadGameMenu) {
				

				try {
					
					oIS = new ObjectInputStream(new FileInputStream(fileLocation));
				
					try {
						saveGame = (SaveGame)oIS.readObject();
					} catch (ClassNotFoundException e1) {
						System.out.println("Error while reading file.");
					}
					
					
					killCount = saveGame.getKillCount();
					System.out.println(killCount);
					System.out.println(experienceCount);
					
					experienceCount = saveGame.getExperienceCount();
					
					oIS.close();
					
					setUpNextLevel();
				} catch (IOException ex) {
					System.out.println("There was an error while trying to save the game.");
			}
			}
		}
	}
	





/**
 * ALL DONE LAST NIGHT
 */

public void counts() {
	g.drawString("Kills " + killCount,20,40);
	g.drawString("Experience " + experienceCount,20,60);
}

public int getLevel() {
	return level;
}

public static void main(String[]args)
{
	
}
}