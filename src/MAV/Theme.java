package MAV;

import java.awt.*;

public final class Theme {
    public static final Color GRAD_TOP       = new Color(0x1E1E2E);
    public static final Color GRAD_BOTTOM    = new Color(0x0F172A);
    public static final Color CARD_BG        = new Color(0x111827);
    public static final Color TITLE_COLOR    = new Color(0xF8FAFC);
    public static final Color SUBTITLE_COLOR = new Color(0x94A3B8);
    public static final Color MAP_BG_LIGHT   = new Color(0x4D5260);

    
    public static final Color PRIMARY_BG     = new Color(0x38BDF8);
    public static final Color PRIMARY_HOVER  = new Color(0x22D3EE);
    public static final Color PRIMARY_PRESS  = new Color(0x0891B2);
    public static final Color SECONDARY_BG   = new Color(0xA78BFA);
    public static final Color SECONDARY_HOV  = new Color(0xC084FC);
    public static final Color SECONDARY_PRE  = new Color(0x7C3AED);
    public static final Color BTN_TEXT       = new Color(0x0B1220);

    private Theme() {}

    public static void paintGradient(Component c, Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = c.getWidth(), h = c.getHeight();
        GradientPaint gp = new GradientPaint(0, 0, GRAD_TOP, 0, h, GRAD_BOTTOM);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
        g2.dispose();
    }
}