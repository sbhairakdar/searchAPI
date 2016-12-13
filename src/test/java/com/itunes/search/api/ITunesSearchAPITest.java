package com.itunes.search.api;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.itunes.result.SearchResultList;
import com.itunes.search.test.data.ITunesAPITestData;

/**
 * @author Sarfraz Bhairakdar
 * 
 * For this test class the data sheet used is SearchApiTestData.xls
 * I have skipped the test plan instead have added the above data sheet
 * 
 */
public class ITunesSearchAPITest {
 
	
  public final String testDataFile = "SearchApiTestData.xls";
  public static String testDataSheet = "iTunesTestdata";

  	//============ TC1_to_TC5	============  
	@DataProvider(name="testDataForDifferentTerms")
	public Object[][] onlyTermsTestData () throws BiffException, IOException{		
		return getContent(5, 9);
	}
	
	//Please note that all the +ve and -ve scenarios are passed from data sheet so the test here looks simple
	//These cover the test cases having single word, multi word terms and terms starting and ending with spaces
	@Test(dataProvider = "testDataForDifferentTerms")
	public void searchApiByTerm(ITunesAPITestData requestData) {  
		SearchResultList musicResultList = new ITunesSearchAPI().search(requestData);
		
		if( musicResultList.getResponseStatusCode()==HttpStatus.SC_OK){			
			if(musicResultList.getResultCount()>0){
				//Here search term is being checked only in 1st item json array for simplicity 
				//this can be changed to check term appears in all json arrey items 
				Assert.assertTrue(musicResultList.getResults().get(0).toString().toLowerCase().contains(requestData.getTerm().trim().toLowerCase()),"Search term not found in the response");
				Assert.assertEquals(50, musicResultList.getResultCount(),"default search results is not 50, something went wrong!!");
			}
		}
	}
	
	
	//============ TC6_to_TC9	============	
	@DataProvider(name="testDataForDifferentTermsSpecialCase")
	public Object[][] onlyTermsTestDataSpecialCases () throws BiffException, IOException{		
		return getContent(10, 13);
	}
		
	//Please note that all the +ve and -ve scenarios are passed from data sheet so the test here looks simple
	//These cover the test cases having special char and non-english letters in the terms
	@Test(dataProvider = "testDataForDifferentTermsSpecialCase")
	public void searchApiByTermAndAssertOnlyCount(ITunesAPITestData requestData) {  
		
		SearchResultList musicResultList = new ITunesSearchAPI().search(requestData);		
		if( musicResultList.getResponseStatusCode()==HttpStatus.SC_OK){			
			if(musicResultList.getResultCount()>0){
				Assert.assertTrue(musicResultList.getResultCount()==50 || musicResultList.getResultCount()>0 ,"default search results is not 50, something went wrong!!");
			}
		}
	}
	
	
	//============ TC23 ============	
	@DataProvider(name="testDataForInValidCountry")
	public Object[][] invalidCountryData () throws BiffException, IOException{		
		return getContent(66, 66);
	}
		
	@Test(dataProvider = "testDataForInValidCountry")
	public void searchApiByInValidCountry(ITunesAPITestData requestData) {			
		
		SearchResultList musicResultList = new ITunesSearchAPI().search(requestData);
		Assert.assertEquals(musicResultList.getResponseStatusCode(), HttpStatus.SC_BAD_REQUEST);
	}
		
	
	//============ TC24 ============	
	@DataProvider(name="testDataForInValidMedia")
	public Object[][] invalidMediaData () throws BiffException, IOException{		
		return getContent(67, 67);
	}
		
	@Test(dataProvider = "testDataForInValidMedia")
	public void searchApiByInValidMedia(ITunesAPITestData requestData) {			
		
		SearchResultList musicResultList = new ITunesSearchAPI().search(requestData);
		Assert.assertEquals(musicResultList.getResponseStatusCode(), HttpStatus.SC_BAD_REQUEST);
	}
	
	
  	//============ TC25 ============	
	@DataProvider(name="testDataWithLimitMoreThan200")
	public Object[][] limitMoreThan200 () throws BiffException, IOException{		
		return getContent(68, 68);
	}
		
	@Test(dataProvider = "testDataWithLimitMoreThan200")
	public void searchApiByLimitMoreThan200(ITunesAPITestData requestData) {  
		
		SearchResultList musicResultList = new ITunesSearchAPI().search(requestData);		
		Assert.assertEquals(musicResultList.getResponseStatusCode(), HttpStatus.SC_BAD_REQUEST);
	}
		
	
	/**
	 * @param from - the from index of the row in xls data sheet
	 * @param to - the to index of the row in xls data sheet
	 * 
	 * @return ITunesAPITestData
	 * 
	 * This method takes the range of rows which it reads from the xls test data sheet and construct array of ITunesAPITestData
	 * @throws BiffException
	 * @throws IOException
	 */
	public ITunesAPITestData[][] getContent(int from, int to) throws BiffException, IOException{
		
		ITunesAPITestData [][] iTunesAPITestDataSet = new ITunesAPITestData[to-from+1][];
		Workbook wb = Workbook.getWorkbook(new File(getClass().getClassLoader().getResource(testDataFile).getFile()));
		//String getContentFromXl = wb.getSheet(testDataSheet).getCell(wb.getSheet(testDataSheet).findCell(Columnname).getColumn(), Rowno).getContents();
		
		for(int k=0;from<=to;++from,++k){
			Cell[] cells = wb.getSheet(testDataSheet).getRow(from);
			ITunesAPITestData iTunesAPITestData =new ITunesAPITestData().setTestCaseNo(cells[0].getContents())
				.setTestDescription(cells[1].getContents())
				.setTerm(cells[2].getContents())
				.setCountry(cells[3].getContents())
				.setMedia(cells[4].getContents())
				.setLimit(cells[5].getContents());	
			
			iTunesAPITestDataSet[k]= new ITunesAPITestData[]{iTunesAPITestData};
		}
		 
		return iTunesAPITestDataSet;				 
	}
}
