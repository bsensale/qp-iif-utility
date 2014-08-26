package net.sensale.qp.quickbooks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ShowTransformer {
    
	static ShowTransformer sTransformer;
	
    private static String sShowFileName = "shows.txt";
	Map<String, String> mShowMap;

	ShowTransformer(Map<String,String> showMap) {
		mShowMap = showMap;
	}
	
	static Map<String,String> loadShows() throws IOException {
    	InputStream showFilePath = ClassLoader.getSystemResourceAsStream(getsShowFileName());
    	if(showFilePath == null) {
    		throw new RuntimeException ("Can't find " + getsShowFileName());
    	}
    	Map<String,String> results = new HashMap<String, String>(); 
    	BufferedReader buffy = new BufferedReader(new InputStreamReader(showFilePath, "UTF-8"));
        try {
        	String line;
        	while((line = buffy.readLine()) != null) {
        		String[] parts = line.split("\t");
                if(parts.length != 2) {
                    throw new RuntimeException("Weird parts: " + parts);
                }
                results.put(parts[0], parts[1]);
        	}
        }
        finally {
        	showFilePath.close();
        	buffy.close();
        }
        return results;
    }

    String getShow(String pInput) {
    	if(mShowMap == null) {
    		throw new RuntimeException("Show map was never initialized.  Did you forget to getInstance()?");
    	}
        String result = pInput;
        for(String pattern : mShowMap.keySet()) {
            if(result.contains(pattern)) {
                result = result.replaceAll(pattern, mShowMap.get(pattern));
            }
        }
        return result;
    }
    
    static ShowTransformer getInstance() {
    	if(sTransformer == null) {
    		try {
    			Map<String,String> showMap = loadShows();
    			sTransformer = new ShowTransformer(showMap);
    		} catch (IOException e) {
				throw new RuntimeException("Error loading shows text file", e);
			}
    	}
    	return sTransformer;
    }

	static String getsShowFileName() {
		return sShowFileName;
	}

}
