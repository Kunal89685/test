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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class InvertedIndexServiceImpl extends UnicastRemoteObject implements InvertedIndexService {
    private ForkJoinPool pool;
    private ExecutorService executorService;

    public InvertedIndexServiceImpl() throws RemoteException {
        super();
        // Initialize the pool with the number of available processors
        pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        // Similarly initialize the ExecutorService
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }


 @Override
 public Map<String, List<Integer>> getInvertedIndexThreadPool(String file) throws RemoteException {
     String text;
     try {
         text = Files.readString(Paths.get(file));
     } catch (IOException e) {
         throw new RemoteException("Error reading file: " + file, e);
     }
     String[] lines = text.split("\n");
     ConcurrentHashMap<String, Integer> wordFrequencies = new ConcurrentHashMap<>();
     ConcurrentHashMap<String, List<Integer>> wordLines = new ConcurrentHashMap<>();
     
     for (int i = 0; i < lines.length; i++) {
         String line = lines[i];
         final int lineIndex = i; 
         pool.submit(() -> {
             String[] words = line.split("\\s+");
             for (String word : words) {
                 String normalizedWord = word.toLowerCase().replaceAll("\\W+", "");
                 wordFrequencies.merge(normalizedWord, 1, Integer::sum);
                 wordLines.computeIfAbsent(normalizedWord, k -> new ArrayList<>()).add(lineIndex);
             }
         });
     }
     pool.awaitQuiescence(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

     List<Map.Entry<String, Integer>> sortedByFrequency = wordFrequencies.entrySet().stream()
             .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
             .limit(5)
             .collect(Collectors.toList());

     Map<String, List<Integer>> result = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order
     for (Map.Entry<String, Integer> entry : sortedByFrequency) {
         result.put(entry.getKey(), wordLines.get(entry.getKey()));
     }

     return result;
 }
 
 

 
 
 @Override
 public Map<String, List<Integer>> getInvertedIndexCompletableFuture(String file) throws RemoteException {
     String text;
     try {
         text = Files.readString(Paths.get(file));
     } catch (IOException e) {
         throw new RemoteException("Error reading file: " + file, e);
     }
     String[] lines = text.split("\n");
     ConcurrentHashMap<String, List<Integer>> wordLines = new ConcurrentHashMap<>();

     // Create a list of CompletableFutures
     List<CompletableFuture<Void>> futures = new ArrayList<>();

     for (int i = 0; i < lines.length; i++) {
         final int lineIndex = i;
         CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
             String[] words = lines[lineIndex].split("\\s+");
             for (String word : words) {
                 String normalizedWord = word.toLowerCase().replaceAll("\\W+", "");
                 wordLines.computeIfAbsent(normalizedWord, k -> new ArrayList<>()).add(lineIndex);
             }
         }, executorService);
         futures.add(future);
     }

     // Wait for all futures to complete
     CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

     // Compute frequencies and get top 5 frequent words
     Map<String, Integer> wordFrequencies = wordLines.entrySet().stream()
             .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));

     List<Map.Entry<String, Integer>> sortedByFrequency = wordFrequencies.entrySet().stream()
             .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
             .limit(5)
             .collect(Collectors.toList());

     // Build the final result map for top 5 words
     Map<String, List<Integer>> result = new LinkedHashMap<>();
     sortedByFrequency.forEach(entry -> result.put(entry.getKey(), wordLines.get(entry.getKey())));

     return result;
 }


 
 public static void main(String args[]) {
     try {

         
         
         InvertedIndexServiceImpl server = new InvertedIndexServiceImpl();

        
         LocateRegistry.createRegistry(8099);

         Naming.rebind("rmi://127.0.0.1:8099/InvertedIndexService", server);
         System.out.println("InvertedIndexService ready...");

     } catch (Exception e) {
         e.printStackTrace();
         System.exit(1);
     }
 }
 
 
}
