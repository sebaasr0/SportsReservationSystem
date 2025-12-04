//Yasir Pervaiz
// Screen for selecting a subtype of a sports field.
package ui;

import model.FieldSubtype;
import model.SportType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SubtypeSelectionScreen extends JPanel {

    private final SportType sportType;
    private FieldSubtype selectedSubtype = null;
    private final List<SubtypeDrawingPanel> subtypePanels = new ArrayList<>();
    private final JButton continueButton;
    private final Consumer<FieldSubtype> onSubtypeSelected;
    private final Runnable onBack;

    public SubtypeSelectionScreen(SportType sportType, Consumer<FieldSubtype> onSubtypeSelected, Runnable onBack) {
        this.sportType = sportType;
        this.onSubtypeSelected = onSubtypeSelected;
        this.onBack = onBack;

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(24, 24, 27));  // Charcoal background
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(24, 24, 27));  // Charcoal background

        JButton backButton = new JButton("← Back");
        backButton.addActionListener(e -> onBack.run());
        topPanel.add(backButton, BorderLayout.WEST);

        String sportName = sportType.name().charAt(0) + sportType.name().substring(1).toLowerCase();
        JLabel titleLabel = new JLabel("Select " + sportName + " Type", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);  // White text
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Subtypes Panel
        JPanel subtypesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        subtypesPanel.setBackground(new Color(24, 24, 27));  // Charcoal background

        FieldSubtype[] subtypes = FieldSubtype.getSubtypesFor(sportType);
        for (FieldSubtype subtype : subtypes) {
            SubtypeDrawingPanel panel = createSubtypePanel(subtype);
            subtypePanels.add(panel);
            subtypesPanel.add(panel);
        }

        add(subtypesPanel, BorderLayout.CENTER);

        // Continue Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(24, 24, 27));  // Charcoal background

        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        continueButton.setPreferredSize(new Dimension(200, 40));
        continueButton.setEnabled(false);
        continueButton.addActionListener(e -> {
            if (selectedSubtype != null) {
                onSubtypeSelected.accept(selectedSubtype);
            }
        });

        bottomPanel.add(continueButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private SubtypeDrawingPanel createSubtypePanel(FieldSubtype subtype) {
        SubtypeDrawingPanel panel = new SubtypeDrawingPanel(subtype);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectSubtype(panel);
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

    private void selectSubtype(SubtypeDrawingPanel selectedPanel) {
        for (SubtypeDrawingPanel panel : subtypePanels) {
            panel.setSelected(false);
        }

        selectedPanel.setSelected(true);
        selectedSubtype = selectedPanel.getSubtype();
        continueButton.setEnabled(true);
    }
}