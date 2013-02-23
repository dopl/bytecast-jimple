/*
 * This file is part of Bytecast.
 *
 * Bytecast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bytecast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bytecast.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package edu.syr.bytecast.test.interfaces;

import java.util.ArrayList;
import java.util.List;

public class TestGenerator {
    
    private List<ITestCase> m_testCases = new ArrayList<ITestCase>();
    
    public void addTest(ITestCase testCase)
    {
        m_testCases.add(testCase);
    }
    
    public void start()
    {
        for(ITestCase testCase : m_testCases)
        {
            testCase.getResult();
        }
    }
    
    public void printResults()
    {
        System.out.println("Printing Result");
        for(ITestCase testCase : m_testCases)
        {
            System.out.println("Test Name : " + testCase.getTestName());
            TestResult result = testCase.getResult();
            if(result.getPassed())
            {
                System.out.println("Test Passed!!"); 
                System.out.println("Test Message : ");
                
            }
            else
            {
                System.out.println("Test Failed!");
                System.out.println("Error Messsage : ");
            }
           System.out.println(result.getMessage()); 
        }
    }    
}
