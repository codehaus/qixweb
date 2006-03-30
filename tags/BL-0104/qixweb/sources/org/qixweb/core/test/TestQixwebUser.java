package org.qixweb.core.test;

import org.apache.commons.lang.StringUtils;
import org.qixweb.core.QixwebUser;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;


public class TestQixwebUser extends ExtendedTestCase
{
    public static QixwebUser createReadWriteWith(String aName, String aPassword, boolean isToAuthenticateViaLdap)
    {
        return QixwebUser.createUserWith(aName, aPassword, null, null, null, null, isToAuthenticateViaLdap, true);
    }

    public void testAuthenticationOnLdap()
    {
        QixwebUser user = createReadWriteWith("Loris", "Monica", true);
        assertTrue("The user should be authenticated via LDAP", user.isAuthenticatedViaLdap());

        user.disableAuthenticationViaLdap();
        assertFalse("The user should not be authenticated via LDAP", user.isAuthenticatedViaLdap());

        user.enableAuthenticationViaLdap();
        assertTrue("The user should be authenticated via LDAP again", user.isAuthenticatedViaLdap());
    }
    
    public void testGrantAndRemoveReadWritePermission()
    {
        QixwebUser user = createReadWriteWith("Loris", "Monica", false);
        assertTrue("The user should be RW", user.hasWritePermission());

        user.removeWritePermission();
        assertFalse("The user should not be RW anymore", user.hasWritePermission());

        user.grantWritePermission();
        assertTrue("The user should be RW again", user.hasWritePermission());
    }    
    
	public void testChangePassword()
	{
		QixwebUser user = createReadWriteWith("Loris", "Monica", false);
		user.password("Clara");
		assertEquals("Clara", user.password());
	}

    public void testChangeEmail()
    {
        QixwebUser user = TestQixwebUser.createReadWriteWith("Loris", "Monica", false);
        assertTrue(StringUtils.isEmpty(user.email()));
        user.email("santaclara");
        assertEquals("santaclara", user.email());
    }

	public void testSuperAdminUser()
	{
        QixwebUser admin = QixwebUser.createSuperAdminWith("an admin", "admin password", "admin name", "admin surname", "admin email", "admin company");
		assertTrue("It should be the administrator", admin.isSuperAdmin());
	}

    public void testCompareTo()
	{
        QixwebUser user = TestQixwebUser.createReadWriteWith("UGL", "UGL", false);
		QixwebUser anotherUser = TestQixwebUser.createReadWriteWith("CAS", "CAS", false);

		assertTrue("CAS - CAS", user.compareTo(user) == 0);
		assertTrue("CAS - UGL", user.compareTo(anotherUser) > 0);
		assertTrue("UGL - CAS", anotherUser.compareTo(user) < 0);
	}
    
    public void testEquals()
    {
        QixwebUser user = TestQixwebUser.createReadWriteWith("UGL", "UGL", false);
        QixwebUser anotherEqualUser = TestQixwebUser.createReadWriteWith("UGL", "UGL", false);
        QixwebUser differentUser = TestQixwebUser.createReadWriteWith("CAS", "CAS", false);
        
        EqualsBehaviourVerifier.check(user, anotherEqualUser, differentUser);
        EqualsBehaviourVerifier.checkHashCode(user, anotherEqualUser);
    }
	
	public void testIsDisabled()
	{
	    QixwebUser user = TestQixwebUser.createReadWriteWith("qui", "qui1", false);
		assertTrue("User by default is enabled", user.isEnabled());

		user.disable();
		assertFalse("User should now be disabled", user.isEnabled());

		user.enable();
		assertTrue("User should now be enabled", user.isEnabled());
	}

    public void testCreateUser() throws Exception
    {
        QixwebUser user = QixwebUser.createUserWith("ugl", "pswd", "Loris", "Conte", "ug.l@quy.com", "Quinary SPA", false, true);

        assertFalse("The user should not be the administrator", user.isSuperAdmin());

        assertTrue("The user should be RW", user.hasWritePermission());
        assertFalse("The user should not be authenticated via LDAP", user.isAuthenticatedViaLdap());

        assertEquals("Wrong user name", "ugl", user.name());
        assertEquals("Wrong password", "pswd", user.password());
        assertEquals("Wrong first name", "Loris", user.firstName());
        assertEquals("Wrong last name", "Conte", user.lastName());
        assertEquals("Wrong email", "ug.l@quy.com", user.email());
        assertEquals("Wrong company", "Quinary SPA", user.company());
        
        user = QixwebUser.createUserWith("ugl", "pswd", "Loris", "Conte", "ug.l@quy.com", "Quinary SPA", false, false);    
        
        assertFalse("The user should not be the administrator", user.isSuperAdmin());
        assertFalse("The user should be Read Only", user.hasWritePermission());
    }    

    public void testGenerateRandomPassword() throws Exception
    {
        assertNotEquals("Generated passwords should change every time", QixwebUser.generateRandomPassword(), QixwebUser.generateRandomPassword());
        assertEquals("The password should have 10 chars", 10, QixwebUser.generateRandomPassword().length()); 
    }
    

}
