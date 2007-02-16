package org.qixweb.core.test;

import java.io.IOException;
import java.util.*;

import org.qixweb.block.Function;
import org.qixweb.block.LightInternalIterator;
import org.qixweb.core.*;
import org.qixweb.util.*;
import org.qixweb.util.test.ExtendedTestCase;


public class TestWebNode extends ExtendedTestCase
{
    private FakeResponseHandler itsFakeResponseHandler;

    public void testFailWhenSendingHtml()
    {
        itsFakeResponseHandler.simulateExceptionSendingHTMLOnlyOnce();

        try
        {
            new AnyNode().displayThrough(itsFakeResponseHandler);
            fail("Exception raised rendering a node should re-thrown");
        }
        catch (IOException expectedException)
        {
        }
    }

    public void testFailWhenTemplateMerging() throws Exception
    {
        XpLogger.off();

        itsFakeResponseHandler.simulateFailureInTemplateMergingFor(AnyNode.class);
        try
        {
            new AnyNode().displayThrough(itsFakeResponseHandler);
            fail("Exception raised when rendering a node should be re-thrown");
        }
        catch (RuntimeException expectedException)
        {
        }

        XpLogger.resume();
    }

    public void testDisplay() throws IOException
    {
        AnyNode expectedNode = new AnyNode();

        expectedNode.displayThrough(itsFakeResponseHandler);

        assertEquals(expectedNode, itsFakeResponseHandler.displayedNode());
    }

    protected void setUp() throws Exception
    {
        itsFakeResponseHandler = new FakeResponseHandler();
    }

    public class ReturningUrlNode extends WebNode
    {
        private WebAppUrl itsUrl;

        public ReturningUrlNode(WebAppUrl anUrl)
        {
            itsUrl = anUrl;
        }

        public ReturningUrlNode()
        {
            this(new WebAppUrl(AnyNode.class, "base"));
        }

        public WebAppUrl url()
        {
            return itsUrl;
        }
    }

    public class ReturningFormNode extends WebNode
    {
        private WebAppUrl itsUrl;

        public ReturningFormNode(WebAppUrl anUrl)
        {
            itsUrl = anUrl;
        }

        public ReturningFormNode()
        {
            this(new WebAppUrl(AnyNode.class, "base"));
        }

        public WebForm form()
        {
            return new WebForm()
            {
                public WebAppUrl actionUrl()
                {
                    return itsUrl;
                }
            };
        }
    }

    public class ReturningUrlArrayNode extends WebNode
    {
        private WebAppUrl[] itsUrlArray;

        public ReturningUrlArrayNode(WebAppUrl[] someUrls)
        {
            itsUrlArray = someUrls;
        }

        public ReturningUrlArrayNode(WebAppUrl anUrl)
        {
            itsUrlArray = new WebAppUrl[] { anUrl };
        }

        public ReturningUrlArrayNode()
        {
            this(new WebAppUrl(AnyNode.class, "base"));
        }

        public WebAppUrl[] someUrls()
        {
            return itsUrlArray;
        }
    }
    
    public class ReturningUrlListNode extends WebNode
    {
        private List itsUrlList;

        public ReturningUrlListNode(List aUrlList)
        {
            itsUrlList = aUrlList;
        }

        public ReturningUrlListNode()
        {
            itsUrlList = new ArrayList();
            itsUrlList.add(new WebAppUrl(AnyNode.class, "base"));
        }

        public List someUrls()
        {
            return itsUrlList;
        }
    }    

    public class ReturningUrlIteratorNode extends WebNode
    {
        private ReturningUrlArrayNode itsEncapsulatedNode;

        public ReturningUrlIteratorNode(WebAppUrl[] someUrls)
        {
            itsEncapsulatedNode = new ReturningUrlArrayNode(someUrls);
        }

        public ReturningUrlIteratorNode(WebAppUrl anUrl)
        {
            this(new WebAppUrl[] { anUrl });
        }

        public ReturningUrlIteratorNode()
        {
            this(new WebAppUrl(AnyNode.class, "base"));
        }

        public Iterator someUrls()
        {
            if (itsEncapsulatedNode != null)
                return CollectionTransformer.toArrayList(itsEncapsulatedNode.someUrls()).iterator();
            else
                return null;
        }

        public ReturningUrlIteratorNode(Iterator iterator)
        {
        }
    }

    public class ReturningUrlViaArrayOfIteratorsNode extends WebNode
    {
        private ReturningUrlIteratorNode[] itsEncapsulatedNodes;

        public ReturningUrlViaArrayOfIteratorsNode()
        {
            this(new ReturningUrlIteratorNode[] { new ReturningUrlIteratorNode() });
        }

        public ReturningUrlViaArrayOfIteratorsNode(ReturningUrlIteratorNode[] someNodes)
        {
            itsEncapsulatedNodes = someNodes;
        }

        public Iterator[] someUrls()
        {
            if (itsEncapsulatedNodes != null)
                return (Iterator[]) LightInternalIterator.createOn(itsEncapsulatedNodes).collect(new Function()
                {
                    public Object eval(Object each)
                    {
                        ReturningUrlIteratorNode node = (ReturningUrlIteratorNode) each;
                        return node.someUrls();
                    }
                }, Iterator.class);
            else
                return null;
        }
    }
    
    public class ReturningObjectWithMethodReturingUrlNode extends WebNode
    {
        public Object objectWithMethodReturningUrlNode()
        {
            return new ReturningUrlNode();
        }
    }    

    public class ReturningUrlComprehensiveNode extends WebNode
    {
        private ReturningUrlNode itsReturningUrlNode;
        private ReturningUrlArrayNode itsReturningUrlArrayNode;
        private ReturningUrlListNode itsReturningUrlListNode; 
        private ReturningUrlIteratorNode itsReturningUrlIteratorNode;
        private ReturningUrlViaArrayOfIteratorsNode itsReturningUrlViaArrayOfIteratorsNode;

        public ReturningUrlComprehensiveNode()
        {
            itsReturningUrlNode = new ReturningUrlNode();
            itsReturningUrlArrayNode = new ReturningUrlArrayNode();
            itsReturningUrlListNode = new ReturningUrlListNode(); 
            itsReturningUrlIteratorNode = new ReturningUrlIteratorNode();
            itsReturningUrlViaArrayOfIteratorsNode = new ReturningUrlViaArrayOfIteratorsNode();
        }

        public WebAppUrl url()
        {
            return itsReturningUrlNode.url();
        }

        public WebAppUrl[] urls()
        {
            return itsReturningUrlArrayNode.someUrls();
        }

        public Iterator iterator()
        {
            return itsReturningUrlIteratorNode.someUrls();
        }

        public Iterator[] iterators()
        {
            return itsReturningUrlViaArrayOfIteratorsNode.someUrls();
        }
        
        public List urlList()
        {
            return itsReturningUrlListNode.someUrls();
        }

    }

    public static class ReturningNotValidUrlNode extends WebNode
    {
        final WebAppUrl neverReturnedUrl = new WebAppUrl(getClass(), "");

        WebAppUrl defaultVisibility()
        {
            return neverReturnedUrl;
        }

        private WebAppUrl privateVisibility()
        {
            return neverReturnedUrl;
        }

        protected WebAppUrl protectedVisibility()
        {
            return neverReturnedUrl;
        }

        public WebAppUrl oneParameter(String aString)
        {
            return neverReturnedUrl;
        }

        public static WebAppUrl staticModifier()
        {
            return new WebAppUrl(Object.class, "");
        }

        public Iterator oneParameterIterator(String aString)
        {
            return CollectionTransformer.toArrayList(new WebAppUrl[] { neverReturnedUrl }).iterator();
        }
    }

    public void testNoConnections()
    {
        WebNode node = new WebNode()
        {
        };
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningNullUrl()
    {
        ReturningUrlNode node = new ReturningUrlNode(null);
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningEmptyUrlArrays()
    {
        ReturningUrlArrayNode node = new ReturningUrlArrayNode(new WebAppUrl[0]);
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningNullUrlArrays()
    {
        ReturningUrlArrayNode node = new ReturningUrlArrayNode((WebAppUrl[]) null);
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningNullIterator()
    {
        ReturningUrlIteratorNode node = new ReturningUrlIteratorNode((Iterator) null);
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningNullArrayOfIterators()
    {
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode(null);
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningArrayOfNullIterators()
    {
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode(new ReturningUrlIteratorNode[] { new ReturningUrlIteratorNode((Iterator) null) });
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningArrayOfIteratorsWithoutElements()
    {
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode(new ReturningUrlIteratorNode[] { new ReturningUrlIteratorNode(new WebAppUrl[0]) });
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningArrayOfIteratorsWithNullElements()
    {
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode(new ReturningUrlIteratorNode[] { new ReturningUrlIteratorNode(new WebAppUrl[] { null }) });
        assertEmpty(node.connections());
    }

    public void testMethodsExclusion()
    {
        assertEmpty("no such methods shoulb be returned", new ReturningNotValidUrlNode().connections());
    }

    public void testNoConnectionsWithMethodsReturningIteratorWithNullElements()
    {
        ReturningUrlIteratorNode node = new ReturningUrlIteratorNode(new WebAppUrl[] { null });
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningIteratorWithoutElements()
    {
        ReturningUrlIteratorNode node = new ReturningUrlIteratorNode(new WebAppUrl[0]);
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningUrlArraysWithNullElements()
    {
        ReturningUrlArrayNode node = new ReturningUrlArrayNode((WebAppUrl) null);
        assertEmpty(node.connections());
    }

    public void testMethodsReturningUrlArraysMixedWithNulls()
    {
        WebAppUrl validUrl1 = new WebAppUrl(getClass(), "");
        WebAppUrl validUrl2 = new WebAppUrl(getClass(), "A");
        ReturningUrlArrayNode node = new ReturningUrlArrayNode(new WebAppUrl[] { validUrl2, null, validUrl1 });
        ArrayAsserter.assertEqualsIgnoringOrder(new WebAppUrl[] { validUrl1, validUrl2 }, node.connections());
    }

    public void testMethodsReturningUrlIteratorMixedWithNulls()
    {
        WebAppUrl validUrl1 = new WebAppUrl(getClass(), "");
        WebAppUrl validUrl2 = new WebAppUrl(getClass(), "A");
        ReturningUrlIteratorNode node = new ReturningUrlIteratorNode(new WebAppUrl[] { validUrl2, null, validUrl1 });
        ArrayAsserter.assertEqualsIgnoringOrder(new WebAppUrl[] { validUrl1, validUrl2 }, node.connections());
    }

    public void testMethodsReturningUrlArrays()
    {
        ReturningUrlArrayNode node = new ReturningUrlArrayNode();
        ArrayAsserter.assertEqualsIgnoringOrder(node.someUrls(), node.connections());
    }

    public void testAllMethods()
    {
        WebAppUrl url = new ReturningUrlNode().url();
        ReturningUrlComprehensiveNode node = new ReturningUrlComprehensiveNode();
        ArrayAsserter.assertEquals(new WebAppUrl[] { url, url, url, url, url }, node.connections());
    }
    
    public void testMethodsReturningUrlIterator()
    {
        ReturningUrlIteratorNode node = new ReturningUrlIteratorNode();
        ArrayAsserter.assertEqualsIgnoringOrder(new WebAppUrl[] { (WebAppUrl) node.someUrls().next() }, node.connections());
    }

    public void testMethodsReturningArrayOfIterators()
    {
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode();
        ArrayAsserter.assertEqualsIgnoringOrder(new WebAppUrl[] { (WebAppUrl) node.someUrls()[0].next() }, node.connections());
    }

    public void testMethodsReturningArrayOfIteratorsWithTwoIteratorsAndMixedWithNullElements()
    {
        WebAppUrl validUrl1 = new WebAppUrl(getClass(), "");
        WebAppUrl validUrl2 = new WebAppUrl(getClass(), "1");
        WebAppUrl validUrl3 = new WebAppUrl(getClass(), "2");
        ReturningUrlIteratorNode node1 = new ReturningUrlIteratorNode(new WebAppUrl[] { null, validUrl1 });
        ReturningUrlIteratorNode node2 = new ReturningUrlIteratorNode(new WebAppUrl[] { validUrl2, null, validUrl3 });
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode(new ReturningUrlIteratorNode[] { node1, node2 });
        ArrayAsserter.assertEqualsIgnoringOrder(new WebAppUrl[] { validUrl1, validUrl2, validUrl3 }, node.connections());
    }

    public void testMethodsReturningUrl()
    {
        ReturningUrlNode node = new ReturningUrlNode();
        ArrayAsserter.assertEqualsIgnoringOrder(new WebAppUrl[] { node.url() }, node.connections());
    }

    public void testMethodsReturningForm()
    {
        ReturningFormNode node = new ReturningFormNode();
        ArrayAsserter.assertEqualsIgnoringOrder(new WebAppUrl[] { node.form().actionUrl() }, node.connections());
    }
}