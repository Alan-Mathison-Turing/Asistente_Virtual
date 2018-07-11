package edu.unlam.asistente.nine_gag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import at.neonartworks.ninesearch.core.GagPost;
import at.neonartworks.ninesearch.core.GagSearchV2;

public class GagSearch {

	private static List<GagPost> posts = new ArrayList<GagPost>();

	public static void main(String[] args) {
		GagSearchV2 gs = new GagSearchV2(); // create a new instance of
											// GagSearch.

		try {
			// ALWAYS call the initSearch before the search method.
			gs.initSearch("VSauce");

			// use the search method to retrieve the posts.
			posts = gs.search();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// check the retrieved posts by printing everyone to the console.
		for (GagPost p : posts) {
			System.out.println(p.toString());

		}

	}

}
