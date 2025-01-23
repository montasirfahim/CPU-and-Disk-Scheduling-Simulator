import java.util.ArrayList;
import java.util.List;
public class PriorityScheduling {
    private final List<Processes> processes;
    private List<String> ganttPIDList = new ArrayList<>();
    private List<String> ganttPTimeList = new ArrayList<>();
    float priorityAWT = 0, priorityATAT = 0, priorityART = 0;

    public PriorityScheduling(List<Processes> processes) {
        this.processes = processes;
    }

    public void runPriorityScheduling() {
        int currentTime = 0;
        for (Processes process : processes) {
            if (process.startTime == -1) process.startTime = currentTime;
            int executionTime = process.getProBurstTime();
            currentTime += executionTime;
            process.completionTime = currentTime;
            process.turnaroundTime = process.completionTime;
            process.waitingTime = process.turnaroundTime - process.proBurstTime;
            process.responseTime = process.startTime - process.proArrivalTime;
            process.proArrivalTime = -1;

            priorityAWT += process.waitingTime;
            priorityATAT += process.turnaroundTime;
            priorityART += process.remainingTime;

            ganttPIDList.add("P" + process.getProcessId());
            ganttPTimeList.add(String.valueOf(currentTime));
        }

        priorityART /= processes.size();
        priorityATAT /= processes.size();
        priorityAWT /= processes.size();
    }

    public List<Processes> getProcesses() {
        return processes;
    }
    public String getGanttPID() {
        StringBuilder tmp = new StringBuilder();
        for (String s : ganttPIDList) {
            tmp.append(String.format("%-5s", s));
        }
        return tmp.toString();
    }

    public String getGanttPTime() {
        StringBuilder tmp = new StringBuilder();
        for (String s : ganttPTimeList) {
            tmp.append(String.format("%-5s", s));
        }
        return tmp.toString();
    }

}
