package org.qixweb.core.test;

import junit.framework.TestCase;

import org.apache.velocity.VelocityContext;
import org.qixweb.core.VelocityHTMLBinder;


public class TestVelocityHTMLBinder extends TestCase
{
	public void testBinding()
	{
		VelocityContext context = new VelocityContext();
		AnyNode node = new AnyNode();
		VelocityHTMLBinder html = new VelocityHTMLBinder(node);
		html.bindTo(context);

		assertEquals(node, context.get("node"));
	}

}

