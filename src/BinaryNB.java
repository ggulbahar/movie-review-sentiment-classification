import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;



public class BinaryNB {
	static double posBinaryWordCount = 0.0;
	static double negBinaryWordCount = 0.0;
	static int[] test(File folder) {
		int[] posneg = {0,0};
		try {
			
			for ( File file : folder.listFiles(FileRead.filter)) {
				
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
				    	// I'll compute 2 probability.
				    	// probability of word in positive class | P(w|pos)
				    	double pp;
				    	if ( BernoulliNB.posWordDocCounts.containsKey(word) ) {    		
				    		pp = ( (double) BernoulliNB.posWordDocCounts.get(word) + 1 ) / 
				    				(posBinaryWordCount + (double) MultinomialNB.vocabulary.size());
				        }else {
				        	pp = ( 0 + 1 ) / 
				        			(posBinaryWordCount + (double) MultinomialNB.vocabulary.size());
				        }
				    	posProbability *= pp;
				    	
				    	// probability of word in negative class | P(w|neg)
				    	double pn; 
				    	if ( BernoulliNB.negWordDocCounts.containsKey(word) ) {
				    		pn = ( (double)BernoulliNB.negWordDocCounts.get(word) + 1 ) /
				    				(negBinaryWordCount + (double) MultinomialNB.vocabulary.size());
				        }else {
				        	pn = ( 0 + 1 ) / 
				        			(negBinaryWordCount + (double) MultinomialNB.vocabulary.size());
				        }
				    	negProbability *= pn;
				    	
				    	// In order to prevent number overflow
				    	double lowerLimit = Math.pow(10, -200);
						if(posProbability < lowerLimit || negProbability < lowerLimit) {
							posProbability *= Math.pow(10, 150);
							negProbability *= Math.pow(10, 150);
						}
				     }
					

				}
				if( posProbability > negProbability ) {
					posneg[0] ++;
				}else posneg[1] ++;
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return posneg;

	}
	

}
