# Parking
Java console application for managing a parking lot. 
The system supports vehicle entry and exit, automatic fee calculation, and multiple pricing strategies.
## Features
- Add and remove cars by their number
- Prevent duplicate parking
- Search for a parked car by its number

---
**Slot management**
- Show available and occupied parking slots
- Configurable parking size
- Automatically assigns the first available slot
- Ability to view currently parked cars in real time
---
**Payment calculation**
- Automatic fee calculation based on parking duration
- Multiple pricing strategies via the PricingCalculator interface:
  - `SimplePricingCalculator` - standard per-hour rate
  - `WeekendFreeCalculator` - parking is free on weekends
  - `LenientPricingCalculator` 
  - `NightDiscountCalculator` — discounted night parking rate
---
**Persistence**
- Parking state is stored in PostgreSQL
- Parking data is loaded from the database on application start
- Parking data is saved to the database on application exit
- Car records are removed from the database when a car exits the parking lot
---
### Console menu
- Console-based user menu
- Allows entering cars, exiting cars, checking parking status, searching cars, and generating reports
## Technical Overview

- Built with **Java SE 17+**
- Uses:
  - `LocalDateTime` and `Duration` for time management
  - `BigDecimal` for payment calculation
  - `record ParkingRecord` for immutable parking data
  - `Optional` for safe lookups in the `findCar` method
  - PostgreSQL for current parking state persistence
  - CSV file for parking history logging
  - HTML file generation for parking reports
- Uses JDBC for database interaction
- Exception handling for invalid input, duplicate parking, missing cars, and database errors
- Clean architecture following interface-based design

## 🖥️ User Menu

Welcome to Car Parking:
- 1 - Car Enter
- 2 - Car Exit
- 3 - Show Parking Status
- 4 - Show Parked Cars
- 5 - Find specified car
- 6 - Generate report to HTML file
- 0 - Close app
## Usage
## Example output:

- Waiting for input car number...
- Car parked successfully.
- Sorry, parking is already full (10/10 slots taken).
- Car not found. Please verify data and try again.
