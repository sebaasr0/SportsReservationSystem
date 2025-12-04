package ui;

import model.FieldSubtype;
import model.SportType;
import ui.FieldSelectionScreen;
import ui.ReservationFormPanel;
import ui.SubtypeSelectionScreen;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    
    private static final String FIELD_SELECTION = "FIELD_SELECTION";
    private static final String SUBTYPE_SELECTION = "SUBTYPE_SELECTION";
    private static final String RESERVATION_FORM = "RESERVATION_FORM";
    
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    
    private FieldSelectionScreen fieldSelectionScreen;
    private SubtypeSelectionScreen subtypeSelectionScreen;
    private ReservationFormPanel reservationFormPanel;
    
    private SportType selectedSport;
    private FieldSubtype selectedSubtype;
    
    public MainFrame() {
        super("Sports Field Reservation System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(20, 30, 70));
        
        // Initialize the first screen
        initFieldSelectionScreen();
        
        add(mainPanel);
        
        // Show the field selection screen
        cardLayout.show(mainPanel, FIELD_SELECTION);
    }
    
    private void initFieldSelectionScreen() {
        fieldSelectionScreen = new FieldSelectionScreen(this::onSportSelected);
        mainPanel.add(fieldSelectionScreen, FIELD_SELECTION);
    }
    
    private void onSportSelected(SportType sport) {
        this.selectedSport = sport;
        
        // Remove old subtype selection screen if exists
        if (subtypeSelectionScreen != null) {
            mainPanel.remove(subtypeSelectionScreen);
        }
        
        // Create new subtype selection screen for this sport
        subtypeSelectionScreen = new SubtypeSelectionScreen(
            sport,
            this::onSubtypeSelected,
            this::goBackToFieldSelection
        );
        mainPanel.add(subtypeSelectionScreen, SUBTYPE_SELECTION);
        
        // Navigate to subtype selection
        cardLayout.show(mainPanel, SUBTYPE_SELECTION);
    }
    
    private void onSubtypeSelected(FieldSubtype subtype) {
        this.selectedSubtype = subtype;
        
        // Remove old reservation form if exists
        if (reservationFormPanel != null) {
            mainPanel.remove(reservationFormPanel);
        }
        
        // Create new reservation form with selected subtype
        reservationFormPanel = new ReservationFormPanel(
            subtype,
            this::goBackToSubtypeSelection,
            this::startNewReservation
        );
        mainPanel.add(reservationFormPanel, RESERVATION_FORM);
        
        // Navigate to reservation form
        cardLayout.show(mainPanel, RESERVATION_FORM);
    }
    
    private void goBackToFieldSelection() {
        cardLayout.show(mainPanel, FIELD_SELECTION);
    }
    
    private void goBackToSubtypeSelection() {
        cardLayout.show(mainPanel, SUBTYPE_SELECTION);
    }
    
    private void startNewReservation() {
        // Reset selections and go back to field selection
        selectedSport = null;
        selectedSubtype = null;
        
        // Recreate field selection screen to clear selections
        mainPanel.remove(fieldSelectionScreen);
        initFieldSelectionScreen();
        
        cardLayout.show(mainPanel, FIELD_SELECTION);
    }
}
