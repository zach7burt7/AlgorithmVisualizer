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
			PathfindingPanel pathfindingPanel = new PathfindingPanel(this);
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

