package org.qixweb.core;

import java.io.Serializable;


public interface WebCommand extends Serializable
{
	WebAppUrl execute(QixwebEnvironment environment) throws Exception;
}
