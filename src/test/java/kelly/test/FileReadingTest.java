package kelly.test;

import java.io.File;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReadingTest {
    private static final Logger logger = LoggerFactory.getLogger(FileReadingTest.class);
    
    @Test
    public void test() {
        File f1 = new File("src/test/resources/sample1.txt");
        Assertions.assertTrue(f1.canRead());
        Map data1 = FileProcessing.parseFile(f1);
        logger.info("Data 1: {}", data1);

        File f2 = new File("src/test/resources/sample2.txt");
        Assertions.assertTrue(f2.canRead());        
        Map data2 = FileProcessing.parseFile(f2);
        logger.info("Data 2: {}", data2);
                
        @SuppressWarnings("unchecked")
        Map<Integer, Integer[]> merged = FileProcessing.merge(
            new Map[] {
                data1
                , data2
            }
        );
        FileProcessing.dump(merged);
        

//        BufferedReader brx = null;
//        try {
//            brx = new BufferedReader(new FileReader(f1));
//        } catch (IOException e) {
//            
//        } finally {
//            if (brx != null) {
//                try {
//                    brx.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
