package com.mycompany.parse;

import java.nio.charset.Charset;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    /**
     * Test of main method, of class App.
     */
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = {"/home/jpeak5/Dropbox/Documents/school/csc4402/par-test"};
        App.main(args);

    }

    /**
     * Test of readFile method, of class App.
     */
    public void testReadFile() throws Exception {
        System.out.println("readFile");
        String path = "src/test/java/abba";
        Charset encoding = Charset.forName("UTF-8");
        String expResult = "abba";
        String result = App.readFile(path, encoding);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
}
