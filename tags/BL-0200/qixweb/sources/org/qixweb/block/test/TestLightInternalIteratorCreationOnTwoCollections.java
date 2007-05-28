package org.qixweb.block.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.qixweb.block.AlwaysTruePredicate;
import org.qixweb.block.LightInternalIterator;

public class TestLightInternalIteratorCreationOnTwoCollections extends TestCase
{
    private class Pair
    {
        Object first;
        Object second;

        Pair(Object[] twoElements)
        {
            first = twoElements[0];
            second = twoElements[1];
        }

        Pair(Object first, Object second)
        {
            this.first = first;
            this.second = second;
        }

        void assertEquals(String aMessage, Pair anotherPair)
        {
            Assert.assertEquals(aMessage + " - wrong first element", first, anotherPair.first);
            Assert.assertEquals(aMessage + " - wrong second element", second, anotherPair.second);
        }

    }

    public void testBothEmptyCollections()
    {
        LightInternalIterator iterator = LightInternalIterator.createOn(new Object[0], new Object[0]);
        assertEquals(0, iterator.count(new AlwaysTruePredicate()));
    }

    public void testOneEmptyCollection()
    {
        LightInternalIterator iterator = LightInternalIterator.createOn(new Object[] { "ugl" }, new Object[0]);
        assertEquals(0, iterator.count(new AlwaysTruePredicate()));

        iterator = LightInternalIterator.createOn(new Object[0], new Object[] { "ugl", "qua" });
        assertEquals(0, iterator.count(new AlwaysTruePredicate()));
    }

    public void testOneElementCollections()
    {
        LightInternalIterator iterator = LightInternalIterator.createOn(new Object[] { "ugl" }, new Object[] { "cas" });

        Object[][] pairElements = (Object[][]) iterator.select(new AlwaysTruePredicate(), Object[].class);
        assertEquals("There should be one pair", 1, pairElements.length);

        Pair expecatedPair = new Pair("ugl", "cas");
        expecatedPair.assertEquals("Wrong pair", new Pair(pairElements[0]));
    }

    public void testManyElementCollections()
    {
        LightInternalIterator iterator = LightInternalIterator.createOn(new Object[] { "ugl", "vaa", "dip" }, new Object[] { "blocks", "utilities" });
        Object[][] pairElements = (Object[][]) iterator.select(new AlwaysTruePredicate(), Object[].class);
        assertEquals("There should be six pairs", 6, pairElements.length);

        Pair[] expectedPairs = new Pair[] { new Pair("ugl", "blocks"), new Pair("ugl", "utilities"), new Pair("vaa", "blocks"), new Pair("vaa", "utilities"), new Pair("dip", "blocks"),
                new Pair("dip", "utilities") };

        for (int i = 0; i < pairElements.length; i++)
            expectedPairs[i].assertEquals("Wrong pair n." + i, new Pair(pairElements[i]));
    }
}
