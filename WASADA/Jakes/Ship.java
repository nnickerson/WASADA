package asteroids;
import java.awt.*;

public class Ship 
{
final double[] origXPts={14,-10,-6,-10},origYPts={0,-8,0,8};
final int radius=6;
double x, y, angle, xVelocity, yVelocity, acceleration,velocityDecay, rotationalSpeed;
boolean turningLeft, turningRight, accelerating, active;
int[] xPts, yPts;
int shotDelay, shotDelayLeft;

public Ship(double x, double y, double angle, double acceleration,double velocityDecay, double rotationalSpeed,int shotDelay)
{
this.x=x;
this.y=y;
this.angle=angle;
this.acceleration=acceleration;
this.velocityDecay=velocityDecay;
this.rotationalSpeed=rotationalSpeed;
xVelocity=0;
yVelocity=0;
turningLeft=false; 
turningRight=false;
accelerating=false;
active=false; 
xPts=new int[4];
yPts=new int[4];
this.shotDelay=shotDelay; 
shotDelayLeft=0; 
}

public void draw(Graphics g)
{
for(int i=0;i<4;i++)
{
xPts[i]=(int)(origXPts[i]*Math.cos(angle)- origYPts[i]*Math.sin(angle)+x+.5); 
yPts[i]=(int)(origXPts[i]*Math.sin(angle)+ origYPts[i]*Math.cos(angle)+y+.5); 
}

if(active) 
g.setColor(Color.red);

else 
g.setColor(Color.darkGray);
g.fillPolygon(xPts,yPts,4); 
}

public void move(int scrnWidth, int scrnHeight)
{
if(shotDelayLeft>0) 
shotDelayLeft--; 

if(turningLeft) 
angle-=rotationalSpeed;

if(turningRight) 
angle+=rotationalSpeed; 

if(angle>(2*Math.PI)) 
angle-=(2*Math.PI);

else if(angle<0)
angle+=(2*Math.PI);

if(accelerating)
{ 
xVelocity+=acceleration*Math.cos(angle);
yVelocity+=acceleration*Math.sin(angle);
}

x+=xVelocity; 
y+=yVelocity;
xVelocity*=velocityDecay; 
yVelocity*=velocityDecay; 

if(x<0) 
x = 0;

else if(x>scrnWidth)
x=scrnWidth;

if(y<0)
y=0;

else if(y>scrnHeight)
y=scrnHeight;
}

public void setAccelerating(boolean accelerating)
{
this.accelerating=accelerating;
}

public void setTurningLeft(boolean turningLeft)
{
this.turningLeft=turningLeft;
}

public void setTurningRight(boolean turningRight)
{
this.turningRight=turningRight;
}

public double getX()
{
return x;
}

public double getY()
{
return y;
}

public double getRadius()
{
return radius;
}

public void setActive(boolean active)
{
this.active=active;
}

public boolean isActive()
{
return active;
}

public boolean canShoot()
{
if(shotDelayLeft>0) 
return false; 
else
return true;
}

public Shot shoot()
{
shotDelayLeft=shotDelay;
return new Shot(x,y,angle,xVelocity,yVelocity,40);
}
}