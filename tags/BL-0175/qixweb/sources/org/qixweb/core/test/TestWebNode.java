package org.qixweb.core.test;

import java.io.IOException;
import java.util.*;

import org.qixweb.block.Function;
import org.qixweb.block.LightInternalIterator;
import org.qixweb.core.*;
import org.qixweb.core.test.support.FakeResponseHandler;
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
        private QixwebUrl itsUrl;

        public ReturningUrlNode(QixwebUrl anUrl)
        {
            itsUrl = anUrl;
        }

        public ReturningUrlNode()
        {
            this(new QixwebUrl(AnyNode.class));
        }

        public QixwebUrl url()
        {
            return itsUrl;
        }
    }

    public class ThrowingExceptionOnUrlNode extends WebNode
    {
        public final IllegalStateException GENERATED_EXCEPTION = new IllegalStateException("fake generated exception");

        public QixwebUrl url()
        {
            throw GENERATED_EXCEPTION;
        }
    }

    public class ReturningFormNode extends WebNode
    {
        private QixwebUrl itsUrl;

        public ReturningFormNode(QixwebUrl anUrl)
        {
            itsUrl = anUrl;
        }

        public ReturningFormNode()
        {
            this(new QixwebUrl(AnyNode.class));
        }

        public WebForm form()
        {
            return new WebForm()
            {
                public QixwebUrl actionUrl()
                {
                    return itsUrl;
                }
            };
        }
    }

    public class ReturningUrlArrayNode extends WebNode
    {
        private QixwebUrl[] itsUrlArray;

        public ReturningUrlArrayNode(QixwebUrl[] someUrls)
        {
            itsUrlArray = someUrls;
        }

        public ReturningUrlArrayNode(QixwebUrl anUrl)
        {
            itsUrlArray = new QixwebUrl[] { anUrl };
        }

        public ReturningUrlArrayNode()
        {
            this(new QixwebUrl(AnyNode.class));
        }

        public QixwebUrl[] someUrls()
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
            itsUrlList.add(new QixwebUrl(AnyNode.class));
        }

        public List someUrls()
        {
            return itsUrlList;
        }
    }    

    public class ReturningUrlIteratorNode extends WebNode
    {
        private ReturningUrlArrayNode itsEncapsulatedNode;

        public ReturningUrlIteratorNode(QixwebUrl[] someUrls)
        {
            itsEncapsulatedNode = new ReturningUrlArrayNode(someUrls);
        }

        public ReturningUrlIteratorNode(QixwebUrl anUrl)
        {
            this(new QixwebUrl[] { anUrl });
        }

        public ReturningUrlIteratorNode()
        {
            this(new QixwebUrl(AnyNode.class));
        }

        public Iterator someUrls()
        {
            if (itsEncapsulatedNode != null)
                return CollectionUtil.toList(itsEncapsulatedNode.someUrls()).iterator();
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

        public QixwebUrl url()
        {
            return itsReturningUrlNode.url();
        }

        public QixwebUrl[] urls()
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
        final QixwebUrl neverReturnedUrl = new QixwebUrl(getClass());

        QixwebUrl defaultVisibility()
        {
            return neverReturnedUrl;
        }

        private QixwebUrl privateVisibility()
        {
            return neverReturnedUrl;
        }

        protected QixwebUrl protectedVisibility()
        {
            return neverReturnedUrl;
        }

        public QixwebUrl oneParameter(String aString)
        {
            return neverReturnedUrl;
        }

        public static QixwebUrl staticModifier()
        {
            return new QixwebUrl(Object.class);
        }

        public Iterator oneParameterIterator(String aString)
        {
            return CollectionUtil.toList(new QixwebUrl[] { neverReturnedUrl }).iterator();
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
        ReturningUrlArrayNode node = new ReturningUrlArrayNode(new QixwebUrl[0]);
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningNullUrlArrays()
    {
        ReturningUrlArrayNode node = new ReturningUrlArrayNode((QixwebUrl[]) null);
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
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode(new ReturningUrlIteratorNode[] { new ReturningUrlIteratorNode(new QixwebUrl[0]) });
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningArrayOfIteratorsWithNullElements()
    {
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode(new ReturningUrlIteratorNode[] { new ReturningUrlIteratorNode(new QixwebUrl[] { null }) });
        assertEmpty(node.connections());
    }

    public void testMethodsExclusion()
    {
        assertEmpty("no such methods shoulb be returned", new ReturningNotValidUrlNode().connections());
    }

    public void testNoConnectionsWithMethodsReturningIteratorWithNullElements()
    {
        ReturningUrlIteratorNode node = new ReturningUrlIteratorNode(new QixwebUrl[] { null });
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningIteratorWithoutElements()
    {
        ReturningUrlIteratorNode node = new ReturningUrlIteratorNode(new QixwebUrl[0]);
        assertEmpty(node.connections());
    }

    public void testNoConnectionsWithMethodsReturningUrlArraysWithNullElements()
    {
        ReturningUrlArrayNode node = new ReturningUrlArrayNode((QixwebUrl) null);
        assertEmpty(node.connections());
    }

    public void testMethodsReturningUrlArraysMixedWithNulls()
    {
        QixwebUrl validUrl1 = new QixwebUrl(getClass());
        QixwebUrl validUrl2 = new QixwebUrl(getClass());
        ReturningUrlArrayNode node = new ReturningUrlArrayNode(new QixwebUrl[] { validUrl2, null, validUrl1 });
        ArrayAsserter.assertEqualsIgnoringOrder(new QixwebUrl[] { validUrl1, validUrl2 }, node.connections());
    }

    public void testMethodsReturningUrlIteratorMixedWithNulls()
    {
        QixwebUrl validUrl1 = new QixwebUrl(getClass());
        QixwebUrl validUrl2 = new QixwebUrl(getClass());
        ReturningUrlIteratorNode node = new ReturningUrlIteratorNode(new QixwebUrl[] { validUrl2, null, validUrl1 });
        ArrayAsserter.assertEqualsIgnoringOrder(new QixwebUrl[] { validUrl1, validUrl2 }, node.connections());
    }

    public void testMethodsReturningUrlArrays()
    {
        ReturningUrlArrayNode node = new ReturningUrlArrayNode();
        ArrayAsserter.assertEqualsIgnoringOrder(node.someUrls(), node.connections());
    }

    public void testGettingAnExceptionOnConnectionsIsEncapsulatedInRuntimeException()
    {
        ThrowingExceptionOnUrlNode node = new ThrowingExceptionOnUrlNode();
        try
        {
            node.connections();
            fail("An exception is expected");
        }
        catch (RuntimeException re)
        {
            assertTrue(re.getCause().getCause().equals(node.GENERATED_EXCEPTION));
        }
    }
    
    public void testAllMethods()
    {
        QixwebUrl url = new ReturningUrlNode().url();
        ReturningUrlComprehensiveNode node = new ReturningUrlComprehensiveNode();
        ArrayAsserter.assertEquals(new QixwebUrl[] { url, url, url, url, url }, node.connections());
    }
    
    public void testMethodsReturningUrlIterator()
    {
        ReturningUrlIteratorNode node = new ReturningUrlIteratorNode();
        ArrayAsserter.assertEqualsIgnoringOrder(new QixwebUrl[] { (QixwebUrl) node.someUrls().next() }, node.connections());
    }

    public void testMethodsReturningArrayOfIterators()
    {
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode();
        ArrayAsserter.assertEqualsIgnoringOrder(new QixwebUrl[] { (QixwebUrl) node.someUrls()[0].next() }, node.connections());
    }

    public void testMethodsReturningArrayOfIteratorsWithTwoIteratorsAndMixedWithNullElements()
    {
        QixwebUrl validUrl1 = new QixwebUrl(getClass());
        QixwebUrl validUrl2 = new QixwebUrl(getClass());
        QixwebUrl validUrl3 = new QixwebUrl(getClass());
        ReturningUrlIteratorNode node1 = new ReturningUrlIteratorNode(new QixwebUrl[] { null, validUrl1 });
        ReturningUrlIteratorNode node2 = new ReturningUrlIteratorNode(new QixwebUrl[] { validUrl2, null, validUrl3 });
        ReturningUrlViaArrayOfIteratorsNode node = new ReturningUrlViaArrayOfIteratorsNode(new ReturningUrlIteratorNode[] { node1, node2 });
        ArrayAsserter.assertEqualsIgnoringOrder(new QixwebUrl[] { validUrl1, validUrl2, validUrl3 }, node.connections());
    }

    public void testMethodsReturningUrl()
    {
        ReturningUrlNode node = new ReturningUrlNode();
        ArrayAsserter.assertEqualsIgnoringOrder(new QixwebUrl[] { node.url() }, node.connections());
    }

    public void testMethodsReturningForm()
    {
        ReturningFormNode node = new ReturningFormNode();
        ArrayAsserter.assertEqualsIgnoringOrder(new QixwebUrl[] { node.form().actionUrl() }, node.connections());
    }
    
    public void testErrorMessages() throws Exception
    {
        WebNode node = new AnyNode("title");
        assertEquals(StringUtil.EMPTY, node.errorMessageFor("parameter1"));

        HashMap messages = new HashMap();
        messages.put("parameter1", "invalid date&time");
        node = new AnyNode("title", messages);
        assertEquals("invalid date&amp;time", node.errorMessageFor("parameter1"));
    }
    
}