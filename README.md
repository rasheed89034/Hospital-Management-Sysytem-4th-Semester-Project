# Hospital-Management-Sysytem-4th-Semester-Project

# 🏥 Smart Hospital Management System (DSA-Based)

A comprehensive, desktop-based **Hospital Management System** built using **Java Swing** for the frontend GUI and optimized using core **Data Structures & Algorithms (DSA)** to manage real-world healthcare workflows efficiently.

---

## 🚀 Key Features & DSA Implementation

This project bridges the gap between theoretical data structures and real-world implementation. Each hospital department is powered by a specific, optimized data structure:

| Department / Feature | Data Structure Used | Purpose & Algorithm |
| :--- | :--- | :--- |
| **Emergency Room (ER)** | `PriorityQueue (Min-Heap)` | Automatically triages patients based on condition severity (1-5). High-severity patients are treated first. |
| **Appointment Scheduling**| `LinkedList` | Manages regular patient queues using FIFO (First-In, First-Out) logic and allows efficient emergency slot insertions. |
| **Patient Records** | `Binary Search Tree (BST)` | Permanent storage that enables efficient $O(\log n)$ searching, inserting, and sorted (In-order) retrieval of patient profiles. |
| **Treatment History** | `Stack` | Tracks doctor visits using LIFO (Last-In, First-Out) structure, enabling seamless "Undo" features for accidental entries. |
| **Ward & Bed Allocation** | `2D Array (Matrix)` | Maps a physical layout of hospital floors and beds to efficiently assign, discharge, and track room occupancy. |

---

## 🛠️ Project Architecture & Modules

*   **`Patient.java`**: The core data model storing critical info like Patient ID, Name, Severity, Condition, and Contact details.
*   **`ERManager.java`**: Implements emergency triaging using a Custom Java `PriorityQueue` with a severity comparator.
*   **`AppointmentManager.java`**: Handles scheduling, queue counts, and cancellations using Java's standard `LinkedList`.
*   **`RecordManager.java`**: A custom-built **Binary Search Tree** from scratch to handle lightning-fast lookups by Patient ID.
*   **`HistoryManager.java`**: Uses a `Stack` to store patient treatment history with custom LIFO sorting capabilities.
*   **`WardManager.java`**: Manages matrix-based bed tracking system (Floor × Bed grid layout).
*   **`MainDashboard.java`**: A modern, interactive **Java Swing GUI** featuring cross-platform look-and-feel adjustments and a real-time system activity terminal.

---

## 🖥️ UI / Visual Features

*   **Real-time System Logs**: A dedicated green-on-black console panel inside the GUI that live-tracks every backend operation.
*   **Modular Tabbed Interface**: Separate dedicated viewports for ER, Appointments, Tree Searching, History, and Wards.
*   **User-Friendly Forms**: Built-in validation checks to handle numeric inputs safely without system crashes.

---
