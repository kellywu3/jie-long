package kelly.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileProcessing {
    private static final Logger logger = LoggerFactory.getLogger(FileProcessing.class);
    
    public static String[] parseLine(String line) {
        if(StringUtils.isNotBlank(line)) {
            return line.split(" ");   
        } else {
            return new String[0];
        }
    }
    
    public static Map<Integer, Integer> parseFile(File f) {
        HashMap<Integer, Integer> result = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                logger.debug("line: {}", line);
                String[] parts = FileProcessing.parseLine(line);
                if(parts.length == 2) {
                    Integer key = Integer.parseInt(parts[0]);
                    Integer val = Integer.parseInt(parts[1]);
                    result.put(key, val);
                }
            }
        } catch(IOException e) {
            logger.error("Error", e);
        }
        return result;
    }
    
    public static Map<Integer, Integer[]> merge(Map<Integer, Integer>[] maps) {
        HashMap<Integer, Integer[]> result = new HashMap<>();
        
        for(int i=0; i<maps.length; i++) {
            Map<Integer, Integer> m = maps[i];
            for(Integer k : m.keySet()) {
                Integer[] rowNew = new Integer[i + 1];
                rowNew[i] = m.get(k);
                Integer[] rowExisting = result.get(k);
                if (rowExisting != null) {
                    for(int j=0; j<rowExisting.length; j++) {
                        rowNew[j] = rowExisting[j];
                    }
                }
                result.put(k, rowNew);
            }
        }
        
        return result;
    }
    
    public static void dump(Map<Integer, Integer[]> map) {
        for(Integer k : map.keySet()) {
            Integer[] row = map.get(k);
            String out = k + "|" + StringUtils.join(row, "|");
            logger.info(out);
        }
    }
}
