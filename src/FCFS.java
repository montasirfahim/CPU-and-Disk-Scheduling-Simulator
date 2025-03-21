import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCFS {
    private final List<Processes> processes;
    float fcfsAWT = 0, fcfsATAT = 0, fcfsART = 0;
    private String gantString = "";
    private String gantTimeString = "";
    public FCFS(List<Processes> processes) {
        this.processes = processes;
    }

    public void runFCFS(){
        processes.sort(Comparator.comparing(Processes::getProArrivalTime));
        int currentTime = 0;
        for(Processes process : processes){
            int executionTime = process.getProBurstTime();
            if(process.startTime == -1) process.startTime = currentTime;
            currentTime += executionTime;
            process.completionTime = currentTime;
            process.turnaroundTime = process.completionTime - process.getProArrivalTime();
            process.waitingTime = process.turnaroundTime - process.getProBurstTime();
            process.responseTime = process.startTime - process.getProArrivalTime();


            gantString += String.format("%-5s", "P" + process.getProcessId());
            gantTimeString += String.format("%-5s", currentTime);
        }

        System.out.println(gantString);
        System.out.println(gantTimeString);
    }

    public void getAverageFCFSResult(){
        for(Processes process : processes){
            fcfsAWT += process.waitingTime;
            fcfsATAT += process.turnaroundTime;
            fcfsART += process.responseTime;
        }

        fcfsAWT = fcfsAWT / processes.size();
        fcfsATAT = fcfsATAT / processes.size();
        fcfsART = fcfsART / processes.size();
    }

    public List<Processes> getProcesses(){
        return processes;
    }

    public String getGantString(){
        return gantString;
    }
    public String getGantTimeString(){
        return gantTimeString;
    }

}
