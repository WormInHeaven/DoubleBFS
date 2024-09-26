import java.util.*;

public class Double_BFS {
    //Our 2 "nodes"
    private final int int1;
    private final int int2;

    //For testing purposes I am limiting int1 and int2 to be ints between 0 and 10,000
    //if you adjust these parameters make sure to adjust the range of ints in the if blocks
    //accordingly. By using ints to simulate nodes we can simulate an extremely large and
    //dense graph without having to construct and maintain one.
    public Double_BFS(int int1, int int2) {
        this.int1 = int1;
        this.int2 = int2;
    }

    //This method will return an Arraylist containing one of the shortest paths between
    //each node using parallel BFS to reduce the magnitude at which the exponential
    //rate of change grows in number nodes we must visit. For this proof of concept
    //we'll assume our 2 int nodes are related if; they are +-1 from each other,
    //they are */2 from each other, they are */5 from each other, that all integers are positive,
    //and that all edges are undirected and unweighted.
    public ArrayList<Integer> optimizedBFS() {
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> result = new ArrayList<>();
        int glueNode = -1;

        //This will serve as a boolean switch for the BFS algorithms. Once this value is flipped
        //the BFS should stop generating new nodes and immediately move to gluing our paths
        //together.
        boolean pathNotFound = true;

        //Special case handling
        if (int1 == int2) {
            result.add(int1);
            return result;
        }

        //Queues for the BFS algorithms
        Queue<Integer> queue1 = new LinkedList<>();
        Queue<Integer> queue2 = new LinkedList<>();
        queue1.offer(int1);
        queue2.offer(int2);

        //Adjacency Maps. Using parallel hashmaps we can cross-reference keys to determine
        //whether the other BFS has visited a node we've encountered while also storing
        //parent/child relationships between nodes in one data structure.
        HashMap<Integer, Integer> adjMap1 = new HashMap<>();
        HashMap<Integer, Integer> adjMap2 = new HashMap<>();

        //These entries will serve as our initial nodes with a parent value of -1 to denote roots
        adjMap1.put(int1, -1);
        adjMap2.put(int2, -1);

        //This is our umbrella loop, one iteration of this loop should constitute one iteration for each of
        //our inner BFS algorithms
        while (pathNotFound) {

            //Special case handling for if there is no path between nodes, prevents null exceptions.
            if (queue1.isEmpty() || queue2.isEmpty()) {
                return result;
            }

            int temp1 = queue1.poll();
            int temp2 = queue2.poll();

            //These if statements check to see if the new nodes fall into our domain and add them
            //to the appropriate adjacency map and queue if they haven't been visited yet.
            if (pathNotFound && !adjMap1.containsKey(temp1 + 1) && temp1 + 1 <= 50000) {
                adjMap1.put(temp1 + 1, temp1);
                queue1.offer(temp1 + 1);

                //If the node is contained in the other hashmap then we've found our path. We'll also
                //make a note of our node that connects the two BFS algorithms to use when gluing our
                //paths together. We run this if statement everytime we generate a new node because
                //it is more efficient than waiting for the right node to pop out of the queue.
                if (adjMap2.containsKey(temp1 + 1)) {
                    pathNotFound = false;
                    glueNode = temp1 + 1;
                }
            }
            if (pathNotFound && !adjMap1.containsKey(temp1 - 1) && temp1 - 1 > 0) {
                adjMap1.put(temp1 - 1, temp1);
                queue1.offer(temp1 - 1);

                if (adjMap2.containsKey(temp1 - 1)) {
                    pathNotFound = false;
                    glueNode = temp1 - 1;
                }
            }
            if (pathNotFound && !adjMap1.containsKey(temp1 * 2) && temp1 * 2 <= 50000) {
                adjMap1.put(temp1 * 2, temp1);
                queue1.offer(temp1 * 2);

                if (adjMap2.containsKey(temp1 * 2)) {
                    pathNotFound = false;
                    glueNode = temp1 * 2;
                }
            }
            if (pathNotFound && temp1 % 2 == 0 && !adjMap1.containsKey(temp1 / 2)) {
                adjMap1.put(temp1 / 2, temp1);
                queue1.offer(temp1 / 2);

                if (adjMap2.containsKey(temp1 / 2)) {
                    pathNotFound = false;
                    glueNode = temp1 / 2;
                }
            }
            if (pathNotFound && !adjMap1.containsKey(temp1 * 5) && temp1 * 5 <= 50000) {
                adjMap1.put(temp1 * 5, temp1);
                queue1.offer(temp1 * 5);

                if (adjMap2.containsKey(temp1 * 5)) {
                    pathNotFound = false;
                    glueNode = temp1 * 5;
                }
            }
            if (pathNotFound && temp1 % 5 == 0 && !adjMap1.containsKey(temp1 / 5)) {
                adjMap1.put(temp1 / 5, temp1);
                queue1.offer(temp1 / 5);

                if (adjMap2.containsKey(temp1 / 5)) {
                    pathNotFound = false;
                    glueNode = temp1 / 5;
                }
            }

            //Same block but for the second BFS
            if (pathNotFound && !adjMap2.containsKey(temp2 + 1) && temp2 + 1 <= 50000) {
                adjMap2.put(temp2 + 1, temp2);
                queue2.offer(temp2 + 1);

                if (adjMap1.containsKey(temp2 + 1)) {
                    pathNotFound = false;
                    glueNode = temp2 + 1;
                }
            }
            if (pathNotFound && !adjMap2.containsKey(temp2 - 1) && temp2 - 1 > 0) {
                adjMap2.put(temp2 - 1, temp2);
                queue2.offer(temp2 - 1);

                if (adjMap1.containsKey(temp2 - 1)) {
                    pathNotFound = false;
                    glueNode = temp2 - 1;
                }
            }
            if (pathNotFound && !adjMap2.containsKey(temp2 * 2) && temp2 * 2 <= 50000) {
                adjMap2.put(temp2 * 2, temp2);
                queue2.offer(temp2 * 2);

                if (adjMap1.containsKey(temp2 * 2)) {
                    pathNotFound = false;
                    glueNode = temp2 * 2;
                }
            }
            if (pathNotFound && temp2 % 2 == 0 && !adjMap2.containsKey(temp2 / 2)) {
                adjMap2.put(temp2 / 2, temp2);
                queue2.offer(temp2 / 2);

                if (adjMap1.containsKey(temp2 / 2)) {
                    pathNotFound = false;
                    glueNode = temp2 / 2;
                }
            }
            if (pathNotFound && !adjMap2.containsKey(temp2 * 5) && temp2 * 5 <= 50000) {
                adjMap2.put(temp2 * 5, temp2);
                queue2.offer(temp2 * 5);

                if (adjMap1.containsKey(temp2 * 5)) {
                    pathNotFound = false;
                    glueNode = temp2 * 5;
                }
            }
            if (pathNotFound && temp2 % 5 == 0 && !adjMap2.containsKey(temp2 / 5)) {
                adjMap2.put(temp2 / 5, temp2);
                queue2.offer(temp2 / 5);

                if (adjMap1.containsKey(temp2 / 5)) {
                    pathNotFound = false;
                    glueNode = temp2 / 5;
                }
            }
        }
        //Once we've exited the previous while loop we should have 2 adjacency maps
        //and the node that connects the two paths. All we need to do from here is
        //glue the paths together.
        ArrayList<Integer> list = new ArrayList<>();
        int x = glueNode;
        while (x != -1) {
            list.add(x);
            x = adjMap1.get(x);
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        while ((adjMap2.get(glueNode) != -1)) {
            result.add(adjMap2.get(glueNode));
            glueNode = adjMap2.get(glueNode);
        }
        long endTime = System.currentTimeMillis();

        //Subtract 1 because glueNode is in both adjacency maps and gets counted twice
        System.out.println("This algorithm visited " + (adjMap1.size() + adjMap2.size() - 1) + " nodes.");
        System.out.println("And took " + (endTime - startTime) + "ms to execute using the double BFS method.");
        return result;
    }

    //The control method, this will be a traditional 1 root BFS in the same style as the previous method.
    //Use this to compare execution time and nodes visited between the two methods.
    public ArrayList<Integer> singleBFS(){
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> result = new ArrayList<>();
        boolean pathNotFound = true;
        Queue<Integer> queue = new LinkedList<>();
        HashMap<Integer, Integer> adjMap = new HashMap<>();
        queue.offer(int1);
        adjMap.put(int1, -1);

        while (pathNotFound){
            if (int1 == int2){
                result.add(int1);
                return result;
            }
            int temp = queue.poll();

            if (pathNotFound && !adjMap.containsKey(temp + 1) && temp + 1 <= 50000){
                adjMap.put(temp + 1, temp);
                queue.offer(temp + 1);

                if(temp + 1 == int2){
                    pathNotFound = false;
                }
            }

            if (pathNotFound && !adjMap.containsKey(temp - 1) && temp - 1 >= 0){
                adjMap.put(temp - 1, temp);
                queue.offer(temp - 1);

                if(temp - 1 == int2){
                    pathNotFound = false;
                }
            }

            if (pathNotFound && !adjMap.containsKey(temp * 2) && temp * 2 <= 50000){
                adjMap.put(temp * 2, temp);
                queue.offer(temp * 2);

                if(temp * 2 == int2){
                    pathNotFound = false;
                }
            }

            if (pathNotFound && temp % 2 == 0 && !adjMap.containsKey(temp / 2)){
                adjMap.put(temp / 2, temp);
                queue.offer(temp / 2);

                if(temp / 2 == int2){
                    pathNotFound = false;
                }
            }

            if (pathNotFound && !adjMap.containsKey(temp * 5) && temp * 5 <= 50000){
                adjMap.put(temp * 5, temp);
                queue.offer(temp * 5);

                if(temp * 5 == int2){
                    pathNotFound = false;
                }
            }

            if (pathNotFound && temp % 5 == 0 && !adjMap.containsKey(temp / 5)){
                adjMap.put(temp / 5, temp);
                queue.offer(temp / 5);

                if(temp / 5 == int2){
                    pathNotFound = false;
                }
            }
        }
        int x = int2;
        while(x != -1){
            result.add(x);
            x = adjMap.get(x);
        }
        Collections.reverse(result);
        long endTime = System.currentTimeMillis();
        System.out.println("This algorithm visited " + adjMap.size() + " nodes.");
        System.out.println("And took " + (endTime - startTime) + "ms to execute using the single BFS method.");
        return result;
    }
}
