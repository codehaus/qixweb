package org.qixweb.core;


public interface WebRefreshableCommand
{
	Browsable execute(QixwebEnvironment environment) throws Exception;
}
