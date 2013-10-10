package wasada;
import java.awt.*;

public class Computer 
{
double x, y, radius;
int hitsLeft, numSplit;
public Computer(double x,double y,double radius,int hitsLeft,int numSplit)
{
this.x=x;
this.y=y;
this.radius=radius;
this.hitsLeft=hitsLeft; 
this.numSplit=numSplit;
}
public void move(int playerX, int playerY)
{
if(x<playerX)
{
x++;
}
else if(x>playerX)
{
x--;
}

if(y<playerY)
{
y++;
}
else if(y>playerY)
{
y--;
}

}

public void draw(Graphics g, Color color)
{
g.setColor(color);
g.fillOval((int)(x-radius+.5),(int)(y-radius+.5),(int)(2*radius),(int)(2*radius));
}

public Computer createSplitComputer()
{
return new Computer(x,y,radius/Math.sqrt(numSplit),hitsLeft-1,numSplit);
}

public boolean playerCollision(Player player)
{
if(Math.pow(radius+player.getRadius(),2) >Math.pow(player.getX()-x,2) + Math.pow(player.getY()-y,2)&& player.isActive())
return true;
return false;
}

public boolean shotCollision(Shot shot)
{
if(Math.pow(radius,2) > Math.pow(shot.getX()-x,2)+Math.pow(shot.getY()-y,2))
return true;
return false;
}

public int getHitsLeft()
{
return hitsLeft;
}

public int getNumSplit()
{
return numSplit;
}
}