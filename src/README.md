# Sports Field Reservation System

This project is a GUI-based **Sports Field Reservation System** implemented in Java using Swing and object-oriented design principles.  
It demonstrates multiple design patterns in a real-world application including:

- Singleton
- Factory
- Observer
- Command
- Decorator
- Chain of Responsibility

The system allows users to create reservations, apply add-ons, receive notifications, and log operations automatically.

---

## Overview

Users can:

- Choose a sport and field type
- Select date and time
- Enter contact information
- Add optional services
- Confirm bookings
- Receive confirmation messages
- View reservation logs

The entire workflow is structured using design patterns for modularity, maintainability, and extensibility.

---

## Design Patterns Used

---

### 🔹 Singleton Pattern
Ensures only one instance of the reservation manager exists.

**Implementation:**
- `ReservationManager`

Used for:
- Central reservation management
- Avoiding duplicate conflicts
- System coordination

---

### 🔹 Factory Pattern
Creates sport field objects dynamically based on user choice.

**Implementation:**
- `FieldFactory`

**Creates:**
- `TennisField`
- `SoccerField`
- `PadelField`
- `BasketballCourt`

---

### 🔹 Observer Pattern
Notifies components when reservation events occur.

**Interfaces:**
- `Observer`
- `Subject`

**Observers:**
- `EmailNotifier`
- `AdminDashboard`
- `AuditLog`

---

### 🔹 Command Pattern
Encapsulates user operations as command objects.

**Commands:**
- `ReserveCommand`
- `ModifyCommand`
- `CancelCommand`

**Invoker:**
- `CommandInvoker`

---

### 🔹 Decorator Pattern
Adds optional add-ons to reservations dynamically.

**Decorators:**
- `LightingDecorator`
- `EquipmentDecorator`
- `RefreshmentDecorator`

**Core:**
- `ReservationCost`
- `BaseReservationCost`
- `AddOnDecorator`

---

### 🔹 Chain of Responsibility Pattern
Validates contact and reservation data through independent rules.

**Handlers:**
- `RequiredFieldsHandler`
- `EmailFormatHandler`
- `PhoneFormatHandler`
- `DuplicateUserCheckHandler`

**Base Classes:**
- `BaseContactHandler`
- `ContactHandler`
- `ValidationResult`

---

## Features

✔ Java Swing GUI  
✔ Real-time validation  
✔ Reservation processing pipeline  
✔ Email simulation  
✔ Audit logs  
✔ Admin dashboard  
✔ Add-on pricing logic  
✔ Clean architecture  
✔ Extendable design

---

## How to Run

1. Open project in **IntelliJ IDEA**
2. Run:

```
src/App/Main.java
```

3. The GUI will appear.
4. Select a field and complete reservation details.
5. Confirm booking to see notifications and logs.

---

## Example Console Output

```
[EMAIL] Event-CREATED -> UserEmail | Tennis | 2025-12-02 08:00-09:00 | $38.00 | CONFIRMED
[DASH] CREATED -> UserName | Tennis | 2025-12-02 08:00-09:00 | $38.00 | CONFIRMED
[AUDIT] CREATED -> UserName | Tennis | 2025-12-02 08:00-09:00 | $38.00 | CONFIRMED
[CMD] Reserved -> UserName | Tennis | 2025-12-02 08:00-09:00 | $38.00 | CONFIRMED
```

---

## Project Structure

```
SportsReservationSystem/
│
├── App/
│   └── Main.java
│
├── chain/
│   ├── BaseContactHandler
│   ├── ContactHandler
│   ├── RequiredFieldsHandler
│   ├── EmailFormatHandler
│   ├── PhoneFormatHandler
│   ├── DuplicateUserCheckHandler
│   └── ValidationResult
│
├── Command/
│   ├── Command
│   ├── CommandInvoker
│   ├── ReserveCommand
│   ├── ModifyCommand
│   └── CancelCommand
│
├── Decorator/
│   ├── ReservationCost
│   ├── BaseReservationCost
│   ├── AddOnDecorator
│   ├── EquipmentDecorator
│   ├── LightingDecorator
│   └── RefreshmentDecorator
│
├── factory/
│   ├── FieldFactory
│
├── model/
│   ├── Field
│   ├── TennisField
│   ├── SoccerField
│   ├── PadelField
│   ├── Reservation
│   ├── ReservationStatus
│   ├── User
│   └── Timeslot
│
├── Observer/
│   ├── Observer
│   ├── Subject
│   ├── EmailNotifier
│   ├── AdminDashboard
│   └── AuditLog
│
├── singleton/
│   └── ReservationManager
│
└── ui/
    ├── MainFrame
    ├── FieldSelectionScreen
    ├── SubtypeSelectionScreen
    ├── ReservationFormPanel
    ├── ReservationTableModel
    ├── FieldDrawingPanel
    └── SubtypeDrawingPanel
```

---

## Technologies Used

- Java Swing
- IntelliJ IDEA
- Object-Oriented Design Patterns

---
## Sources Used

- Course lecture slides and class materials (CIS3303 – Object-Oriented Design)


## Author

**Jose ArayaSancho**

**Sebastian Rodriguez**

**Yasir Dar**

New College of Florida

CIS3303 – Object-Oriented Design

---

## Version

**1.2.0** — December 2025
