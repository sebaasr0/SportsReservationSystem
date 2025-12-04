package ui;

import model.FieldSubtype;

import javax.swing.*;
import java.awt.*;

public class SubtypeDrawingPanel extends JPanel {
    
    private final FieldSubtype subtype;
    private boolean selected = false;
    private boolean hovered = false;
    
    public SubtypeDrawingPanel(FieldSubtype subtype) {
        this.subtype = subtype;
        setPreferredSize(new Dimension(220, 180));
        setBackground(new Color(81, 101, 170));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 3));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }
    
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
        repaint();
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public FieldSubtype getSubtype() {
        return subtype;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        
        // Hover effect
        if (hovered && !selected) {
            g2d.setColor(new Color(136, 147, 159));
            g2d.fillRect(0, 0, w, h);
        }
        
        // Selection border
        if (selected) {
            g2d.setColor(new Color(245, 16, 234));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(2, 2, w - 4, h - 4);
        }
        
        // Draw field
        int fieldX = 30;
        int fieldY = 20;
        int fieldW = w - 60;
        int fieldH = h - 80;
        
        switch (subtype) {
            case SOCCER_5V5 -> drawSoccerField(g2d, fieldX, fieldY, fieldW, fieldH, "5v5");
            case SOCCER_7V7 -> drawSoccerField(g2d, fieldX, fieldY, fieldW, fieldH, "7v7");
            case TENNIS_SINGLES -> drawTennisCourt(g2d, fieldX, fieldY, fieldW, fieldH, true);
            case TENNIS_DOUBLES -> drawTennisCourt(g2d, fieldX, fieldY, fieldW, fieldH, false);
            case PADEL_WITH_ROOF -> drawPadelCourt(g2d, fieldX, fieldY, fieldW, fieldH, true);
            case PADEL_WITHOUT_ROOF -> drawPadelCourt(g2d, fieldX, fieldY, fieldW, fieldH, false);
        }
        
        // Label
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 13));
        String label = subtype.getDisplayName();
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(label, (w - fm.stringWidth(label)) / 2, h - 28);
        
        // Price
        g2d.setColor(new Color(113, 255, 113));
        g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
        String price = String.format("$%.2f/hour", subtype.getBasePrice());
        fm = g2d.getFontMetrics();
        g2d.drawString(price, (w - fm.stringWidth(price)) / 2, h - 10);
        
        g2d.dispose();
    }
    
    private void drawSoccerField(Graphics2D g2d, int x, int y, int w, int h, String size) {
        // Green field
        g2d.setColor(new Color(76, 153, 0));
        g2d.fillRect(x, y, w, h);
        
        // White lines
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x, y, w, h);
        g2d.drawLine(x + w/2, y, x + w/2, y + h);
        
        // Center circle
        int r = h/5;
        g2d.drawOval(x + w/2 - r, y + h/2 - r, r*2, r*2);
        
        // Goals
        int goalW = w/7;
        int goalH = h/3;
        g2d.drawRect(x, y + h/2 - goalH/2, goalW, goalH);
        g2d.drawRect(x + w - goalW, y + h/2 - goalH/2, goalW, goalH);
        
        // Size label on field
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(size, x + w/2 - fm.stringWidth(size)/2, y + h/2 + 5);
    }
    
    private void drawTennisCourt(Graphics2D g2d, int x, int y, int w, int h, boolean isSingles) {
        // Blue court
        g2d.setColor(new Color(70, 130, 180));
        g2d.fillRect(x, y, w, h);
        
        // White lines
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x, y, w, h);
        
        // Net
        g2d.drawLine(x, y + h/2, x + w, y + h/2);
        
        // Service boxes
        int serviceH = h/4;
        g2d.drawLine(x, y + serviceH, x + w, y + serviceH);
        g2d.drawLine(x, y + h - serviceH, x + w, y + h - serviceH);
        g2d.drawLine(x + w/2, y + serviceH, x + w/2, y + h - serviceH);
        
        // Singles lines (highlighted for singles)
        if (isSingles) {
            g2d.setColor(new Color(255, 255, 0));
            g2d.setStroke(new BasicStroke(3));
            int margin = w/8;
            g2d.drawLine(x + margin, y, x + margin, y + h);
            g2d.drawLine(x + w - margin, y, x + w - margin, y + h);
        }
    }
    
    private void drawPadelCourt(Graphics2D g2d, int x, int y, int w, int h, boolean hasRoof) {
        // Court
        g2d.setColor(new Color(100, 149, 237));
        g2d.fillRect(x, y, w, h);
        
        // White lines
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x, y, w, h);
        
        // Net
        g2d.drawLine(x, y + h/2, x + w, y + h/2);
        
        // Service lines
        int serviceH = h/3;
        g2d.drawLine(x, y + serviceH, x + w, y + serviceH);
        g2d.drawLine(x, y + h - serviceH, x + w, y + h - serviceH);
        g2d.drawLine(x + w/2, y, x + w/2, y + serviceH);
        g2d.drawLine(x + w/2, y + h - serviceH, x + w/2, y + h);
        
        // Walls
        g2d.setColor(new Color(70, 70, 100));
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(x, y, x, y + h);
        g2d.drawLine(x + w, y, x + w, y + h);
        
        // Roof indicator
        if (hasRoof) {
            g2d.setColor(new Color(100, 100, 100));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(x - 5, y - 8, x + w + 5, y - 8);
            g2d.drawLine(x, y - 8, x, y);
            g2d.drawLine(x + w, y - 8, x + w, y);
        }
    }
}
