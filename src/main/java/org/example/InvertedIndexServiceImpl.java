package org.example;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
//RMI service implementation
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class InvertedIndexServiceImpl extends UnicastRemoteObject implements InvertedIndexService {
 // A ForkJoinPool to handle concurrent tasks
 private ForkJoinPool pool;

 public InvertedIndexServiceImpl() throws RemoteException {
     super();
     // Initialize the pool with the number of available processors
     pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
 }

// @Override
// public Map<String, Integer> getInvertedIndex(String text) throws RemoteException {
//     // Split the text into lines
//     String[] lines = text.split("\n");
//     // Create a map to store the results
//     Map<String, Integer> index = new HashMap<>();
//     // For each line, submit a task to the pool that computes the inverted index for that line
//     for (String line : lines) {
//         pool.submit(() -> {
//             // Split the line into words and count their occurrences
//             String[] words = line.split("\\s+");
//             Map<String, Integer> lineIndex = new HashMap<>();
//             for (String word : words) {
//                 lineIndex.put(word, lineIndex.getOrDefault(word, 0) + 1);
//             }
//             // Merge the line index with the global index
//             synchronized (index) {
//                 for (Map.Entry<String, Integer> entry : lineIndex.entrySet()) {
//                     index.put(entry.getKey(), index.getOrDefault(entry.getKey(), 0) + entry.getValue());
//                 }
//             }
//         });
//     }
 
// @Override
// public Map<String, List<Integer>> getInvertedIndex(String text) throws RemoteException {
//     // Split the text into lines
//     String[] lines = text.split("\n");
//     // Create a map to store the results
//     Map<String, List<Integer>> index = new HashMap<>();
//     // For each line, submit a task to the pool that computes the inverted index for that line
//     for (int i = 0; i < lines.length; i++) {
//         String line = lines[i];
//         final int lineIndex = i; // Make a final copy of i
//         pool.submit(() -> {
//             // Split the line into words and count their occurrences
//             String[] words = line.split("\\s+");
//             for (String word : words) {
//                 synchronized (index) {
//                     if (!index.containsKey(word)) {
//                         index.put(word, new ArrayList<>());
//                     }
//                     index.get(word).add(lineIndex); // Add the index of the line where the word occurs
//                 }
//             }
//         });
//     }
//     // Wait for all tasks to finish
//     pool.awaitQuiescence(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
//     // Return the inverted index
//     return index;
// }
//
// 
// public static void main(String args[]) {
//     try {
//
//         
//         //creating an instance of the interface implementation
//         InvertedIndexServiceImpl server = new InvertedIndexServiceImpl();
//
//         // this fixed: java.rmi.ConnectException: Connection refused to host: 127.0.0.1;
//         LocateRegistry.createRegistry(8099);
//
//         Naming.rebind("rmi://127.0.0.1:8099/InvertedIndexService", server);
//         System.out.println("InvertedIndexService ready...");
////       String text = "Hello world\nHello Java world";
////       Map<String, Integer> invertedIndex = server.getInvertedIndex(text);
////       
////       System.out.println("Inverted Index:");
////       for (Map.Entry<String, Integer> entry : invertedIndex.entrySet()) {
////           System.out.println(entry.getKey() + ": " + entry.getValue());
////       }
//     } catch (Exception e) {
//         e.printStackTrace();
//         System.exit(1);
//     }
// }
// 
// 
//}
 
 
 
 
 //////for assignment
// @Override
// public List<String> calculateFrequency(String file) throws RemoteException {
//	 ConcurrentHashMap<String, Integer> frequencyMap = new ConcurrentHashMap<>();
//     String text;
//
//     try {
//         text = Files.readString(Paths.get(file));
//     } catch (IOException e) {
//         throw new RemoteException("Error reading file: " + file, e);
//     }
//
//     String[] lines = text.split("\n");
//     List<Future<?>> futures = new ArrayList<>();
//
//     for (String line : lines) {
//         // Submit each line processing task to the pool
//         Future<?> future = pool.submit(() -> {
//             String[] words = line.toLowerCase().split("\\W+");
//             for (String word : words) {
//                 if (!word.isEmpty()) {
//                     frequencyMap.merge(word, 1, Integer::sum);
//                 }
//             }
//         });
//         futures.add(future);
//     }
//
//     // Wait for all tasks to complete
//     for (Future<?> future : futures) {
//         try {
//             future.get(); // This will block until the task is completed
//         } catch (Exception e) {
//             throw new RemoteException("Error waiting for a task to complete", e);
//         }
//     }
//
//     // Stream the frequencyMap, sort by frequency, and collect the top 5 words
//     List<String> top5Words = frequencyMap.entrySet().stream()
//             .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//             .limit(5)
//             .map(Map.Entry::getKey)
//             .collect(Collectors.toList());
//
//     return top5Words;}
// 
 
 
 
 
 
// public List<String> calculateFrequency(String file) throws RemoteException {
//     String text;
//     try {
//         // Read the entire content of the file into a string
//         text = Files.readString(Paths.get(file));
//     } catch (IOException e) {
//         // Convert IOException to RemoteException
//         throw new RemoteException("Error reading file: " + file, e);
//     }
//
//     ConcurrentHashMap<String, Integer> frequencyMap = new ConcurrentHashMap<>();
//
//     // Split the text into lines
//     String[] lines = text.split("\n");
//
//     // For each line, submit a task to the pool that computes the frequency of each word in the line
//     Arrays.stream(lines).forEach(line ->
//         pool.submit(() -> {
//             // Split the line into words
//             String[] words = line.split("\\s+");
//             Arrays.stream(words).forEach(word -> {
//                 // Normalize word
//                 String normalizedWord = word.toLowerCase().replaceAll("[^a-zA-Z]", "");
//                 // Update frequency map
//                 frequencyMap.merge(normalizedWord, 1, Integer::sum);
//             });
//         })
//     );
//
//     // Wait for all tasks to finish
//     pool.awaitQuiescence(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
//
//     // Sort by frequency and get top 5 entries
//     List<String> topWords = frequencyMap.entrySet().stream()
//             .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//             .limit(5)
//             .map(Map.Entry::getKey) // Extract the key (word) from the entry
//             .collect(Collectors.toList());
//
//         // Return the list of top 5 words
//         return topWords;
// }


 



 
 
 
 
// @Override
// public Map<String, List<Integer>> getInvertedIndex(String file) throws RemoteException {
//	 String text;
//     try {
//         // Read the entire content of the file into a string
//         text = Files.readString(Paths.get(file));
//     } catch (IOException e) {
//         // Convert IOException to RemoteException
//         throw new RemoteException("Error reading file: " + file, e);
//     }
//     // Split the text into lines
//     String[] lines = text.split("\n");
//     // Create a map to store the results
//     Map<String, List<Integer>> index = new HashMap<>();
//     // For each line, submit a task to the pool that computes the inverted index for that line
//     for (int i = 0; i < lines.length; i++) {
//         String line = lines[i];
//         final int lineIndex = i; // Make a final copy of i
//         pool.submit(() -> {
//             // Split the line into words and count their occurrences
//             String[] words = line.split("\\s+");
//             for (String word : words) {
//                 synchronized (index) {
//                     if (!index.containsKey(word)) {
//                         index.put(word, new ArrayList<>());
//                     }
//                     index.get(word).add(lineIndex); // Add the index of the line where the word occurs
//                 }
//             }
//         });
//     }
//     // Wait for all tasks to finish
//     pool.awaitQuiescence(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
//     // Return the inverted index
//     return index;
// }
 
 
 @Override
 public Map<String, List<Integer>> getInvertedIndex(String file) throws RemoteException {
     String text;
     try {
         // Read the entire content of the file into a string
         text = Files.readString(Paths.get(file));
     } catch (IOException e) {
         // Convert IOException to RemoteException
         throw new RemoteException("Error reading file: " + file, e);
     }
     // Split the text into lines
     String[] lines = text.split("\n");
     // Create a concurrent map to store word frequencies and their line numbers
     ConcurrentHashMap<String, Integer> wordFrequencies = new ConcurrentHashMap<>();
     ConcurrentHashMap<String, List<Integer>> wordLines = new ConcurrentHashMap<>();

     // Process each line to update word frequencies and line numbers
     for (int i = 0; i < lines.length; i++) {
         String line = lines[i];
         final int lineIndex = i; // Make a final copy of i
         pool.submit(() -> {
             // Split the line into words and process each word
             String[] words = line.split("\\s+");
             for (String word : words) {
                 // Normalize the word to ensure consistency (e.g., lowercase)
                 String normalizedWord = word.toLowerCase().replaceAll("\\W+", "");
                 // Update frequency and line numbers
                 wordFrequencies.merge(normalizedWord, 1, Integer::sum);
                 wordLines.computeIfAbsent(normalizedWord, k -> new ArrayList<>()).add(lineIndex);
             }
         });
     }
     // Wait for all tasks to finish
     pool.awaitQuiescence(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

     // Sort words by frequency and select the top 5
     List<Map.Entry<String, Integer>> sortedByFrequency = wordFrequencies.entrySet().stream()
             .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
             .limit(5)
             .collect(Collectors.toList());

     // Prepare the final result
     Map<String, List<Integer>> result = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order
     for (Map.Entry<String, Integer> entry : sortedByFrequency) {
         result.put(entry.getKey(), wordLines.get(entry.getKey()));
     }

     
//     System.out.println("size of result= "+result.size());
//     
//     // Print the index map and its values
//     System.out.println("Contents of the result map:");
//     for (Map.Entry<String, List<Integer>> entry : result.entrySet()) {
//         System.out.println(entry.getKey() + ": " + entry.getValue());
//     }
     // Return the top 5 words and the lines they appear on
     return result;
 }
 
 
// @Override
// public Map<String, Integer> calculateFrequency(String file) throws RemoteException {
//     String text;
//     try {
//         // Read the entire content of the file into a string
//         text = Files.readString(Paths.get(file));
//     } catch (IOException e) {
//         // Convert IOException to RemoteException
//         throw new RemoteException("Error reading file: " + file, e);
//     }
//
//     String[] lines = text.split("\n");
//     ConcurrentHashMap<String, Integer> frequencyMap = new ConcurrentHashMap<>();
//
//     // Use a parallel stream to process lines concurrently
//     Arrays.stream(lines).parallel().forEach(line -> {
//         // Split the line into words and count their occurrences
//         String[] words = line.split("\\s+");
//         Arrays.stream(words).forEach(word -> {
//             if (!word.trim().isEmpty()) { // Check if the word is not empty after trimming
//                 String normalizedWord = word.toLowerCase().replaceAll("\\W+", "");
//                 frequencyMap.merge(normalizedWord, 1, Integer::sum);
//             }
//         });
//     });
//
//     // Now, we select the top five most frequent words
//     List<Map.Entry<String, Integer>> sortedEntries = frequencyMap.entrySet().stream()
//             .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
//             .limit(5)
//             .collect(Collectors.toList());
//
//     // Convert the sorted list back to a map
//     Map<String, Integer> topFive = new LinkedHashMap<>();
//     sortedEntries.forEach(entry -> topFive.put(entry.getKey(), entry.getValue()));
//
//     return topFive;
// }


 
 public static void main(String args[]) {
     try {

         
         //creating an instance of the interface implementation
         InvertedIndexServiceImpl server = new InvertedIndexServiceImpl();

         // this fixed: java.rmi.ConnectException: Connection refused to host: 127.0.0.1;
         LocateRegistry.createRegistry(8099);

         Naming.rebind("rmi://168.138.93.215:8080/InvertedIndexService", server);
         System.out.println("InvertedIndexService ready...");
//       String text = "Hello world\nHello Java world";
//       Map<String, Integer> invertedIndex = server.getInvertedIndex(text);
//       
//       System.out.println("Inverted Index:");
//       for (Map.Entry<String, Integer> entry : invertedIndex.entrySet()) {
//           System.out.println(entry.getKey() + ": " + entry.getValue());
//       }
     } catch (Exception e) {
         e.printStackTrace();
         System.exit(1);
     }
 }
 
 
}