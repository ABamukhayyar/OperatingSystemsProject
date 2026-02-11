# CPU Scheduler & Memory Management Simulator

A Java-based Operating System simulator that models the **Long-Term Scheduler** (Job Scheduler) and **Short-Term Scheduler** (CPU Scheduler). The project demonstrates multi-threading, memory management constraints, and various process scheduling algorithms.

## 📌 Overview
This simulation reads a list of processes (jobs) from a file, loads them into memory if space permits, and schedules them for CPU execution using one of three user-selected algorithms. It visualizes the execution flow using a text-based Gantt Chart.

### Key Features
* **Multi-threaded Architecture:** Uses separate threads for loading jobs (`JobLoader`) and moving them to the Ready Queue (`JobManager`).
* **Memory Management:** Simulates a fixed system memory of **2048 MB**. Jobs are only moved to the Ready Queue if sufficient memory is available.
* **PCB Implementation:** Full Process Control Block implementation tracking Burst Time, Priority, Waiting Time, and Turnaround Time.
* **Gantt Chart Visualization:** Generates a timeline of process execution after completion.

## ⚙️ Scheduling Algorithms
The simulator supports the following algorithms:

1.  **FCFS (First-Come, First-Served):** Non-preemptive scheduling based on arrival time.
2.  **Round Robin (RR):** Preemptive scheduling with a fixed **Time Quantum of 7ms**.
3.  **Priority Scheduling:** Non-preemptive scheduling where the highest priority (lowest number or highest number depending on logic) executes first.
    * *Includes Starvation Detection:* alerts if a low-priority process waits too long while others are executed.

## 📂 Project Structure

| Class | Description |
| :--- | :--- |
| `CPUScheduler.java` | The main entry point. Initializes queues, starts threads, and handles user input. |
| `JobLoader.java` | Reads `job.txt`, parses process attributes, and validates data (Priority 1-8). |
| `JobManager.java` | Acts as the Long-Term Scheduler. Moves jobs from Job Queue $\to$ Ready Queue when memory is available. |
| `Scheduler.java` | Contains the logic for FCFS, Round Robin, and Priority algorithms. |
| `SystemCalls.java` | Simulates OS calls: Process Creation, Termination, and Memory Allocation/Deallocation. |
| `PCB.java` | Data structure representing a Process Control Block. |
| `job.txt` | Input file containing process data. |
