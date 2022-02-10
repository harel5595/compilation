package TYPES;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father = null;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_LIST data_members;
	public List<String> data_names;

	public TYPE_LIST getAllDataMembers()
	{
		TYPE_LIST save, head = new TYPE_LIST(null, null) , curr = data_members;
		if(father != null)
			head = father.getAllDataMembers();
		save = head;
		while (curr != null)
		{
			while (head.head != null)
			{
				if(head.tail == null)
					head.tail = new TYPE_LIST(null, null);
				head = head.tail;
			}
			head.head = curr.head;
			curr = curr.tail;
		}
		return save;
	}

	public List<String> getAllDataNames() {
		List<String> all = new LinkedList<>();
		if(father != null)
			all = father.getAllDataNames();
		all.addAll(data_names);
		return all;
	}

	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_LIST data_members, LinkedList<String> data_names)
	{
		this.name = name;
		this.father = father;
		this.data_members = data_members;
		this.data_names = data_names;
	}

	public TYPE_FUNCTION findFunc(String name) {
		TYPE_LIST datas = getAllDataMembers();
		int counter = 0;
		while (datas != null)
		{
			if(datas.head instanceof TYPE_FUNCTION && Objects.equals(((TYPE_FUNCTION) datas.head).name, name))
				return (TYPE_FUNCTION) datas.head;
			counter++;
			datas = datas.tail;
		}
		return null;
	}
}
