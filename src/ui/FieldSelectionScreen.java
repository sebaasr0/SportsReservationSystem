//Yasir Pervaiz
// A GUI panel for selecting a sports field type (Soccer, Tennis, Padel)
package ui;

import model.SportType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class FieldSelectionScreen extends JPanel {
    
    private SportType selectedSport = null;
    private final FieldDrawingPanel soccerPanel;
    private final FieldDrawingPanel tennisPanel;
    private final FieldDrawingPanel padelPanel;
    private final JButton continueButton;
    private final Consumer<SportType> onSportSelected;
    
    public FieldSelectionScreen(Consumer<SportType> onSportSelected) {
        this.onSportSelected = onSportSelected;
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(68, 68, 81));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        
        // Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(68, 68, 81));
        JLabel titleLabel = new JLabel("Select a Field Type");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Fields Panel
        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        fieldsPanel.setBackground(new Color(68, 68, 81));
        
        soccerPanel = createFieldPanel(SportType.SOCCER);
        tennisPanel = createFieldPanel(SportType.TENNIS);
        padelPanel = createFieldPanel(SportType.PADEL);
        
        fieldsPanel.add(soccerPanel);
        fieldsPanel.add(tennisPanel);
        fieldsPanel.add(padelPanel);
        
        add(fieldsPanel, BorderLayout.CENTER);
        
        // Continue Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(68, 68, 81));
        
        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        continueButton.setPreferredSize(new Dimension(200, 40));
        continueButton.setEnabled(false);
        continueButton.addActionListener(e -> {
            if (selectedSport != null) {
                onSportSelected.accept(selectedSport);
            }
        });
        
        bottomPanel.add(continueButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private FieldDrawingPanel createFieldPanel(SportType sportType) {
        FieldDrawingPanel panel = new FieldDrawingPanel(sportType);
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectField(panel);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setHovered(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setHovered(false);
            }
        });
        
        return panel;
    }
    
    private void selectField(FieldDrawingPanel selectedPanel) {
        soccerPanel.setSelected(false);
        tennisPanel.setSelected(false);
        padelPanel.setSelected(false);
        
        selectedPanel.setSelected(true);
        selectedSport = selectedPanel.getSportType();
        continueButton.setEnabled(true);
    }
}
