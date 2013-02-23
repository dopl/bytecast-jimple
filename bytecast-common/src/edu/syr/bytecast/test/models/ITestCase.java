/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.syr.bytecast.test.interfaces;


/**
 *
 * @author dhrumin
 */
public interface ITestCase {
    TestResult getResult();
    String getTestName();
    void setTestName(String testName);
}
