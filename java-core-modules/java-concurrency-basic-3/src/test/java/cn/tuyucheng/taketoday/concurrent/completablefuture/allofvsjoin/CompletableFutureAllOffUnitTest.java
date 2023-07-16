package cn.tuyucheng.taketoday.concurrent.completablefuture.allofvsjoin;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompletableFutureAllOffUnitTest {

   @Test
   void whenCallingJoin_thenBlocksThreadAndGetValue() {
      CompletableFuture<String> future = waitAndReturn(1_000, "Harry");
      assertEquals("Harry", future.join()); // waits one second
   }

   @Test
   void whenCallingJoin_thenBlocksThreadAndThrowException() {
      CompletableFuture<String> futureError = waitAndThrow(1_000);
      assertThrows(RuntimeException.class, futureError::join); // waits one second
   }

   @Test
   void whenCallingJoinTwoTimes_thenBlocksThreadAndGetValues() {
      CompletableFuture<String> f1 = waitAndReturn(1_000, "Harry");
      CompletableFuture<String> f2 = waitAndReturn(2_000, "Ron");

      assertEquals("Harry", f1.join()); // waits for one second
      assertEquals("Ron", f2.join()); // waits for one more second
   }


   @Test
   void whenCallingAllOfJoin_thenBlocksThreadAndGetValues() {
      CompletableFuture<String> f1 = waitAndReturn(1_000, "Harry");
      CompletableFuture<String> f2 = waitAndReturn(2_000, "Ron");

      CompletableFuture<Void> combinedFutures = CompletableFuture.allOf(f1, f2); // does NOT wait
      combinedFutures.join(); // waits for two seconds

      assertEquals("Harry", f1.join()); // does NOT wait
      assertEquals("Ron", f2.join()); // does NOT wait
   }

   @Test
   void whenCallingJoinInaLoop_thenProcessesDataPartially() {
      CompletableFuture<String> f1 = waitAndReturn(1_000, "Harry");
      CompletableFuture<String> f2 = waitAndThrow(2_000);
      CompletableFuture<String> f3 = waitAndReturn(1_000, "Ron");

      assertThrows(RuntimeException.class, () -> Stream.of(f1, f2, f3)
            .map(CompletableFuture::join)
            .forEach(this::sayHello));
   }

   @Test
   void whenCallingAllOfJoin_thenFailsForAll() {
      CompletableFuture<String> f1 = waitAndReturn(1_000, "Harry");
      CompletableFuture<String> f2 = waitAndThrow(2_000);
      CompletableFuture<String> f3 = waitAndReturn(1_000, "Ron");

      assertThrows(RuntimeException.class, () -> CompletableFuture.allOf(f1, f2, f3)
            .join());
   }

   @Test
   void whenCallingExceptionally_thenRecoversWithDefaultValue() {
      CompletableFuture<String> f1 = waitAndReturn(1_000, "Harry");
      CompletableFuture<String> f2 = waitAndThrow(2_000);
      CompletableFuture<String> f3 = waitAndReturn(1_000, "Ron");

      CompletableFuture<String> names = CompletableFuture.allOf(f1, f2, f3)
            .thenApply(__ -> f1.join() + "," + f2.join() + "," + f3.join())
            .exceptionally(err -> {
               System.out.println("oops, there was a problem! " + err.getMessage());
               return "names not found!";
            });

      assertEquals("names not found!", names.join());
   }

   private void sayHello(String name) {
      System.out.println(LocalDateTime.now() + " - " + name);
   }

   private CompletableFuture<String> waitAndReturn(long millis, String value) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            // Thread.sleep() is commented to avoid slowing down the pipeline
            // Thread.sleep(millis);
            return value;
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      });
   }

   private CompletableFuture<String> waitAndThrow(long millis) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            // Thread.sleep() is commented to avoid slowing down the pipeline
            // Thread.sleep(millis);
         } finally {
            throw new RuntimeException();
         }
      });
   }
}