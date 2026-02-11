import java.util.*;
class Scheduler {
    private LinkedList<PCB> readyQueue;
    private Queue<PCB> jobQueue;
    private List<GanttChartEntry> ganttChart = new ArrayList<>();
    private List<PCB> finishedProcceses = new ArrayList<>();
    public static int executedProcessesCount = 0;

    public Scheduler(Queue<PCB> readyQueue, Queue<PCB> jobQueue) {
        this.readyQueue = (LinkedList<PCB>) readyQueue;
        this.jobQueue = jobQueue;
    }

    public void runFCFS() {
        int currentTime = 0;
        while (readyQueue.size() > 0 || jobQueue.size() > 0) {
            PCB process = readyQueue.poll();
            if (process == null) {
                continue;
            }
            int startTime = currentTime;
            process.waitingTime = startTime;
            process.turnaroundTime = process.waitingTime + process.burstTime;
            currentTime += process.burstTime;
            int endTime = currentTime;
    
            ganttChart.add(new GanttChartEntry(process.processID, startTime, endTime));
    
            System.out.println("Process " + process.processID + " executed from " + startTime + " to " + endTime);
            SystemCalls.terminateProcess(process);
            finishedProcceses.add(process);
        SystemCalls.freeMemory(process);
        }
        printGanttChart();
        calculateAndPrintAverages();
        

    }
    

    public void runRoundRobin(int quantum) {
        int currentTime = 0;
        
        System.out.println("\nRound Robin Execution Order:");
        while (readyQueue.size() > 0 || jobQueue.size() > 0){
            PCB process = readyQueue.poll();
            if (process == null) {
                continue;
            }
            
            // Calculate waiting time since last execution
            if (process.lastExecutionTime != 0) {
                process.waitingTime += (currentTime - process.lastExecutionTime);
            } else {
                // First time, waiting is start - arrival (arrival = 0)
                process.waitingTime += currentTime;
            }
            
            int startTime = currentTime;
            
            if (process.burstTime > quantum) {
                process.burstTime -= quantum;
                currentTime += quantum;
                process.lastExecutionTime = currentTime;
                readyQueue.add(process);
            } else {
                currentTime += process.burstTime;
                process.turnaroundTime = currentTime;
                process.lastExecutionTime = currentTime;
                SystemCalls.terminateProcess(process);
                finishedProcceses.add(process);
                SystemCalls.freeMemory(process);
            }
            
            int endTime = currentTime;
            ganttChart.add(new GanttChartEntry(process.processID, startTime, endTime));
            
            System.out.println("Process " + process.processID + " executed from " + startTime + " to " + endTime);
           
        }
        printGanttChart();
        calculateAndPrintAverages();
    }
    
    

    public void runPriorityScheduling() {

        int currentTime = 0;
        System.out.println("\nPriority Scheduling Execution Order:");

        while (readyQueue.size() > 0 || jobQueue.size() > 0){
            if(readyQueue.size() > 0){
                readyQueue.sort(Comparator.comparingInt(p -> -p.priority)); // Highest priority first

            }

            PCB process = readyQueue.poll();
            if (process == null) {
                continue;
            }
        if (!process.executed) {
        if (executedProcessesCount - process.numberOfFinishedProcessesAtArrival > process.numberOfProcessesAtArrival) {
            System.out.println("Starvation detected: Process " + process.processID  + " executed after " + (executedProcessesCount-process.numberOfFinishedProcessesAtArrival) + " processes but arrived when " + process.numberOfProcessesAtArrival + " existed.");
        }
         }
         int startTime = currentTime;
         process.waitingTime = startTime;
        process.turnaroundTime = process.waitingTime + process.burstTime;
         currentTime += process.burstTime;
        int endTime = currentTime;

        ganttChart.add(new GanttChartEntry(process.processID, startTime, endTime));

         process.executed = true;
        executedProcessesCount++;

        System.out.println("Process " + process.processID + " executed from " + startTime + " to " + endTime);
        SystemCalls.terminateProcess(process);
        finishedProcceses.add(process);
        SystemCalls.freeMemory(process);
}
        printGanttChart();
        calculateAndPrintAverages();
    }
    
    private void printGanttChart() {
        System.out.println("\nGantt Chart:");
    
        // Print top row with process names
        for (GanttChartEntry entry : ganttChart) {
            System.out.print("|  P" + entry.processID + "  ");
        }
        System.out.println("|");
    
        // Print timeline numbers
        System.out.print("0");
        for (GanttChartEntry entry : ganttChart) {
            System.out.printf("%7d", entry.endTime);
        }
        System.out.println("\n");
    }
    private void calculateAndPrintAverages() {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int numberOfProcesses = finishedProcceses.size(); // or processList.size()
    
        // For FCFS and Priority Scheduling
        for (PCB process : finishedProcceses) {
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
        }
    
        double averageWaitingTime = (double) totalWaitingTime / numberOfProcesses;
        double averageTurnaroundTime = (double) totalTurnaroundTime / numberOfProcesses;
    
        System.out.printf("Average Waiting Time: %.2f ms\n", averageWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f ms\n", averageTurnaroundTime);
    }
    
}
    
    