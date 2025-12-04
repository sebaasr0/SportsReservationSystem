//Yasir Pervaiz
// A panel that draws different sports fields based on the selected sport type
package ui;

import model.SportType;

import javax.swing.*;
import java.awt.*;

public class FieldDrawingPanel extends JPanel {
    
    private final SportType sportType;
    private boolean selected = false;
    private boolean hovered = false;
    
    public FieldDrawingPanel(SportType sportType) {
        this.sportType = sportType;
        setPreferredSize(new Dimension(200, 160));
        setBackground(new Color(81, 101, 170));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
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
    
    public SportType getSportType() {
        return sportType;
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
        int fieldH = h - 70;
        
        switch (sportType) {
            case SOCCER -> drawSoccerField(g2d, fieldX, fieldY, fieldW, fieldH);
            case TENNIS -> drawTennisCourt(g2d, fieldX, fieldY, fieldW, fieldH);
            case PADEL -> drawPadelCourt(g2d, fieldX, fieldY, fieldW, fieldH);
        }
        
        // Label
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        String label = sportType.name().charAt(0) + sportType.name().substring(1).toLowerCase();
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(label, (w - fm.stringWidth(label)) / 2, h - 15);
        
        g2d.dispose();
    }
    
    private void drawSoccerField(Graphics2D g2d, int x, int y, int w, int h) {
        // Green field
        g2d.setColor(new Color(76, 153, 0));
        g2d.fillRect(x, y, w, h);
        
        // White lines
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x, y, w, h);
        
        // Center line
        g2d.drawLine(x + w/2, y, x + w/2, y + h);
        
        // Center circle
        int r = h/4;
        g2d.drawOval(x + w/2 - r, y + h/2 - r, r*2, r*2);
        
        // Goals
        int goalW = w/6;
        int goalH = h/3;
        g2d.drawRect(x, y + h/2 - goalH/2, goalW, goalH);
        g2d.drawRect(x + w - goalW, y + h/2 - goalH/2, goalW, goalH);
    }
    
    private void drawTennisCourt(Graphics2D g2d, int x, int y, int w, int h) {
        // Blue court
        g2d.setColor(new Color(70, 130, 180));
        g2d.fillRect(x, y, w, h);
        
        // White lines
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x, y, w, h);
        
        // Net line
        g2d.drawLine(x, y + h/2, x + w, y + h/2);
        
        // Service boxes
        int serviceH = h/4;
        g2d.drawLine(x, y + serviceH, x + w, y + serviceH);
        g2d.drawLine(x, y + h - serviceH, x + w, y + h - serviceH);
        g2d.drawLine(x + w/2, y + serviceH, x + w/2, y + h - serviceH);
    }
    
    private void drawPadelCourt(Graphics2D g2d, int x, int y, int w, int h) {
        // Green/blue court
        g2d.setColor(new Color(100, 149, 237));
        g2d.fillRect(x, y, w, h);
        
        // White lines
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x, y, w, h);
        
        // Net line
        g2d.drawLine(x, y + h/2, x + w, y + h/2);
        
        // Service lines
        int serviceH = h/3;
        g2d.drawLine(x, y + serviceH, x + w, y + serviceH);
        g2d.drawLine(x, y + h - serviceH, x + w, y + h - serviceH);
        g2d.drawLine(x + w/2, y, x + w/2, y + serviceH);
        g2d.drawLine(x + w/2, y + h - serviceH, x + w/2, y + h);
        
        // Walls (darker borders)
        g2d.setColor(new Color(70, 70, 100));
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(x, y, x, y + h);
        g2d.drawLine(x + w, y, x + w, y + h);
    }
}
