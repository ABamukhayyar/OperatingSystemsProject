import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
public class CPUScheduler {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCB> jobQueue = new LinkedBlockingQueue<>();
        Queue<PCB> readyQueue = new LinkedList<>();

        Thread jobLoaderThread = new Thread(new JobLoader(jobQueue));
        Thread JobManagerThread = new Thread(new JobManager(jobQueue, readyQueue));

        jobLoaderThread.start();
        jobLoaderThread.join();
        JobManagerThread.start();

        // Allow some time for jobs to be loaded

        Scheduler scheduler = new Scheduler(readyQueue, jobQueue);
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
           
        
        System.out.println("Choose a scheduling algorithm:");
        System.out.println("1. FCFS");
        System.out.println("2. Round Robin (Quantum = 7ms)");
        System.out.println("3. Priority Scheduling ");


        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                scheduler.runFCFS();
                break;
            case 2:
                scheduler.runRoundRobin(7);
                break;
            case 3:
                scheduler.runPriorityScheduling();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
     
        scanner.close();

    
    }
}
