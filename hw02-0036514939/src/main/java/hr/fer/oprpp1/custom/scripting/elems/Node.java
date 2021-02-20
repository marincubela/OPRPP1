package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Bazna klasa za sve čvorove.
 * 
 * @author Cubi
 *
 */
public class Node {
	private ArrayIndexedCollection childrenNodes;
	
	public Node() {
		// Ne radi nista korisno
	}
	
	/**
	 * Metoda koja dodaje jedan čvor ovome čvoru.
	 * 
	 * @param child čvor koji se dodaje.
	 * @throws NullPointerException baca se ako je argument <code>null</code>.
	 */
	public void addChildNode(Node child) {
		if(child == null) {
			throw new NullPointerException("Node child ne smije biti null!");
		}
		
		if(childrenNodes == null) {
			childrenNodes = new ArrayIndexedCollection();
		}
		
		childrenNodes.add(child);		
	}
	
	/**
	 * Vraća broj djece čvorova koje ovaj čvor ima pohranjene.
	 * 
	 * @return broj djece čvorova.
	 */
	public int numberOfChildren() {
		if(childrenNodes ==null) {
			return 0;
		}
		return childrenNodes.size();
	}
	
	/**
	 * Vraća dijete čvor na danom indexu.
	 * 
	 * @param index redni broj čvora kojeg treba vratiti.
	 * @return čvor na danom indexu.
	 */
	public Node getChild(int index) {
		return (Node) childrenNodes.get(index);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childrenNodes == null) ? 0 : childrenNodes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (childrenNodes == null) {
			if (other.childrenNodes != null)
				return false;
		} else if (!childrenNodes.equals(other.childrenNodes))
			return false;
		return true;
	}
}
