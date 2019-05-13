//Name      :   Sonia Liu
//Vulcan ID :   sonialiu
//Session   :   CSCI 3320 - 003
//
//Sources	:	tutorialspoint.com/java/util/scanner_hasnextint.htm
//				java2s.com/Code/Java/Development-Class/useScannertoreadinputthatcontainsseveraldifferenttypesofdata.htm
//				https://www.mkyong.com/java/java-convert-string-to-int/
import java.util.*;
public class EXP
{
	Stack s1 = new Stack();
	Stack s2 = new Stack();
	Scanner in = new Scanner(System.in);
	BinaryNode first, current;
	int num1 = 0, num2 = 0, i = 0;
	char op = ' ', op2 = ' ', c = ' ';
	String str;
	
	public static void main(String[] args)
	{
		System.out.print("Please give an infix expression: ");
		str = in.nextLine();
		
		System.out.printf("\ninfix expression: %s\n", str);
		
		System.out.print("postfix expression: ");
		
		while(str != null)													//while there's still unprocessed data
		{
			if(str.hasNextInt() == true)									//if a number
			{
				i = Integer.parseInt(str.next());
				
				if(s1.empty())
				{
					first = new BinaryNode(i);
					s1.push(first);
				}
				else
				{
					current = new BinaryNode(i);
					s1.push(current);
				}
			}
			else															//if an op
			{
				op = str.next();												//next() to get single char?
				current = new BinaryNode(op);
				
				if(s1.peek() == first || s1.empty())						//if s1 has less than 2
				{
					s2.push(current);
				}
				else														//if s1 has 2 or more
				{
					if(op == '(' || s2.peek() == '(')
					{}
					else if(op == ')')										//if () - precedence
					{
						op2 = s2.pop();
						do
						{
							num2 = s1.pop();
							num1 = s1.pop();
							//eval?
							BinaryNode(num1, null, op2);
							BinaryNode(op2, num1, num2);
							BinaryNode(num2, op2, null);
							s1.push(op2);
							//op2 = s2.pop();
						}while(op2.peek() != '(');
					}
					else													//if not in ()
					{
						num2 = s1.pop();
						num1 = s1.pop();
						current = BinaryNode(op, num1, num2);
						BinaryNode(num1, null, current);
						BinaryNode(num2, current, null);
						s1.push(current);
					}
				}
			}
		}
		while(s2.empty() != true)
		{
			op = s2.pop();
			num2 = s1.pop();
			num1 = s1.pop();
			current = BinaryNode(op, num1, num2);
			BinaryNode(num1, null, current);
			BinaryNode(num2, current, null);
			s1.push(current);
		}
		current = s1.pop();
		printPostfix(current);
		i = eval(current);
		System.out.printf("\nevaluation result: %d\n", i);
	}
	
	void printPostfix(BinaryNode p)
	{
		if(p == null)
		{
			return;
		}
		printPostfix(p.left);
		printPostfix(p.right);
		System.out.print(p.name + " ");
	}
	
	int eval(BinaryNode p)
	{
		int sum = 0;
		if(p.left == null && p.right == null)
		{
			return sum;
		}
		eval(p.left);
		if((typeof(p.left) == int.class) && (typeof(p.right) == int.class))	//.class?
		{
			switch(p.data)
			{
				case '+':
					sum = (p.left + p.right);
					break;
				case '-':
					sum = (p.left - p.right);
					break;
				case '*':
					sum = (p.left * p.right);
					break;
				case '/':
					sum = (p.left / p.right);
					break;
			}
			p.data = sum;
			p.left = null;
			p.right = null;
		}
		eval(p.right);
	}
}
