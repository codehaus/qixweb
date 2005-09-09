package org.qixweb.core;

import java.io.Serializable;

public interface WebCommand extends Serializable
{
	Browsable execute(QixwebEnvironment environment) throws Exception;
}
