package me.otho.metamods.core.jsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/**
 * This class is responsible for reading json files from a path
 * and returning their data as JsonArrays
 * 
 * @author Otho
 *
 */
public class JsonHandler {
	
	public static ArrayList<JsonObject> read (File jsonFolder) {
		// Stores read data
		ArrayList<JsonObject> jsonData = new ArrayList<JsonObject>();
		
		if ( jsonFolder.exists() ) {
			// Create a json file filter
			FilenameFilter jsonFilter = new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".json");
				}
			};
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			
			JsonReader fileReader;
			
			// Get a list of all json files
			File[] files = jsonFolder.listFiles(jsonFilter);
			
			// Read json data from json files
			// Check if array is not empty
			if ( files.length > 0 ) {
				// For each json file
				for( File file : files ) {
					// Check if it is a file (could be a folder)
					if ( file.isFile() ) {
						try {
							// Read json data from file
							fileReader = new JsonReader( new FileReader( file ) );
							// Parse data as object
							Object data = parser.parse(fileReader);
							
							// Check if it is an object or array
							if ( data instanceof JsonObject ) {
								JsonObject obj = (JsonObject) data;
								
								// Add it to read data
								jsonData.add(obj);
							} else if ( data instanceof JsonArray ) {
								JsonArray arr = (JsonArray) data;
								
								// For each element in this array
								for( JsonElement obj : arr ) {
									// Check if it is an object
									if ( obj.isJsonObject() ) {
										// Add it to read data
										jsonData.add((JsonObject) obj);
									}
								}
							} else {
								// If read data is not array or object reject it
								throw new Error ("Invalid Json");
							}							
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						
					}
				}
			}
		}
		
		
		return jsonData;
	}

	public static ArrayList<JsonObject> resolvePrototypeDelegations(ArrayList<JsonObject> jsonData) {
		// Throw error if object has no id
		for ( JsonObject element: jsonData ) {
			if ( element.get("id").getAsString().isEmpty() ) {
				throw new Error("Found json object without id");
			}
		}
		
		// Sort jsonData. 'Root' elements come before 'children' elements
		Collections.sort(jsonData, new Comparator<JsonObject>() {
	        @Override
	        public int compare(JsonObject obj2, JsonObject obj1)
	        {
	        	Boolean isRoot1 = !obj1.has("prototype");
	        	Boolean isRoot2 = !obj2.has("prototype");
	        	

	            return isRoot1.compareTo(isRoot2);
	        }
	    });
		
		// Read
		
		return jsonData;
	}
}
