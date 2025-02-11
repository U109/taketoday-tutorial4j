package cn.tuyucheng.taketoday.mockito.bddmockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.willThrow;

public class BDDMockitoUnitTest {

   PhoneBookService phoneBookService;
   PhoneBookRepository phoneBookRepository;

   String momContactName = "Mom";
   String momPhoneNumber = "01234";
   String xContactName = "x";
   String tooLongPhoneNumber = "01111111111111";

   @BeforeEach
   public void init() {
      phoneBookRepository = Mockito.mock(PhoneBookRepository.class);
      phoneBookService = new PhoneBookService(phoneBookRepository);
   }

   @Test
   public void givenValidContactName_whenSearchInPhoneBook_thenReturnPhoneNumber() {
      given(phoneBookRepository.contains(momContactName)).willReturn(true);
      given(phoneBookRepository.getPhoneNumberByContactName(momContactName))
            .will((InvocationOnMock invocation) -> {
               if (invocation.getArgument(0).equals(momContactName)) {
                  return momPhoneNumber;
               } else {
                  return null;
               }
            });

      String phoneNumber = phoneBookService.search(momContactName);

      then(phoneBookRepository).should().contains(momContactName);
      then(phoneBookRepository).should().getPhoneNumberByContactName(momContactName);
      assertEquals(phoneNumber, momPhoneNumber);
   }

   @Test
   public void givenInvalidContactName_whenSearch_thenReturnNull() {
      given(phoneBookRepository.contains(xContactName)).willReturn(false);

      String phoneNumber = phoneBookService.search(xContactName);

      then(phoneBookRepository).should().contains(xContactName);
      then(phoneBookRepository).should(never()).getPhoneNumberByContactName(xContactName);
      assertNull(phoneNumber);
   }

   @Test
   public void givenValidContactNameAndPhoneNumber_whenRegister_thenSucceed() {
      given(phoneBookRepository.contains(momContactName)).willReturn(false);

      phoneBookService.register(momContactName, momPhoneNumber);

      verify(phoneBookRepository).insert(momContactName, momPhoneNumber);
   }

   @Test
   public void givenEmptyPhoneNumber_whenRegister_thenFail() {
      given(phoneBookRepository.contains(momContactName)).willReturn(false);

      phoneBookService.register(xContactName, "");

      then(phoneBookRepository).should(never()).insert(momContactName, momPhoneNumber);
   }

   @Test
   public void givenLongPhoneNumber_whenRegister_thenFail() {
      given(phoneBookRepository.contains(xContactName)).willReturn(false);
      willThrow(new RuntimeException())
            .given(phoneBookRepository).insert(any(String.class), eq(tooLongPhoneNumber));

      try {
         phoneBookService.register(xContactName, tooLongPhoneNumber);
         fail("Should throw exception");
      } catch (RuntimeException ignored) {
      }

      then(phoneBookRepository).should(never()).insert(momContactName, tooLongPhoneNumber);
   }

   @Test
   public void givenExistentContactName_whenRegister_thenFail() {
      given(phoneBookRepository.contains(momContactName))
            .willThrow(new RuntimeException("Name already exist"));

      try {
         phoneBookService.register(momContactName, momPhoneNumber);
         fail("Should throw exception");
      } catch (Exception ignored) {
      }

      then(phoneBookRepository).should(never()).insert(momContactName, momPhoneNumber);
   }
}