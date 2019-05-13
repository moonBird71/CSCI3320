//Name      :   Sonia Liu
//Vulcan ID :   sonialiu
//Session   :   CSCI 3320 - 003

//Sources   :   docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html
//				codereview.stackexchange.com/questions/35750/postfix-evaluation-using-a-stack
//				tutorialspoint.com/java/java_stack_class.htm

import java.io.*;
import java.util.*;

public class JCB
{
    public static void main(String[] args)  //input?  main?
    {
        Scanner in = new Scanner(System.in);
        Stack stack = new Stack();
		//char[] start = {'/', '(', '[', '{'};
		//char[] end = {'*', ')', ']', '}'};		//address /* */
        boolean balance = true;
        char x = ' ';
        char y = ' ';

        do{
			//System.out.println("working");		//debugging	
            x = in.next().charAt(0);
            if(x == ' '){
            }
            else{
                if((x == '(') || (x == '[') || (x == '{')){  //if single start char
                    stack.push(x);
                }
				else if(x == '/')					//if 2 start char
				{
					y = in.next().charAt(0);
					if(y == '*')
					{
						stack.push(y);				//push * in stack
					}
				}
                else if((x == ')') || (x == ']') || (x == '}')){	//if single end char
                    y = (Character)stack.pop();
                    switch(x){
                        case ')':
                            if(x == ')' && y != '('){
								balance = false;
							}
                            break;
                        case ']':
                            if(x == ']' && y != '['){
								balance = false;
							}
                            break;
                        case '}':
							if(x == '}' && y != '{'){
								balance = false;
							}
                            break;
                    }
                }
				else if(x == '*')	//if 2 end char
				{
					y = in.next().charAt(0);
					if(y == '/')
					{
						char z = (Character)stack.pop();
						if(z != '*')
						{
							balance = false;
						}
					}
				}
                else{}
            }
        }while(in.hasNext());              //or EOF?
		
		if(balance == true){
			System.out.println("The code is balanced");
		}
		else{
			System.out.println("There are balancing issues");
		}
    }
}
