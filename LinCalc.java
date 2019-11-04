package Lincalc;
import java.util.Scanner;

public class LinCalc {

	public static void printArray(String[] array){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            sb.append(", ");
        }
        // Replace the last ", " with "]"
        sb.replace(sb.length() - 2, sb.length(), "]");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String expression;

        double result;

        System.out.print("Enter expression: ");
        expression = in.nextLine();
        do {
            result = evaluate(expression);
            System.out.println("Result was: " + result);
            System.out.print("Enter expression: ");
        } while (!"".equals(expression = in.nextLine()));
    }

    // AXELS KOD!!!
    
    public static double calc(String[] lexedPostfix) { //Calc
    	Stack stack = new Stack();
    	final String operators = ("-+/*");
    	
    	for (int i = 0; i < lexedPostfix.length; i++) {
    		String current = lexedPostfix[i];
    		
    		if (current.equals("~")) { //Special fall för tilde/unärt minus
    			double tilde = Double.parseDouble(stack.pop()); //gör om till double
    			double tildeVal = 0 - tilde;
    			String minusstring = Double.toString(tildeVal);
    			stack.push(minusstring);
    			continue;
    		} 		
    		if (memberOf(current.charAt(0), operators)) { 
	    			Double nbr1 = Double.parseDouble(stack.pop()); 
	    			Double nbr2 = Double.parseDouble(stack.pop());
	        		
	        		switch (current) { 	//Kollar varje fall och räknar med avseende på fall
		        		case "+":
		        			double add = nbr1 + nbr2;
		        			String addString = Double.toString(add);
		        			stack.push(addString);
		        			break;        			
		        		case "-":
		        			double sub = nbr2 - nbr1;
		        			String subString = Double.toString(sub);
		        			stack.push(subString);
		        			break;        		
		        		case "*":
		        			double mult = nbr1 * nbr2;
		        			String multString = Double.toString(mult);
		        			stack.push(multString);
		        			break;	        			
		        		case "/":
		        			double div = nbr2 / nbr1;
		        			String divString = Double.toString(div);
		        			stack.push(divString);
		        			break;			
	        		}	        		
    		} else {
    			stack.push(current); //När det inte finns några operatorer, pushar hela stacken
    		}
    	} 	
    	return Double.valueOf(stack.pop()); //returnerar värdet av stacken
    }

    //OLIVERS KOD !!!!!
/*
  //Här börjar metod för att beräkna våra postfix uttryck
    public static double calc(String[] lexedPostfix) {
    	Stack calcus = new Stack();
    	String operator = "+*-/";
    	String unmin = "~";
    	
    	for (int i=0; i < lexedPostfix.length; i++) {
    		String k = lexedPostfix[i];
    		//här kommer en egen sats för unära minus
    		if (unmin.contains(lexedPostfix[i])) {
    			double val3 = Double.parseDouble(calcus.pop());
    			double f = 0 - val3;
    			String unminus = Double.toString(f);
    			calcus.push(unminus);
    			continue;	
    		}
    		//här är en sats för att för att avgöra vad den ska göra för vilken operator
    		//Därefter beräknar den och pushar värdet
    		if (operator.contains(lexedPostfix[i])) {
    			double val1 = Double.parseDouble(calcus.pop());
    			double val2 = Double.parseDouble(calcus.pop());
    			
    			switch (k) {
    			//Varje case sats går ut på att skapa en string där man placerar värdet på uträkningen för att sedan pusha denna string
    			case"+":
    				double a = val2 + val1;
    				String add = Double.toString(a);
    				calcus.push(add);
    				break;
    			
    			case"-":
    				double b = val2 - val1;
    				String sub = Double.toString(b);
    				calcus.push(sub);
    				break;
    				
    			case"*":
    				double c = val2 * val1;
    				String mult = Double.toString(c);
    				calcus.push(mult);
    				break;
    				
    			case"/":
    				double d = val2 / val1;
    				String div = Double.toString(d);
    				calcus.push(div);
    				break;
    				}
    	}else {
    		calcus.push(lexedPostfix[i]);
    		}
    }
    	//här returnerar jag de slutgiltiga värdet, som sedan skickas till överstående metoder
    	return Double.valueOf(calcus.pop());
}
	*/
    public static String[] toPostfix(String[] inputData) { //postfix
    	
    	Stack stack = new Stack();
    	final String operators = ("-+/*~");
    	final String operands = ("1234567890.");
    	char a = 0;
    	String [] outputData = new String [inputData.length*2];

		for (int i = 0; i < inputData.length; i++) {
			String current = inputData[i];

			if (memberOf(current.charAt(0), operands)) { //kollar om operand
				outputData [a] = current; //om nummer, lägg i output
				a++;
			}
			else if (current.equals("(")) { //om vänsterparantes pusha till stacken
				stack.push(current);
			}
			else if (current.equals(")")) { //om högerparantes kommer och stacken inte är tom poppa stacken till output
				while (!stack.isEmpty() && !stack.peek().equals("(")) {
					outputData[a] = stack.pop();
					a++;		
				}
				stack.pop(); //poppar bort parantes, behövs ej längre
			}
			else if (memberOf(current.charAt(0), operators)) { //kollar och jämför prioritet i stacken
				while ((!stack.isEmpty()) && (priority(stack.peek()) >= priority(current))) {
					outputData [a] = stack.pop();
					a++;
				}
				stack.push(current);
			}
		}
		while (!stack.isEmpty()) { //loop för att poppa stacken om den inte är tom
			outputData[a] = stack.pop();
			a++;
		}
		outputData = clearNulls(outputData); //Rensar outputdata på nulls, ny metod
  		return outputData;	
    }
	
    public static double evaluate(String expression) {
        String[] lexedInfix = lex(expression);
        String[] lexedPostfix = toPostfix(lexedInfix);
        return calc(lexedPostfix);
    }
    
    public static String[] lex(String expression) { //infix
    	
    	String [] splitArray = new String [expression.length()];
    	expression = expression.replaceAll("\\s", "");	
    	final String operators = ("-+/*()");
    	final String operands = ("1234567890.");
    	char prevCurrent = '?';
		String tmp = "";
		int a = 0;		

    		for (int i = 0; i < expression.length(); i++) {			
    			char current = expression.charAt(i);
    			
    			if (memberOf(current, operators)) { //Kollar om operator
					if(!tmp.isEmpty()) { //om tmp inte är tom lägg till i array
						splitArray[a] = tmp;
						a++;
						tmp = ""; //tömmer/nollställer tmp
					}			
    				if (tmp.isEmpty()) { //om tmp är tom
    					
    					if (a == 0 && current == '-') { //Hanterar tilde
    					current = '~';
    					}
    					else if (memberOf(prevCurrent, operators) && current == '-') { 
    					current = '~'; //gör om unärt minus till tilde
    					}    	
    					splitArray [a] = tmp + current;
    					a++;    						
    				}	   				
    			}			    		
    			if (memberOf(current, operands)) { //kollar om operand  				
    				tmp = tmp + current; //lägger ihop nummer, så de blir hela
    				splitArray[a] = tmp;  
        		}	
    			prevCurrent = current;
    		}		  		
    	splitArray = clearNulls(splitArray); //rensar på nulls 	
    	return splitArray;
    }
    
    public static int priority(String prio) { //Kollar prioritet för operatorer
    	
    	switch (prio) {
			case "+":
			case "-":
				return 0;
    		case "*":
    		case "/":
    			return 1;
    		case "~": //Högst prioritet
    			return 2; 
    	}
    	return -1;
    }
     
    public static String[] clearNulls(String[] arr) { //Rensar array på nulls
    	int reallength = 0;
    	for (int i = 0; i < arr.length; i++) { 		
    		String current = arr[i];   		
    		if (current == null) {
        		reallength = i++;
            	String[] cleared = new String [reallength];
            		for(int a = 0; a < reallength; a++) {
            			cleared[a] = arr[a];
            	}		
            	return cleared; 
    		}
    	}
		return arr;
    }
     
    public static boolean memberOf(char medlem ,String grupp) { //Kollar om medlem i grupp
    	if (medlem == '?') {
    		return false;
    	}
    	for (int i = 0; i < grupp.length(); i++) {
    		if (grupp.charAt(i) == medlem) {
    			return true;
    		}
    	}
    	return false;
    }
}