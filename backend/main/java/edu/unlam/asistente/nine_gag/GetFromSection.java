package edu.unlam.asistente.nine_gag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.neonartworks.ninesearch.core.GagPost;
import at.neonartworks.ninesearch.core.GagSearchV2;
import at.neonartworks.ninesearch.utils.GagSection;


public class GetFromSection {
	
		private static List<GagPost> posts = new ArrayList<GagPost>();

		public static void main(String[] args) {
			GagSearchV2 gs = new GagSearchV2(); // create a new instance of
												// GagSearch.

			try {
				// use the getFromSectoin command to retrieve the posts.
				posts = gs.getFromSection(GagSection.CUTE_ANIMALS);
				gs.initSearch("DOGS");
				for (GagPost p : posts) {
					System.out.println(p.toString());
					
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			// check the retrieved posts by printing everyone to the console.

		}

}
