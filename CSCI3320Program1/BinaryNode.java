//Name      :   Sonia Liu
//Vulcan ID :   sonialiu
//Session   :   CSCI 3320 - 003
public class BinaryNode<E>
{
	private E data;
	private BinaryNode left;
	private BinaryNode right;
	
	public BinaryNode(E d)
	{
		data = d;
		left = null;
		right = null;
	}
	
	public BinaryNode(E d, BinaryNode<E> lt, BinaryNode<E> rt)
	{
		data = d;
		left = lt;
		right = rt;
	}
}