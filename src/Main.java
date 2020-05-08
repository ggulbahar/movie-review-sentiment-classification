import java.io.File;

public class Main {

	
	
	public static void main(String[] args) {
		
		FileRead.readPos();
		FileRead.readNeg();
		
		File posFolder = new File("test/pos");
		File negFolder = new File("test/neg");
		
		
		int[] result = MultinomialNB.test(posFolder);
		System.out.println("Results for Multinomial NB on positive test set:");
		System.out.println("Classified as positive: " + result[0]);
		System.out.println("Classified as negative: " +result[1]);
		
		for(int i=0; i<50; i++)System.out.print("-");
		System.out.println(); 
		
		
		result = MultinomialNB.test(negFolder);
		System.out.println("Results for Multinomial NB on negative test set:");
		System.out.println("Classified as positive: " + result[0]);
		System.out.println("Classified as negative: " +result[1]);
		
		System.out.println(); 
		for(int i=0; i<50; i++)System.out.print("*");
		System.out.println(); 
		System.out.println(); 
		
		result = BernoulliNB.test(posFolder);
		System.out.println("Results for Bernoulli NB on positive test set:");
		System.out.println("Classified as positive: " + result[0]);
		System.out.println("Classified as negative: " +result[1]);
		
		for(int i=0; i<50; i++)System.out.print("-");
		System.out.println();
		
		result = BernoulliNB.test(negFolder);
		System.out.println("Results for Bernoulli NB on negative test set:");
		System.out.println("Classified as positive: " + result[0]);
		System.out.println("Classified as negative: " +result[1]);
		
		System.out.println(); 
		for(int i=0; i<50; i++)System.out.print("*");
		System.out.println(); 
		System.out.println(); 
		
		result = BinaryNB.test(posFolder);
		System.out.println("Results for Binary NB on positive test set:");
		System.out.println("Classified as positive: " + result[0]);
		System.out.println("Classified as negative: " +result[1]);
		
		for(int i=0; i<50; i++)System.out.print("-");
		System.out.println();
		
		result = BinaryNB.test(negFolder);
		System.out.println("Results for Binary NB on negative test set:");
		System.out.println("Classified as positive: " + result[0]);
		System.out.println("Classified as negative: " +result[1]);
		
		
	}
	

}
