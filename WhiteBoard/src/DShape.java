import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class DShape implements ModelListener
{
	protected DShapeModel model;
	public DShape()
	{
		model = new DShapeModel();
		model.registerListener(this);
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		if(Canvas.isSelected(this))
		{
			g2.setColor(Color.BLACK);
			g2.fillRect((int)(model.getBound().getX()-4),
				    (int)model.getBound().getY()-4, 9, 9);
			g2.fillRect((int)model.getBound().getX()+(int)model.getBound().getWidth()-5,
				    (int)model.getBound().getY()-4, 9, 9);
			g2.fillRect((int)model.getBound().getX()-4,
				    (int)model.getBound().getY()+(int)model.getBound().getHeight()-5, 9, 9);
			g2.fillRect((int)model.getBound().getX()+(int)model.getBound().getWidth()-5,
				    (int)model.getBound().getY()+(int)model.getBound().getHeight()-5, 9, 9);
		}
	}
	public void loadModel(DShapeModel m)
	{
		model.setBound((int)m.getBound().getX(),
				       (int)m.getBound().getY(),
				       (int)m.getBound().getWidth(),
				       (int)m.getBound().getHeight());
	}
	public void modelChanged(DShapeModel model)
	{
		System.out.println("Receive model changed");
	}
	public ArrayList<Point> getKnobs()
	{
		ArrayList <Point> knobs = new ArrayList(4);
		int shapeX = this.model.getPars()[0];
		int shapeY = this.model.getPars()[1];
		int width = this.model.getPars()[2];
		int height = this.model.getPars()[3];
		knobs.get(0).setLocation(shapeX,shapeY);                    //upper-left
		knobs.get(1).setLocation(shapeX+width,shapeY);              //upper-right
		knobs.get(2).setLocation(shapeX,shapeY+height);             //lower-left
		knobs.get(3).setLocation(shapeX+width,shapeY+height);       //lower-right
		return knobs;
	}
}
class DRect extends DShape
{
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(model.getColor());
		g2.fillRect((int)model.getBound().getX(),
				    (int)model.getBound().getY(),
				    (int)model.getBound().getWidth(),
				    (int)model.getBound().getHeight());
		super.draw(g);
	}
}

class DOval extends DShape
{
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(model.getColor());
		g2.fillOval((int)model.getBound().getX(),
				    (int)model.getBound().getY(),
				    (int)model.getBound().getWidth(),
				    (int)model.getBound().getHeight());
		super.draw(g);
	}
	
}
