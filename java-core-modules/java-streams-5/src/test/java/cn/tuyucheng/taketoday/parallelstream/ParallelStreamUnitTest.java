package cn.tuyucheng.taketoday.parallelstream;

import cn.tuyucheng.taketoday.streams.parallelstream.Book;
import cn.tuyucheng.taketoday.streams.parallelstream.MyBookContainer;
import cn.tuyucheng.taketoday.streams.parallelstream.ParallelStreamApplication;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ParallelStreamUnitTest {

   @Test
   public void givenCollectionWhenCollectionsParallelIsUsedThenReturnCount() {
      ParallelStreamApplication parallelStreamApplication = new ParallelStreamApplication();
      Assert.assertEquals(parallelStreamApplication.usingCollectionsParallel(generateListOfBooks(), 1974), 2);
   }

   @Test
   public void givenCollectionWhenStreamParallelIsUsedThenReturnCount() {
      ParallelStreamApplication parallelStreamApplication = new ParallelStreamApplication();
      Assert.assertEquals(parallelStreamApplication.usingStreamParallel(generateListOfBooks(), 1974), 2);
   }

   @Test
   public void givenBookContainerWhenParallelStreamIsUsedThenReturnIncorrectCount() {
      ParallelStreamApplication parallelStreamApplication = new ParallelStreamApplication();
      Assert.assertNotEquals(parallelStreamApplication.usingWithCustomSpliterator(getBookContainer(), 1974), 2);
   }

   private List<Book> generateListOfBooks() {
      Book book1 = new Book("The Blue Umbrella", "Ruskin Bond", 1974);
      Book book2 = new Book("Carrie", "Stephen King", 1974);
      Book book3 = new Book("The Psychology of money", "Morgan Housel", 2020);
      List<Book> books = List.of(book1, book2, book3);
      return books;
   }

   private MyBookContainer<Book> getBookContainer() {
      MyBookContainer<Book> listOfBooks = new MyBookContainer<>(new Book[]{new Book("The Blue Umbrella", "Ruskin Bond", 1974),
            new Book("Carrie", "Stephen King", 1974),
            new Book("The Psychology of money", "Morgan Housel", 2020)});
      return listOfBooks;
   }
}