
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
        
       
        Scanner sc = new Scanner(System.in);
        System.out.println("Type 'bot' for optimized answers or anything else to play for fun! ");
        String input = sc.next();
        if(input.toLowerCase().equals("bot")) {
        	guessWord();
        } else {
        	playGame();
        }

    }
	
	public static void guessWord() throws IOException {
		Wordle wordle = new Wordle(true);
		if(wordle.generateWordList()) {
			Scanner sc = new Scanner(System.in);
			boolean run = true;
			while(run) {
				System.out.print("Enter Your Input: ");
				String input = sc.next();
				System.out.println();
				System.out.println("Enter Your Confirmed Values: ");
				System.out.print("status 1: ");
				String status1 = sc.next();
				System.out.println();
				System.out.print("status 2: ");
				String status2 = sc.next();
				System.out.println();
				System.out.print("status 3: ");
				String status3 = sc.next();
				System.out.println();
				System.out.print("status 4: ");
				String status4 = sc.next();
				System.out.println();
				System.out.print("status 5: ");
				String status5 = sc.next();
				System.out.println();
				wordle.wordStatus.add(status1);
				wordle.wordStatus.add(status2);
				wordle.wordStatus.add(status3);
				wordle.wordStatus.add(status4);
				wordle.wordStatus.add(status5);
				System.out.print("Add Confirmed letters separated by commas: ");
				String confirmed = sc.next();
				String[] confirmedArr = confirmed.split(",");
				
				for(String letter : confirmedArr) {
					wordle.correctLetters.add(letter);
				}
				
				System.out.print("Add Removed letters separated by commas: ");
				String removed = sc.next();
				String[] removedArr = removed.split(",");
				
				for(String letter : removedArr) {
					wordle.removedLetters.add(letter);
				}
				
				
				wordle.reduceWords(input);
				System.out.println(Arrays.toString(wordle.words));
				System.out.print("type 'done' to be done using: ");
	            if(sc.next().toLowerCase().equals("done")) {
	            	run = false;
	            	sc.close();
	            	break;
	            	
	            }
			}
			
		}
	}
	
	
	
	public static void playGame() throws IOException {
		Wordle wordle = new Wordle(false);
		if (wordle.generateWordList()) {
            String word = wordle.getRandWord();
            Scanner sc = new Scanner(System.in);
            int guess = 0;
            boolean run = true;
            while (run || guess < 6) {
                guess++;
                System.out.println("Guess: " + guess + " | status:" + wordle.getWordStatus()
                        + " | confirmed letters: " + wordle.getCorrectLetters() + " | removed letters: "
                        + wordle.getRemovedLetters().toString() + " | remaining: " + wordle.remaining);

                String input = sc.next().toLowerCase().trim();
                while (input.length() != 5) {
                    System.out.println("Enter an input of 5 characters");
                    input = sc.next().toLowerCase();
                }
                wordle.getCorrectness(input, word);
                wordle.reduceWords(input);
                System.out.println(Arrays.toString(wordle.words));
                if (wordle.isCorrect(input, word)) {
                    System.out.println("Correct! The answer was " + word);
                    System.out.print("Press 'p' to play again or 'q' to quit: ");
                    if(sc.next().equals("p")) {
                    	run = true;
                    	guess = 0;
                    	wordle.emptyAll();
                    	wordle.generateWordList();
                    	word = wordle.getRandWord();
                    	
                    } else if (sc.next().equals("q")){
                    	run = false;
                    	guess = 7;
                    	sc.close();
                    	break;
                    }
                   
                } else if (guess == 6) {
                	System.out.println("Sorry, You ran out of attempts. The answer was: " + word);
                	System.out.print("Press 'p' to play again or 'q' to quit: ");
                    if(sc.next().equals("p")) {
                    	run = true;
                    	guess = 0;
                    	wordle.emptyAll();
                    	wordle.generateWordList();
                    	word = wordle.getRandWord();
                    	
                    } else if (sc.next().equals("q")){
                    	run = false;
                    	guess = 7;
                    	sc.close();
                    	break;
                    }
                }
                
            }
            

            sc.close();
        }
	}
}
