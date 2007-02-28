package org.qixweb.time.test;

import org.qixweb.time.QixwebTime;
import org.qixweb.time.RealTimeProvider;
import org.qixweb.util.test.ExtendedTestCase;

public class TestTimeProvider extends ExtendedTestCase
{
    private RealTimeProvider itsTimeProvider;

    public void testNow() throws InterruptedException
    {
        QixwebTime time1 = itsTimeProvider.now();
        Thread.sleep(1000);
        QixwebTime time2 = itsTimeProvider.now();
        assertNotEquals("Changing second of day times should differ", time1, time2);
    }

    public void testThreeInvocationsWithinASecond() throws Exception
    {
        QixwebTime time1 = itsTimeProvider.now();
        Thread.sleep(10);
        QixwebTime time2 = itsTimeProvider.now();
        Thread.sleep(10);
        QixwebTime time3 = itsTimeProvider.now();
        assertTrue("At least 2 invocations should be equal", time1.equals(time2) || time2.equals(time3));
    }

    protected void setUp()
    {
        itsTimeProvider = new RealTimeProvider();
    }
}
