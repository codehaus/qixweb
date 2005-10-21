package org.qixweb.core.test;

import junit.framework.TestCase;

import org.qixweb.core.QixwebUser;
import org.qixweb.core.QixwebWorkgroup;
import org.qixweb.util.EqualsBehaviourVerifier;



public class TestQixwebWorkgroup extends TestCase
{
    private QixwebWorkgroup itsWorkgroup ; 

	protected void setUp() throws Exception
	{
        itsWorkgroup = new QixwebWorkgroup();
	}

    public void testDefaultUsers()
    {
        assertEquals("There should' t be any default user", 0,itsWorkgroup.allUsers().size());
    }
    public void testFindUserBy()
    {
        assertNull("A not present user cannot be found", itsWorkgroup.findUserBy("User"));
            
        QixwebUser user = TestQixwebUser.createReadWriteWith("User", "Pswd", false);
        itsWorkgroup.add(user);
            
        assertSame("A present user should be found", user, itsWorkgroup.findUserBy("User"));
    }
   
    public void testRemove()
    {
        QixwebUser notExistentUser = TestQixwebUser.createReadWriteWith("VAD", "VAD1", false);
        QixwebUser vaa = TestQixwebUser.createReadWriteWith("VAA", "VAA1", false);       
        QixwebUser dip = TestQixwebUser.createReadWriteWith("DIP", "DIP1", false);       
        itsWorkgroup.add(vaa);
        itsWorkgroup.add(dip);

        itsWorkgroup.remove(new QixwebUser[] {vaa,dip,notExistentUser});
        
        assertEquals("After removing shouldn' t be any user", 0,itsWorkgroup.allUsers().size() );
    }
    
    public void testAdd()
    {
        QixwebUser newUser = TestQixwebUser.createReadWriteWith("VAD", "VAD", false);
        
		assertTrue("New user should be added correctly", itsWorkgroup.add(newUser));
        assertTrue("The new user should be added", itsWorkgroup.allUsers().contains(newUser));
        assertFalse("Adding again should fail because already present", itsWorkgroup.add(newUser));
    }

    public void testEquals() throws Exception
    {
        QixwebUser vaa = TestQixwebUser.createReadWriteWith("VAA", "VAA1", false);       
        QixwebUser dip = TestQixwebUser.createReadWriteWith("DIP", "DIP1", false);       
        itsWorkgroup.add(vaa);
        itsWorkgroup.add(dip);

        QixwebUser dipWithDifferentPassword = TestQixwebUser.createReadWriteWith("DIP", "ANOTHER_PASSWD", false);       
        QixwebWorkgroup sameWorkgroup = new QixwebWorkgroup();
        sameWorkgroup.add(vaa);
        sameWorkgroup.add(dipWithDifferentPassword);

        QixwebWorkgroup differentWorkgroup = new QixwebWorkgroup();

        EqualsBehaviourVerifier.check(itsWorkgroup, sameWorkgroup, differentWorkgroup);
        EqualsBehaviourVerifier.checkHashCode(itsWorkgroup, sameWorkgroup);
    }
}
