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

- Choose a sport and field type (Soccer, Tennis, Padel)
- Select a field subtype (e.g., 5v5, Singles, With Roof, etc.)
- Pick date and time
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
- Maintaining a single list of reservations
- Coordinating notifications to observers

Why:
- Prevents data inconsistency
- Guarantees a single source of truth

---

### 🔹 Factory Pattern
Creates sport field objects dynamically based on user choice.

**Implementation:**
- `FieldFactory`

**Creates (by type or subtype):**
- `SoccerField`
- `TennisField`
- `PadelField`

Why:
- Centralizes creation logic
- UI does not depend on concrete classes
- Easy to add new field types

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

How it works:
1. Observers register with `ReservationManager`
2. On create/cancel, manager calls `notifyObservers()`
3. All observers update automatically

Why:
- Loose coupling
- Easy to add new notification channels

---

### 🔹 Command Pattern
Encapsulates user operations as command objects.

**Commands:**
- `ReserveCommand`
- `ModifyCommand`
- `CancelCommand`

**Invoker:**
- `CommandInvoker`

How it works:
- Commands wrap actions (`execute()`)
- Invoker triggers execution
- Result (e.g., created reservation) can be retrieved

Why:
- Actions as objects
- Enables logging, queueing, and future undo/redo

---

### 🔹 Decorator Pattern
Adds optional add-ons to reservations dynamically.

**Decorators:**
- `LightingDecorator` (+$10)
- `EquipmentDecorator` (+$8)
- `RefreshmentDecorator` (+$5)

**Core:**
- `ReservationCost`
- `BaseReservationCost`
- `AddOnDecorator`

Example:
```java
ReservationCost cost = new BaseReservationCost(field);
cost = new LightingDecorator(cost);
cost = new EquipmentDecorator(cost);
cost = new RefreshmentDecorator(cost);
```

Why:
- Stack features in any order
- No subclass explosion
- Easy extension 

---

### 🔹 Chain of Responsibility Pattern
Validates contact and reservation data through independent rules.

**Handlers (in order):**
- `RequiredFieldsHandler`
- `EmailFormatHandler`
- `PhoneFormatHandler`
- `DuplicateUserCheckHandler`

**Base Classes:**
- `BaseContactHandler`
- `ContactHandler`
- `ValidationResult`

Setup:
```java
ContactHandler chain = new RequiredFieldsHandler();
chain.setNext(new EmailFormatHandler())
     .setNext(new PhoneFormatHandler())
     .setNext(new DuplicateUserCheckHandler());

ValidationResult result = chain.handle(input);
```

Why:
- One responsibility per handler
- Stops at first failure
- Reorder/add rules easily

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
[EMAIL] Event=CREATED To=a@gmail.com -> v | Tennis | 2025-12-03 08:00-09:00 | $38.00 | CONFIRMED
[DASH]  CREATED -> v | Tennis | 2025-12-03 08:00-09:00 | $38.00 | CONFIRMED
[AUDIT] CREATED -> v | Tennis | 2025-12-03 08:00-09:00 | $38.00 | CONFIRMED
[CMD] Reserved -> v | Tennis | 2025-12-03 08:00-09:00 | $38.00 | CONFIRMED
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
│   └── (Other commands)
│
├── Decorator/
│   ├── ReservationCost
│   ├── BaseReservationCost
│   ├── AddOnDecorator
│   ├── LightingDecorator
│   ├── EquipmentDecorator
│   └── RefreshmentDecorator
│
├── factory/
│   ├── FieldFactory
│
├── model/
│   ├── SportType
│   ├── FieldSubtype
│   ├── Field
│   ├── SoccerField
│   ├── TennisField
│   ├── PadelField
│   ├── User
│   ├── Timeslot
│   ├── Reservation
│   └── ReservationStatus
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

- Java
- Java Swing
- IntelliJ IDEA
- Object-Oriented Design Patterns 

---

## Sources Used

- Course lecture slides and class materials (CIS3303 – Object-Oriented Design)
- Github copilot \

---

## Author

**Jose Araya-Sancho**  
**Sebastian Rodriguez**  
**Yasir Dar**

New College of Florida  
CIS3303 – Object-Oriented Design

---

## Version

**1.2.0** — December 2025
