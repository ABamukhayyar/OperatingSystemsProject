class PCB {
    int processID;
    int burstTime;
    int priority;
    int memoryRequired;
    int waitingTime = 0;
    int turnaroundTime = 0;
    long lastExecutionTime = 0; 
    int numberOfProcessesAtArrival = 0;
    int numberOfFinishedProcessesAtArrival = 0; 
    boolean executed = false;


    
    public PCB(int processID, int burstTime, int priority, int memoryRequired) {
        this.processID = processID;
        this.burstTime = burstTime;
        this.priority = priority;
        this.memoryRequired = memoryRequired;
    }
    
}
