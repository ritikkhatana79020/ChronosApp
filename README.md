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


## Algorithms, Data Structures, and Design Patterns Used

### 1. PriorityQueue (Min-Heap) for Dispatch Order

- **Java Class Used:** `PriorityQueue<Package>`
- **Why:** To always pick the highest-priority package in `O(log n)` time.
- **Comparator Logic:**
  - EXPRESS packages are prioritized over STANDARD.
  - If both are the same, the one with the earlier **deadline** is prioritized.
  - If deadlines match, the one with the earlier **order time** is prioritized.

```java
PriorityQueue<Package> packageQueue = new PriorityQueue<>((a, b) -> {
    if (a.getPriority() != b.getPriority()) {
        return a.getPriority() == Priority.EXPRESS ? -1 : 1;
    }
    if (a.getDeadline() != b.getDeadline()) {
        return Long.compare(a.getDeadline(), b.getDeadline());
    }
    return Long.compare(a.getOrderTime(), b.getOrderTime());
});
```

### 2. Greedy Algorithm for Rider Assignment

- **Approach:** Select the first available and eligible rider for each pending package.
- **Why:** It's a fast and straightforward strategy in real-time systems to reduce latency, even if it's not globally optimal.

### 3. Object-Oriented Design Patterns

- **Encapsulation:**  Classes like Rider, Package, and AuditEvent encapsulate their state and behavior.
- **Separation of Concerns:** ll dispatch logic is isolated in the DispatchCenter service class.
- **Extensibility:** New rules, filters, or scoring logic can be easily added without impacting core flow.

### 4. Other Data structure and Algos used:
- HashMap for Constant-Time Access
- ArrayList for Ordered Audit Trail
- Filtering with Java Streams
---

## 🙋 Author

**Ritik Khatana**  
📧 Email: [ritikkhatana79020@gmail.com](mailto:ritikkhatana79020@gmail.com)  
💻 GitHub: [@ritikkhatana79020](https://github.com/ritikkhatana79020)


