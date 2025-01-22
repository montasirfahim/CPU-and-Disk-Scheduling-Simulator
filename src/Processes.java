public class Processes {
    int processId, proArrivalTime, proBurstTime;
    int remainingTime;
    int startTime = -1;
    int completionTime = -1;
    int waitingTime;
    int turnaroundTime;
    int responseTime;
    public Processes(int id, int arrTime, int burTime){
        this.processId = id;
        this.proArrivalTime = arrTime;
        this.proBurstTime = burTime;
        this.remainingTime = burTime;
    }

    public Processes(Processes original){ //copy constructor
        this.processId = original.processId;
        this.proArrivalTime = original.proArrivalTime;
        this.proBurstTime = original.proBurstTime;
        this.remainingTime = original.remainingTime;
        this.startTime = original.startTime;
        this.completionTime = original.completionTime;
        this.waitingTime = original.waitingTime;
        this.turnaroundTime = original.turnaroundTime;
        this.responseTime = original.responseTime;
    }

    public int getProcessId() {
        return processId;
    }

    public int getProArrivalTime() {
        return proArrivalTime;
    }

    public int getProBurstTime() {
        return proBurstTime;
    }

    public int getRemainingTime(){
        return remainingTime;
    }
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
    public int getCompletionTime(){
        return completionTime;
    }
    public int getTurnaroundTime(){
        return turnaroundTime;
    }
    public int getWaitingTime(){
        return waitingTime;
    }
    public int getResponseTime(){
        return responseTime;
    }


    public String toString(){
        return "Process ID: " + processId + ", Arrival Time: " + proArrivalTime + ", Burst Time: " + proBurstTime;
    }
}
