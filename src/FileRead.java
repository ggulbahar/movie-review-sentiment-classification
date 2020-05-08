import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class FileRead {
	static FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
           return !pathname.isHidden();
        }
    };
	static double posDocCount;
	static double negDocCount;  
	
	static void readPos() {
		try {
			File posFolder = new File("train/pos");
			posDocCount = posFolder.listFiles(filter).length;	
			for (File file : posFolder.listFiles(filter)) {
				Set<String> documentWords = new HashSet<String>();
				String line="";
				Scanner scanner = new Scanner(new File("train/pos/" + file.getName()));
				while(scanner.hasNextLine()) {
					line = scanner.nextLine();
					line = line.replaceAll("[^a-zA-Z ]", "");
					StringTokenizer st = new StringTokenizer(line);
					while (st.hasMoreTokens()) {
				    	String word = st.nextToken();
				    	
				    	// For Multinomial NB
				    	int countMultinomial;
				    	if ( MultinomialNB.posWordCounts.containsKey(word) ) {
				    		countMultinomial = MultinomialNB.posWordCounts.get(word) + 1 ;
				        	MultinomialNB.posWordCounts.replace(word, countMultinomial);
				        }else {
				        	MultinomialNB.posWordCounts.put(word, 1);
				        }
				        MultinomialNB.vocabulary.add(word);	
				        MultinomialNB.posTotalCount++;
				        
				        // For Bernoulli NB and Binary NB
				        if ( !documentWords.contains(word)) {
				        	int countBernoulli;
					    	if ( BernoulliNB.posWordDocCounts.containsKey(word) ) {
					    		countBernoulli = BernoulliNB.posWordDocCounts.get(word) + 1 ;
					        	BernoulliNB.posWordDocCounts.replace(word, countBernoulli);
					        }else {
					        	BernoulliNB.posWordDocCounts.put(word, 1);
					        }
					    	BinaryNB.posBinaryWordCount++;
					    	documentWords.add(word);
				        }
				       
				        	
				        
				     }
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static void readNeg() {
		try {
			File negFolder = new File("train/neg");
			negDocCount = negFolder.listFiles(filter).length;
			
			for (File file : negFolder.listFiles(filter)) {
				Set<String> documentWords = new HashSet<String>();
				String line="";
				Scanner scanner = new Scanner(new File("train/neg/" + file.getName()));
				while(scanner.hasNextLine()) {
					line = scanner.nextLine();
					line = line.replaceAll("[^a-zA-Z ]", "");
					StringTokenizer st = new StringTokenizer(line);
					while (st.hasMoreTokens()) {
				    	String word = st.nextToken();
				    	
				    	// For Multinomial NB
				    	int countMultinomial; 
				    	if ( MultinomialNB.negWordCounts.containsKey(word) ) {
				    		countMultinomial = MultinomialNB.negWordCounts.get(word) + 1 ;
				        	MultinomialNB.negWordCounts.replace(word, countMultinomial);
				        }else {
				        	MultinomialNB.negWordCounts.put(word, 1);
				        }
				        MultinomialNB.vocabulary.add(word);	  
				        MultinomialNB.negTotalCount++;
				        
				        // For Bernoulli NB
				        if ( !documentWords.contains(word)) {
				        	int countBernoulli;
					    	if ( BernoulliNB.negWordDocCounts.containsKey(word) ) {
					    		countBernoulli = BernoulliNB.negWordDocCounts.get(word) + 1 ;
					        	BernoulliNB.negWordDocCounts.replace(word, countBernoulli);
					        }else {
					        	BernoulliNB.negWordDocCounts.put(word, 1);
					        }
					    	BinaryNB.negBinaryWordCount++;
					    	documentWords.add(word);
				        }
				     }
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
