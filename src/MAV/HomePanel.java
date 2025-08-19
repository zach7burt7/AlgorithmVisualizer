package MAV;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	    private final Color base, hover, press, text;
	    private boolean hovered = false, pressed = false;
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

	       
	        g2.setColor(new Color(0, 0, 0, 60));
	        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);

	        // text
	        FontMetrics fm = g2.getFontMetrics();
	        int tx = (getWidth() - fm.stringWidth(getText())) / 2;
	        int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
	        g2.setColor(text);
	        g2.drawString(getText(), tx, ty);
	        g2.dispose();
	    }
	}
}