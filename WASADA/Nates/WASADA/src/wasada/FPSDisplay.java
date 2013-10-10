package wasada;

import java.awt.Color;
import java.awt.Graphics;

public class FPSDisplay {
	
//	long nextSecond = System.currentTimeMillis() + 1000;
//	int framesInLastSecond = 0;
//	int framesInCurrentSecond = 0;
//
//	public void draw(Graphics g)
//	{
//		long currentTime = System.currentTimeMillis();
//	    if (currentTime > nextSecond) {
//	        nextSecond += 1000;
//	        framesInLastSecond = framesInCurrentSecond;
//	        framesInCurrentSecond = 0;
//	    }
//	    framesInCurrentSecond++;
//
//	    g.drawString("FPS: " + framesInLastSecond, 425, 20);
//	}
	
	public void draw(Graphics g, int fps)
	{
	
	g.setColor(Color.cyan);
	g.drawString(""+fps, 425, 20);
	}
}
