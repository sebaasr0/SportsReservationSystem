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
    // Hour selection with AM/PM format (8:00 AM to 8:00 PM)
    private final JComboBox<String> hourCombo = new JComboBox<>(new String[]{
            "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM",
            "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM",
            "6:00 PM", "7:00 PM", "8:00 PM"
    });
    private final JComboBox<Integer> durCombo = new JComboBox<>(new Integer[]{1, 2});

    private final JCheckBox chkLighting = new JCheckBox("Lighting (+$10/hr)");
    private final JCheckBox chkEquip = new JCheckBox("Equipment (+$8/hr)");
    private final JCheckBox chkRefresh = new JCheckBox("Refreshments (+$5/hr)");

    private final JTable table;
    private final ReservationTableModel tableModel;
    private final CommandInvoker invoker = new CommandInvoker();

    private static boolean observersInitialized = false;

    public ReservationFormPanel(FieldSubtype selectedSubtype, Runnable onBack, Runnable onNewReservation) {
        this.selectedSubtype = selectedSubtype;
        this.onBack = onBack;
        this.onNewReservation = onNewReservation;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(24, 24, 27));  // Charcoal background
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

    //Converts AM/PM time string to 24-hour format integer
    private int getSelectedHour() {
        String selected = (String) hourCombo.getSelectedItem();
        // Parse "8:00 AM" or "1:00 PM" format
        String[] parts = selected.split(":");
        int hour = Integer.parseInt(parts[0]);
        boolean isPM = selected.contains("PM");

        if (isPM && hour != 12) {
            hour += 12; // Convert PM hours
        } else if (!isPM && hour == 12) {
            hour = 0;  // Convert 12 AM to 0 hour
        }
        return hour;
    }

    // Helper method to format hour to AM/PM for display
    private String formatHour(int hour) {
        if (hour == 0 || hour == 24) return "12:00 AM";
        if (hour == 12) return "12:00 PM";
        if (hour < 12) return hour + ":00 AM";
        return (hour - 12) + ":00 PM";
    }

    // Helper method to create white label
    private JLabel createWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);  // White text
        return label;
    }

    // Helper method to create styled border with white title
    private TitledBorder createWhiteTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(161, 161, 170)),  // Zinc border
                title
        );
        border.setTitleColor(Color.WHITE);  // White title
        return border;
    }

    //top panel
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(24, 24, 27));  // Charcoal background

        JButton backButton = new JButton("← Back");
        backButton.addActionListener(e -> onBack.run());
        panel.add(backButton, BorderLayout.WEST);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(24, 24, 27));  // Charcoal background
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Complete Your Reservation");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);  // White text
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String fieldInfo = selectedSubtype.getSportType().name().charAt(0) +
                selectedSubtype.getSportType().name().substring(1).toLowerCase() +
                " - " + selectedSubtype.getDisplayName() +
                " ($" + String.format("%.2f", selectedSubtype.getBasePrice()) + "/hour)";
        JLabel fieldLabel = new JLabel("Selected: " + fieldInfo);
        fieldLabel.setForeground(new Color(34, 211, 238));  // Cyan for price/success
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
        panel.setBackground(new Color(24, 24, 27));  // Charcoal background

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Date & Time Selection
        JPanel timePanel = new JPanel(new GridLayout(3, 2, 10, 5));
        timePanel.setBackground(new Color(39, 39, 42));  // Dark Gray panel
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
        contactPanel.setBackground(new Color(39, 39, 42));  // Dark Gray panel
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
        addonsPanel.setBackground(new Color(39, 39, 42));  // Dark Gray panel
        addonsPanel.setBorder(createWhiteTitledBorder("Add-ons (Optional - per hour)"));

        // Style checkboxes with white text
        chkLighting.setForeground(Color.WHITE);  // White text
        chkLighting.setBackground(new Color(39, 39, 42));  // Dark Gray
        chkEquip.setForeground(Color.WHITE);  // White text
        chkEquip.setBackground(new Color(39, 39, 42));  // Dark Gray
        chkRefresh.setForeground(Color.WHITE);  // White text
        chkRefresh.setBackground(new Color(39, 39, 42));  // Dark Gray

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
        panel.setBackground(new Color(24, 24, 27));  // Charcoal background

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

        Reservation reservation = dialogTableModel.getReservationAt(selectedRow);
        if (reservation == null) {
            JOptionPane.showMessageDialog(parentDialog,
                    "Could not find the selected reservation.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (reservation.getStatus() == ReservationStatus.CANCELED) {
            JOptionPane.showMessageDialog(parentDialog,
                    "This reservation is already canceled.",
                    "Already Canceled", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(parentDialog,
                "Are you sure you want to cancel this reservation?\n\n" +
                        "User: " + reservation.getUser().getName() + "\n" +
                        "Field: " + reservation.getField().getFullDescription() + "\n" +
                        "Date: " + reservation.getTimeslot().getDate() + "\n" +
                        "Time: " + formatHour(reservation.getTimeslot().getStart().getHour()) +
                        " - " + formatHour(reservation.getTimeslot().getEnd().getHour()),
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                CancelCommand cmd = new CancelCommand(reservation);
                invoker.setCommand(cmd);
                invoker.execute();

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

        Reservation reservation = dialogTableModel.getReservationAt(selectedRow);
        if (reservation == null) {
            JOptionPane.showMessageDialog(parentDialog,
                    "Could not find the selected reservation.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (reservation.getStatus() == ReservationStatus.CANCELED) {
            JOptionPane.showMessageDialog(parentDialog,
                    "Cannot modify a canceled reservation.",
                    "Cannot Modify", JOptionPane.WARNING_MESSAGE);
            return;
        }

        showModifyDialog(parentDialog, reservation, dialogTableModel);
    }

    // Shows a dialog to modify the reservation cost (add-ons)
    private void showModifyDialog(JDialog parentDialog, Reservation reservation, ReservationTableModel dialogTableModel) {
        JDialog modifyDialog = new JDialog(parentDialog, "Modify Reservation", true);
        modifyDialog.setSize(450, 400);
        modifyDialog.setLocationRelativeTo(parentDialog);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        //Get base price from the ACTUAL field subtype stored in reservation
        double basePrice = reservation.getField().getBasePrice();
        double currentCost = reservation.getTotalCost();

        // Calculate duration from timeslot
        int duration = reservation.getTimeslot().getEnd().getHour() - reservation.getTimeslot().getStart().getHour();
        if (duration <= 0) duration = 1;

        // Calculate HOURLY cost first, then determine add-ons
        double hourlyCost = currentCost / duration;
        double hourlyAddOns = hourlyCost - basePrice;

        // Determine which add-ons are selected based on HOURLY add-on cost
        boolean hasLighting = false;
        boolean hasEquipment = false;
        boolean hasRefreshments = false;

        // Check combinations (with small tolerance for floating point)
        double remaining = hourlyAddOns;
        if (remaining >= 9.99) {
            hasLighting = true;
            remaining -= 10.0;
        }
        if (remaining >= 7.99) {
            hasEquipment = true;
            remaining -= 8.0;
        }
        if (remaining >= 4.99) {
            hasRefreshments = true;
        }

        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Current Reservation"));
        infoPanel.add(new JLabel("User: " + reservation.getUser().getName()));
        infoPanel.add(new JLabel("Field: " + reservation.getField().getFullDescription()));
        infoPanel.add(new JLabel("Date: " + reservation.getTimeslot().getDate()));
        infoPanel.add(new JLabel("Time: " + formatHour(reservation.getTimeslot().getStart().getHour()) +
                " - " + formatHour(reservation.getTimeslot().getEnd().getHour())));
        infoPanel.add(new JLabel("Duration: " + duration + " hour(s)"));
        infoPanel.add(new JLabel("Base Price: $" + String.format("%.2f", basePrice) + "/hr"));
        infoPanel.add(new JLabel("Current Total: $" + String.format("%.2f", currentCost)));
        panel.add(infoPanel, BorderLayout.NORTH);

        // Add-ons modification panel
        JPanel addonsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        addonsPanel.setBorder(BorderFactory.createTitledBorder("Modify Add-ons (per hour)"));

        JCheckBox modLighting = new JCheckBox("Lighting ($10/hr)");
        JCheckBox modEquip = new JCheckBox("Equipment ($8/hr)");
        JCheckBox modRefresh = new JCheckBox("Refreshments ($5/hr)");

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

        final int finalDuration = duration;
        JButton applyBtn = new JButton("Apply Changes");
        applyBtn.addActionListener(e -> {
            // Calculate new hourly cost based on selected add-ons
            double newHourlyCost = basePrice;
            if (modLighting.isSelected()) newHourlyCost += 10.0;
            if (modEquip.isSelected()) newHourlyCost += 8.0;
            if (modRefresh.isSelected()) newHourlyCost += 5.0;

            // Multiply by duration for total cost
            double newTotalCost = newHourlyCost * finalDuration;

            // Check if anything changed
            if (Math.abs(newTotalCost - currentCost) < 0.01) {
                JOptionPane.showMessageDialog(modifyDialog,
                        "No changes were made to the add-ons.",
                        "No Changes", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                ModifyCommand cmd = new ModifyCommand(reservation, newTotalCost);
                invoker.setCommand(cmd);
                invoker.execute();

                dialogTableModel.setData(ReservationManager.getInstance().listAll());
                JOptionPane.showMessageDialog(modifyDialog,
                        "Reservation modified successfully!\n" +
                                "Hourly rate: $" + String.format("%.2f", newHourlyCost) + "\n" +
                                "Duration: " + finalDuration + " hour(s)\n" +
                                "New total: $" + String.format("%.2f", newTotalCost),
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
            ContactInput input = new ContactInput(nameField.getText(), emailField.getText(), phoneField.getText());

            ContactHandler chain = new RequiredFieldsHandler();
            chain.setNext(new EmailFormatHandler())
                    .setNext(new PhoneFormatHandler())
                    .setNext(new DuplicateUserCheckHandler());

            ValidationResult vr = chain.handle(input);
            if (!vr.isOk()) {
                JOptionPane.showMessageDialog(this, vr.getMessage(), "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Build field and cost with decorators
            Field field = FieldFactory.createField(selectedSubtype);

            // Get duration
            int duration = (Integer) durCombo.getSelectedItem();

            // Calculate hourly cost first, then multiply by duration
            ReservationCost hourlyCost = new BaseReservationCost(field);
            if (chkLighting.isSelected()) hourlyCost = new LightingDecorator(hourlyCost);
            if (chkEquip.isSelected()) hourlyCost = new EquipmentDecorator(hourlyCost);
            if (chkRefresh.isSelected()) hourlyCost = new RefreshmentDecorator(hourlyCost);

            // Get hourly rate and calculate total
            final double hourlyRate = hourlyCost.getCost();
            final double totalCost = hourlyRate * duration;
            final String description = hourlyCost.getDescription();

            // Create a cost object with the total (for the command)
            ReservationCost finalCost = new ReservationCost() {
                @Override
                public double getCost() {
                    return totalCost;
                }
                @Override
                public String getDescription() {
                    return description + " x " + duration + " hour(s)";
                }
            };

            // Create timeslot - Parse AM/PM time correctly
            LocalDate selectedDate = getSelectedDate();
            int startHour = getSelectedHour();
            Timeslot slot = new Timeslot(selectedDate, LocalTime.of(startHour, 0), LocalTime.of(startHour + duration, 0));

            // Create user and execute reservation command
            User user = new User(input.name(), input.email(), input.phone());
            ReserveCommand cmd = new ReserveCommand(user, selectedSubtype, slot, finalCost);
            invoker.setCommand(cmd);
            invoker.execute();

            // Format times for display
            String startTimeDisplay = formatHour(startHour);
            String endTimeDisplay = formatHour(startHour + duration);

            // Show success message with detailed breakdown
            JOptionPane.showMessageDialog(this,
                    "Reservation created!\n\n" +
                            "Field: " + field.getFullDescription() + "\n" +
                            "Date: " + selectedDate + "\n" +
                            "Time: " + startTimeDisplay + " - " + endTimeDisplay + "\n" +
                            "Duration: " + duration + " hour(s)\n" +
                            "Hourly Rate: $" + String.format("%.2f", hourlyRate) + "\n" +
                            "Total: $" + String.format("%.2f", totalCost),
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