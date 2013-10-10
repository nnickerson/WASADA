package wasada;

import java.awt.Color;
import java.awt.Graphics;

public class LifeBar {

	
	public void draw(Graphics g, int numHits)
	{
	
	g.setColor(Color.GREEN);
	g.fillRect(20, 250, 10, numHits);
	}
}
