package com.itunes.search.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;

import com.google.gson.Gson;
import com.itunes.result.SearchResultList;
import com.itunes.search.test.data.ITunesAPITestData;

/**
 * @author Sarfraz Bhairakdar
 * 
 */
public class ITunesSearchAPI {

	private static final String itunesUrl = "https://itunes.apple.com/search?";
	private static final Logger logger = Logger.getLogger(ITunesSearchAPI.class.getName());
	
	//public static String fileName = "SearchApiTestData.xls";

	public static Workbook wbook;
	public static WritableWorkbook wwbCopy;
	
	
	public SearchResultList search(ITunesAPITestData requestData) {
		
		URL url = buildUrl(requestData);
		
		HttpURLConnection httpConn = null;
		BufferedReader reader = null;
		String line;
		StringBuilder responseData = new StringBuilder();
		SearchResultList musicResultList =  null;
		try {
			httpConn = (HttpURLConnection) url.openConnection();
			//httpConn.getResponseCode()
			reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				responseData.append(line);
			}
			
			System.out.println(responseData);
			Gson gson = new Gson();
			musicResultList =  gson.fromJson(responseData.toString(), SearchResultList.class);
			musicResultList.setResponseStatusCode(httpConn.getResponseCode());
			reader.close();
		} catch (IOException e) {
			if(e.getMessage().startsWith("Server returned HTTP response code: 400 for URL:")){
				musicResultList = new SearchResultList();
				musicResultList.setResponseStatusCode(HttpStatus.SC_BAD_REQUEST);
			}
			logger.log(Level.ALL, e.getMessage(), e);
			
		}
		
		
		return musicResultList;
	}
	
	
	/**
	 * @param data - Test data
	 * @return URL
	 * 
	 * This method creates URL along with query string by encoding params with UTF-8
	 */
	private URL buildUrl(ITunesAPITestData data) {
		
		URL url = null;
		StringBuilder queryString = new StringBuilder();
		
		try {
			queryString.append("term=" + URLEncoder.encode(data.getTerm(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
		
		if(data.getCountry() != null) {
			queryString.append("&country=" + data.getCountry());
		}
		
		if(data.getMedia() != null) {
			queryString.append("&media=" + data.getMedia().toString());
		}
		
		if(data.getLimit() != null) {
			queryString.append("&limit=" + data.getLimit());
		}
		
		try {
			url = new URL(itunesUrl + queryString);
		} catch (MalformedURLException e) {
			logger.log(Level.ALL, e.getMessage(), e);
		}
		
		System.out.println(url);
		return url;
	}
	
	
	
	/*public static void main(String[] args) throws BiffException, IOException {
		// TODO Auto-generated method stub
		logger.log(Level.ALL, "starting the API call");
		System.out.println("starting the API call");
		
		for (ITunesAPITestData testData: getContent(1, 2)){
			MusicResultList musicResultList = search(testData);
			//logger.log(Level.ALL, musicResultList.toString());
			System.out.println( musicResultList.toString());
		}
	}
*/
	
	/*public static ITunesAPITestData[] getContent(int from, int to) throws BiffException, IOException{
		
		ITunesAPITestData [] iTunesAPITestDataSet = new ITunesAPITestData[to];
		Workbook wb = Workbook.getWorkbook(new File(fileName));
		//String getContentFromXl = wb.getSheet(testDataSheet).getCell(wb.getSheet(testDataSheet).findCell(Columnname).getColumn(), Rowno).getContents();
		
		for(int i=from,k=0;i<=to;++i,++k){		
			
			Cell[] cells = wb.getSheet(testDataSheet).getRow(i);
			iTunesAPITestDataSet[k]= new ITunesAPITestData().setTestCaseNo(cells[0].getContents())
				.setTestDescription(cells[1].getContents())
				.setTerm(cells[2].getContents())
				.setCountry(cells[3].getContents())
				.setMedia(cells[4].getContents())
				.setLimit(cells[5].getContents());	 
			}
		 
		return iTunesAPITestDataSet;
				 
	}*/

}
