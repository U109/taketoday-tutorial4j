package cn.tuyucheng.taketoday.hamcrest;

import cn.tuyucheng.taketoday.hamcrest.objectmatchers.City;
import org.junit.jupiter.api.Test;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.beans.PropertyUtil.getPropertyDescriptor;
import static org.hamcrest.beans.PropertyUtil.propertyDescriptorsFor;

class HamcrestBeansUnitTest {

   @Test
   void givenACity_whenHasProperty_thenCorrect() {
      City city = new City("San Francisco", "CA");

      assertThat(city, hasProperty("name"));
   }

   @Test
   void givenACity_whenNotHasProperty_thenCorrect() {
      City city = new City("San Francisco", "CA");

      assertThat(city, not(hasProperty("country")));
   }

   @Test
   void givenACity_whenHasPropertyWithValueEqualTo_thenCorrect() {
      City city = new City("San Francisco", "CA");

      assertThat(city, hasProperty("name", equalTo("San Francisco")));
   }

   @Test
   void givenACity_whenHasPropertyWithValueEqualToIgnoringCase_thenCorrect() {
      City city = new City("San Francisco", "CA");

      assertThat(city, hasProperty("state", equalToIgnoringCase("ca")));
   }

   @Test
   void givenACity_whenSamePropertyValuesAs_thenCorrect() {
      City city = new City("San Francisco", "CA");
      City city2 = new City("San Francisco", "CA");

      assertThat(city, samePropertyValuesAs(city2));
   }

   @Test
   void givenACity_whenNotSamePropertyValuesAs_thenCorrect() {
      City city = new City("San Francisco", "CA");
      City city2 = new City("Los Angeles", "CA");

      assertThat(city, not(samePropertyValuesAs(city2)));
   }

   @Test
   void givenACity_whenGetPropertyDescriptor_thenCorrect() {
      City city = new City("San Francisco", "CA");
      PropertyDescriptor descriptor = getPropertyDescriptor("state", city);

      assertThat(descriptor
            .getReadMethod()
            .getName(), is(equalTo("getState")));
   }

   @Test
   void givenACity_whenGetPropertyDescriptorsFor_thenCorrect() {
      City city = new City("San Francisco", "CA");
      PropertyDescriptor[] descriptors = propertyDescriptorsFor(city, Object.class);
      List<String> getters = Arrays
            .stream(descriptors)
            .map(x -> x.getReadMethod().getName())
            .collect(toList());

      assertThat(getters, containsInAnyOrder("getName", "getState"));
   }
}