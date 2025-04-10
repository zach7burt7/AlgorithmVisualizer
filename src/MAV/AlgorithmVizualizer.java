package MAV;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.Queue;
import java.util.Stack;



public class AlgorithmVizualizer {
 
   //Primitive Variables
   private int delay = 10;
   private int WIDTH = 1200;
   private int HEIGHT = 800;
   private int endX = -1;
   private int endY = -1;
   private int startY = -1;
   private int startX = -1;
   private int cells = 30;
   private int cellSize;
   private int currTool = 0;
   private int currAlg = 0;
   private int mapHeight = 600;
   private String[] tools = {"Start", "End", "Wall", "Eraser"};
   private String[] algorithm = {"BFS", "DFS", "A*"};
   private int dRow[] = { 0, 1, 0, -1 };
   private int dCol[] = { -1, 0, 1, 0 };
   
   //Frames
   JFrame frame;
   JPanel controlPanel;
   JPanel titlePanel;
   JPanel mapPanel;
   JLabel title;
   JLabel mapDensityTitle;
   JLabel densityValueLabel= new JLabel(cells + "x" + cells);
   Node[][] map;
   Algorithms alg = new Algorithms();
   
   //Colors
   Color darkPurple = new Color(44, 32, 66); 
   Color turq = new Color(124, 170, 170);
   Color green = new Color(39, 110, 0);
   Color red = new Color(200, 0, 0);
   //DDM
   JComboBox toolDropDownMenu = new JComboBox(tools);
   JComboBox algorithmDropDownMenu = new JComboBox(algorithm);
   
   //Buttons
   JButton startAlgButton = new JButton("Start Search");
   JButton resetButton = new JButton("Reset Map");
   
   //Slider
   JSlider mapDensitySlider = new JSlider(1, 7, 4);
 

   //Bool
   private boolean solving = false;
	
	public static void main(String[] arg) {
		new AlgorithmVizualizer();
	}
	
	
	public AlgorithmVizualizer() {
	     clearMap();
	     initialize();
	     
	
	}
	
	//Clears entire map
	public void clearMap() {
        endX = -1; 
        endY = -1;
        startX = -1;
        startY = -1;
        map = new Node[cells][cells]; 
        for (int x = 0; x < cells; x++) {
            for (int y = 0; y < cells; y++) {
                map[x][y] = new Node(3, x, y); 
            }
        }
        reset(); 
    }
	
	//Clears Walls but not Start and End node
	public void clearVisited() {
	    for (int x = 0; x < cells; x++) {
	        for (int y = 0; y < cells; y++) {
	            if (map[x][y].getType() == 4 || map[x][y].getType() == 5) {
	                map[x][y] = new Node(3, x, y);
	            }
	        }
	    }
	}
	
	//Reset flag/s
    public void reset() {
        solving = false;
    }
    
    //set the tick speed
    public void delay() {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }
    
    public void startSearch() {
        if (solving) {
            switch (currAlg) {
                case 0:
                    alg.BFS();
                    break;
                case 1:
                	alg.DFS();
                    break;
                case 2:

                    break;
                case 3:
  
                    break;
            }
        }
        pause();
    }
    
    public void pause() {
        int i = 0;
        while (!solving) {
            i++;
            if (i > 500)
                i = 0;
            try {
                Thread.sleep(1);
            } catch (Exception e) { }
        }
        startSearch();
    }
    
    
    
    public void resetMap() {
    	for(int x = 0; x < cells; x++) {
    		for(int y = 0; y < cells; y++) {
    			Node curr = map[x][y];
    			if (curr.getType() == 4 || curr.getType() == 5 || curr.getType() == 2)
                    map[x][y] = new Node(3, x, y);
    		}
    	}
    	
    	 if (startX > -1 && startY > -1) {
             map[startX][startY] = new Node(0, startX, startY);
             map[startX][startY].setHops(0);
         }
         if (endX > -1 && endY > -1) {
             map[endX][endY] = new Node(1, endX, endY);
         }
         reset(); 
    }
    
    
    public void update() {
    	frame.repaint();
    }
	
	public void initialize() {
		 // Create the frame
	    frame = new JFrame("Algorithm Visualizer");
	    frame.setSize(WIDTH, HEIGHT);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.getContentPane().setLayout(null);
	    
	    // Title Area
	    titlePanel = new JPanel();
	    titlePanel.setBounds(0, 0, 1200, 50);
	    title = new JLabel("Algorithm Visualizer");
	    title.setFont(new Font("Arial", Font.BOLD, 24));
	    titlePanel.add(title);
	    
	    // Control Area
	    controlPanel = new JPanel();
	    controlPanel.setBounds(0, 50, 1185, 100);
	    controlPanel.setBorder(BorderFactory.createTitledBorder(
	    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Controls"));
	    controlPanel.add(startAlgButton);
	    controlPanel.add(resetButton);
	    controlPanel.add(toolDropDownMenu);
	    controlPanel.add(algorithmDropDownMenu);
	    
	    // Map Density Slider Setup
	    // Set the slider's preferred size (this controls the slider's dimensions)
	    mapDensitySlider.setMajorTickSpacing(10);
	 
	    
	    // Create and initialize the label for the slider
	    mapDensityTitle = new JLabel("Map Density");
	    mapDensityTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    // Create a new panel (densityPanel) to hold the map density label and slider
	    JPanel densityPanel = new JPanel();
	    densityPanel.setPreferredSize(new Dimension(100, 50)); // Increase panel size as desired
	    densityPanel.setLayout(new BoxLayout(densityPanel, BoxLayout.Y_AXIS));
	    
	    //Center each component
	    mapDensityTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
	    mapDensitySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
	    densityValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    densityPanel.add(mapDensityTitle);
	    densityPanel.add(mapDensitySlider);
	    densityPanel.add(densityValueLabel);
	    
	    controlPanel.add(densityPanel);
	    
	    mapPanel = new Map();
	    mapPanel.setBounds(290, 150, 1200, 650);
	    

	    frame.add(titlePanel);
	    frame.add(controlPanel);
	    frame.add(mapPanel);
	    
	    frame.setVisible(true);
		cellSize = Math.min(WIDTH / cells, mapHeight / cells);
		
		 toolDropDownMenu.addItemListener(new ItemListener() {
	            @Override
	            public void itemStateChanged(ItemEvent e) {
	                currTool = toolDropDownMenu.getSelectedIndex();
	            }
	        });
		 
		 algorithmDropDownMenu.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				currAlg = algorithmDropDownMenu.getSelectedIndex();
				
			}
		});
		 
		
		    startAlgButton.addActionListener(new ActionListener() {
		    	 @Override
		    	    public void actionPerformed(ActionEvent e) {
		             reset();
		                if ((startX > -1 && startY > -1) && (endX > -1 && endY > -1))
		                    solving = true;
		    	
		    	    }
		    	
	        });
		    
		    resetButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					resetMap();
					update();
					
				}
			});
		    
		       mapDensitySlider.addChangeListener(new ChangeListener() {
		            @Override
		            public void stateChanged(ChangeEvent e) {
		                cells = mapDensitySlider.getValue() * 10;
		                clearMap();
		                resetMap();
		                cellSize = Math.min(mapPanel.getWidth() / cells, mapPanel.getHeight() / cells);
		                update();
		            }
		        });
		       
		    startSearch();

		}
	
	class Node {
		  private int cellType = 0;
	      private int hops, x, y, lastX, lastY;
	      private double distToEnd = 0;
	      
	      public Node(int type, int x, int y) {
	            this.cellType = type;
	            this.x = x;
	            this.y = y;
	            this.hops = -1;
	        }

	        //finds the dist to end node
	        public double getEuclidDist() {
	            int xdif = Math.abs(x - endX);
	            int ydif = Math.abs(y - endY);
	            distToEnd = Math.sqrt((xdif * xdif) + (ydif * ydif));
	            return distToEnd;
	        }

	        //getter methods
	        public int getX() {
	            return x;
	        }

	        public int getY() {
	            return y;
	        }

	        public int getLastX() {
	            return lastX;
	        }

	        public int getLastY() {
	            return lastY;
	        }

	        public int getType() {
	            return cellType;
	        }

	        public int getHops() {
	            return hops;
	        }


	        public void setType(int type) {
	            cellType = type;
	        }

	        public void setLastNode(int x, int y) {
	            lastX = x;
	            lastY = y;
	        }

	        public void setHops(int hops) {
	            this.hops = hops;
	        }
	}
	
	
	
	class Map extends JPanel implements MouseListener, MouseMotionListener {
        public Map() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int x = 0; x < cells; x++) {
                for (int y = 0; y < cells; y++) {
                    Color fillColor;
                    
                    switch (map[x][y].getType()) {
                        case 0: fillColor = green; break;     
                        case 1: fillColor = red; break;       
                        case 2: fillColor = Color.BLACK; break; 
                        case 3: fillColor = Color.WHITE; break; 
                        case 4: fillColor = turq; break;       
                        case 5: fillColor = darkPurple; break; 
                        default:fillColor = Color.GRAY; break; 
                    }

                    // Draw cell background
                    g.setColor(fillColor);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);

                    // Draw cell border
                    g.setColor(Color.BLACK);
                    g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        	
        }

        @Override
        public void mousePressed(MouseEvent e) {
        	if (solving) return;
        	try {
        		int x = e.getX() / cellSize;
    			int y = e.getY() / cellSize;
    			Node curr = map[x][y];
        		switch(currTool) {     
        		//Start Node
        		case 0:
        			if(curr.getType() != 2) {
        				
        				if(startX > -1 && startY > -1) {
        					map[startX][startY].setType(3);
        				}
        				
        				startX = x;
        				startY = y;
        				curr.setType(0);
        				
        			}
        			break;
        		//End Node    
        		case 1:
        		    clearVisited();
        		    if (endX > -1 && endY > -1) {
        		        map[endX][endY].setType(3);
        		    }
        		    
        		    endX = x;
        		    endY = y;       
        		    map[x][y].setType(1);
        		    solving = false;
        		    
        		    break;
        		//Wall Node
        		case 2:
        				
        		    if(curr.getType() != 0 && curr.getType() != 1) {
        		        curr.setType(2);
        		    }
        		    solving = false;
        		    break;
        		case 3:
        			 if(curr.getType() != 0 && curr.getType() != 1) {
         		        curr.setType(3);
         		    }
        			
        			break;
        		default:  
        		
        		}
        		AlgorithmVizualizer.this.update();
        		
        	}catch (Exception ex) {}
        
       
        	
        }

        @Override
        public void mouseReleased(MouseEvent e){

        }

        @Override
        public void mouseEntered (MouseEvent e){

        }

        @Override
        public void mouseExited (MouseEvent e){

        }

        @Override
        public void mouseDragged (MouseEvent e){
       
        	
        	try {
        	int x = e.getX() / cellSize;
			int y = e.getY() / cellSize;
			Node curr = map[x][y];
			
			  if (currTool == 2 && curr.getType() != 0 && curr.getType() != 1) { 
				curr.setType(2);
				
			}
			  if (currTool == 3 && curr.getType() != 0 && curr.getType() != 1) { 
					curr.setType(3);
					
				}
        	
			AlgorithmVizualizer.this.update();
        }catch(Exception ex) {}

	
	}

        @Override
        public void mouseMoved (MouseEvent e){

        }
     
	
    }


public class Algorithms {
	
	
	 
	
	public void mySearch() {
	    for (int col = startY; col < map[startX].length; col++) {
	    	for(int row = startX; row < map[0].length; row++) {
	    		if(!solving) {
	    			break;
	    		}
	      
	        map[row][col].setType(4); // Mark as visited
	        update();
	        delay();
	    }
	    	
		     
	    }
	
	}
	
	public void DFS() {
		if (startX < 0 || startY < 0 || endX < 0 || endY < 0) {
	        return;
	    }
		 Stack<Node> stack = new Stack<>();
		 Node startNode = map[startX][startY];
		 startNode.setHops(0);
		 stack.add(startNode);
		 
		 boolean found = false;
		    
		    while (!stack.isEmpty() && !found && solving) {
		        Node current = stack.pop();
		        int x = current.getX();
		        int y = current.getY();
		        
		        //Explore each direction
		        for (int i = 0; i < 4; i++) {
		            if (!solving) break;
		            
		            int newY = y + dCol[i];
		            int newX = x + dRow[i];
		       
		            
		            //Ensure its in bounds
		            if (newX < 0 || newX >= cells || newY < 0 || newY >= cells) {
		                continue;
		            }
		            
		            Node neighbor = map[newX][newY];
		            
		            // Ignore walls and visited nodes
		            if (neighbor.getType() == 2 || neighbor.getType() == 4 || neighbor.getType() == 5) {
		                continue;
		            }
		            
		            
		            neighbor.setLastNode(x, y);
		            neighbor.setHops(current.getHops() + 1);
		            
		            // If we've reached the end node, mark found and break.
		            if (newX == endX && newY == endY) {
		                found = true;
		                break;
		            }
		            
		    
		            if (neighbor.getType() != 0 && neighbor.getType() != 1) {
		                neighbor.setType(4);
		                update();
		                delay();
		            }
		            
		            stack.add(neighbor);
		        }
		         
		    }
		    // Exit if end was never found
		    if (!solving || !found) {
		        solving = false;
		        return;
		    }
		    
		    int x = endX;
		    int y = endY;
		    while (!(x == startX && y == startY) && solving) {
		        Node node = map[x][y];
		        x = node.getLastX();
		        y = node.getLastY();
		        // Mark if part of path + dont include start and end node
		        if (!(x == startX && y == startY) && !(x == endX && y == endY)) {
		            map[x][y].setType(5);
		        }
		        update();
		        delay();
		    }
		    
		    solving = false;
		
	}
	
	public void BFS() {
	    if (startX < 0 || startY < 0 || endX < 0 || endY < 0) {
	        return;
	    }
	    
	    Queue<Node> queue = new LinkedList<>();
	    Node startNode = map[startX][startY];
	    startNode.setHops(0);
	    queue.add(startNode);
	    
	    boolean found = false;
	    
	    while (!queue.isEmpty() && !found && solving) {
	        Node current = queue.poll();
	        int x = current.getX();
	        int y = current.getY();
	        
	        //Explore each direction
	        for (int i = 0; i < 4; i++) {
	            if (!solving) break;
	            
	            int newX = x + dRow[i];
	            int newY = y + dCol[i];
	            
	            //Ensure its in bounds
	            if (newX < 0 || newX >= cells || newY < 0 || newY >= cells) {
	                continue;
	            }
	            
	            Node neighbor = map[newX][newY];
	            
	            // Ignore walls and visited nodes
	            if (neighbor.getType() == 2 || neighbor.getType() == 4 || neighbor.getType() == 5) {
	                continue;
	            }
	            
	            
	            neighbor.setLastNode(x, y);
	            neighbor.setHops(current.getHops() + 1);
	            
	            // If we've reached the end node, mark found and break.
	            if (newX == endX && newY == endY) {
	                found = true;
	                break;
	            }
	            
	    
	            if (neighbor.getType() != 0 && neighbor.getType() != 1) {
	                neighbor.setType(4);
	                update();
	                delay();
	            }
	            
	            queue.add(neighbor);
	        }
	    }
	    
	    // Exit if end was never found
	    if (!solving || !found) {
	        solving = false;
	        return;
	    }
	    
	    int x = endX;
	    int y = endY;
	    while (!(x == startX && y == startY) && solving) {
	        Node node = map[x][y];
	        x = node.getLastX();
	        y = node.getLastY();
	        // Mark if part of path + dont include start and end node
	        if (!(x == startX && y == startY) && !(x == endX && y == endY)) {
	            map[x][y].setType(5);
	        }
	        update();
	        delay();
	    }
	    
	    solving = false;
	}

	
	
	
}
	
}	
