package ui;

import chain.*;
import Command.*;
import Decorator.*;
import factory.FieldFactory;
import model.*;
import Observer.AdminDashboard;
import Observer.AuditLog;
import Observer.EmailNotifier;
import singleton.ReservationManager;
import ui.ReservationTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Initialize date combo with next 14 days
        initializeDateCombo();
        
        // Initialize observers
        ReservationManager m = ReservationManager.getInstance();
        if (!observersInitialized) {
            m.addObserver(new EmailNotifier());
            m.addObserver(new AdminDashboard());
            m.addObserver(new AuditLog());
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
    
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JButton backButton = new JButton("← Back");
        backButton.addActionListener(e -> onBack.run());
        panel.add(backButton, BorderLayout.WEST);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("Complete Your Reservation");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        String fieldInfo = selectedSubtype.getSportType().name().charAt(0) + 
                          selectedSubtype.getSportType().name().substring(1).toLowerCase() + 
                          " - " + selectedSubtype.getDisplayName() + 
                          " ($" + String.format("%.2f", selectedSubtype.getBasePrice()) + "/hour)";
        JLabel fieldLabel = new JLabel("Selected: " + fieldInfo);
        fieldLabel.setForeground(new Color(0, 128, 0));
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
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        
        // Date & Time Selection
        JPanel timePanel = new JPanel(new GridLayout(3, 2, 10, 5));
        timePanel.setBackground(Color.WHITE);
        timePanel.setBorder(BorderFactory.createTitledBorder("Date & Time Selection"));
        timePanel.add(new JLabel("Date:"));
        timePanel.add(dateCombo);
        timePanel.add(new JLabel("Start Hour:"));
        timePanel.add(hourCombo);
        timePanel.add(new JLabel("Duration (hours):"));
        timePanel.add(durCombo);
        
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        panel.add(timePanel, c);
        
        // Contact Info
        JPanel contactPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        contactPanel.setBackground(Color.WHITE);
        contactPanel.setBorder(BorderFactory.createTitledBorder("Contact Information"));
        contactPanel.add(new JLabel("Full Name:"));
        contactPanel.add(nameField);
        contactPanel.add(new JLabel("Email:"));
        contactPanel.add(emailField);
        contactPanel.add(new JLabel("Phone:"));
        contactPanel.add(phoneField);
        
        c.gridy = 1;
        panel.add(contactPanel, c);
        
        // Add-ons
        JPanel addonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        addonsPanel.setBackground(Color.WHITE);
        addonsPanel.setBorder(BorderFactory.createTitledBorder("Add-ons (Optional)"));
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
        panel.setBackground(Color.WHITE);
        
        JButton viewButton = new JButton("View Reservations");
        viewButton.addActionListener(e -> showReservationsDialog());
        panel.add(viewButton);
        
        return panel;
    }
    
    private void showReservationsDialog() {
        tableModel.setData(ReservationManager.getInstance().listAll());
        
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "All Reservations", true);
        dialog.setSize(800, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dialog.dispose());
        
        btnPanel.add(closeBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
    
    private void onReserve() {
        try {
            ContactInput input = new ContactInput(nameField.getText(), emailField.getText(), phoneField.getText(), true);
            
            ContactHandler chain = new RequiredFieldsHandler();
            chain.setNext(new EmailFormatHandler())
                 .setNext(new PhoneFormatHandler())
                 .setNext(new DuplicateUserCheckHandler());
            
            ValidationResult vr = chain.handle(input);
            if (!vr.isOk()) {
                JOptionPane.showMessageDialog(this, vr.getMessage(), "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Field field = FieldFactory.createField(selectedSubtype);
            ReservationCost cost = new BaseReservationCost(field);
            if (chkLighting.isSelected()) cost = new LightingDecorator(cost);
            if (chkEquip.isSelected()) cost = new EquipmentDecorator(cost);
            if (chkRefresh.isSelected()) cost = new RefreshmentDecorator(cost);
            
            LocalDate selectedDate = getSelectedDate();
            int startHour = (Integer) hourCombo.getSelectedItem();
            int dur = (Integer) durCombo.getSelectedItem();
            Timeslot slot = new Timeslot(selectedDate, LocalTime.of(startHour, 0), LocalTime.of(startHour + dur, 0));
            
            User user = new User(input.name(), input.email(), input.phone());
            ReserveCommand cmd = new ReserveCommand(user, selectedSubtype.getSportType(), slot, cost);
            invoker.setCommand(cmd);
            invoker.execute();
            
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
