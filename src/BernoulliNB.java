import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class BernoulliNB {

	static HashMap<String, Integer> posWordDocCounts = new HashMap< String, Integer>();
	static HashMap<String, Integer> negWordDocCounts = new HashMap< String, Integer>();
	

	final static double NUMBER_OF_CLASSES = 2.0;


	static int[] test(File folder) {

		int[] posneg = {0,0}; 
		try {
			
			for ( File file : folder.listFiles(FileRead.filter)) {
				Set<String> documentWords = new HashSet<String>();
				String line="";
				Scanner scanner = new Scanner(new File(folder + "/" + file.getName()));
				// positive probability is initialized with the prior probability
				double posProbability = FileRead.posDocCount / (FileRead.posDocCount + FileRead.negDocCount); 
				// negative probability is initialized with the prior probability
				double negProbability = FileRead.negDocCount / (FileRead.posDocCount + FileRead.negDocCount);
				while(scanner.hasNextLine()) {
					line = scanner.nextLine();
					line = line.replaceAll("[^a-zA-Z ]", "");
					StringTokenizer st = new StringTokenizer(line);
					while (st.hasMoreTokens()) {
				    	String word = st.nextToken();
				    	double pp; // pos probability factor
				    	double pn; // neg probability factor
				    	
				    	if(!documentWords.contains(word)) {
				    		if ( BernoulliNB.posWordDocCounts.containsKey(word) ) {				    		
					    		pp = ((double) BernoulliNB.posWordDocCounts.get(word) + 1 ) / (FileRead.posDocCount + NUMBER_OF_CLASSES);
					        }else {
					        	pp = ( 0 + 1 ) / (FileRead.posDocCount + NUMBER_OF_CLASSES);
					        }
					    	posProbability *= pp;
					    	
					    	if(BernoulliNB.negWordDocCounts.containsKey(word)) {
					    		pn = ((double) BernoulliNB.negWordDocCounts.get(word) + 1 ) / (FileRead.negDocCount + NUMBER_OF_CLASSES);
					        }else {
					        	pn = ( 0 + 1 ) / (FileRead.negDocCount + NUMBER_OF_CLASSES);
					        }
					    	negProbability *= pn;
					    	documentWords.add(word);
					    	
					    	// In order to prevent number overflow
					    	double lowerLimit = Math.pow(10, -200);
							if(posProbability < lowerLimit || negProbability < lowerLimit) {
								posProbability *= Math.pow(10, 150);
								negProbability *= Math.pow(10, 150);
							}
				    	}
				     }
				}
				
				// to compute the probability of words that are not in the test document
				for (String w : MultinomialNB.vocabulary) {
					if(documentWords.contains(w)) continue;
					double pp; // pos probability factor
			    	double pn;  // neg probability factor
			    	// for pos probability
					if ( BernoulliNB.posWordDocCounts.containsKey(w) ) {				    		
			    		pp = ((double) BernoulliNB.posWordDocCounts.get(w) + 1 ) / (FileRead.posDocCount + NUMBER_OF_CLASSES);
			        }else {
			        	pp = ( 0 + 1 ) / (FileRead.posDocCount + NUMBER_OF_CLASSES);
			        }
			    	posProbability *= (1.0 - pp);
			    	
			    	// for neg probability
			    	if(BernoulliNB.negWordDocCounts.containsKey(w)) {
			    		pn = ((double) BernoulliNB.negWordDocCounts.get(w) + 1 ) / (FileRead.negDocCount + NUMBER_OF_CLASSES);
			        }else {
			        	pn = ( 0 + 1 ) / (FileRead.negDocCount + NUMBER_OF_CLASSES);
			        }
			    	negProbability *= (1 - pn);
			    	
			    	// In order to prevent number overflow
			    	double lowerLimit = Math.pow(10, -300);
					if(posProbability < lowerLimit || negProbability < lowerLimit) {
						posProbability *= Math.pow(10, 250);
						negProbability *= Math.pow(10, 250);
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
