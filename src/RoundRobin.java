import java.util.ArrayList;
import java.util.List;;

public class RoundRobin {
    private final int timeQuantum;
    private List<Processes> processQueue = new ArrayList<>();
    private List<Processes> processes = new ArrayList<>();
    private int totalTime = 0;
    float rrAWT = 0, rrATAT = 0, rrART = 0;

    public RoundRobin(List<Processes> processes, int timeQuantum){
        this.processes = processes;
        this.timeQuantum = timeQuantum;
    }

    public void runRoundRobin(){
        int minArrival = processes.getFirst().getProArrivalTime(); //first process in queue/list
        int lastIndexOfAddedProcesses = 0;
        for(int i = 0; i < processes.size(); i++){
            if(processes.get(i).getProArrivalTime() == minArrival){
                processQueue.add(processes.get(i));
                lastIndexOfAddedProcesses = i;
            }
        }

        int currentTime = 0;

        //until we reach end of queue(while queue may be larger in each iteration after adding some required processes)
        for(int index = 0; index < processQueue.size(); index++){
            Processes currentProcess = processQueue.get(index);
            if(currentProcess.getRemainingTime() > 0){  //check if process has remaining time to execute
                if(currentProcess.startTime == -1){
                    currentProcess.startTime = currentTime;  //start time for the process
                }

                //calculate the time to execute
                int executionTime = Math.min(currentProcess.getRemainingTime(), timeQuantum);
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - executionTime);
                currentTime += executionTime;

                //if current process is completed
                if(currentProcess.getRemainingTime() == 0){
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.proArrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.proBurstTime;
                    if(currentProcess.waitingTime < 0) currentProcess.waitingTime = 0;
                    currentProcess.responseTime = currentProcess.startTime - currentProcess.proArrivalTime;

                    System.out.println("Process " + currentProcess.getProcessId() +
                            " completed. Completion Time: " + currentProcess.completionTime +
                            ", StartTime: " + currentProcess.startTime +
                            ", Turnaround Time: " + currentProcess.turnaroundTime +
                            ", Waiting Time: " + currentProcess.waitingTime +
                            ", Response Time: " + currentProcess.responseTime); //debug
                }

                //now add processes who arrive within the current time
                for(int i = lastIndexOfAddedProcesses + 1; i < processes.size(); i++){
                    if(processes.get(i).getProArrivalTime() <= currentTime){
                        processQueue.add(processes.get(i));
                        lastIndexOfAddedProcesses = i;
                    }
                }

                //lastly, add currentProcess if it needs more time to complete
                if(currentProcess.getRemainingTime() > 0){
                    processQueue.add(currentProcess);
                }
            }
        }

        totalTime = currentTime; //Final time after all processes are executed
    }

    public List<Processes> getProcesses(){
        return processes;
    }

    public int getTotalTime(){
        return totalTime;
    }

    public void getAverageResult(){
        int totalProcess = 0;
        for(Processes process: processes){
            totalProcess++;
            rrAWT += process.getWaitingTime();
            rrATAT += process.getTurnaroundTime();
            rrART += process.getResponseTime();
        }

        rrAWT = rrAWT / totalProcess;
        rrATAT = rrATAT / totalProcess;
        rrART = rrART / totalProcess;
    }

    public void printProcessInfo(){//debugging purpose
        System.out.printf("%-10s %-10s %-10s %-15s %-15s %-15s%n",
                "Process", "Arrival", "Burst", "Completion", "Turnaround", "Waiting");
        for (Processes process : processes) {
            System.out.printf("%-10s %-10d %-10d %-15d %-15d %-15d%n",
                    process.getProcessId(),
                    process.getProArrivalTime(),
                    process.getProBurstTime(),
                    process.getCompletionTime(),
                    process.getTurnaroundTime(),
                    process.getWaitingTime());
        }
    }

}
