package safir.data;

import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class Termocam extends DefaultMutableTreeNode{
	private String name;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name
	 * @param id
	 */
	public Termocam(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String toString()
	{
		return name;
	}
	
	

}
