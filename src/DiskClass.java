import java.util.*;

import static java.lang.Math.abs;

public class DiskClass {
    private final int headPointer, rangeStart, rangeEnd;
    private final List<Integer> trackSequenceArray;
    private final List<Integer> headMovement = new ArrayList<>();
    private final StringBuilder directions = new StringBuilder();

    public DiskClass(int[] sequence, int head, int rangeStart, int rangeEnd){
        this.trackSequenceArray = new ArrayList<>();
        for(int num : sequence){
            this.trackSequenceArray.add(num);
        }
        this.headPointer = head;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    public int seekDistanceFCFS(){
        int total = 0;
        int prev = headPointer;
        headMovement.add(prev);
        for(int i = 0; i < trackSequenceArray.size(); i++){
            total += (abs(trackSequenceArray.get(i) - prev));
            prev = trackSequenceArray.get(i);
            headMovement.add(prev);
        }
        return total;
    }
    public int seekDistanceSSTF(){
        int total = 0, prev = headPointer;
        Map<Integer, Boolean> map = new HashMap<>();
        headMovement.add(prev);
        for(int i = 0; i < trackSequenceArray.size(); i++){
            map.put(trackSequenceArray.get(i), false);
        }
        for(int i = 0; i < trackSequenceArray.size(); i++){
            int mn = 10000000, ind = -1, currDistance = 0;
            for(int j = 0; j < trackSequenceArray.size(); j++){
                if(!map.get(trackSequenceArray.get(j))){
                    currDistance = abs(trackSequenceArray.get(j) - prev);
                    if(currDistance < mn){
                        mn = currDistance;
                        ind = j;
                    }
                }

            }
            System.out.println(prev + " " + trackSequenceArray.get(ind));
            total += mn;
            map.put(trackSequenceArray.get(ind), true); //taken
            prev = trackSequenceArray.get(ind);
            headMovement.add(prev);
        }

        System.out.println("total: " + total);

        System.out.println("Movement: " + headMovement);

        return total;
    }

    public int seekDistanceSCAN(){
        int total = 0;
        int prev = headPointer;
        trackSequenceArray.add(rangeEnd);
        trackSequenceArray.add(headPointer);
        Collections.sort(trackSequenceArray);
        int index = Collections.binarySearch(trackSequenceArray, headPointer);

        headMovement.add(prev);
        for(int i = index + 1; i < trackSequenceArray.size(); i++){
            total += (abs(trackSequenceArray.get(i) - prev));
            prev = trackSequenceArray.get(i);
            headMovement.add(prev);
            if(i == trackSequenceArray.size() - 1){
                for(int j = index - 1; j >= 0; j--){
                    total += (abs(prev - trackSequenceArray.get(j)));
                    prev = trackSequenceArray.get(j);
                    headMovement.add(prev);
                }
            }
        }

        System.out.println("seek: " + total);
        return total;
    }

    public int seekDistanceC_SCAN(){
        int total = 0;
        int prev = headPointer;
        trackSequenceArray.add(rangeStart);
        trackSequenceArray.add(rangeEnd);
        trackSequenceArray.add(headPointer);
        Collections.sort(trackSequenceArray);
        int index = Collections.binarySearch(trackSequenceArray, headPointer);

        headMovement.add(prev);
        for(int i = index + 1; i < trackSequenceArray.size(); i++){
            total += (abs(trackSequenceArray.get(i) - prev));
            prev = trackSequenceArray.get(i);
            headMovement.add(prev);
            if(i == trackSequenceArray.size() - 1){
                for(int j = 0; j < index; j++){
                    total += (abs(prev - trackSequenceArray.get(j)));
                    prev = trackSequenceArray.get(j);
                    headMovement.add(prev);
                }
            }
        }

        System.out.println("seek: " + total);
        return total;
    }

    public int seekDistanceLOOK(){
        int total = 0;
        int prev = headPointer;
        trackSequenceArray.add(headPointer);
        Collections.sort(trackSequenceArray);
        int index = Collections.binarySearch(trackSequenceArray, headPointer);

        headMovement.add(prev);
        for(int i = index + 1; i < trackSequenceArray.size(); i++){
            total += (abs(trackSequenceArray.get(i) - prev));
            prev = trackSequenceArray.get(i);
            headMovement.add(prev);
            if(i == trackSequenceArray.size() - 1){
                for(int j = index - 1; j >= 0; j--){
                    total += (abs(prev - trackSequenceArray.get(j)));
                    prev = trackSequenceArray.get(j);
                    headMovement.add(prev);
                }
            }
        }

        System.out.println("seek: " + total);
        return total;
    }
    public int seekDistanceC_LOOK(){
        int total = 0;
        int prev = headPointer;
        trackSequenceArray.add(headPointer);
        Collections.sort(trackSequenceArray);
        int index = Collections.binarySearch(trackSequenceArray, headPointer);

        headMovement.add(prev);
        for(int i = index + 1; i < trackSequenceArray.size(); i++){
            total += (abs(trackSequenceArray.get(i) - prev));
            prev = trackSequenceArray.get(i);
            headMovement.add(prev);
            if(i == trackSequenceArray.size() - 1){
                for(int j = 0; j < index; j++){
                    total += (abs(prev - trackSequenceArray.get(j)));
                    prev = trackSequenceArray.get(j);
                    headMovement.add(prev);
                }
            }
        }

        System.out.println("seek: " + total);
        return total;
    }

    public String getMovementDirections(){
        for(int i = 0; i < headMovement.size(); i++){
            directions.append(headMovement.get(i));
            if(i != headMovement.size() - 1)
                directions.append(" --> ");

        }
        return directions.toString();
    }
}
