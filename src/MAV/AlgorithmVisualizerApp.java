import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.EventListener;

// Main application using CardLayout for different views
public class AlgorithmVisualizerApp {
	
	

	
	// Cards
	public static final String HOME_CARD = "home";
	public static final String PATHFINDING_CARD = "pathfinding";
	public static final String SORTING_CARD = "sorting";

	private JFrame frame;
	private JPanel mainPanel;
	private CardLayout cardLayout;

	public AlgorithmVisualizerApp() {
		try {
			// Main Frame
			frame = new JFrame("Algorithm Visualizer App");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1200, 800);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);

			// CardLayout container
			cardLayout = new CardLayout();
			mainPanel = new JPanel(cardLayout);

			// Create and add the Home panel
			HomePanel homePanel = new HomePanel(this);
			mainPanel.add(homePanel, HOME_CARD);

			// Create and add the Pathfinding Visualizer panel
			AlgorithmVisualizerPanel pathfindingPanel = new AlgorithmVisualizerPanel(this);
			mainPanel.add(pathfindingPanel, PATHFINDING_CARD);

			// Create and add the Sorting Visualizer panel.
			SortingPanel sortingPanel = new SortingPanel(this);
			mainPanel.add(sortingPanel, SORTING_CARD);

			// Add the main panel to the frame and display it
			frame.add(mainPanel);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to switch views
	public void showCard(String cardName) {
		cardLayout.show(mainPanel, cardName);
	}

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
			System.err.println("Uncaught exception in thread " + thread.getName());
			throwable.printStackTrace();
		});

		SwingUtilities.invokeLater(() -> {
			new AlgorithmVisualizerApp();
		});
	}
}

//Home page
class HomePanel extends JPanel {
 private final AlgorithmVisualizerApp app;

//Theme Colors


 public HomePanel(AlgorithmVisualizerApp app) {
     this.app = app;
     setLayout(new GridBagLayout());
     setOpaque(false);

     // Title stack (card)
     JPanel card = new JPanel();
     card.setOpaque(true);
     card.setBackground(Theme.CARD_BG);
     card.setBorder(BorderFactory.createEmptyBorder(32, 48, 40, 48));
     card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

     JLabel titleLabel = new JLabel("Welcome to Zach's Algorithm Visualizer", JLabel.CENTER);
     titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
     titleLabel.setForeground(Theme.TITLE_COLOR);
     titleLabel.setFont(new Font("Inter", Font.BOLD, 28));

     JLabel subtitle = new JLabel("Choose a module to begin", JLabel.CENTER);
     subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
     subtitle.setForeground(Theme.SUBTITLE_COLOR);
     subtitle.setFont(new Font("Inter", Font.PLAIN, 16));
     subtitle.setBorder(BorderFactory.createEmptyBorder(6, 0, 20, 0));

     // Buttons row
     JPanel buttons = new JPanel();
     buttons.setOpaque(false);
     buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 0));

     RoundedButton pathfindingButton = new RoundedButton("Pathfinding Visualizer",
             Theme.PRIMARY_BG, Theme.PRIMARY_HOVER, Theme.PRIMARY_PRESS, Theme.BTN_TEXT);
     RoundedButton sortingButton = new RoundedButton("Sorting Visualizer",
    		 Theme.SECONDARY_BG, Theme.SECONDARY_HOV, Theme.SECONDARY_PRE, Theme.BTN_TEXT);

     pathfindingButton.addActionListener(e -> app.showCard(AlgorithmVisualizerApp.PATHFINDING_CARD));
     sortingButton.addActionListener(e -> app.showCard(AlgorithmVisualizerApp.SORTING_CARD));

     Dimension btnSize = new Dimension(240, 46);
     pathfindingButton.setPreferredSize(btnSize);
     sortingButton.setPreferredSize(btnSize);

     buttons.add(pathfindingButton);
     buttons.add(sortingButton);

     // Add to card
     card.add(titleLabel);
     card.add(subtitle);
     card.add(buttons);

     // Center the card
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.gridx = 0; gbc.gridy = 0;
     add(card, gbc);
 }

 @Override
 protected void paintComponent(Graphics g) {
     super.paintComponent(g);
     Graphics2D g2 = (Graphics2D) g.create();
     g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
     int w = getWidth(), h = getHeight();
     GradientPaint gp = new GradientPaint(0, 0, Theme.GRAD_TOP, 0, h, Theme.GRAD_BOTTOM);
     g2.setPaint(gp);
     g2.fillRect(0, 0, w, h);
     g2.dispose();
 }

 static class RoundedButton extends JButton {
     private final Color base;
     private final Color hover;
     private final Color press;
     private final Color text;
     private boolean hovered = false;
     private boolean pressed = false;
     private final int arc = 18;

     RoundedButton(String text, Color base, Color hover, Color press, Color textColor) {
         super(text);
         this.base = base; this.hover = hover; this.press = press; this.text = textColor;
         setFocusPainted(false);
         setBorderPainted(false);
         setContentAreaFilled(false);
         setForeground(textColor);
         setFont(new Font("Inter", Font.BOLD, 15));
         setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

         addMouseListener(new MouseAdapter() {
             @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
             @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
             @Override public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
             @Override public void mouseReleased(MouseEvent e){ pressed = false; repaint(); }
         });
     }

     @Override
     protected void paintComponent(Graphics g) {
         Graphics2D g2 = (Graphics2D) g.create();
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

         Color fill = pressed ? press : hovered ? hover : base;
         g2.setColor(fill);
         g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

         // subtle shadow
         g2.setColor(new Color(0,0,0,60));
         g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);

         // text
         FontMetrics fm = g2.getFontMetrics();
         String t = getText();
         int tx = (getWidth() - fm.stringWidth(t)) / 2;
         int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
         g2.setColor(text);
         g2.drawString(t, tx, ty);
         g2.dispose();
     }
 }
}

// Sorting Page
class SortingPanel extends JPanel {
	private static final int WIDTH = 1185;
	private static final int TITLE_HEIGHT = 50;
	private static final int CONTROL_HEIGHT = 90;
	private int arraySize = 200;
	private final AlgorithmVisualizerApp app;
	private int[] array = new int[arraySize];
	private int currAlg = 0;
	private boolean solving = false;
	// Lists

	private String[] sortingAlgorithms = { "Bubble Sort", "Merge Sort", "Quick Sort" };

	// UI components
	private JPanel titlePanel;
	private JPanel controlPanel;
	private JPanel drawPanel;
	private final JButton homeButton = new JButton("Home");
	private final JButton shuffleButton = new JButton("Shuffle");
	private final JButton startSortingAlgorithmButton = new JButton("Start Sort");
	private JComboBox<String> sortingToolsDropDownMenu = new JComboBox<>(sortingAlgorithms);
	private JSlider arraySizeSlider = new JSlider(50, 350, 200);
	private JLabel arraySizeLabel = new JLabel(arraySize + "");

    public SortingPanel(AlgorithmVisualizerApp app) {
        this.app = app;
        setLayout(null);
        setOpaque(false); 

        initArray();
        

        // ---- Title Panel ----
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBounds(0, 0, WIDTH, TITLE_HEIGHT);
        titlePanel.setBackground(Theme.CARD_BG);
        titlePanel.setOpaque(true);

        JLabel title = new JLabel("Sorting Visualizer", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Theme.TITLE_COLOR);
        


        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        buttonWrapper.setOpaque(false);
        homeButton.setPreferredSize(new Dimension(80, 30));
        homeButton.setBackground(Theme.PRIMARY_BG);
        homeButton.setForeground(Theme.BTN_TEXT);
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(e -> app.showCard(AlgorithmVisualizerApp.HOME_CARD));
        buttonWrapper.add(homeButton);

        titlePanel.add(buttonWrapper, BorderLayout.LINE_START);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.add(Box.createRigidArea(new Dimension(80, 0)), BorderLayout.LINE_END);

        add(titlePanel);

        // ---- Control Panel ----
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBounds(0, TITLE_HEIGHT, WIDTH, CONTROL_HEIGHT);
        controlPanel.setBackground(Theme.CARD_BG);
        controlPanel.setOpaque(true);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Start Button
        startSortingAlgorithmButton.setPreferredSize(new Dimension(100, 30));
        startSortingAlgorithmButton.setBackground(Theme.SECONDARY_BG);
        startSortingAlgorithmButton.setForeground(Theme.BTN_TEXT);
        startSortingAlgorithmButton.setFocusPainted(false);
        startSortingAlgorithmButton.addActionListener(e -> {
            if (solving) return;
            new Thread(() -> {
                switch (currAlg) {
                    case 0 -> bubbleSort();
                    case 1 -> startMergeSort();
                    case 2 -> startQuickSort();
                }
            }).start();
        });
        controlPanel.add(startSortingAlgorithmButton);

        // Shuffle Button
        shuffleButton.setPreferredSize(new Dimension(100, 30));
        shuffleButton.setBackground(Theme.SECONDARY_BG);
        shuffleButton.setForeground(Theme.BTN_TEXT);
        shuffleButton.setFocusPainted(false);
        shuffleButton.addActionListener(e -> {
            solving = false;
            resetArray();
        });
        controlPanel.add(shuffleButton);

        // Dropdown Menu
        sortingToolsDropDownMenu.addItemListener(e -> currAlg = sortingToolsDropDownMenu.getSelectedIndex());
        controlPanel.add(sortingToolsDropDownMenu);

        // Array Size Slider
        arraySizeSlider.addChangeListener(e -> {
            solving = false;
            arraySize = arraySizeSlider.getValue();
            arraySizeLabel.setText(arraySize + "");
            initArray();
            update();
        });
        arraySizeSlider.setMajorTickSpacing(10);

        JPanel arraySizePanel = new JPanel();
        arraySizePanel.setPreferredSize(new Dimension(100, 50));
        arraySizePanel.setOpaque(false);
        arraySizePanel.setLayout(new BoxLayout(arraySizePanel, BoxLayout.Y_AXIS));
        JLabel arraySizeTitle = new JLabel("Array Size");
        arraySizeTitle.setForeground(Theme.SUBTITLE_COLOR);
        arraySizeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        arraySizeSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        arraySizeLabel.setForeground(Theme.TITLE_COLOR);
        arraySizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        arraySizeSlider.setBackground(Theme.CARD_BG);       
        arraySizeSlider.setForeground(Theme.TITLE_COLOR); 
        arraySizeSlider.setOpaque(true);
        arraySizePanel.add(arraySizeTitle);
        arraySizePanel.add(arraySizeSlider);
        arraySizePanel.add(arraySizeLabel);
        
        

        controlPanel.add(arraySizePanel);

        add(controlPanel);

        // ---- Draw Panel ----
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawArray(g);
            }
        };
        drawPanel.setBackground(Theme.CARD_BG);
        int drawY = TITLE_HEIGHT + CONTROL_HEIGHT;
        drawPanel.setBounds(0, drawY, WIDTH, getPreferredSize().height - drawY);
        add(drawPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Theme.paintGradient(this, g); 
    }


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, 800);
	}

	private void initArray() {
		Random rnd = new Random();
		array = new int[arraySize];
		for (int i = 0; i < arraySize; i++) {
			array[i] = rnd.nextInt(100) + 1;
		}
	}

	private void drawArray(Graphics g) {
		int w = drawPanel.getWidth();
		int h = drawPanel.getHeight();
		int n = array.length;
		if (w <= 0 || h <= 0 || n == 0)
			return;

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double cellWidth = (double) w / n;
		double yScale = (double) h / Arrays.stream(array).max().orElse(1);

		double gap = 0.5;
		for (int i = 0; i < n; i++) {
			double val = array[i];
			double x1 = i * cellWidth + gap;
			double barW = cellWidth - 2 * gap;
			double barH = val * yScale;
			double y = h - barH;

			// draw a sub-pixel rectangle
			g.setColor(Theme.PRIMARY_BG);
			g2.fill(new Rectangle2D.Double(x1, y, barW, barH));
		}
	}

	private void resetArray() {
		initArray();
		drawPanel.repaint();
	}

	public void delay() {
		try {
			Thread.sleep(5);
		} catch (Exception e) {
		}
	}

	public void update() {
		repaint();
	}

	private void bubbleSort() {
		solving = true;
		int n = array.length;
		for (int i = 0; i < n - 1 && solving; i++) {
			for (int j = 0; j < n - i - 1 && solving; j++) {
				if (array[j] > array[j + 1]) {
					// swap
					int tmp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = tmp;
					update();
					delay();
				}
			}
		}
		solving = false;
	}

	// Start merge sort size of global array size
	private void startMergeSort() {
		solving = true;
		mergeSort(0, array.length - 1);
		solving = false;
	}

	// Recursive merge sort implementation
	private void mergeSort(int left, int right) {
		if (!solving)
			return;
		if (left >= right)
			return;

		int mid = left + (right - left) / 2;

		mergeSort(left, mid);
		mergeSort(mid + 1, right);
		merge(left, mid, right);
	}

	// merge both halves.
	private void merge(int left, int mid, int right) {
		if (!solving)
			return;
		int n1 = mid - left + 1;
		int n2 = right - mid;

		int[] L = new int[n1];
		int[] R = new int[n2];

		for (int i = 0; i < n1; ++i)
			L[i] = array[left + i];
		for (int j = 0; j < n2; ++j)
			R[j] = array[mid + 1 + j];

		int i = 0, j = 0, k = left;

		while (i < n1 && j < n2 && solving) {

			if (L[i] <= R[j]) {
				array[k] = L[i];
				i++;
			} else {
				array[k] = R[j];
				j++;
			}
			k++;
			update();
			delay();
		}

		while (i < n1 && solving) {
			array[k] = L[i];
			i++;
			k++;
			update();
			delay();
		}

		while (j < n2 && solving) {
			array[k] = R[j];
			j++;
			k++;
			update();
			delay();
		}
	}

	public void startQuickSort() {
		solving = true;
		quickSort(0, array.length - 1);
		solving = false;
	}

	private void quickSort(int low, int high) {
		if (!solving || low >= high)
			return;
		int p = partition(low, high);
		quickSort(low, p - 1);
		quickSort(p + 1, high);
	}

	private int partition(int low, int high) {
		int pivot = array[high];
		int i = low - 1;
		for (int j = low; j < high && solving; j++) {
			if (array[j] <= pivot) {
				i++;
				swap(array, i, j);
				update();
				delay();
			}
		}
		swap(array, i + 1, high);
		update();
		delay();
		return i + 1;
	}

	public void swap(int[] arr, int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
}

//Pathfinding Page
class AlgorithmVisualizerPanel extends JPanel {
 private AlgorithmVisualizerApp app;
 // Primitive Variables
 private int delay = 3;
 private int WIDTH = 1200;
 private int endX = -1;
 private int endY = -1;
 private int startX = -1;
 private int startY = -1;
 private int cells = 30;
 private int cellSize;
 private int currTool = 0;
 private int currAlg = 0;
 private int mapHeight = 600;
 private double density = 0.3;
 private String[] tools = {"Start", "End", "Wall", "Eraser"};
 private String[] algorithm = {"BFS", "DFS", "A*"};
 private final int[] dRow = { -1, 1, 0, 0 };
 private final int[] dCol = { 0, 0, -1, 1 };

 // Panels and Controls
 private JPanel controlPanel;
 private JPanel titlePanel;
 private JPanel mapPanel;
 private JLabel densityValueLabel = new JLabel(cells + " x " + cells);
 private JLabel mazeDensityValueLabel = new JLabel((int)(density * 100) + "%");
 private JLabel searchSpeedValueLabel = new JLabel(delay + "");
 private Node[][] map;


 // Colors
 private Color darkPurple = new Color(44, 32, 66); 
 private Color turq = new Color(124, 170, 170);
 private Color green = new Color(39, 110, 0);
 private Color red = new Color(200, 0, 0);

 // GUI Components
 private JComboBox<String> toolDropDownMenu = new JComboBox<>(tools);
 private JComboBox<String> algorithmDropDownMenu = new JComboBox<>(algorithm);
 private JButton startAlgButton = new JButton("Start Search");
 private JButton resetButton = new JButton("Reset Map");
 private JButton homeButton = new JButton("Home");
 private JButton generateMazeButton = new JButton("Generate Maze");
 private JButton clearButton = new JButton("Clear Search");
 private JSlider mapDensitySlider = new JSlider(2, 6, 4);
 private JSlider mazeDensitySlider = new JSlider(1, 5, 3);
 private JSlider searchSpeedSlider = new JSlider(1, 7, 4);

 // Flags
 private boolean solving = false;
 
 //Tools
 Random rand = new Random();


 public AlgorithmVisualizerPanel(AlgorithmVisualizerApp app) {
     this.app = app;
     setLayout(null);
     setOpaque(false);
     // Initialize the map grid
     clearMap();

  // ========== Title ==========
     titlePanel = new JPanel(new BorderLayout());
     titlePanel.setBounds(0, 0, WIDTH, 50);
     titlePanel.setBackground(Theme.CARD_BG);
     titlePanel.setOpaque(true);

     JLabel title = new JLabel("Pathfinding Visualizer", SwingConstants.CENTER);
     title.setFont(new Font("Arial", Font.BOLD, 24));
     title.setForeground(Theme.TITLE_COLOR);

     JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
     buttonWrapper.setOpaque(false);

     
     homeButton.setBackground(Theme.SECONDARY_BG);
     homeButton.setForeground(Theme.BTN_TEXT);
     homeButton.setFocusPainted(false);
     homeButton.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
     homeButton.addActionListener(e -> app.showCard(AlgorithmVisualizerApp.HOME_CARD));
     buttonWrapper.add(homeButton);

     titlePanel.add(buttonWrapper, BorderLayout.LINE_START);
     titlePanel.add(title, BorderLayout.CENTER);
     add(titlePanel);

     //Control card
     controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
     controlPanel.setBounds(0, 50, 1185, 90);
     controlPanel.setBackground(Theme.CARD_BG);
     controlPanel.setOpaque(true);
     controlPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

     // Buttons
     startAlgButton.setBackground(Theme.PRIMARY_BG);
     startAlgButton.setForeground(Theme.BTN_TEXT);
     startAlgButton.setFocusPainted(false);
     startAlgButton.addActionListener(e -> {
         reset();
         if ((startX > -1 && startY > -1) && (endX > -1 && endY > -1)) solving = true;
     });
     controlPanel.add(startAlgButton);

     clearButton.setBackground(Theme.PRIMARY_BG);
     clearButton.setForeground(Theme.BTN_TEXT);
     clearButton.setFocusPainted(false);
     clearButton.addActionListener(e -> { solving = false; clear(); update(); });
     controlPanel.add(clearButton);

     resetButton.setBackground(Theme.PRIMARY_BG);
     resetButton.setForeground(Theme.BTN_TEXT);
     resetButton.setFocusPainted(false);
     resetButton.addActionListener(e -> { resetMap(); update(); });
     controlPanel.add(resetButton);

     generateMazeButton.setBackground(Theme.PRIMARY_BG);
     generateMazeButton.setForeground(Theme.BTN_TEXT);
     generateMazeButton.setFocusPainted(false);
     generateMazeButton.addActionListener(e -> { generateMapp(density); update(); });
     controlPanel.add(generateMazeButton);

     // Tools
     toolDropDownMenu.addItemListener(e -> currTool = toolDropDownMenu.getSelectedIndex());
     algorithmDropDownMenu.addItemListener(e -> currAlg = algorithmDropDownMenu.getSelectedIndex());
     controlPanel.add(toolDropDownMenu);
     controlPanel.add(algorithmDropDownMenu);

     // Sliders
     JLabel mapDensityTitle = new JLabel("Map Density");
     JLabel mazeDensityTitle = new JLabel("Maze Density");
     JLabel searchSpeedTitle = new JLabel("Search Speed");
     mapDensityTitle.setForeground(Theme.SUBTITLE_COLOR);
     mazeDensityTitle.setForeground(Theme.SUBTITLE_COLOR);
     searchSpeedTitle.setForeground(Theme.SUBTITLE_COLOR);
     densityValueLabel.setForeground(Theme.TITLE_COLOR);
     mazeDensityValueLabel.setForeground(Theme.TITLE_COLOR);
     searchSpeedValueLabel.setForeground(Theme.TITLE_COLOR);

     // Theme Sliders
     for (JSlider s : new JSlider[]{mapDensitySlider, mazeDensitySlider, searchSpeedSlider}) {
         s.setOpaque(true);
         s.setBackground(Theme.CARD_BG);
         s.setForeground(Theme.TITLE_COLOR);
     }

     JPanel densityPanel = new JPanel();
     densityPanel.setOpaque(false);
     densityPanel.setPreferredSize(new Dimension(140, 52));
     densityPanel.setLayout(new BoxLayout(densityPanel, BoxLayout.Y_AXIS));
     mapDensitySlider.setMajorTickSpacing(1);
     mapDensitySlider.addChangeListener(e -> {
         cells = mapDensitySlider.getValue() * 10;
         densityValueLabel.setText(cells + " x " + cells);
         clearMap(); resetMap();
         cellSize = Math.min(mapPanel.getWidth() / cells, mapPanel.getHeight() / cells);
         update();
     });
     mapDensityTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
     mapDensitySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
     densityValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
     densityPanel.add(mapDensityTitle);
     densityPanel.add(mapDensitySlider);
     densityPanel.add(densityValueLabel);
     controlPanel.add(densityPanel);

     JPanel mazePanel = new JPanel();
     mazePanel.setOpaque(false);
     mazePanel.setPreferredSize(new Dimension(140, 52));
     mazePanel.setLayout(new BoxLayout(mazePanel, BoxLayout.Y_AXIS));
     mazeDensitySlider.setMajorTickSpacing(1);
     mazeDensitySlider.addChangeListener(e -> {
         density = mazeDensitySlider.getValue() * 0.15;
         mazeDensityValueLabel.setText((int)(density * 100) + "%");
         resetMap(); generateMapp(density); update();
     });
     mazeDensityTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
     mazeDensitySlider.setAlignmentX(Component.CENTER_ALIGNMENT);
     mazeDensityValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
     mazePanel.add(mazeDensityTitle);
     mazePanel.add(mazeDensitySlider);
     mazePanel.add(mazeDensityValueLabel);
     controlPanel.add(mazePanel);

     JPanel speedPanel = new JPanel();
     speedPanel.setOpaque(false);
     speedPanel.setPreferredSize(new Dimension(140, 52));
     speedPanel.setLayout(new BoxLayout(speedPanel, BoxLayout.Y_AXIS));
     searchSpeedSlider.setMajorTickSpacing(1);
     searchSpeedSlider.addChangeListener(e -> {
         int speed = searchSpeedSlider.getValue();
         delay = Math.max(1, 8 - speed);
         searchSpeedValueLabel.setText("Speed: " + speed);
         update();
     });
     searchSpeedTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
     searchSpeedSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
     searchSpeedValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
     speedPanel.add(searchSpeedTitle);
     speedPanel.add(searchSpeedSlider);
     speedPanel.add(searchSpeedValueLabel);
     controlPanel.add(speedPanel);

     add(controlPanel);

     //Map
     mapPanel = new Map();
     mapPanel.setBounds(290, 150, WIDTH, mapHeight + 5);
     mapPanel.setBackground(Theme.CARD_BG); // dark behind cells
     add(mapPanel);

     // Compute cell size
     cellSize = Math.min(mapPanel.getWidth() / cells, mapPanel.getHeight() / cells);

     // Control listeners
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
     
     clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				solving = false;
				clear();
				update(); 
			}

		
		});

     resetButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             resetMap();
             update();
         }
     });

     mapDensitySlider.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent e) {
             cells = mapDensitySlider.getValue() * 10;
             densityValueLabel.setText(cells + " x " + cells);
             clearMap();
             resetMap();
             cellSize = Math.min(mapPanel.getWidth() / cells, mapPanel.getHeight() / cells);
             update();
         }
     });
     
     mazeDensitySlider.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent e) {
             density = mazeDensitySlider.getValue() * 0.15;
             mazeDensityValueLabel.setText((int)(density * 100) + "%");
             resetMap();
             generateMapp(density);
             update();
         }
     });
     
     searchSpeedSlider.addChangeListener(new ChangeListener() {
         @Override
         public void stateChanged(ChangeEvent e) {
         	   int min = searchSpeedSlider.getMinimum();
         	    int max = searchSpeedSlider.getMaximum();
         	    int speed = searchSpeedSlider.getValue();
         	    delay = (max + min) - speed;
         	    searchSpeedValueLabel.setText(String.valueOf(delay));
         	    searchSpeedValueLabel.setText(String.valueOf(speed));
         	    update();
         }
     });
     
     generateMazeButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
         	generateMapp(density);
             update();
         }
     });
     
     

     //Return to home page
     homeButton.addActionListener(e -> app.showCard(AlgorithmVisualizerApp.HOME_CARD));

     new Thread(() -> startSearch()).start();
 }
 
 @Override
 protected void paintComponent(Graphics g) {
     super.paintComponent(g);
     Theme.paintGradient(this, g);
 }

 // Clears the entire map grid
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
 
 private void clear() {
     for (int x = 0; x < cells; x++) {
         for (int y = 0; y < cells; y++) {
             int type = map[x][y].getType();
             if (type == 4 || type == 5) {
                 map[x][y] = new Node(3, x, y);
             }
         }
     }
     
 }
 
 public void generateMapp(double density) {
 	resetMap();
 	for(int x = 0; x < cells; x++) {
 		for (int y = 0; y < cells; y++) {
 			if(rand.nextDouble() < density && map[x][y].getType() != 1 && map[x][y].getType() != 0) {
 		
 				map[x][y].setType(2);
 				
 				
 			}
 		}
  	}
 }

 // Resets visited nodes (but not walls, start, or end)
 public void clearVisited() {
     for (int x = 0; x < cells; x++) {
         for (int y = 0; y < cells; y++) {
             if (map[x][y].getType() == 4 || map[x][y].getType() == 5) {
                 map[x][y] = new Node(3, x, y);
             }
         }
     }
 }
 
 private void carveMaze(int x, int y) {
     if (map[x][y].getType() != 0 && map[x][y].getType() != 1) {
         map[x][y].setType(2); // Path
     }

     int[] dirX = {0, 0, -1, 1};
     int[] dirY = {-1, 1, 0, 0};

     List<Integer> shuffled = new ArrayList<>();
     shuffled.add(0);
     shuffled.add(1);
     shuffled.add(2);
     shuffled.add(3);
     Collections.shuffle(shuffled);

     for (int dir : shuffled) {
         int nx = x + dirX[dir] * 2;
         int ny = y + dirY[dir] * 2;

         if (nx > 0 && ny > 0 && nx < cells - 1 && ny < cells - 1) {
             if (map[nx][ny].getType() == 3) {
                 int wallX = x + dirX[dir];
                 int wallY = y + dirY[dir];

                 if (map[wallX][wallY].getType() != 0 && map[wallX][wallY].getType() != 1) {
                     map[wallX][wallY].setType(2); // Carve tunnel

                 }

                 carveMaze(nx, ny);
             }
         }
     }
 }

 
 public void generateMap() {
     clearMap();

     // Fill map with walls first
     for (int x = 0; x < cells; x++) {
         for (int y = 0; y < cells; y++) {
             int type = map[x][y].getType();
             if (type != 0 && type != 1) {
                 map[x][y].setType(3); // Wall
             }
         }
     }

     // Start carving maze from a random odd cell
     Random rand = new Random();
     int startX = rand.nextInt(cells / 2) * 2 + 1;
     int startY = rand.nextInt(cells / 2) * 2 + 1;
     
     // Make sure it's not the start or end
     if (map[startX][startY].getType() == 0 || map[startX][startY].getType() == 1) {
         startX = 1;
         startY = 1;
     }

     carveMaze(startX, startY);

     update(); 
    
 }

 // Reset flag
 public void reset() {
     solving = false;
 }

 // Delay method
 public void delay() {
     try {
         Thread.sleep(delay);
     } catch (Exception e) { }
 }

 // Starts the search algorithm based on the current selection
 public void startSearch() {
     while (true) {
         if (solving) {
             if (currAlg == 0) {
                BFS();
             } else if (currAlg == 1) {
                DFS();
             } else if (currAlg == 2) {
             	AStar();
             }
         }
         try {
             Thread.sleep(10);
         } catch (InterruptedException ex) { }
     }
 }

 // Resets the map’s visited/path nodes but preserves start/end and walls.
 public void resetMap() {
     for (int x = 0; x < cells; x++) {
         for (int y = 0; y < cells; y++) {
             int type = map[x][y].getType();
             if (type == 4 || type == 5 || type == 2)
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

 // Calls repaint on the panel
 public void update() {
 	
     repaint();
 }

 // Node Class
 class Node {
     private int cellType;
     private int hops, x, y, lastX, lastY;
     private double distToEnd;

     public Node(int type, int x, int y) {
         this.cellType = type;
         this.x = x;
         this.y = y;
         this.hops = -1;
     }


     public int getX() { return x; }
     public int getY() { return y; }
     public int getLastX() { return lastX; }
     public int getLastY() { return lastY; }
     public int getType() { return cellType; }
     public int getHops() { return hops; }

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

 // Map class handles actions on the Grid and Painting components
 class Map extends JPanel implements MouseListener, MouseMotionListener {
     public Map() {
         addMouseListener(this);
         addMouseMotionListener(this);
     }

     @Override
     public void paintComponent(Graphics g) {
         super.paintComponent(g);

         g.setColor(Theme.CARD_BG); // backdrop
         g.fillRect(0, 0, getWidth(), getHeight());

         for (int x = 0; x < cells; x++) {
             for (int y = 0; y < cells; y++) {
                 Color fillColor;
                 switch (map[x][y].getType()) {
                     case 0: fillColor = Color.green; break;
                     case 1: fillColor = Color.red; break;
                     case 2: fillColor = Color.BLACK; break;
                     case 3: fillColor = Theme.CARD_BG; break;
                     case 4: fillColor = Theme.SECONDARY_BG; break;// visited
                     case 5: fillColor = Color.magenta; break;   // path
                     default: fillColor = Theme.CARD_BG; break;
                 }
                 g.setColor(fillColor);
                 g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);

                 g.setColor(Color.black);
                 g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
             }
         }
     }

     @Override public void mouseClicked(MouseEvent e) { }
     @Override public void mouseEntered(MouseEvent e) { }
     @Override public void mouseExited(MouseEvent e) { }

     @Override
     public void mousePressed(MouseEvent e) {
         if (solving) return;
         try {
             int x = e.getX() / cellSize;
             int y = e.getY() / cellSize;
             Node curr = map[x][y];
             switch (currTool) {
                 // Start Node
                 case 0:
                     if (curr.getType() != 2) {
                         if (startX > -1 && startY > -1) {
                             map[startX][startY].setType(3);
                         }
                         startX = x;
                         startY = y;
                         curr.setType(0);
                     }
                     break;
                 // End Node
                 case 1:
                     clearVisited();
                     if (endX > -1 && endY > -1) {
                         map[endX][endY].setType(3);
                     }
                     endX = x;
                     endY = y;
                     curr.setType(1);
                     solving = false;
                     break;
                 // Wall Node
                 case 2:
                     if (curr.getType() != 0 && curr.getType() != 1) {
                         curr.setType(2);
                     }
                     solving = false;
                     break;
                 // Eraser
                 case 3:
                     if (curr.getType() != 0 && curr.getType() != 1) {
                         curr.setType(3);
                     }
                     break;
                 default:
             }
             AlgorithmVisualizerPanel.this.update();
         } catch (Exception ex) { }
     }

     @Override
     public void mouseReleased(MouseEvent e) { }

     @Override
     public void mouseDragged(MouseEvent e) {
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
             AlgorithmVisualizerPanel.this.update();
         } catch(Exception ex) { }
     }

     @Override public void mouseMoved(MouseEvent e) { }
 }

 // Alogirthm Implementaions
 	
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
             for (int i = 0; i < 4; i++) {
                 if (!solving) break;
                 int newX = x + dRow[i];
                 int newY = y + dCol[i];
                 if (newX < 0 || newX >= cells || newY < 0 || newY >= cells) continue;
                 Node neighbor = map[newX][newY];
                 if (neighbor.getType() == 2 || neighbor.getType() == 4 || neighbor.getType() == 5) continue;
                 neighbor.setLastNode(x, y);
                 neighbor.setHops(current.getHops() + 1);
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
         if (!solving || !found) {
             solving = false;
             return;
         }
         int x = endX, y = endY;
         while (!(x == startX && y == startY) && solving) {
             Node node = map[x][y];
             x = node.getLastX();
             y = node.getLastY();
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
             for (int i = 0; i < 4; i++) {
                 if (!solving) break;
                 int newX = x + dRow[i];
                 int newY = y + dCol[i];
                 if (newX < 0 || newX >= cells || newY < 0 || newY >= cells) continue;
                 Node neighbor = map[newX][newY];
                 if (neighbor.getType() == 2 || neighbor.getType() == 4 || neighbor.getType() == 5) continue;
                 neighbor.setLastNode(x, y);
                 neighbor.setHops(current.getHops() + 1);
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
         if (!solving || !found) {
             solving = false;
             return;
         }
         int x = endX, y = endY;
         while (!(x == startX && y == startY) && solving) {
             Node node = map[x][y];
             x = node.getLastX();
             y = node.getLastY();
             if (!(x == startX && y == startY) && !(x == endX && y == endY)) {
                 map[x][y].setType(5);
             }
             update();
             delay();
         }
         solving = false;
     }
     
     public void AStar() {
         if (startX < 0 || startY < 0 || endX < 0 || endY < 0) {
             return;
         }
         // gScore holds current best cost to start
         int[][] gScore = new int[cells][cells];
         for (int i = 0; i < cells; i++) {
             for (int j = 0; j < cells; j++) {
                 gScore[i][j] = Integer.MAX_VALUE;
             }
         }

         // openSet f = g + h
         PriorityQueue<Node> openSet = new PriorityQueue<>(
             Comparator.comparingInt(n -> n.getHops()
                 + Math.abs(n.getX() - endX)
                 + Math.abs(n.getY() - endY))
         );

         // initialize
         Node start = map[startX][startY];
         gScore[startX][startY] = 0;
         start.setHops(0);
         openSet.add(start);

         boolean found = false;

         while (!openSet.isEmpty() && !found && solving) {
             Node current = openSet.poll();
             int x = current.getX(), y = current.getY();

             // skip any stale entries
             if (current.getHops() > gScore[x][y]) continue;

             // if round the end node break
             if (x == endX && y == endY) {
                 found = true;
                 break;
             }

             // mark as visited and update map 
             if (current.getType() != 0 && current.getType() != 1) {
                 current.setType(4);
                 update();
                 delay();
             }

             // explore neighbors
             for (int i = 0; i < 4; i++) {
                 if (!solving) break;
                 int nx = x + dRow[i], ny = y + dCol[i];
                 if (nx < 0 || nx >= cells || ny < 0 || ny >= cells) continue;

                 Node neighbor = map[nx][ny];
                 // skip walls, visited or path nodes
                 if (neighbor.getType() == 2 ||
                     neighbor.getType() == 4 ||
                     neighbor.getType() == 5) continue;

                 int tentativeG = gScore[x][y] + 1;
                 if (tentativeG < gScore[nx][ny]) {
                     // Find Better Path
                     neighbor.setLastNode(x, y);
                     gScore[nx][ny] = tentativeG;
                     neighbor.setHops(tentativeG);
                     openSet.add(neighbor);

                     // update map
                     if (neighbor.getType() != 0 && neighbor.getType() != 1) {
                         neighbor.setType(4);
                         update();
                         delay();
                     }
                 }
             }
         }

         // if no path found exit
         if (!solving || !found) {
             solving = false;
             return;
         }

         // Find path back to start
         int px = endX, py = endY;
         while (!(px == startX && py == startY) && solving) {
             Node node = map[px][py];
             int lx = node.getLastX(), ly = node.getLastY();
             px = lx; py = ly;
             if (!(px == startX && py == startY) &&
                 !(px == endX && py == endY)) {
                 map[px][py].setType(5);
             }
             update();
             delay();
         }
         solving = false;
     }
     
   
     
 
}