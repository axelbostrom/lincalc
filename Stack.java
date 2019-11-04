package Lincalc;

public class Stack {
	
	String [] array;
	
	public Stack() {
		
		array = new String [0];
		
	}
	
	public void push(String pushstack) {
		
		String [] tmp = new String[array.length + 1];
		for (int i = 0; i < array.length; i++) {
			tmp [i] = array [i];
		}
		
		tmp [array.length] = pushstack;
		array = tmp;
	}
	
	public String pop() {
		
		if (array.length == 0) {
			return null;
		}
		
		String Return = array [array.length-1];	
		String [] popArray = new String [array.length-1];
		
		for (int i = 0; i < array.length-1; i++) {
			popArray [i] = array [i];
		}
		
		array = popArray;
		return Return;
		
	}
		
	public String peek() {
		
		if (array.length == 0) {
			return null;
			
		} else {
			
			String check = array[array.length-1];
			return check;
			
		}
	}

	public boolean isEmpty() {
		if (array.length == 0) {	
			return true;	
		}
		return false;	
	}
	
	public String[] getArray() {
		return array;
	}
}

