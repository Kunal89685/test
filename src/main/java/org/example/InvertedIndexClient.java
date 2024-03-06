package org.example;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Map;

public class InvertedIndexClient {
	 public static void main(String args[]) {
		    try {

		        String endpoint = "rmi://127.0.0.1:8099/InvertedIndexService";
		        InvertedIndexService service = (InvertedIndexService) Naming.lookup(endpoint);
		        
		      Map<String, List<Integer>> index = service.getInvertedIndexThreadPool("C:\\Users\\Navdeep singh\\Desktop\\distributed_system_rmi_multithreading (2)\\distributed_system_rmi_multithreading\\src\\main\\java\\org\\example\\file.txt");
		      Map<String, List<Integer>> index1 = service.getInvertedIndexCompletableFuture("C:\\Users\\Navdeep singh\\Desktop\\distributed_system_rmi_multithreading (2)\\distributed_system_rmi_multithreading\\src\\main\\java\\org\\example\\file.txt");

		      System.out.println("size of index for thread pool= "+index.size());

		      System.out.println("Contents of the index map for thread pool:");
		      for (Map.Entry<String, List<Integer>> entry : index.entrySet()) {
		          System.out.println(entry.getKey() + ": " + entry.getValue());
		      }
		      System.out.println();
		      System.out.println("---------------------------------------------");
		      System.out.println();
		      System.out.println("size of index for Completable Future = "+index1.size());

		      System.out.println("Contents of the index map for Completable Future:");
		      for (Map.Entry<String, List<Integer>> entry : index1.entrySet()) {
		          System.out.println(entry.getKey() + ": " + entry.getValue());
		      }
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.exit(1);
		    }
		}
	


}
