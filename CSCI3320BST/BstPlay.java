//Name      :   Sonia Liu
//Vulcan ID :   sonialiu
//Session   :   CSCI 3320 - 003
//Citations :	www.cs.swarthmore.edu/~newhall/unixhelp/Java_files.html
//		stackoverflow.com/questions/2864117/read-data-from-a-text-file-using-java
//		java2blog.com/2014/07/binary-tree-inorder-traversal-in-java.html
//		stackoverflow.com/questions/5067942/what-is-the-best-way-to-extract-the-first-word-from-a-string-in-java
//		www.tutorialspoint.com/java/java_string_charat.html
//		beginnersbook.com/2014/01/how-to-write-to-a-file-in-java-using-fileoutputstream
//		http://www.java2s.com/Code/Java/File-Input-Output/Readfilecharacterbycharacter.htm
//		CSLC Tutors
//		Data Structures and Algorithm Analysis in Java by Mark Allen Weiss
import java.io.*;
import java.util.*;
public class BstPlay				//correct?
{
    public static void main(String args[]) throws IOException {
		
        String com, word, str = "";
		String arr[] = new String[4];
        char x, y;
		int d = 0, f = 0, i = 0;
		
		Scanner in = new Scanner(System.in);
		BinaryNode root = new BinaryNode();
		BinaryNode current = new BinaryNode();
		current = root;
		BufferedReader load = new BufferedReader(new FileReader("data.txt"));
        String input = load.readLine();
		
		for(int n = 1; n < input.length(); n += 6){
			if(input.charAt(n) == '('){					//is input
				x = input.charAt(n + 1);				//data
				y = input.charAt(n + 3);				//freq
				if(n != 1){							//if not root, move
					if(input.charAt(n + 1) < current.data){
						current = current.left;
					}
					else{
						current = current.right;
					}
				}
				
				BinaryNode node = new BinaryNode();
				node = current;
				node.data = Character.getNumericValue(x);
				node.freq = Character.getNumericValue(y);
			}
			else{										//not input
				current.left = null;				//set left to null
				if(input.charAt(n + 2) != '('){		//if next/right is null
					current.right = null;
				}
				else{								//if right not null
					current = current.right;
					x = input.charAt(n + 3);
					current.data = Character.getNumericValue(x);
					y = input.charAt(n + 5);
					current.freq = Character.getNumericValue(y);
				}
				n--;							//decrease to set count
			}
		}//end for/input
		
		inOrder(root);
        System.out.println();

        do{
            com = in.nextLine();							//get user input
            arr = com.split(" ");							//separate words in input	//correct?
            word = arr[0];									//get first word

            switch(word){
                case "insert":
                    i = Integer.parseInt(arr[1]);
                    insert(root, i);
                    inOrder(root);
                    System.out.println();
                    break;
                case "delete":
                    i = Integer.parseInt(arr[1]);
                    root = delete(root, i);
                    inOrder(root);
                    System.out.println();
                    break;
                case "search":
                    i = Integer.parseInt(arr[1]);
                    boolean b = search(root, i);
                    if(b == false){
                        System.out.printf("The number %d is not in the system.\n", i);
                    }
                    break;
                case "print":
                    if(arr[1] == "inorder"){
                        inOrder(root);
                        System.out.println();
                    }
                    else if(arr[1] == "between"){
                        int k1 = Integer.parseInt(arr[2]);
                        int k2 = Integer.parseInt(arr[4]);
                        between(root, k1, k2);	
                        System.out.println();
                    }
                    else{									//assume is kth command
                        try{
                            String k[] = new String[2];
							k = arr[1].split("-");
                            i = Integer.parseInt(k[0]);
                            kth(root, i, 1);
                            System.out.println();			//need revision	
                        }
                        catch(Exception e){
                            System.out.println("Not a Kth");
                        }
                    }
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("No command entered");
            }
        }while(arr[0] != "exit");
		
		String s = "";
		s = printTree(root, s);
		
		PrintWriter out = new PrintWriter("data.txt");
		out.println(s);
		
        /*str = save(root, str, send);						//can I put a stack in parameter list?
        byte[] bArr = str.getBytes();						//do I have the imports?

        out.write(bArr);									//does this work?
		out.close();*/
	}//end main
	
	public static void inOrder(BinaryNode n){
		if(n.left != null){
			inOrder(n.left);
		}
		//freq
		System.out.printf("%d ", n.data);
		if(n.right != null){
			inOrder(n.right);
		}
	}
	
	/*public static String save(BinaryNode n, String s, Stack t){
        if(n == null){
            s += "[]";
        }
        else{
            s += String.format("[(%d,%d)", n.data, n.freq);
            t.push("]");
            save(n.left, s, t);
            save(n.right, s, t);
            s+= t.pop();
        }
        return s;
    }//end save
	*/

    public static void insert(BinaryNode n, int j){
        if(n == null){
            BinaryNode node = new BinaryNode();
            node.data = j;
            node.freq = 0;
            node.left = null;
            node.right = null;
        }
        if(j < n.data){
            insert(n.left, j);
        }
        else if(j > n.data){
            insert(n.right, j);
        }
        else{												//assume =
            n.freq++;
        }
    }//end insert

    public static BinaryNode findMin(BinaryNode n){
        if(n == null){
            return null;
        }
        else if(n.left == null){
            return n;
        }
        return findMin(n.left);								//no else?
    }//end findMin

    public static BinaryNode delete(BinaryNode n, int j){
        if(n == null){										//couldn't find anything, do nothing
            return n;
        }
        else{												//else, compare
            if(j < n.data){									//go left
                n.left = delete(n.left, j);
            }
            else if(j > n.data){							//go right
                n.right = delete(n.right, j);
            }
            else if(n.left != null && n.right != null){		//if find, but has 2 children
                n.data = findMin(n.right).data;				//get smallest on right side to replace current
                n.right = delete(n.right, n.data);			
            }
            else{											//if find, but have 0-1 children
                n = (n.left != null) ? n.left : n.right;	//if child on left, it becomes current node; otherwise, right becomes current
            }
			return n;
        }
    }//end delete

    public static boolean search(BinaryNode n, int j){
        if(n == null){
            return false;
        }
        if(j < n.data){										//else
            return search(n.left, j);
        }
        else if(j > n.data){
            return search(n.right, j);
        }
        else{
            System.out.printf("number: %d; counter: %d\n", n.data, n.freq);
            return true;
        }
    }//end search

    public static void between(BinaryNode n, int k1, int k2){
        if(n == null){
            return;
        }

        between(n.left, k1, k2);

        if(n.data >= k1 && n.data <= k2){
            System.out.printf("%d ", n.data);
        }
        between(n.right, k1, k2);

    }//end between

    public static void kth(BinaryNode n, int k, int count){
        if(k == 0){
            System.out.print("Not viable k");
        }
        if(k == count){
            System.out.printf("%d", n.data);
        }
        else if(k > count){
            kth(n.right, k, count);
            count++;
            kth(n.left, k, count);
        }
        else{												//assume k < count
            System.out.printf("There are less than %d numbers in list", k);
        }
    }//end kth
	
	public static String printTree(BinaryNode n, String s){
		if(n == null){
			s += "[]";
		}
		else{
			s += "[";
			s += "(";
			s += n.data;
			s += ",";
			s += n.freq;
			s += ")";
			printTree(n.left, s);
			printTree(n.right, s);
			s += "]";
		}
		return s;
	}
}
