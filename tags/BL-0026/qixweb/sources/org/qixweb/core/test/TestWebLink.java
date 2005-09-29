package org.qixweb.core.test;

import org.qixweb.core.WebLink;
import org.qixweb.util.EqualsBehaviourVerifier;
import org.qixweb.util.test.ExtendedTestCase;

public class TestWebLink extends ExtendedTestCase
{
    public void testLabel()
    {
        WebLink link = new WebLink(Object.class, "url", "pippo");
        assertEquals("pippo", link.label());
    }

    public void testEquals()
    {
        WebLink link = new WebLink(Object.class, "url", "pippo");
        WebLink sameLink = new WebLink(Object.class, "url", "pippo");
        WebLink differentLink = new WebLink(Object.class, "url", "pluto");
        EqualsBehaviourVerifier.check(link, sameLink, differentLink);
        EqualsBehaviourVerifier.checkHashCode(link, sameLink);

        WebLink sameLinkButDisabled = new WebLink(Object.class, "url", "pippo");
        sameLinkButDisabled.disable();
        assertNotEquals(link, sameLinkButDisabled);
    }
    
    public void testGhostLink()
    {
        WebLink expected = new WebLink("", "label");
        expected.disable();
        assertEquals(expected, WebLink.createGhostLink("label"));
    }
}
