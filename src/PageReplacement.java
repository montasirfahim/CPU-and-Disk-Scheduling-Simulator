import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageReplacement {
    private final int frameSize;
    private List<Integer> referenceString;

    public PageReplacement(List<Integer> referenceString, int frameSize) {
        this.referenceString = referenceString;
        this.frameSize = frameSize;
    }

    public int hitOfFIFO(){
        Map<Integer, Integer> pageFrame = new LinkedHashMap<>();
        int hitCount = 0;
        for(int i = 0; i < referenceString.size(); i++){
            if(pageFrame.size() < frameSize){
                if(pageFrame.containsKey(referenceString.get(i))){
                    hitCount++;
                }
                else{
                    pageFrame.put(referenceString.get(i), i);
                }
            }
            else if(pageFrame.size() == frameSize){
                if(pageFrame.containsKey(referenceString.get(i))){
                    hitCount++;
                }
                else{
                    int oldestKey = pageFrame.entrySet().iterator().next().getKey();
                    pageFrame.remove(oldestKey);
                    System.out.println("removedKey: " + oldestKey + " at step: " + (i + 1));
                    pageFrame.put(referenceString.get(i), i);
                }
            }
        }

        System.out.println("hit: " + hitCount);
        return hitCount;
    }
    public int hitOfLRU(){
        Map<Integer, Integer> pageFrame = new LinkedHashMap<>(frameSize + 1, 0.75f, true);
        int hitCount = 0;
        for(int i = 0; i < referenceString.size(); i++){
            if(pageFrame.size() < frameSize){
                if(pageFrame.containsKey(referenceString.get(i))){
                    hitCount++;
                    pageFrame.put(referenceString.get(i), i);
                }
                else{
                    pageFrame.put(referenceString.get(i), i);
                }
            }
            else if(pageFrame.size() == frameSize){
                if(pageFrame.containsKey(referenceString.get(i))){
                    hitCount++;
                    pageFrame.put(referenceString.get(i), i);
                }
                else{
                    int leastUsedKey = pageFrame.entrySet().iterator().next().getKey();
                    pageFrame.remove(leastUsedKey);
                    System.out.println("removedKey: " + leastUsedKey + " at step: " + (i + 1));
                    pageFrame.put(referenceString.get(i), i);
                }
            }
        }

        System.out.println("hit: " + hitCount);
        return hitCount;
    }
    public int hitOfOptimal(){
        int hitCount = 0;
        List<Integer> pageFrame = new ArrayList<>();

        for(int i = 0; i < referenceString.size(); i++){
            int currentPage = referenceString.get(i);

            if(pageFrame.contains(currentPage)){
                hitCount++;
                continue;
            }

            if(pageFrame.size() < frameSize){
                pageFrame.add(currentPage);
            }
            else{
                //Optimal replacement strategy to find the page to replace
                int farthestIndex = -1, pageToReplace = -1;

                for(int j = 0; j < pageFrame.size(); j++){
                    int page = pageFrame.get(j);

                    //Find the next occurrence of the page
                    int nextIndex = Integer.MAX_VALUE;
                    for(int k = i + 1; k < referenceString.size(); k++) {
                        if(referenceString.get(k) == page) {
                            nextIndex = k;
                            break;
                        }
                    }

                    //If a page is not found in the future, replace it
                    if(nextIndex == Integer.MAX_VALUE) {
                        pageToReplace = page;
                        break;
                    }

                    //Find the page farthest in the future
                    if(nextIndex > farthestIndex) {
                        farthestIndex = nextIndex;
                        pageToReplace = page;
                    }
                }

                //Replace the page in the frame
                pageFrame.remove((Integer) pageToReplace);
                pageFrame.add(currentPage);
            }
        }

        System.out.println("hit: " + hitCount);
        return hitCount;
    }

}
