public class Processes {
    int processId, proArrivalTime, proBurstTime;
    int remainingTime;
    int startTime = -1;
    int completionTime = -1;
    int waitingTime;
    int turnaroundTime;
    public  Processes(int id, int arrTime, int burTime){
        this.processId = id;
        this.proArrivalTime = arrTime;
        this.proBurstTime = burTime;
    }

    public int getProcessId() {
        return processId;
    }

    public int getArrivalTime() {
        return proArrivalTime;
    }

    public int getBurstTime() {
        return proBurstTime;
    }

    public int getRemainingTime(){
        return remainingTime;
    }
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String toString(){
        return "Process ID: " + processId + ", Arrival Time: " + proArrivalTime + ", Burst Time: " + proBurstTime;
    }
}
