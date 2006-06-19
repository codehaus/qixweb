package org.qixweb.core;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.qixweb.util.XpLogger;


public class VelocityInitializer
{
    public static VelocityEngine init(String aTemplateDir)
	{
        VelocityEngine velocityEngine;
		try
		{
			XpLogger.info("Velocity template dir: " + aTemplateDir);				
			velocityEngine = new VelocityEngine();
			velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, aTemplateDir);
            velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
			velocityEngine.init();
		}
		catch (Exception e)
		{
			XpLogger.logException(e);
			throw new RuntimeException(e);
		}
		return velocityEngine;
	}
}
