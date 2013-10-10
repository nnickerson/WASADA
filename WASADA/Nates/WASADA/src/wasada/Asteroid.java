package wasada;

import java.awt.*;

public class Asteroid 
{
double x, y, xVelocity, yVelocity, radius;
int hitsLeft, numSplit;
public Asteroid(double x,double y,double radius,double minVelocity,double maxVelocity,int hitsLeft,int numSplit)
{
this.x=x;
this.y=y;
this.radius=radius;
this.hitsLeft=hitsLeft; 
this.numSplit=numSplit;
double vel=minVelocity + Math.random()*(maxVelocity-minVelocity),dir=2*Math.PI*Math.random(); 
xVelocity=vel*Math.cos(dir);
yVelocity=vel*Math.sin(dir);
}
public void move(int shipX, int shipY)
{
if(x<shipX)
{
x++;
}
else if(x>shipX)
{
x--;
}

if(y<shipY)
{
y++;
}
else if(y>shipY)
{
y--;
}

}

public void draw(Graphics g, Color color)
{
g.setColor(color);
g.fillOval((int)(x-radius+.5),(int)(y-radius+.5),(int)(2*radius),(int)(2*radius));
}

public Asteroid createSplitAsteroid(double minVelocity,double maxVelocity)
{
return new Asteroid(x,y,radius/Math.sqrt(numSplit),minVelocity,maxVelocity,hitsLeft-1,numSplit);
}

public boolean shipCollision(Player ship)
{
if(Math.pow(radius+ship.getRadius(),2) >Math.pow(ship.getX()-x,2) + Math.pow(ship.getY()-y,2)&& ship.isActive())
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