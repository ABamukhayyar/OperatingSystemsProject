import java.util.*;
import java.util.concurrent.BlockingQueue;
class JobManager implements Runnable {
    private BlockingQueue<PCB> jobQueue;
    private Queue<PCB> readyQueue;

    public JobManager(BlockingQueue<PCB> jobQueue, Queue<PCB> readyQueue) {
        this.jobQueue = jobQueue;
        this.readyQueue = readyQueue;
    }

    @Override
    public void run() {
        while (jobQueue.size() > 0) {
            
                PCB job = jobQueue.peek();
                if (SystemCalls.allocateMemory(job)) {
                    readyQueue.add(job);
                    job.numberOfProcessesAtArrival = readyQueue.size(); // How many are already loaded
                    job.numberOfFinishedProcessesAtArrival = Scheduler.executedProcessesCount;
                    SystemCalls.createProcess(job);
                    jobQueue.remove(job);
                } 
              
        }
    }
    
}
