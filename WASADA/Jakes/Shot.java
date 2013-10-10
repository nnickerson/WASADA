package asteroids;
import java.awt.*;

public class Shot 
{
final double shotSpeed=12;
double x,y,xVelocity,yVelocity;
int lifeLeft;

public Shot(double x, double y, double angle, double shipXVel,double shipYVel, int lifeLeft)
{
this.x=x;
this.y=y;
xVelocity=shotSpeed*Math.cos(angle)+shipXVel;
yVelocity=shotSpeed*Math.sin(angle)+shipYVel;
this.lifeLeft=lifeLeft;
}

public void move(int scrnWidth, int scrnHeight)
{
lifeLeft--;
x+=xVelocity; 
y+=yVelocity;

}

public void draw(Graphics g)
{
g.setColor(Color.magenta); 
g.fillOval((int)(x-.5), (int)(y-.5), 3, 3);
}

public double getX()
{
return x;
}

public double getY()
{
return y;
}

public int getLifeLeft()
{
return lifeLeft;
}
}