# Parking
Java console application for managing a parking lot. The system supports vehicle entry and exit, automatic fee calculation, data persistence via CSV files, and multiple pricing strategies.
## Features
- Add and remove cars by their number
- Prevent duplicate parking
---
**Slot management**
- Show available and occupied parking slots
- Configurable parking size
---
**Payment calculation**
- Automatic fee calculation based on parking duration
- Multiple pricing strategies via the PricingCalculator interface:
  - SimplePricingCalculator — standard per-hour rate
  - WeekendFreeCalculator — parking is free on weekends
  - LenientPricingCalculator
---
**Persistence**
- Automatically saves parking state to a CSV file
- Loads saved data on application start
---
**Utilities**
- Search for a parked car by its number
- Console-based user menu
## Technical Overview

- Built with **Java SE 17+**
- Uses:
    - `LocalDateTime` and `Duration` for time management
    - `record ParkingRecord` for immutable data storage
    - `Optional` for safe lookups (`findCar` method)
    - CSV files for persistent storage
- Exception handling for invalid or duplicate input
- Clean architecture following interface-based design

## 🖥️ User Menu

Welcome to Car Parking:
- 1 - Car Enter
- 2 - Car Exit
- 3 - Show Parking Status
- 4 - Find specified car
- 0 - Close app
## Usage
## Example output:

- Waiting for input car number...
- Car added successfully.
- Sorry, parking is already full (10/10 slots taken).
- Car not found, please check input data one more time.
