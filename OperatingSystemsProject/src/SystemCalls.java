
public class SystemCalls {
    private static final int TOTAL_MEMORY = 2048;
    private static int availableMemory = TOTAL_MEMORY;


    public static void createProcess(PCB pcb) {
        System.out.println("[System Call] Creating process " + pcb.processID);
    }

    public static void terminateProcess(PCB pcb) {
        System.out.println("[System Call] Terminating process " + pcb.processID);
    }

    public synchronized static boolean allocateMemory(PCB pcb) {
        if (pcb.memoryRequired <= availableMemory) {
            availableMemory -= pcb.memoryRequired;
            System.out.println("[System Call] Memory allocated for process " + pcb.processID);
            return true;
        } else {
            return false;
        }
    }

    public synchronized static void freeMemory(PCB pcb) {
        System.out.println("[System Call] Releasing memory for process " + pcb.processID);
        availableMemory += pcb.memoryRequired;
    }
}
