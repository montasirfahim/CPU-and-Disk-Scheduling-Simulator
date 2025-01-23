import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class ShortestJobFirst {
    private final List<Processes> processes;
    private List<String> ganttPIDList = new ArrayList<>();
    private List<String> ganttPTimeList = new ArrayList<>();
    float sjfAWT = 0, sjfATAT = 0, sjfART = 0;

    public ShortestJobFirst(List<Processes> processes){
        this.processes = processes;
    }

    public void runShortestJobFirst(){
        int currentTime = processes.getFirst().getProBurstTime();
        processes.getFirst().completionTime = currentTime - processes.getFirst().proArrivalTime;
        processes.getFirst().turnaroundTime = processes.getFirst().completionTime;
        processes.getFirst().waitingTime = processes.getFirst().turnaroundTime - processes.getFirst().proBurstTime;
        processes.getFirst().responseTime = processes.getFirst().startTime - processes.getFirst().proArrivalTime;
        processes.getFirst().startTime = currentTime;

        sjfAWT += processes.getFirst().waitingTime;
        sjfATAT += processes.getFirst().turnaroundTime;
        sjfART += processes.getFirst().responseTime;

        ganttPIDList.add("P" + processes.getFirst().getProcessId());
        ganttPTimeList.add(String.valueOf(currentTime));

        int firstProcessID = processes.getFirst().getProcessId();
        processes.sort(Comparator.comparing(process -> process.proBurstTime));

        for(Processes process : processes){
            if(process.getProcessId() != firstProcessID){
                if(process.startTime == -1) process.startTime = currentTime;
                int executionTime = process.getProBurstTime();
                currentTime += executionTime;
                process.completionTime = currentTime;
                process.turnaroundTime = process.completionTime - process.proArrivalTime;
                process.waitingTime = process.turnaroundTime - process.proBurstTime;
                process.responseTime = process.startTime - process.proArrivalTime;

                sjfAWT += process.waitingTime;
                sjfATAT += process.turnaroundTime;
                sjfART += process.remainingTime;

                ganttPIDList.add("P" + process.getProcessId());
                ganttPTimeList.add(String.valueOf(currentTime));
            }
        }

        sjfART /= processes.size();
        sjfAWT /= processes.size();
        sjfATAT /= processes.size();

    }

    public List<Processes> getProcesses(){
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
