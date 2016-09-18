package me.otho.metamods.core.jsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.javafx.collections.MappingChange.Map;

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
		HashMap<String, JsonObject> objectMap = new HashMap<String, JsonObject>();
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
		
		// Check if there is some 'root' object
		if ( jsonData.get(0).has("prototype") ) {
			throw new Error("At least some data must not have a prototype");
		}
		
		for( JsonObject obj : jsonData ) {
			objectMap.put( obj.get("id").getAsString(), obj );
		}
		
		for( JsonObject obj : jsonData ) {
			if( obj.has("prototype") ) {
				// An arraylist to store the prototype chain
				Stack<String> prototypeChain = new Stack<String>();
				
				Boolean foundRoot = false;
				JsonObject seeker = obj;
				
				// Build a prototype chain until finding a root element
				while ( !foundRoot ) {					
					System.out.println("Seeker: " + seeker.get("id").getAsString() + " " + (!seeker.has("prototype") ? "root" : "child") );
					prototypeChain.push( seeker.get("id").getAsString() );
					if( !seeker.has("prototype") ) {
						foundRoot = true;
					} else {
						seeker = objectMap.get( seeker.get("prototype").getAsString() );						
					}
				}
				
				// Create a black json object to fill with properties
				JsonObject fullObject = new JsonObject();
				
				// While there is a prototype in the chain
				while ( !prototypeChain.empty() ) {
					JsonObject node = objectMap.get( prototypeChain.pop() );
					
					// Put all parent properties in new object, overwriting repeated properties
					for ( Entry<String,JsonElement> keySet : node.entrySet() ) {
						String key = keySet.getKey();
						// Ignore id and prototype
						if ( !key.equals("id") && !key.equals("prototype") ) {
							if ( fullObject.has(key) ) {
								fullObject.remove(key);
							}
							fullObject.add(key, keySet.getValue());
						}
					}
				}
				
				fullObject.add("id", obj.get("id"));
				
				objectMap.put(obj.get("id").getAsString(), fullObject);
			}
		}		
		
		return  new ArrayList<JsonObject>(objectMap.values());
	}
}
