import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

class Wordle {
	public static int arrayLength = 2315;
	public String[] words = new String[arrayLength];
	public String path = "\\C:\\Users\\Benjamin\\eclipse-workspace1\\Wordle\\src\\words.txt";
	public ArrayList<String> removedLetters = new ArrayList<String>();
	public ArrayList<String> wordStatus = new ArrayList<String>();
	public ArrayList<String> correctLetters = new ArrayList<String>();
	public HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
	public int remaining = 0;

	public Wordle(boolean guess) {
		if(guess) {
			//
		} else {
			wordStatus.add("_");
			wordStatus.add("_");
			wordStatus.add("_");
			wordStatus.add("_");
			wordStatus.add("_");

		}
		
		for (char i = 'a'; i < 'z'; i++) {
			map.put(String.valueOf(i), new ArrayList<Integer>());
		}
	}

	public boolean generateWordList() throws IOException {
		BufferedReader abc = new BufferedReader(new FileReader(path));
		int i = 0;
		while (i < arrayLength) {
			String line = abc.readLine();
			words[i] = line;
			i++;
		}
		abc.close();
		remaining = words.length;
		return true;
	}

	public String getRandWord() {
		int rand = new Random().nextInt(Wordle.arrayLength);
		return words[rand];
	}

	public boolean isCorrect(String input, String word) {
		return input.equals(word);
	}

	public void getCorrectness(String input, String word) {
		// 0 -> Not in word
		// 1 -> In word but not in right spot
		// 2 -> In word, in correct spot

		boolean isIn = false;
		for (int i = 0; i < 5; i++) {
			if (input.charAt(i) == word.charAt(i)) {
				wordStatus.set(i, String.valueOf(input.charAt(i)));
				if (!correctLetters.contains(String.valueOf(input.charAt(i)))) {
					correctLetters.add(String.valueOf(input.charAt(i)));
				}
				isIn = true;
				continue;
			}
			for (int j = 0; j < 5; j++) {
				if (input.charAt(i) == word.charAt(j)) {
					String curr = String.valueOf(input.charAt(i));
					if (!correctLetters.contains(curr)) {
						correctLetters.add(curr);

					}

					if (map.containsKey(curr)) {

						ArrayList<Integer> al = map.get(curr);
						al.add(i);
						map.put(curr, al);
					}

					isIn = true;
				}
			}
			if (!isIn) {
				if (!removedLetters.contains(String.valueOf(input.charAt(i)))) {
					removedLetters.add(String.valueOf(input.charAt(i)));
				}
			}

			isIn = false;

		}
	}

	public void reduceWords(String input) {
			// 3 cases
			// Case 1: Does NOT have letters that are confirmed by Correctness
			// Case 2: HAS letters that are confirmed not in word by Correctness
			// Case 3: HAS letters BUT not in same spot as Correctness
			// Case 4 (SMART): Knows from previous guesses confirmed letters are not in
			// certain spots

			for (int i = 0; i < words.length; i++) {
				// case 1
				boolean flag = false;
				for (int j = 0; j < correctLetters.size(); j++) {
					if (!words[i].contains(correctLetters.get(j))) {
						flag = true;					
					}
				}
				// case 2
				for (int x = 0; x < removedLetters.size(); x++) {
					
					if (words[i].contains(removedLetters.get(x))) {
						flag = true;		
					}	
				}
				// case 3
				for (int y = 0; y < wordStatus.size(); y++) {
					if (!wordStatus.get(y).equals("_")) {
						
						if (!String.valueOf(words[i].charAt(y)).equals(wordStatus.get(y))) {
							flag = true;
							
						}
					}
				}
				// case 4
				for (int z = 0; z < map.size(); z++) {
					for (int l = 0; l < input.length(); l++) {
						String letter = String.valueOf(input.charAt(l));
						ArrayList<Integer> indeces = map.get(letter);
						if (indeces.contains(words[i].indexOf(input.charAt(l)))) {
							flag = true;
						}	
					}
				}
				if (flag) {
					words[i] = "";
					remaining--;
				}
			}
			updateWords();	
	}

	public String[] getWords() {
		return words;
	}
	
	public void updateWords() {
		ArrayList<String> list = new ArrayList<String>();
		for (String word : words) {
			if (word != "") {
				list.add(word);
			}
		}
		String[] temp = list.toArray(new String[list.size()]);
		words = temp;
	}

	public ArrayList<String> getRemovedLetters() {
		return removedLetters;
	}

	public ArrayList<String> getWordStatus() {
		return wordStatus;
	}

	public ArrayList<String> getCorrectLetters() {
		return correctLetters;
	}
	
	public void emptyAll() {
		this.words = new String[arrayLength];
		this.correctLetters = new ArrayList<String>();
		this.removedLetters = new ArrayList<String>();
		this.wordStatus = new ArrayList<String>();
		wordStatus.add("_");
		wordStatus.add("_");
		wordStatus.add("_");
		wordStatus.add("_");
		wordStatus.add("_");
		this.map = new HashMap<String, ArrayList<Integer>>();
		
	}

}
