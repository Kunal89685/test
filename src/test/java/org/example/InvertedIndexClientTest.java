//package org.example;
//
////JUnit test for the client code
////import org.junit.Test;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.net.URL;
//import java.rmi.Naming;
////import static org.junit.Assert.*;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.util.List;
//import java.util.Map;
//
//public class InvertedIndexClientTest {
// 
// @Test
// public void testInvertedIndex() throws Exception {
//     // Locate the registry and get the stub of the service
//	 String endpoint = "rmi://127.0.0.1:8099/InvertedIndexService";
//
//	
//     org.example.InvertedIndexService service = (InvertedIndexService) 
//     						Naming.lookup(endpoint);
//     
//     // Prepare a sample text
//     String text = "Hello world\nHello Java\nHello RMI";
//     // Invoke the service and get the inverted index
//     Map<String, List<Integer>> index = service.getInvertedIndex(text);
//     // Check the results
//     assertEquals(4, index.size());
//     assertEquals(3, (int) index.get("Hello"));
//     assertEquals(1, (int) index.get("world"));
//     assertEquals(1, (int) index.get("Java"));
//     assertEquals(1, (int) index.get("RMI"));
// }
//}


//package org.example;
//
//import org.junit.jupiter.api.Test;
//
//import java.rmi.Naming;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class InvertedIndexClientTest {
//
//    @Test
//    public void testInvertedIndex() throws Exception {
//        // Locate the registry and get the stub of the service
//        String endpoint = "rmi://127.0.0.1:8099/InvertedIndexService";
//        InvertedIndexService service = (InvertedIndexService) Naming.lookup(endpoint);
//        Map<String, Integer> frequency = service.calculateFrequency("I:\\Concordia\\Wiinter2024\\distributed_system_rmi_multithreading\\src\\test\\java\\org\\example\\file.txt");
//
//        Map<String, List<Integer>> index = service.getInvertedIndex("I:\\Concordia\\Wiinter2024\\distributed_system_rmi_multithreading\\src\\test\\java\\org\\example\\file.txt");
////        assertTrue(index.size() <= 5, "The returned map should contain at most 5 entries.");
////       assertTrue(index.g("hello"));
//
//     //   List<String> topWords = service.calculateFrequency("I:\\Concordia\\Wiinter2024\\distributed_system_rmi_multithreading\\src\\test\\java\\org\\example\\file.txt");
////
////        assertTrue(topWords.size() <= 5);
////
////
////        assertEquals("hello", topWords.get(0));
//        
////        assertEquals(4, index.size());
////       assertTrue(index.get("Hello").contains(0)); // "Hello" should occur in line 0
////        assertTrue(index.get("Hello").contains(1)); // "Hello" should occur in line 1
////        assertTrue(index.get("Hello").contains(2)); // "Hello" should occur in line 2
////        assertTrue(index.get("world").contains(0)); // "world" should occur in line 0
////        assertTrue(index.get("Java").contains(1)); // "Java" should occur in line 1
////        assertTrue(index.get("RMI").contains(2)); // "RMI" should occur in line 2
//    }
//}






package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.Naming;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvertedIndexClientTest {

    @Test
    public void testTopFiveFrequentWordsInvertedIndex() throws Exception {
        // Locate the registry and get the stub of the service
        String endpoint = "rmi://168.138.93.215:8080/InvertedIndexService";
        InvertedIndexService service = (InvertedIndexService) Naming.lookup(endpoint);

        // Assuming the file content has been updated to test this functionality
      Map<String, List<Integer>> index = service.getInvertedIndex("I:\\Concordia\\Wiinter2024\\distributed_system_rmi_multithreading\\src\\test\\java\\org\\example\\file.txt");
    //  Map<String, Integer> frequency = service.calculateFrequency("I:\\Concordia\\Wiinter2024\\distributed_system_rmi_multithreading\\src\\test\\java\\org\\example\\file.txt");

      System.out.println("size of index= "+index.size());
//      System.out.println("size of Frequency= "+frequency.size());
//      
//      System.out.println("Contents of the frequency map:");
//      for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
//          System.out.println(entry.getKey() + ": " + entry.getValue());
//      }

      
      // Print the index map and its values
      System.out.println("Contents of the index map:");
      for (Map.Entry<String, List<Integer>> entry : index.entrySet()) {
          System.out.println(entry.getKey() + ": " + entry.getValue());
      }
        // Verify the map contains exactly 5 entries
       assertTrue(index.size() <= 5, "The returned map should contain at most 5 entries.");

        // Example assertions, adjust according to actual file content
        // Check if the map contains the expected words and line numbers
        // Note: Replace "word1", "word2", etc., with actual words expected to be most frequent
        // and update the line numbers lists according to the expected occurrences in the file
        if (index.containsKey("Hello")) {
            List<Integer> lines = index.get("Hello");
            assertTrue(lines.containsAll(List.of(0,1,2,6,6)));
        }

            assertEquals(null,index.get("jury"));
    
        
//        if (index.containsKey("is")) {
//            List<Integer> lines = index.get("Hello");
//            assertTrue(lines.containsAll(List.of(0,1,2,6,6)));
//        }
//        if (index.containsKey("word2")) {
//            List<Integer> lines = index.get("word2");
//            assertTrue(lines.containsAll(List.of(/* expected line numbers */)));
        }
        // Repeat for other top words as necessary...

        // Additional specific checks based on expected results
        // e.g., assertEquals(List.of(/* expected line numbers */), index.get("specificWord"));
    }

