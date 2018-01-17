package alda.linear;

import java.util.Iterator;
import java.util.LinkedList;

public class Main {
	public static void main(String[] args) {

		MyALDAList<String> list = new MyALDAList<String>();

		String hej = "Hej";
		list.add(0, "1");
		list.add(1, "2");
		list.add(2, "3");
		list.add(3, hej);

		Iterator<String> i2 = list.iterator();

		while (i2.hasNext()) {
			String obj = (String) i2.next();	
			if(obj.equals(hej)) {
				i2.remove();
			} else {
				System.out.println(obj);
			}

		}

	}
}
