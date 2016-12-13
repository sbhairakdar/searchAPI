package com.itunes.search.test.data;

public class ITunesAPITestData {
	
	String testCaseNo;
	
	String testDescription;
	
	String term;
	
	String country;
	
	String media;
	
	String limit;

	public String getTestCaseNo() {
		return testCaseNo;
	}

	public ITunesAPITestData setTestCaseNo(String testCaseNo) {
		this.testCaseNo = testCaseNo;
		return this;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public ITunesAPITestData setTestDescription(String testDescription) {
		this.testDescription = testDescription;
		return this;
	}

	public String getTerm() {
		return term;
	}

	public ITunesAPITestData setTerm(String term) {
		this.term = term;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public ITunesAPITestData setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getMedia() {
		return media;
	}

	public ITunesAPITestData setMedia(String media) {
		this.media = media;
		return this;
	}

	public String getLimit() {
		return limit;
	}

	public ITunesAPITestData setLimit(String limit) {
		this.limit = limit;
		return this;
	}

	@Override
	public String toString() {
		return "ITunesAPITestData [testCaseNo=" + testCaseNo
				+ ", testDescription=" + testDescription + ", term=" + term
				+ ", country=" + country + ", media=" + media + ", limit="
				+ limit + "]";
	}
	
	

}
