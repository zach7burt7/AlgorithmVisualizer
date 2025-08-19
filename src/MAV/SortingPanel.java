package MAV;

// Sorting Page

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

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

        // Control Panel
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

        // Draw Panel
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