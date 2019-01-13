package com.ilter.analysis;

/*
@author Ýlter Lofçalý
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DataPull {

	private static Document doc;

	public static void main(String[] args) throws Exception {

		String myURL = "https://stackoverflow.com/questions?page=";

		List<ContentList> myList = new ArrayList<>();
		List<String> tagList = new ArrayList<>();

		int Page_Numbers = 10;

		for (int i = 1; i <= Page_Numbers; i++) {

			String new_url = myURL + i;

			doc = Jsoup.connect(new_url).get();

			for (Element element : doc.select("div.summary")) {

				ContentList myPojo = new ContentList();
				myPojo.setHeader(element.getElementsByClass("question-hyperlink").text());
				myPojo.setContent(element.getElementsByClass("excerpt").text());
				myPojo.setLabel(element.getElementsByClass("tags").text());
				myList.add(myPojo);

			}

		}
		for (ContentList myPojo : myList) {

			System.out.println("Question:" + myPojo.getHeader());
			System.out.println("Content:" + myPojo.getContent());
			System.out.println("Tags:" + myPojo.getLabel());

		}

		for (Element element : doc.select("a.post-tag")) {

			String myTags = element.getElementsByClass("post-tag").text();

			tagList.add(myTags);

		}

		for (String mytags : tagList) {

			System.out.println("Tag:" + mytags);

		}

		// int freq = Collections.frequency(tagList, "java");
		// System.out.println("Java kelimesinden:" + freq + "adet var");

		Counter(tagList);

	}

	public static void Counter(List<String> list) {

		Map<String, Integer> result = new HashMap<>();

		for (String unique : new HashSet<>(list)) {
			result.put(unique, Collections.frequency(list, unique));
		}

		System.out.println("Her bir tag adedi:");
		System.out.println(result);

	}
}
