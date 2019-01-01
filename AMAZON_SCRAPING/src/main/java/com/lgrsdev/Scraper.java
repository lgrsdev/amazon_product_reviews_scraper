package com.lgrsdev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Scraper {
	
	private static final String PRODUCT_ID 	= "B004R69AG8";
	private static final String USER_AGENT 	= "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36";
	private static final String URL 		= "http://www.amazon.com/product-reviews/%s/?showViewpoints=0&sortBy=byRankDescending&pageSize=50&pageNumber=";
		
	public static void main(String[] args) throws Exception {
		System.out.println(scrapReviews());
	}
	
	private static List<Review> scrapReviews() throws Exception {
		int pageNumber = 1;
		Document reviewsPage = getReviewsPage(pageNumber);
		List<Review> reviews = new ArrayList<>();
		int lastPageNum = Integer.parseInt(reviewsPage.select(".a-pagination .page-button").last().text());
		do {
			reviews.addAll(scrapPage(reviewsPage.select(".a-section.review")));
			reviewsPage = getReviewsPage(++pageNumber);
		} while (pageNumber <= lastPageNum);
		return reviews;
	}
	
	private static Document getReviewsPage(int pageNumber) throws Exception {
		return Jsoup.connect(String.format(URL, PRODUCT_ID).concat(Integer.toString(pageNumber))).userAgent(USER_AGENT).get();
	}

	private static List<Review> scrapPage(Elements reviewsElements) {
		return reviewsElements.stream()
		.map(reviewElement -> {
			Review review = new Review();
			review.setAuthor(reviewElement.select(".author[data-hook='review-author']").text());
			review.setTitle(reviewElement.select("a.review-title").text());
			review.setContent(reviewElement.select(".review-text").text());
			return review;
		})
		.collect(Collectors.toList());
	}
}
