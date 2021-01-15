import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets
    private int numBuckets;
    // load factor needed to check for rehashing
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets;

    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
        buckets = new ArrayList<>();
        this.numEntries = 0;
        this.numBuckets = initialCapacity;

        //creating an empty linkedlist in arraylist
        for (int i=0; i< numBuckets; i++){
            buckets.add(new LinkedList<>());
        }
        //ADD YOUR CODE ABOVE THIS
    }

    public int size() {
        return this.numEntries;
    }

    public boolean isEmpty() {
        return this.numEntries == 0;
    }

    public int numBuckets() {
        return this.numBuckets;
    }

    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }

    /**
     * Given a key, return the bucket position for the key.
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }

    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
        //get bucket index
        int bucketIndex = hashFunction(key);

    	LinkedList<HashPair<K, V>> head = buckets.get(bucketIndex);//get the head of the list
        int indexOfHead = 0;
        int size = head.size();
        V pre = null;

        //search for key in this list
        while (head != null){
            if (indexOfHead < size){
                HashPair<K,V> headHashPair = head.get(indexOfHead);
                if (headHashPair.getKey().equals(key)) {
                    pre = headHashPair.getValue();
                    headHashPair.setValue(value);
                    break;
                }else {
                    indexOfHead++;
                }
            }else {
                HashPair<K,V> hashPair = new HashPair<>(key, value);
                head.addLast(hashPair);
                numEntries++;
                double nE = this.numEntries;
                if((nE/this.numBuckets)>MAX_LOAD_FACTOR){
                    rehash();
                }
                break;
            }
        }

    	return pre;

        //  ADD YOUR CODE ABOVE HERE
    }

//    private int getBucketIndex (K key){
//        int hashCode = key.hashCode();
//        int index = hashCode % numBuckets;
//        return index;
//    }

    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */

    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
        if (((key != null) && !this.isEmpty())) {
            //get bucket index
            int bucketIndex = hashFunction(key);
            LinkedList<HashPair<K, V>> head = buckets.get(bucketIndex);//get the head of the list
            int indexOfHead = 0;

            while (true) {
                if (indexOfHead < head.size()) {
                    HashPair<K, V> hashPair = head.get(indexOfHead);
                    if (hashPair.getKey().equals(key)) {
                        return hashPair.getValue();
                    } else {
                        indexOfHead++;
                    }
                } else {
                    break;
                }
            }

        }
        return null;
        //ADD YOUR CODE ABOVE HERE
    }

    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1)
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
        int bucketIndex = hashFunction(key);
        LinkedList<HashPair<K, V>> head = buckets.get(bucketIndex);//get the head of the list
        int indexOfHead = 0;
        if (key == null) return null;

        while (true){
            if (indexOfHead < head.size()){
                HashPair<K,V> hashPair = head.get(indexOfHead);
                if (key.equals(hashPair.getKey())) {
                    numEntries--;
                    HashPair<K,V> pairToRemove = head.remove(indexOfHead);
                    return pairToRemove.getValue();
                }else {
                    indexOfHead++;
                }
            }else break;
        }
    	return null;

        //ADD YOUR CODE ABOVE HERE
    }


    /**
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
        //double the list
        int initialCapacity = numBuckets*2;
    	MyHashTable<K,V> doubleHashTable;
        doubleHashTable = new MyHashTable<>(initialCapacity);

        //add hashpair
    	for (int i=0; i<this.numBuckets; i++){
    	    for (int j=0; j<this.buckets.get(i).size(); j++){
    	        HashPair <K,V> addHashPair = this.buckets.get(i).get(j);
    	        K addKey = addHashPair.getKey();
    	        V addValue = addHashPair.getValue();
    	        doubleHashTable.put(addKey,addValue);
            }
        }
    	//refresh info
    	this.numBuckets = doubleHashTable.numBuckets;
    	this.numEntries = doubleHashTable.numEntries;
    	this.buckets = doubleHashTable.buckets;
        //ADD YOUR CODE ABOVE HERE
    }


    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */

    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
        ArrayList<K> keys = new ArrayList<>();
        if (isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < numBuckets; i++) {
                LinkedList<HashPair<K, V>> headLL = this.buckets.get(i);
                boolean ableToAdd = !headLL.isEmpty();
                if (ableToAdd) {
                    for (int j = 0; j < this.buckets.get(i).size(); j++) {
                        K keyToAdd = this.buckets.get(i).get(j).getKey();
                        keys.add(keyToAdd);
                    }
                }
            }
            return keys;
        }
        //ADD YOUR CODE ABOVE HERE

    }

    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE
        //Arraylist not working here
//        ArrayList<V> values = new ArrayList<>();
//        isEmpty();
//
//        for (int i=0; i < numBuckets; i++){
//            LinkedList<HashPair<K,V>> headLL = this.buckets.get(i);
//            boolean ableToAdd = (!headLL.isEmpty()) && !(headLL == null);
//            if (ableToAdd) {
//                for (int j=0; j < this.buckets.get(i).size(); j++){
//                    V valuesToAdd = this.buckets.get(i).get(j).getValue();
//                    values.add(valuesToAdd);
//                }
//            }
//        }
//    	return values;
        MyHashTable<V,V> values = new MyHashTable<>(numEntries);

        if (isEmpty()) {
            return null;
        } else {
            for (int i = 0; i < numBuckets; i++) {
                LinkedList<HashPair<K, V>> headLL = this.buckets.get(i);
                boolean ableToAdd = !headLL.isEmpty();
                if (ableToAdd) {
                    for (int j = 0; j < this.buckets.get(i).size(); j++) {
                        V valuesToAdd = this.buckets.get(i).get(j).getValue();
                        values.put(valuesToAdd, null);
                    }
                }
            }
            return values.keys();
        }
        //ADD CODE ABOVE HERE
    }


	/**
	 * This method takes as input an object of type MyHashTable with values that
	 * are Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 *
	 * The time complexity for this method is O(n^2), where n is the number
	 * of pairs in the map.
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }


	/**
	 * This method takes as input an object of type MyHashTable with values that
	 * are Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 *
	 * The time complexity for this method is O(n*log(n)), where n is the number
	 * of pairs in the map.
	 */

	// have inspired by merge sort algorithm from git
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        //ADD CODE BELOW HERE
    	ArrayList<HashPair<K,V>> sortedAry = new ArrayList<>();
    	ArrayList<K> sortedKeys = new ArrayList<>();

    	for (HashPair<K,V> hashPair : results){
    	    sortedAry.add(hashPair);
        }
        //sort here
        mergeSort(sortedAry.size()-1, 0, sortedAry, new ArrayList<>(sortedAry));

        for (HashPair<K, V> kvHashPair : sortedAry) {
            sortedKeys.add(kvHashPair.getKey());
        }

    	return sortedKeys;

    }

    private static <K,V extends Comparable<V>> void mergeSort
            (int large, int small, ArrayList<HashPair<K,V>> aryList1, ArrayList<HashPair<K,V>> aryList2){
        if (large > small) {
            int mid = small + (large - small)/2;
            int right = mid + 1;
            int left = small;

            MyHashTable.mergeSort(mid, small, aryList1, aryList2);
            MyHashTable.mergeSort(large, mid + 1, aryList1, aryList2);

            int i = small;
            while (i <= large) {
                aryList2.set(i, aryList1.get(i));
                i++;
            }

            while(true){
                if (right > large || left > mid) break;
                //compare left value and right value
                if (aryList2.get(left).getValue().compareTo(aryList2.get(right).getValue()) < 0 ) {
                    aryList1.set(small++, aryList2.get(right++));
                } else aryList1.set(small++, aryList2.get(left++));
            }

            while(mid >= left){
                aryList1.set(small++, aryList2.get(left++));
            }
        }
    }


        //ADD CODE ABOVE HERE




    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }

    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
        int i = 0;
    	private ArrayList<HashPair<K,V>> hashPairs = new ArrayList<>();

        //ADD YOUR CODE ABOVE HERE

    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
            for (LinkedList<HashPair<K, V>> headLL : buckets) {
                boolean ableToAdd = !headLL.isEmpty();
                if (ableToAdd) {
                    hashPairs.addAll(headLL);

                }
            }
            //ADD YOUR CODE ABOVE HERE
        }

        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
            return i < numEntries;
            //ADD YOUR CODE ABOVE HERE
        }

        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	HashPair<K,V> nextHashPair;
            nextHashPair = hashPairs.get(i);
            i++;
        	return nextHashPair;
            //ADD YOUR CODE ABOVE HERE
        }

    }
}
