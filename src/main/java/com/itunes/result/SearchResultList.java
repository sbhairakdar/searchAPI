package com.itunes.result;

import java.util.List;

public class SearchResultList {
	private int resultCount;
	private List<SearchResult> results;
	
	public int getResultCount() {
		return resultCount;
	}
	
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	
	public List<SearchResult> getResults() {
		return results;
	}
	
	public void setResults(List<SearchResult> results) {
		this.results = results;
	}
	
	private int responseStatusCode;

	public int getResponseStatusCode() {
		return responseStatusCode;
	}

	public void setResponseStatusCode(int responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}

	@Override
	public String toString() {
		return "MusicResultList [responseStatusCode=" + responseStatusCode + ", resultCount=" + resultCount + ", results="
				+ results + "]";
	}
	
	
	
	
}
