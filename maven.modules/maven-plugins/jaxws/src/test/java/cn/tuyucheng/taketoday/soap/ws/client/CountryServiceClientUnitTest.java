package cn.tuyucheng.taketoday.soap.ws.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class CountryServiceClientUnitTest {

   CountryServiceClient countryServiceClient;
   CountryService countryService;

   @BeforeEach
   void setUp() {
      countryService = mock(CountryService.class);
      countryServiceClient = new CountryServiceClient(countryService);
   }

   @DisplayName("Get capital by country name when country not found")
   @Test
   void givenCountryDoesNotExist_whenGetCapitalByCountryName_thenThrowsCountryNotFoundException() {
      doThrow(CountryNotFoundException.class).when(countryService).findByName(any());
      assertThrows(CountryNotFoundException.class, () -> countryServiceClient.getCapitalByCountryName(any()));
   }

   @DisplayName("Get capital by country name when country is India then should return capital")
   @Test
   void givenCountryIndia_whenGetCapitalByCountryName_thenShouldReturnCapital() {
      Country country = mock(Country.class);

      doReturn("New Delhi").when(country).getCapital();
      doReturn(country).when(countryService).findByName("India");

      assertEquals("New Delhi", countryServiceClient.getCapitalByCountryName("India"));
   }

   @DisplayName("Get population by country name when country not found")
   @Test
   void givenCountryDoesNotExist_getPopulationByCountryName_thenThrowsCountryNotFoundException() {
      doThrow(CountryNotFoundException.class).when(countryService).findByName(any());
      assertThrows(CountryNotFoundException.class, () -> countryServiceClient.getPopulationByCountryName(any()));
   }

   @DisplayName("Get population by country name when country is India then should return population")
   @Test
   void givenCountryIndia_getPopulationByCountryName_thenShouldReturnPopulation() {
      Country country = mock(Country.class);

      doReturn(1000000).when(country).getPopulation();
      doReturn(country).when(countryService).findByName("India");

      assertEquals(1000000, countryServiceClient.getPopulationByCountryName("India"));
   }

   @DisplayName("Get currency by country name when country not found")
   @Test
   void givenCountryDoesNotExist_getCurrencyByCountryName_thenThrowsCountryNotFoundException() {
      doThrow(CountryNotFoundException.class).when(countryService).findByName(any());
      assertThrows(CountryNotFoundException.class, () -> countryServiceClient.getCurrencyByCountryName(any()));
   }

   @DisplayName("Get currency by country name when country is India then should return currency")
   @Test
   void givenCountryIndia_getCurrencyByCountryName_thenShouldReturnCurrency() {
      Country country = mock(Country.class);

      doReturn(Currency.INR).when(country).getCurrency();
      doReturn(country).when(countryService).findByName("India");

      assertEquals(Currency.INR, countryServiceClient.getCurrencyByCountryName("India"));
   }
}