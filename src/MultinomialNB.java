import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class MultinomialNB {
	

	static HashMap<String, Integer> posWordCounts = new HashMap< String, Integer>();
	static HashMap<String, Integer> negWordCounts = new HashMap< String, Integer>();
	static Set<String> vocabulary = new HashSet<String>();
	static int posTotalCount = 0;
	static int negTotalCount = 0;

	
	static int[] test(File folder) {

		int[] posneg = {0,0}; 
		try {
			
			for ( File file : folder.listFiles(FileRead.filter)) {
				
				String line="";
				Scanner scanner = new Scanner(new File(folder + "/" + file.getName()));
				// positive probability is initialized with the prior probability
				double posProbability = Math.log(FileRead.posDocCount / (FileRead.posDocCount + FileRead.negDocCount)); 
				// negative probability is initialized with the prior probability
				double negProbability = Math.log(FileRead.negDocCount / (FileRead.posDocCount + FileRead.negDocCount));
				while(scanner.hasNextLine()) {
					line = scanner.nextLine();
					line = line.replaceAll("[^a-zA-Z ]", "");
					StringTokenizer st = new StringTokenizer(line);
					while (st.hasMoreTokens()) {
				    	String word = st.nextToken();
				    	// I'll compute 2 probability.
				    	// probability of word in positive class | P(w|pos)
				    	double pp;
				    	if ( MultinomialNB.posWordCounts.containsKey(word) ) {    		
				    		pp = ( (double) MultinomialNB.posWordCounts.get(word) + 1 ) / ((double) posTotalCount + (double) vocabulary.size());
				        }else {
				        	pp = ( 0 + 1 ) / ((double)posTotalCount + (double)vocabulary.size());
				        }
				    	posProbability += Math.log(pp);
				    	
				    	// probability of word in negative class | P(w|neg)
				    	double pn; 
				    	if ( MultinomialNB.negWordCounts.containsKey(word) ) {
					pn = ( (double)MultinomialNB.negWordCounts.get(word) + 1 ) / ((double)negTotalCount + (double)vocabulary.size());
				        }else {
				        	pn = ( 0 + 1 ) / ((double)negTotalCount + (double)vocabulary.size());
				        }
				    	negProbability += Math.log(pn);
				    	
				    	// In order to prevent number overflow
				    	/*
				    	double lowerLimit = Math.pow(10, -300);
						if(posProbability < lowerLimit || negProbability < lowerLimit) {
							posProbability *= Math.pow(10, 250);
							negProbability *= Math.pow(10, 250);
						}
						*/
				     }
					

				}
				if(posProbability == 0);
				else if( posProbability > negProbability ) {
					posneg[0] ++;
				}else posneg[1] ++;
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return posneg;

	}
 
}
