import java.util.ArrayList;

public class Twitter {
	
	//ADD YOUR CODE BELOW HERE
	private MyHashTable<String,Tweet> authors;
	private MyHashTable<String,ArrayList<Tweet>> dates;
	private MyHashTable<String,Integer> wordFrequency;
	private ArrayList<String> stopWords;
	//ADD CODE ABOVE HERE 
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		this.authors = new MyHashTable<>(tweets.size());
		this.dates = new MyHashTable<>(tweets.size());
		int largerSize = tweets.size()*20;
		this.wordFrequency = new MyHashTable<>(largerSize);
		this.stopWords = stopWords;

		for (Tweet tweet: tweets){
			//authors
			if ((authors.get(tweet.getAuthor()) )!= null){
				boolean isLarger = tweet.compareTo(authors.get(tweet.getAuthor())) > 0;
				if (isLarger) this.authors.put(tweet.getAuthor(),tweet);
			}else {
				this.authors.put(tweet.getAuthor(),tweet);
			}

			//dates
			if (dates.get(tweet.getDateAndTime().substring(0,10)) != null){
				ArrayList<Tweet> aryListDayTime = dates.get(tweet.getDateAndTime().substring(0,10));
				aryListDayTime.add(tweet);
			}else {
				ArrayList<Tweet> aryListTweet = new ArrayList<>();
				aryListTweet.add(tweet);
				this.dates.put(tweet.getDateAndTime().substring(0,10),aryListTweet);
			}

			//wordFrequency
			MyHashTable<String,String> hashTable;
			hashTable = new MyHashTable<>(40);
			ArrayList<String> aryListWords;
			aryListWords = Twitter.getWords(tweet.getMessage().toLowerCase());

			for (String addWords : aryListWords) {
				hashTable.put(addWords, addWords);
			}

			for (HashPair<String,String> hashPair: hashTable) {
				if ((this.wordFrequency.get(hashPair.getKey().toLowerCase()))== null){
					String wordsToAdd = hashPair.getKey().toLowerCase();
					this.wordFrequency.put(wordsToAdd,1);
				}else {
					String wordsToAdd = hashPair.getKey().toLowerCase();
					Integer frequency = this.wordFrequency.get(wordsToAdd) + 1;
					this.wordFrequency.put(wordsToAdd, frequency);
				}
			}
		}
		//ADD CODE ABOVE HERE
	}
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE
		if (t == null) return;
		//authors
		if (this.authors.get(t.getAuthor()) != null){
			boolean isLarger = t.compareTo(this.authors.get(t.getAuthor()) )> 0;
			if (isLarger) this.authors.put(t.getAuthor(),t);
		}else {
			authors.put(t.getAuthor(),t);
		}
		//dates and times
		ArrayList<Tweet> dateTime;
		dateTime = this.dates.get(t.getDateAndTime().substring(0,10));
		if (dateTime != null){
			dateTime.add(t);
		}else {
			ArrayList<Tweet> aryListTweet = new ArrayList<>();
			aryListTweet.add(t);
			this.dates.get(t.getDateAndTime().substring(0,10)).add(t);
		}
		//word frequency
		MyHashTable<String,String> hashTable;
		hashTable = new MyHashTable<>(40);
		ArrayList<String> aryListWords;
		aryListWords = Twitter.getWords(t.getMessage().toLowerCase());

		for (String putWords : aryListWords) {
			hashTable.put(putWords, putWords);
		}

		for (HashPair<String,String> hashPair: hashTable) {
			if ((this.wordFrequency.get(hashPair.getKey().toLowerCase()))== null){
				String wordsToAdd = hashPair.getKey().toLowerCase();
				this.wordFrequency.put(wordsToAdd,1);
			}else {
				String wordsToAdd = hashPair.getKey().toLowerCase();
				Integer frequency = this.wordFrequency.get(wordsToAdd) + 1;
				this.wordFrequency.put(wordsToAdd, frequency);
			}
		}
		//ADD CODE ABOVE HERE 
	}
	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
        //ADD CODE BELOW HERE

    	return this.authors.get(author);
    	
        //ADD CODE ABOVE HERE 
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE
    	
    	return this.dates.get(date);
    	
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE
    	for (int i = 0; i < this.stopWords.size(); i++){
    		wordFrequency.remove(this.stopWords.get(i).toLowerCase());
		}

    	ArrayList<String> sortedAry = MyHashTable.fastSort(wordFrequency);
    	return sortedAry;
        //ADD CODE ABOVE HERE    	
    }
    
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

    

}
