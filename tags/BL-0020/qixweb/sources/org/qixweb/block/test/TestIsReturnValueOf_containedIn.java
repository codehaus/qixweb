package org.qixweb.block.test;

import org.qixweb.block.IsReturnValueOf_containedIn;
import org.qixweb.block.LightInternalIterator;

import junit.framework.TestCase;



public class TestIsReturnValueOf_containedIn extends TestCase
{
	public class User
	{
		private String itsName;
		
		User(String aName)
		{
			itsName = aName;
		}
		
		public String name()
		{
			return itsName;
		}
	}
		
	public void testNoUserNoName()
	{
		int returnedValue = LightInternalIterator.createOn(new User[0]).count(new IsReturnValueOf_containedIn("name", new String[0]));
		assertEquals(0, returnedValue);
	}
	
	public void testSomeUsersNoName()
	{
		User[] users = new User[] {new User("qui"), new User("quo"), new User("qua")};
		int returnedValue = LightInternalIterator.createOn(users).count(new IsReturnValueOf_containedIn("name", new String[0]));
		assertEquals(0, returnedValue);
	}	

	public void testSomeUsersSomeNames()
	{
		User[] users = new User[] {new User("qui"), new User("quo"), new User("qua")};
		int returnedValue = LightInternalIterator.createOn(users).count(new IsReturnValueOf_containedIn("name", new String[] {"qui", "quo", "paperino"}));
		assertEquals(2, returnedValue);
	}	

}
