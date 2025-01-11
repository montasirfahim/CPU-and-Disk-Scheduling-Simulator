import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobin {
    private final int timeQuantum;
    private Queue<Processes> processQueue = new LinkedList<>();
    private List<Processes> processes = new ArrayList<>();
    private int totalTime = 0;

    public RoundRobin(List<Processes> processes, int timeQuantum) {
        this.processes = processes;
        this.timeQuantum = timeQuantum;
    }

    public void runRoundRobin() {
        // Add all processes to the queue
        processQueue.addAll(processes);

        int currentTime = 0;
        while (!processQueue.isEmpty()) {
            Processes currentProcess = processQueue.poll();
            // Check if process has remaining time to execute
            if (currentProcess.getRemainingTime() > 0) {
                // Start time for the process
                if (currentProcess.startTime == -1) {
                    currentProcess.startTime = currentTime;
                }

                // Calculate the time to execute
                int executionTime = Math.min(currentProcess.getRemainingTime(), timeQuantum);
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - executionTime);
                currentTime += executionTime;

                // If the process is completed
                if (currentProcess.getRemainingTime() == 0) {
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.proArrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.proBurstTime;
                } else {
                    processQueue.add(currentProcess); // Re-add process to queue if not finished
                }
            }
        }

        totalTime = currentTime; // Final time after all processes are executed
    }

    public List<Processes> getProcesses() {
        return processes;
    }

    public int getTotalTime() {
        return totalTime;
    }

}
