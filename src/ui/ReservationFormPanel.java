//Yasir Pervaiz
//Reservation form panel for completing sports field reservations with validation and add-ons.
package ui;

import chain.*;
import Command.*;
import Decorator.*;
import factory.FieldFactory;
import model.*;
import Observer.AdminDashboard;
import Observer.EmailNotifier;
import singleton.ReservationManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.ListSelectionModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationFormPanel extends JPanel {

    private final FieldSubtype selectedSubtype;
    private final Runnable onBack;
    private final Runnable onNewReservation;

    private final JTextField nameField = new JTextField(15);
    private final JTextField emailField = new JTextField(15);
    private final JTextField phoneField = new JTextField(15);

    // Date selection - next 14 days
    private final JComboBox<String> dateCombo = new JComboBox<>();
    private final JComboBox<Integer> hourCombo = new JComboBox<>(new Integer[]{8,9,10,11,12,13,14,15,16,17,18,19,20});
    private final JComboBox<Integer> durCombo = new JComboBox<>(new Integer[]{1,2});

    private final JCheckBox chkLighting = new JCheckBox("Lighting (+$10)");
    private final JCheckBox chkEquip = new JCheckBox("Equipment (+$8)");
    private final JCheckBox chkRefresh = new JCheckBox("Refreshments (+$5)");

    private final JTable table;
    private final ReservationTableModel tableModel;
    private final CommandInvoker invoker = new CommandInvoker();

    private static boolean observersInitialized = false;

    public ReservationFormPanel(FieldSubtype selectedSubtype, Runnable onBack, Runnable onNewReservation) {
        this.selectedSubtype = selectedSubtype;
        this.onBack = onBack;
        this.onNewReservation = onNewReservation;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(20, 30, 70));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Initialize date combo with next 14 days
        initializeDateCombo();

        // Initialize observers
        ReservationManager m = ReservationManager.getInstance();
        if (!observersInitialized) {
            m.addObserver(new EmailNotifier());
            m.addObserver(new AdminDashboard());
            observersInitialized = true;
        }

        tableModel = new ReservationTableModel(m.listAll());
        table = new JTable(tableModel);

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildFormPanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);

        new Timer(800, e -> tableModel.setData(ReservationManager.getInstance().listAll())).start();
    }

    private void initializeDateCombo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy");
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 14; i++) {
            LocalDate date = today.plusDays(i);
            String label = date.format(formatter);
            if (i == 0) label = "Today - " + label;
            if (i == 1) label = "Tomorrow - " + label;
            dateCombo.addItem(label);
        }
    }

    private LocalDate getSelectedDate() {
        int selectedIndex = dateCombo.getSelectedIndex();
        return LocalDate.now().plusDays(selectedIndex);
    }

    // Helper method to create white label
    private JLabel createWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    // Helper method to create styled border with white title
    private TitledBorder createWhiteTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                title
        );
        border.setTitleColor(Color.WHITE);
        return border;
    }

    //top panel
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 30, 70));

        JButton backButton = new JButton("← Back");
        backButton.addActionListener(e -> onBack.run());
        panel.add(backButton, BorderLayout.WEST);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(20, 30, 70));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Complete Your Reservation");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String fieldInfo = selectedSubtype.getSportType().name().charAt(0) +
                selectedSubtype.getSportType().name().substring(1).toLowerCase() +
                " - " + selectedSubtype.getDisplayName() +
                " ($" + String.format("%.2f", selectedSubtype.getBasePrice()) + "/hour)";
        JLabel fieldLabel = new JLabel("Selected: " + fieldInfo);
        fieldLabel.setForeground(new Color(100, 255, 150));
        fieldLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(fieldLabel);

        panel.add(titlePanel, BorderLayout.CENTER);

        JButton newButton = new JButton("New Reservation");
        newButton.addActionListener(e -> onNewReservation.run());
        panel.add(newButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(10, 20, 58));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Date & Time Selection
        JPanel timePanel = new JPanel(new GridLayout(3, 2, 10, 5));
        timePanel.setBackground(new Color(10, 20, 58));
        timePanel.setBorder(createWhiteTitledBorder("Date & Time Selection"));
        timePanel.add(createWhiteLabel("Date:"));
        timePanel.add(dateCombo);
        timePanel.add(createWhiteLabel("Start Hour:"));
        timePanel.add(hourCombo);
        timePanel.add(createWhiteLabel("Duration (hours):"));
        timePanel.add(durCombo);

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        panel.add(timePanel, c);

        // Contact Info
        JPanel contactPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        contactPanel.setBackground(new Color(10, 20, 58));
        contactPanel.setBorder(createWhiteTitledBorder("Contact Information"));
        contactPanel.add(createWhiteLabel("Full Name:"));
        contactPanel.add(nameField);
        contactPanel.add(createWhiteLabel("Email:"));
        contactPanel.add(emailField);
        contactPanel.add(createWhiteLabel("Phone:"));
        contactPanel.add(phoneField);

        c.gridy = 1;
        panel.add(contactPanel, c);

        // Add-ons
        JPanel addonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        addonsPanel.setBackground(new Color(10, 20, 58));
        addonsPanel.setBorder(createWhiteTitledBorder("Add-ons (Optional)"));

        // Style checkboxes with white text
        chkLighting.setForeground(Color.WHITE);
        chkLighting.setBackground(new Color(10, 20, 58));
        chkEquip.setForeground(Color.WHITE);
        chkEquip.setBackground(new Color(10, 20, 58));
        chkRefresh.setForeground(Color.WHITE);
        chkRefresh.setBackground(new Color(10, 20, 58));

        addonsPanel.add(chkLighting);
        addonsPanel.add(chkEquip);
        addonsPanel.add(chkRefresh);

        c.gridy = 2;
        panel.add(addonsPanel, c);

        // Reserve Button
        JButton reserveButton = new JButton("Confirm Reservation");
        reserveButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        reserveButton.setPreferredSize(new Dimension(200, 40));
        reserveButton.addActionListener(e -> onReserve());

        c.gridy = 3;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(reserveButton, c);

        return panel;
    }

    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(20, 30, 70));

        JButton viewButton = new JButton("View Reservations");
        viewButton.addActionListener(e -> showReservationsDialog());
        panel.add(viewButton);

        return panel;
    }
    // Shows the reservations dialog with options to cancel or modify
    private void showReservationsDialog() {

        ReservationTableModel dialogTableModel = new ReservationTableModel(ReservationManager.getInstance().listAll());
        JTable dialogTable = new JTable(dialogTableModel);

        // Configure table to maintain selection
        dialogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dialogTable.setRowSelectionAllowed(true);
        dialogTable.setFocusable(true);
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "All Reservations", true);
        dialog.setSize(900, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(new JScrollPane(dialogTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        // Cancel Button
        JButton cancelBtn = new JButton("Cancel Reservation");
        cancelBtn.setFocusable(false);
        cancelBtn.addActionListener(e -> onCancelReservation(dialog, dialogTable, dialogTableModel));
        btnPanel.add(cancelBtn);

        // Modify Button
        JButton modifyBtn = new JButton("Modify Reservation");
        modifyBtn.setFocusable(false);
        modifyBtn.addActionListener(e -> onModifyReservation(dialog, dialogTable, dialogTableModel));
        btnPanel.add(modifyBtn);

        // Close Button
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dialog.dispose());
        btnPanel.add(closeBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    // Handles the cancel reservation action
    private void onCancelReservation(JDialog parentDialog, JTable dialogTable, ReservationTableModel dialogTableModel) {
        int selectedRow = dialogTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(parentDialog,
                    "Please select a reservation to cancel.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Get the selected reservation
        Reservation reservation = dialogTableModel.getReservationAt(selectedRow);
        if (reservation == null) {
            JOptionPane.showMessageDialog(parentDialog,
                    "Could not find the selected reservation.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Check if already canceled
        if (reservation.getStatus() == model.ReservationStatus.CANCELED) {
            JOptionPane.showMessageDialog(parentDialog,
                    "This reservation is already canceled.",
                    "Already Canceled", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Confirm cancellation
        int confirm = JOptionPane.showConfirmDialog(parentDialog,
                "Are you sure you want to cancel this reservation?\n\n" +
                        "User: " + reservation.getUser().getName() + "\n" +
                        "Field: " + reservation.getField().getFullDescription() + "\n" +
                        "Date: " + reservation.getTimeslot().getDate() + "\n" +
                        "Time: " + reservation.getTimeslot().getStart() + " - " + reservation.getTimeslot().getEnd(),
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                CancelCommand cmd = new CancelCommand(reservation);
                invoker.setCommand(cmd);
                invoker.execute();
                // Refresh table data
                dialogTableModel.setData(ReservationManager.getInstance().listAll());
                JOptionPane.showMessageDialog(parentDialog,
                        "Reservation canceled successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentDialog,
                        "Error canceling reservation: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Handles the modify reservation action
    private void onModifyReservation(JDialog parentDialog, JTable dialogTable, ReservationTableModel dialogTableModel) {
        int selectedRow = dialogTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(parentDialog,
                    "Please select a reservation to modify.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Get the selected reservation
        Reservation reservation = dialogTableModel.getReservationAt(selectedRow);
        if (reservation == null) {
            JOptionPane.showMessageDialog(parentDialog,
                    "Could not find the selected reservation.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Check if canceled
        if (reservation.getStatus() == model.ReservationStatus.CANCELED) {
            JOptionPane.showMessageDialog(parentDialog,
                    "Cannot modify a canceled reservation.",
                    "Cannot Modify", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Show modification dialog
        showModifyDialog(parentDialog, reservation, dialogTableModel);
    }

    // Shows a dialog to modify the reservation cost (add-ons)
    private void showModifyDialog(JDialog parentDialog, Reservation reservation, ReservationTableModel dialogTableModel) {
        JDialog modifyDialog = new JDialog(parentDialog, "Modify Reservation", true);
        modifyDialog.setSize(450, 350);
        modifyDialog.setLocationRelativeTo(parentDialog);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Calculate base price and determine which add-ons are currently selected
        double basePrice = reservation.getField().getBasePrice();
        double currentCost = reservation.getTotalCost();
        double addOnsCost = currentCost - basePrice;

        // Determine which add-ons are likely selected based on cost
        boolean hasLighting = false;
        boolean hasEquipment = false;
        boolean hasRefreshments = false;

        // Check combinations to determine add-ons (greedy approach from highest to lowest)
        double remaining = addOnsCost;
        if (remaining >= 10.0) {
            hasLighting = true;
            remaining -= 10.0;
        }
        if (remaining >= 8.0) {
            hasEquipment = true;
            remaining -= 8.0;
        }
        if (remaining >= 5.0) {
            hasRefreshments = true;
            remaining -= 5.0;
        }

        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Current Reservation"));
        infoPanel.add(new JLabel("User: " + reservation.getUser().getName()));
        infoPanel.add(new JLabel("Field: " + reservation.getField().getFullDescription()));
        infoPanel.add(new JLabel("Date: " + reservation.getTimeslot().getDate()));
        infoPanel.add(new JLabel("Base Price: $" + String.format("%.2f", basePrice)));
        infoPanel.add(new JLabel("Current Total: $" + String.format("%.2f", currentCost)));
        panel.add(infoPanel, BorderLayout.NORTH);

        // Add-ons modification panel
        JPanel addonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        addonsPanel.setBorder(BorderFactory.createTitledBorder("Modify Add-ons (check/uncheck to add/remove)"));

        JCheckBox modLighting = new JCheckBox("Lighting ($10)");
        JCheckBox modEquip = new JCheckBox("Equipment ($8)");
        JCheckBox modRefresh = new JCheckBox("Refreshments ($5)");

        // Pre-select checkboxes based on current add-ons
        modLighting.setSelected(hasLighting);
        modEquip.setSelected(hasEquipment);
        modRefresh.setSelected(hasRefreshments);

        addonsPanel.add(new JLabel("Select/deselect add-ons:"));
        addonsPanel.add(modLighting);
        addonsPanel.add(modEquip);
        addonsPanel.add(modRefresh);
        panel.add(addonsPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton applyBtn = new JButton("Apply Changes");
        applyBtn.addActionListener(e -> {
            // Calculate new total based on selected add-ons
            double newCost = basePrice;
            if (modLighting.isSelected()) newCost += 10.0;
            if (modEquip.isSelected()) newCost += 8.0;
            if (modRefresh.isSelected()) newCost += 5.0;

            // Check if anything changed
            if (Math.abs(newCost - currentCost) < 0.01) {
                JOptionPane.showMessageDialog(modifyDialog,
                        "No changes were made to the add-ons.",
                        "No Changes", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // Execute modification command
            try {
                ModifyCommand cmd = new ModifyCommand(reservation, newCost);
                invoker.setCommand(cmd);
                invoker.execute();

                dialogTableModel.setData(ReservationManager.getInstance().listAll());
                JOptionPane.showMessageDialog(modifyDialog,
                        "Reservation modified successfully!\nNew total: $" + String.format("%.2f", newCost),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                modifyDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(modifyDialog,
                        "Error modifying reservation: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnPanel.add(applyBtn);

        JButton cancelDialogBtn = new JButton("Cancel");
        cancelDialogBtn.addActionListener(e -> modifyDialog.dispose());
        btnPanel.add(cancelDialogBtn);

        panel.add(btnPanel, BorderLayout.SOUTH);

        modifyDialog.setContentPane(panel);
        modifyDialog.setVisible(true);
    }

    // Handles the reservation process
    private void onReserve() {
        try {
            ContactInput input = new ContactInput(nameField.getText(), emailField.getText(), phoneField.getText(), true);

            ContactHandler chain = new RequiredFieldsHandler();
            chain.setNext(new EmailFormatHandler())
                    .setNext(new PhoneFormatHandler())
                    .setNext(new DuplicateUserCheckHandler());

            // Validate input through the chain
            ValidationResult vr = chain.handle(input);
            if (!vr.isOk()) {
                JOptionPane.showMessageDialog(this, vr.getMessage(), "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Build field and cost with decorators
            Field field = FieldFactory.createField(selectedSubtype);
            ReservationCost cost = new BaseReservationCost(field);
            if (chkLighting.isSelected()) cost = new LightingDecorator(cost);
            if (chkEquip.isSelected()) cost = new EquipmentDecorator(cost);
            if (chkRefresh.isSelected()) cost = new RefreshmentDecorator(cost);

            // Create timeslot
            LocalDate selectedDate = getSelectedDate();
            int startHour = (Integer) hourCombo.getSelectedItem();
            int dur = (Integer) durCombo.getSelectedItem();
            Timeslot slot = new Timeslot(selectedDate, LocalTime.of(startHour, 0), LocalTime.of(startHour + dur, 0));

            // Create user and execute reservation command
            User user = new User(input.name(), input.email(), input.phone());
            ReserveCommand cmd = new ReserveCommand(user, selectedSubtype.getSportType(), slot, cost);
            invoker.setCommand(cmd);
            invoker.execute();

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Reservation created!\n\n" +
                            "Field: " + field.getFullDescription() + "\n" +
                            "Date: " + selectedDate + "\n" +
                            "Time: " + slot.getStart() + " - " + slot.getEnd() + "\n" +
                            "Total: $" + String.format("%.2f", cost.getCost()),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            chkLighting.setSelected(false);
            chkEquip.setSelected(false);
            chkRefresh.setSelected(false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}