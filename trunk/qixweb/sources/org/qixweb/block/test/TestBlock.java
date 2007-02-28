package org.qixweb.block.test;

import junit.framework.TestCase;

import org.qixweb.block.Block;

public class TestBlock extends TestCase
{

    private Block itsBlock;

    protected void setUp()
    {
        itsBlock = new Block();
    }

    public void testDefaultEval()
    {
        Object each = new Object();
        Object runningValue = new Object();
        assertNull(itsBlock.eval(runningValue, each));
        assertNull(itsBlock.eval(each));
    }

    public void testDefaultIs()
    {
        assertTrue(!itsBlock.is(new Object()));
    }

    public void testDefaultRun()
    {
        itsBlock.run(new Object());
    }
}
