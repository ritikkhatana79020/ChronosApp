## How to Run

1. **Compile the application**
   ```bash
   javac model/*.java service/*.java ChronosApp.java

## Supported Commands

| Command | Arguments | Description |
|--------|-----------|-------------|
| **PLACE_ORDER** `<id> <priority> <deadline> <fragile>` | Adds a new package to the pending queue<br>- `id`: Package ID<br>- `priority`: EXPRESS / STANDARD<br>- `deadline`: Epoch time in ms<br>- `fragile`: true/false |
| **REGISTER_RIDER** `<id> <fragile-capable> <rating>` | Registers a new delivery rider<br>- `id`: Rider ID<br>- `fragile-capable`: true/false<br>- `rating`: 0.0–5.0 |
| **UPDATE_RIDER_STATUS** `<id> <status>` | Updates rider’s availability<br>- `status`: AVAILABLE / ON_DELIVERY / OFFLINE |
| **ASSIGN** | No args | Assigns unassigned packages to matching riders |
| **COMPLETE_DELIVERY** `<packageId>` | Marks package as delivered and frees the rider |
| **REPORT_RIDER** `<riderId>` | Lists all packages delivered by rider in the last 24 hours |
| **REPORT_MISSED** | No args | Lists EXPRESS packages delivered after deadline |
| **AUDIT** | No args | Displays all status changes for all packages |
| **HELP** | No args | Displays list of supported commands |
| **EXIT** | No args | Exits the application |

## System Behavior & Assumptions
- **Priority Rules**:
  1. EXPRESS over STANDARD
  2. Sooner delivery deadline first
  3. Earlier order time if deadline matches

- **Fragile Items**:  
  Riders must be fragile-capable to handle fragile packages.

- **Audit Trail**:  
  Every transition (e.g., PENDING → ASSIGNED) is timestamped and stored.

- **Time Format**:  
  Use `System.currentTimeMillis()` or online epoch converters for deadlines.

- **Single-threaded**:  
  The system is designed for a single-thread, in-memory simulation only.


---

## 🙋 Author

**Ritik Khatana**  
📧 Email: [ritikkhatana79020@gmail.com](mailto:ritikkhatana79020@gmail.com)  
💻 GitHub: [@ritikkhatana79020](https://github.com/ritikkhatana79020)


