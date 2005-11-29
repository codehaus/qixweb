package org.qixweb.core;

import java.io.Serializable;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.qixweb.util.DeepEquals;


public class QixwebUser implements Comparable, Serializable
{
    static final long serialVersionUID = 1L;
    public static final QixwebUser NULL = createUserWith("NullUser", "", "", "", "", "", false, true);

    public static QixwebUser createSuperAdminWith(String aName, String aPassword, String aFirstName, String aLastName, String aEmail, String aCompany)
    {
        return new QixwebUser(aName, aPassword, aFirstName, aLastName, aEmail, aCompany, true, false, false);
    }

    public static QixwebUser createUserWith(String aUserName, String aPassword, String aFirstName, String aLastName, String aEmail, String aCompany, boolean isToAuthenticateViaLdap, boolean hasWritePermissionFlag)
    {
        return new QixwebUser(aUserName, aPassword, aFirstName, aLastName, aEmail, aCompany, false, isToAuthenticateViaLdap, hasWritePermissionFlag);
    }    

    public static String generateRandomPassword()
    {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String itsName;
    private String itsPassword;
    private String itsFirstName;
    private String itsLastName;
    private boolean isSuperAdmin;
    private boolean itsEnableState;
    private boolean isToAuthenticateViaLdap;
    private boolean hasWritePermission;
    private String itsEmail;
    private String itsCompany;

    protected QixwebUser(String aName, String aPassword, String aFirstName, String aLastName, String aEmail, String aCompany, boolean isSuperAdminFlag, boolean isToAuthenticateViaLdapFlag, boolean hasWritePermissionFlag)
    {
        itsName = aName;
        itsPassword = aPassword;
        itsFirstName = aFirstName;
        itsLastName = aLastName;
        itsEmail = aEmail;
        itsCompany = aCompany;
        isSuperAdmin = isSuperAdminFlag;
        itsEnableState = true;
        isToAuthenticateViaLdap = isToAuthenticateViaLdapFlag;
        hasWritePermission = hasWritePermissionFlag;
    }

    public String name()
    {
        return itsName;
    }

    public void password(String newPassword)
    {
        itsPassword = newPassword;
    }

    public String password()
    {
        return itsPassword;
    }

    public String firstName()
    {
        return itsFirstName;
    }

    public String lastName()
    {
        return itsLastName;
    }
    
    public boolean isSuperAdmin()
    {
        return isSuperAdmin;
    }

    public int compareTo(Object anotherUser)
    {
        return name().toLowerCase().compareTo(((QixwebUser) anotherUser).name().toLowerCase());
    }

    public boolean isEnabled()
    {
        return itsEnableState;
    }

    public boolean isAuthenticatedViaLdap()
    {
        return isToAuthenticateViaLdap;
    }

    public void disableAuthenticationViaLdap()
    {
        isToAuthenticateViaLdap = false;
    }
    
    public void enableAuthenticationViaLdap()
    {
    	isToAuthenticateViaLdap = true;
    }
    
    public void disable()
    {
        itsEnableState = false;
    }

    public void enable()
    {
        itsEnableState = true;
    }
    
    public String toString()
    {
        return "[" + name() + "/" + password() + " - " + firstName() + " - " + lastName() + " - " +email() + " - " + company() + " - " + 
            (isSuperAdmin() ? "super admin" : "not super admin") + " - " + (hasWritePermission() ? "RW" : "R") + " - " + 
            (isEnabled() ? "enabled" : "disabled") + " - " + (isAuthenticatedViaLdap() ? "LDAP" : "INTERNAL") + "]";
    }    

    public boolean hasWritePermission()
    {
        return hasWritePermission;
    }

    public void grantWritePermission()
    {
        hasWritePermission = true;
    }

    public void removeWritePermission()
    {
        hasWritePermission = false;
    }

    public String email()
    {
        return itsEmail;
    }

    public String company()
    {
        return itsCompany;
    }

    public boolean equals(Object anotherObject)
    {
        return DeepEquals.equals(this, anotherObject);
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public void email(String anEmail)
    {
        itsEmail = anEmail;
    }    
}