import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class Canvas extends JPanel 
{
	private ArrayList<DShape> shapes = new ArrayList<DShape>();
	private DRect knob = new DRect();
	protected static DShape selected = null;
	private int indexOfSelected = -1;
	public Canvas()
	{
		super();
		this.setSize(400, 400);
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		Border border = BorderFactory.createLineBorder(Color.GRAY);
		this.setBorder(border);
	}
	public int getIndexOfSelected()
	{
		return indexOfSelected;
	}
	public ArrayList<DShape> getShapes()
	{
		return this.shapes;
	}
	public void addAshape(DShape shape)
	{
		shapes.add(shape);
	}
	public void addShape(DShapeModel model)
	{
		if(model instanceof DRectModel)
		{
			DRect temp = new DRect();
			temp.loadModel(model);
			shapes.add(temp);
		}
		else if(model instanceof DOvalModel)
		{
			DOval temp = new DOval();
			temp.loadModel(model);
			shapes.add(temp);
		}
	}
	public void rmSelected()
	{
		shapes.remove(indexOfSelected);
		indexOfSelected = -1;
		selected = null;
	}
    public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(DShape shape: shapes)
		{
			shape.draw(g);
		}
		
	}
	public void Selection(int x,int y)
	{
		int selectedIndex = -1;
		
		for(DShape shape : shapes)
		{
			int shapeX = shape.model.getPars()[0];
			int shapeY = shape.model.getPars()[1];
			int width = shape.model.getPars()[2];
			int height = shape.model.getPars()[3];
			if((shape instanceof DRect)&&((x >= shapeX && x <= shapeX+width && y>=shapeY && y<=shapeY+height)
					||((x>=shapeX-5 && x<=shapeX+5 && y>=shapeY-5 && y<=shapeY+5)  //upper-left
				     ||(x>=shapeX-5 && x<=shapeX+5 && y>=shapeY+height-5 && y<=shapeY+height+5) //lower-left
				     ||(x>=shapeX+width-5 && x<=shapeX+width+5 && y>=shapeY-5 && y<=shapeY+5)   //upper-right
				     ||(x>=shapeX+width-5 && x<=shapeX+width+5 && y>=shapeY+height-5 && y<=shapeY+height+5)))) //lower-right
			{
				if(shapes.indexOf(shape)> selectedIndex)
				{
					selectedIndex = shapes.indexOf(shape);
				}
			}
			else if(shape instanceof DOval)
			{
				double xc = shapeX+width/2.0;
				double yc = shapeY+height/2.0;
				double result = Math.pow(x-xc, 2.0)/Math.pow(width/2.0, 2.0)
						      + Math.pow(y-yc, 2.0)/Math.pow(height/2.0,2.0);
				
				if ((result <= 1.0) ||((x>=shapeX-5 && x<=shapeX+5 && y>=shapeY-5 && y<=shapeY+5)
						             ||(x>=shapeX-5 && x<=shapeX+5 && y>=shapeY+height-5 && y<=shapeY+height+5)
						             ||(x>=shapeX+width-5 && x<=shapeX+width+5 && y>=shapeY-5 && y<=shapeY+5)
						             ||(x>=shapeX+width-5 && x<=shapeX+width+5 && y>=shapeY+height-5 && y<=shapeY+height+5))
						&& (shapes.indexOf(shape) > selectedIndex))
				{
					selectedIndex = shapes.indexOf(shape);
				}
			}
			//TODO: add text and line
		}
		if(selectedIndex != -1)
		{
			selected = shapes.get(selectedIndex);
			indexOfSelected = selectedIndex;
			System.out.println("selected: shapes["+selectedIndex+"]" );
			this.revalidate();
			this.repaint();
		}
		else			
		{
			selected = null;
			System.out.println("selected: none");
			this.revalidate();
			this.repaint();
		}
	}
	public DShape getSelected()
	{
		return this.selected;
	}
	public static boolean isSelected(DShape shape)
	{
		return shape.equals(selected);
	}
	
	public void move(int x,int y)
	{
		int shapeX = getSelected().model.getPars()[0];
    	int shapeY = getSelected().model.getPars()[1];
    	int width = getSelected().model.getPars()[2];
    	int height = getSelected().model.getPars()[3];
    	shapeX = x-width/2;
    	shapeY = y-height/2;
    	getSelected().model.setBound(shapeX, shapeY, width, height);
    	revalidate();
    	repaint();
	}
	public void Resize(int x,int y)
	{
			int shapeX = getSelected().model.getPars()[0];
        	int shapeY = getSelected().model.getPars()[1];
        	int width = getSelected().model.getPars()[2];
        	int height = getSelected().model.getPars()[3];
        	
        	if(x>=shapeX-5 && x<=shapeX+5 && y>=shapeY-5 && y<=shapeY+5) //upperleft knob
        	{
        		int newWidth = shapeX-x+width;
        		int newHeight = shapeY-y+height;
        		int newShapeX = x;
        		int newShapeY = y;
        		getSelected().model.setBound(newShapeX, newShapeY, newWidth, newHeight);
        		this.revalidate();
        		this.repaint();
        	}
        	else if(x>=shapeX+width-5 && x<=shapeX+width+5 && y>=shapeY-5 && y<=shapeY+5) //upperRight knob
        	{
        		int newWidth = x-shapeX;
        		int newHeight = shapeY-y+height;
        		int newShapeY = y;
        		getSelected().model.setBound(shapeX, newShapeY, newWidth, newHeight);
        		this.revalidate();
        		this.repaint();
        	}
        	else if(x>=shapeX-5 && x<=shapeX+5 && y>=shapeY+height-5 && y<=shapeY+height+5) //lowerLight knob
        	{
        		int newWidth = shapeX-x+width;
        		int newHeight = y-shapeY;
        		int newShapeX = x;
        		
        		getSelected().model.setBound(newShapeX, shapeY, newWidth, newHeight);
        		this.revalidate();
        		this.repaint();
        	}
        	else if(x>=shapeX+width-5 && x<=shapeX+width+5 && y>=shapeY+height-5 && y<=shapeY+height+5)
        	{
        		int newWidth = x-shapeX;
        		int newHeight = y-shapeY;
        		getSelected().model.setBound(shapeX, shapeY, newWidth, newHeight);
        		this.revalidate();
        		this.repaint();
        	}
	
	}
	
}
