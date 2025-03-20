import java.util.*;

import static java.lang.Math.abs;

public class MemoryAllocation {
    private Map<Integer, Boolean> partitionsSize = new HashMap<>();
    private Map<Integer, Integer> processesSize = new LinkedHashMap<>();
    private StringBuilder waitedProcess = new StringBuilder();

    public MemoryAllocation(Map<Integer, Boolean> partitionsSize, Map<Integer, Integer> processesSize){
        this.partitionsSize = partitionsSize;
        this.processesSize = processesSize;
    }


    public int firstFit(){
        int fragmentation = 0;
        for(Map.Entry<Integer, Integer> process :  processesSize.entrySet()){
            if(process.getValue() == -1){
                System.out.print(process.getKey() + " process selected ");
                for(Map.Entry<Integer, Boolean> partition : partitionsSize.entrySet()){
                    if(!partition.getValue() && partition.getKey() >= process.getKey()){
                        fragmentation += abs(partition.getKey() - process.getKey());
                        System.out.println(partition.getKey() + " partition selected! " + process.getKey());
                        processesSize.put(process.getKey(), partition.getKey());
                        partitionsSize.put(partition.getKey(), true);
                        break;
                    }
                }
            }

        }

        for(Map.Entry<Integer, Boolean> partition : partitionsSize.entrySet()){
            if(!partition.getValue()) fragmentation += partition.getKey();
        }
        for(Map.Entry<Integer, Integer> process : processesSize.entrySet()){
            //System.out.println(process.getKey());
            if(process.getValue() == -1) {
                System.out.println(process.getKey() + " waited ");
                waitedProcess.append(process.getKey()).append(" ");
            }
        }


        System.out.println("\nans: " + fragmentation);
        return fragmentation;
    }
    public int bestFit(){
        int fragmentation = 0;
        for(Map.Entry<Integer, Integer> process :  processesSize.entrySet()){
            if(process.getValue() == -1){
                System.out.print(process.getKey() + " process selected ");
                int minSufficientPartition = 10000000;
                boolean isFound = false;
                for(Map.Entry<Integer, Boolean> partition : partitionsSize.entrySet()){
                    if(!partition.getValue() && partition.getKey() >= process.getKey()){
                       if(partition.getKey() < minSufficientPartition){
                           minSufficientPartition = partition.getKey();
                           isFound = true;
                       }
                    }
                }

                if(isFound){
                    fragmentation += abs(minSufficientPartition - process.getKey());
                    partitionsSize.put(minSufficientPartition, true);
                    processesSize.put(process.getKey(), minSufficientPartition);
                    System.out.println(minSufficientPartition + " partition selected");
                }
            }
        }

        for(Map.Entry<Integer, Boolean> partition : partitionsSize.entrySet()){
            if(!partition.getValue()) fragmentation += partition.getKey();
        }
        for(Map.Entry<Integer, Integer> process : processesSize.entrySet()){
            if(process.getValue() == -1) {
                System.out.println(process.getKey() + " waited ");
                waitedProcess.append(process.getKey()).append(" ");
            }
        }


        System.out.println("\nans: " + fragmentation);
        return fragmentation;
    }
    public int worstFit(){
        int fragmentation = 0;
        for(Map.Entry<Integer, Integer> process :  processesSize.entrySet()){
            if(process.getValue() == -1){
                System.out.print(process.getKey() + " process selected ");
                int maxSufficientPartition = -10000000;
                boolean isFound = false;
                for(Map.Entry<Integer, Boolean> partition : partitionsSize.entrySet()){
                    if(!partition.getValue() && partition.getKey() >= process.getKey()){
                        if(partition.getKey() > maxSufficientPartition){
                            maxSufficientPartition = partition.getKey();
                            isFound = true;
                        }
                    }
                }

                if(isFound){
                    fragmentation += abs(maxSufficientPartition - process.getKey());
                    partitionsSize.put(maxSufficientPartition, true);
                    processesSize.put(process.getKey(), maxSufficientPartition);
                    System.out.println(maxSufficientPartition + " partition selected");
                }
            }
        }

        for(Map.Entry<Integer, Boolean> partition : partitionsSize.entrySet()){
            if(!partition.getValue()) fragmentation += partition.getKey();
        }
        for(Map.Entry<Integer, Integer> process : processesSize.entrySet()){
            if(process.getValue() == -1) {
                System.out.println(process.getKey() + " waited ");
                waitedProcess.append(process.getKey()).append(" ");
            }
        }

        System.out.println("\nans: " + fragmentation);
        return fragmentation;
    }

    public String getWaitedProcesses(){
        if(waitedProcess.isEmpty()) return "No waiting occurred!";
        return waitedProcess.toString();
    }

}
