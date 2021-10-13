import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

public class DShapeModel 
{
	protected ArrayList<ModelListener> mdListeners = new ArrayList<ModelListener>();
	protected Rectangle bound;
	protected Color color;
	public DShapeModel()
	{
		bound = new Rectangle(0,0,0,0);
		setColor(Color.GRAY);
	}
	public void setColor(Color c)
	{
		this.color = c;
		notifyListeners();
	}
	public Color getColor(){return this.color;}
	public void setBound(int x,int y,int width, int height)
	{
		bound.setBounds(x, y, width, height);
		notifyListeners();
	}
	public Rectangle getBound()
	{
		return this.bound;
	}
	public int[] getPars()
	{
		int[] parList = new int[4];
		parList[0] = (int)bound.getX();
		parList[1] = (int)bound.getY();
		parList[2] = (int)bound.getWidth();
		parList[3] = (int)bound.getHeight();
		return parList;
	}
	public void registerListener(ModelListener listener)
	{
		mdListeners.add(listener);
	}
	public void removeListener(ModelListener listener)
	{
		mdListeners.remove(listener);
	}
	public void notifyListeners()
	{
		for(ModelListener listener:mdListeners)
		{
			listener.modelChanged(this);
		}
	}

}

class DRectModel extends DShapeModel
{}
class DOvalModel extends DShapeModel
{}
class DLineModel extends DShapeModel
{}
class DTextModel extends DShapeModel
{
	protected String str;
	public void setText(String s)
	{
		str = new String(s);
	}
	public String getText(){return str;}

}
