import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;


public class Whiteboard extends JFrame implements ActionListener,MouseListener,MouseMotionListener
{
	Canvas canvas = new Canvas();
	public Whiteboard()
	{
		super("White board");                      //create a JFrame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setExtendedState(NORMAL);
		
	    
		//begin to add component in the Frame
		JPanel ctrlNpos = new JPanel();            //create a panel for control and position
		ctrlNpos.setLayout(new BorderLayout());    
		Box shape = Box.createHorizontalBox();           //create a shape box
		JLabel add = new JLabel("Add");
		shape.add(add);
		shape.add(Box.createHorizontalStrut(8));
		JButton rect = new JButton("Rect");
		rect.addActionListener(this);
		shape.add(rect);
		shape.add(Box.createHorizontalStrut(8));
		
		JButton oval = new JButton("Oval");
		oval.addActionListener(this);
		shape.add(oval);
		shape.add(Box.createHorizontalStrut(8));
		JButton line = new JButton("Line");
		shape.add(line);
		shape.add(Box.createHorizontalStrut(8));
		JButton text = new JButton("Text");
		shape.add(text);
		shape.add(Box.createRigidArea(new Dimension(5,50)));
		
		Box color = Box.createHorizontalBox();           //create a color box
		JButton setColor = new JButton("Set Color");
		setColor.addActionListener(this);
		color.add(setColor);
		color.add(Box.createRigidArea(new Dimension(5,50)));
		
		Box inputText = Box.createHorizontalBox();       //create a text box
		JTextField tf = new JTextField(10);
		inputText.add(tf);
		inputText.add(Box.createHorizontalStrut(8));
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		int index = Arrays.asList(fonts).indexOf("Dialog");
		JComboBox fontList = new JComboBox(fonts);
		fontList.setSelectedIndex(index);
		fontList.setEditable(false);
		inputText.add(fontList);
		
		
		Box operation = Box.createHorizontalBox();        //create a operation box 
		JButton mvFront = new JButton("Move To Front");
		mvFront.addActionListener(this);
		operation.add(mvFront);
		operation.add(Box.createHorizontalStrut(8));
		JButton mvBack = new JButton("Move To Back");
		operation.add(mvBack);
		mvBack.addActionListener(this);
		operation.add(Box.createHorizontalStrut(8));
		JButton rmShape = new JButton("Remove Shape");
		rmShape.addActionListener(this);
		operation.add(rmShape);
		operation.add(Box.createRigidArea(new Dimension(5,70)));
		
		Box ctrlBox = Box.createVerticalBox();          //create a ctrlBox(vertical)
		ctrlBox.add(shape);                             //load all boxes into the ctrlBox box
		ctrlBox.add(color);
		ctrlBox.add(inputText);
		ctrlBox.add(operation);
		for(Component comp : ctrlBox.getComponents())
		{
			((JComponent)comp).setAlignmentX(Box.LEFT_ALIGNMENT);
		}
		ctrlBox.setOpaque(true);
		ctrlBox.setBackground(new Color(223,223,223));
		
		JPanel pos = new JPanel();                  //create a position panel
		pos.setLayout(new GridLayout());
		//TODO
		
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		Container con = this.getContentPane();
		con.add(canvas,BorderLayout.CENTER);
		ctrlNpos.add(ctrlBox,BorderLayout.NORTH);
		ctrlNpos.add(pos, BorderLayout.SOUTH);
	    con.add(ctrlNpos,BorderLayout.WEST);
	    
	    
	    setSize(783,440);
	    setLocation(300, 180);
	    setVisible(true);
	 
	   //TODO test here
	}
	
	public void actionPerformed(ActionEvent e)
	{

		if(e.getActionCommand().equals("Rect"))
		{
			DRectModel md = new DRectModel();
			int x = 10;
			int y = 10;
			int width = 20;
			int height = 20;
			md.setBound(x, y, width, height);
			canvas.revalidate();
			canvas.repaint();
			canvas.addShape(md);
			
		}
		
		else if(e.getActionCommand().equals("Oval"))
		{
			DOvalModel md = new DOvalModel();
			int x = 10;
			int y = 10;
			int width = 20;
			int height = 20;
			md.setBound(x, y, width, height);
			canvas.revalidate();
			canvas.repaint();
			canvas.addShape(md);
			
		}
		else if(e.getActionCommand().equals("Set Color"))
		{
			if(canvas.getSelected()!=null)
			{
				Color color = JColorChooser.showDialog(this,"Color Chooser", Color.BLUE);
				canvas.getSelected().model.setColor(color);
				canvas.revalidate();
				canvas.repaint();
			}
			else
				System.out.println("Please selecte a shape first.");
		}
		else if(e.getActionCommand().equals("Remove Shape"))
		{
			if(canvas.getSelected()!=null)
			{
				canvas.rmSelected();
				canvas.revalidate();
				canvas.repaint();
			}
			else
				System.out.println("Please selecte a shape first.");
		}
		else if(e.getActionCommand().equals("Move To Front"))
		{
			if(canvas.getSelected()!= null)
			{
				for(int i = canvas.getIndexOfSelected();i<canvas.getShapes().size()-1;i++)
				{
					Collections.swap(canvas.getShapes(),i,i+1);
				}
				Canvas.selected = canvas.getShapes().get(canvas.getShapes().size()-1);
				canvas.revalidate();
				canvas.repaint();
			}
			else
				System.out.println("Please selecte a shape first.");
		}
		else if(e.getActionCommand().equals("Move To Back"))
		{
			if(canvas.getSelected()!= null)
			{
				for(int i = canvas.getIndexOfSelected();i>0;i--)
				{
					Collections.swap(canvas.getShapes(),i,i-1);
				}
				Canvas.selected = canvas.getShapes().get(0);
				canvas.revalidate();
				canvas.repaint();
			}
			else
				System.out.println("Please selecte a shape first.");
		}
	
	}
	public void mousePressed(MouseEvent e) 
	{
		int x = e.getX();
    	int y = e.getY();
		canvas.Selection(x, y);
	}
    public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e){}
    public void mouseClicked(MouseEvent e) 
	{
    	int x = e.getX();
    	int y = e.getY();
		canvas.Selection(x, y);
		
	}

    
    public void mouseDragged(MouseEvent e)
    {
    	if(canvas.getSelected()!= null)
    	{
    		int shapeX = canvas.getSelected().model.getPars()[0];
        	int shapeY = canvas.getSelected().model.getPars()[1];
        	int width = canvas.getSelected().model.getPars()[2];
        	int height = canvas.getSelected().model.getPars()[3];
        	
    		int x = e.getX();
    		int y = e.getY();
    		if (x > shapeX+4 && x<shapeX+width-4 && y>shapeY+4&&y<shapeY+height-4)
    			canvas.move(x, y);
    		else
    			canvas.Resize(x, y);
    		
        }
    }
    public static void main(String[] args)
    {
    	try {
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    		} catch (Exception e) {
    		    e.printStackTrace();
    		}
    	Whiteboard wb = new Whiteboard();
    }
}
