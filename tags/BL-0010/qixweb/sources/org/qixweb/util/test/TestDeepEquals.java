package org.qixweb.util.test;

import java.util.*;

import org.qixweb.util.DeepEquals;


public class TestDeepEquals extends ExtendedTestCase
{
	private class User 
	{
		public String itsName;
		public String itsSurname;
		public int itsAge;

		public User(String aName, String aSurname, int anAge)
		{
			itsName = aName;
			itsSurname = aSurname;
			itsAge = anAge;
		}
        
		public boolean equals(Object obj)
		{
			return DeepEquals.equals(this, obj);
		}
	}

	private class Student extends User
	{
	    public Integer[] itsMarks;
		public String[] itsBooks;		

		public Student(String aName, String aSurname, int anAge)
		{
			this(aName, aSurname, anAge, new Integer[0]);
		}

		public Student(String aName, String aSurname, int anAge, Integer[] someMarks)
		{
			this(aName, aSurname, anAge, someMarks, new String[0]);
		}
		
		public Student(String aName, String aSurname, int anAge, Integer[] someMarks, String[] someBooks)
		{
			super(aName, aSurname, anAge);
			itsMarks = someMarks;
			itsBooks = someBooks;
		}
	}

	private class StudentWithList
	{
	    public List itsMarks;

		public StudentWithList(List someMarks)
		{
			itsMarks = someMarks;
		}

		public boolean equals(Object obj)
		{
			return DeepEquals.equals(this, obj);
		}
	}
	
	public static class ClassWithByteArray
	{
		byte[] itsByteArray;
		
		public ClassWithByteArray(byte[] byteArray)
		{
			itsByteArray = byteArray;
		}
	}

	public static class ClassWithStaticField
	{
		public static final ClassWithStaticField STATIC_FIELD = new ClassWithStaticField();
 
		public boolean equals(Object obj)
		{
			return DeepEquals.equals(this, obj);
		}
	}
	
	public void testSkipStaticFields()
	{
		assertTrue("If this call goes in stack overflow means that it compare also static fields, that it's wrong", 
			new ClassWithStaticField().equals(new ClassWithStaticField())); 
	}


	public void testEqualsForSameObjectInSuperclass()
	{
		User user = new User("davide", "varvello", 32);
		User sameUser = new User("davide", "varvello", 32);
		User differentUser = new User("giannandrea", "castaldi", 31);

		assertEquals("the same objects must be equals", user, sameUser);
		assertTrue("two different objects must be different", !user.equals(differentUser));
	}

    public void testEqualsForSameObjectInSubclass()
    {
        Student student = new Student("davide", "varvello", 32, new Integer[] { new Integer(2)});
        Student sameStudent = new Student("davide", "varvello", 32, new Integer[] { new Integer(2)});
        Student differentStudent = new Student("davide", "varvello", 32, new Integer[] { new Integer(10)});
        Student differentStudentForSuperclassFields = new Student("giannandrea", "castaldi", 31, new Integer[] { new Integer(2)});

        assertEquals("the same objects must be equals", student, sameStudent);
        assertTrue("two different objects for their own fields must be different", !student.equals(differentStudent));
        assertTrue("two different objects for their parent fields must be different", !student.equals(differentStudentForSuperclassFields));
    }

	public void testNullFields()
	{
		User user = new User(null, null, 32);
		User sameUser = new User(null, null, 32);

		assertEquals("the same objects must be equals", user, sameUser);
		
		user = new User(null, null, 32);
		User differentUser = new User("Pluto", null, 32);
		
		assertNotEquals("Different name", user, differentUser);		
		assertNotEquals("Different name2", differentUser,user);		
	}
	
    public void testNullArrayFields()
    {
        Student studentWithNullMarks = new Student("mario", "rossi", 33, null);
        Student sameStudent = new Student("mario", "rossi", 33, null);
        assertEquals("the same objects must be equals", studentWithNullMarks, sameStudent);
        
        User studentWithEmptyMarks = new Student("mario", "rossi", 33, new Integer[0]);        
        assertNotEquals("Empty marks", studentWithNullMarks, studentWithEmptyMarks);     
        assertNotEquals("Empty marks symmetric", studentWithEmptyMarks, studentWithNullMarks);     
    }
    
	public void testNullValues()
	{
		User user = new User("Mario", "Rossi", 32);
		User nullUser = null;

		assertFalse("If only second object is null, they must be different", DeepEquals.equals(user, nullUser));		
		assertFalse("If only first object is null, they must be different", DeepEquals.equals(nullUser, user));		
		assertTrue("If both object is null, they must be equals", DeepEquals.equals(nullUser, nullUser));		
	}	

	public void testEqualsHierarchy()
	{
		User user = new User("davide", "varvello", 32);
		Student sameUser = new Student("davide", "varvello", 32);

		assertTrue("even if with the same value two object by two different classes should be different", !user.equals(sameUser));
        assertTrue("the equals must be symmetric", !sameUser.equals(user));
	}

	public void testArray()
	{
		Student student =
			new Student("davide", "varvello", 32, new Integer[] { new Integer(30), new Integer(29), new Integer(28), new Integer(27)});
		Student sameStudent =
			new Student("davide", "varvello", 32, new Integer[] { new Integer(30), new Integer(29), new Integer(28), new Integer(27)});
		Student sameStudentWithMarksInDifferentOrder =
			new Student("davide", "varvello", 32, new Integer[] { new Integer(27), new Integer(30), new Integer(29), new Integer(28)});
		Student sutdentWithDifferentMarks =
			new Student("davide", "varvello", 32, new Integer[] { new Integer(33), new Integer(33), new Integer(28), new Integer(27)});

		assertEquals("the same student must be equals", student, sameStudent);

		assertFalse(
			"two students with same marks but in different order must be different",
			student.equals(sameStudentWithMarksInDifferentOrder));

		assertTrue("two students with different marks must be different", !student.equals(sutdentWithDifferentMarks));
	}

	public void testStoppingOnDetectingFirstDifferentField()
	{
		Student firstStudent = new Student("davide", "varvello", 32, new Integer[] { new Integer(1)}, new String[] {"DivinaCommedia"} );
		Student secondStudent = new Student("davide", "varvello", 32, new Integer[] { new Integer(30)}, new String[] {"DivinaCommedia"} );
		
		assertNotEquals("The student have different marks", firstStudent, secondStudent);		
	}
	
	
	public void testList()
	{
		ArrayList aList = new ArrayList();
		aList.add(new Integer(3));
		aList.add(new Integer(5));
		StudentWithList student = new StudentWithList(aList);

		Vector sameListAsVector = new Vector();
		sameListAsVector.add(new Integer(3));
		sameListAsVector.add(new Integer(5));
		StudentWithList sameStudent = new StudentWithList(sameListAsVector);

		ArrayList listWithMarksInDifferentOrder = new ArrayList();
		listWithMarksInDifferentOrder.add(new Integer(5));
		listWithMarksInDifferentOrder.add(new Integer(3));
		StudentWithList studentWithMarksInDifferentOrder = new StudentWithList(listWithMarksInDifferentOrder);

		ArrayList listWithDifferentMarks = new ArrayList();
		listWithDifferentMarks.add(new Integer(2));
		StudentWithList studentWithDifferentMarks = new StudentWithList(listWithDifferentMarks);

		assertEquals("the same student must be equals", student, sameStudent);

		assertFalse(
			"two students with same marks but in different order must be different",
			student.equals(studentWithMarksInDifferentOrder));

		assertFalse(
			"two students with different marks must be different",
			student.equals(studentWithDifferentMarks));
		
	}
	
	public void testByteArray()
	{
		// @PMD:REVIEWED:StringInstantiation: by bop on 3/8/05 5:27 PM
		assertTrue(DeepEquals.equals(new ClassWithByteArray(new String("hello").getBytes()), new ClassWithByteArray(new String("hello").getBytes())));
	}

	public void testFalseForDifferentClass()
	{
	    assertFalse(new User("pippo", "pluto", 7).equals("another class"));
}
}
