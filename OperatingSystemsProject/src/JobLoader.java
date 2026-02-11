import java.io.*;
import java.util.concurrent.BlockingQueue;

class JobLoader implements Runnable {
    private BlockingQueue<PCB> jobQueue;

    public JobLoader(BlockingQueue<PCB> jobQueue) {
        this.jobQueue = jobQueue;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Abodi/Desktop/227 project/227project/src/job.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("[")) {
                    continue; // Skip empty lines or marker lines
                }

                String[] parts = line.split("[:;]");
                if (parts.length != 4) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                int id = Integer.parseInt(parts[0]);
                int burstTime = Integer.parseInt(parts[1]);
                int priority = Integer.parseInt(parts[2]);
                int memoryRequired = Integer.parseInt(parts[3]);
                if (priority < 1 || priority > 8) {
                    System.out.println("[Error] Invalid priority for job " + id + ". Priority must be between 1 and 8. Job skipped.");
                    continue; // Skip this job do not add it
                }

                PCB pcb = new PCB(id,burstTime,priority,memoryRequired);
                jobQueue.put(pcb);
                System.out.println("Loaded job: " + pcb.processID);
            }
            System.out.println("Finished loading all jobs.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
