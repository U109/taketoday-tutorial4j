package cn.tuyucheng.taketoday.produceimage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ImageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DataProducerControllerIntegrationTest {

   @Autowired
   private MockMvc mockMvc;

   @Test
   void givenJpgTrue_whenGetImageDynamicType_ThenContentTypeIsJpg() throws Exception {
      mockMvc.perform(get("/get-image-dynamic-type?jpg=true"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.IMAGE_JPEG))
            .andExpect(header().stringValues(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE));
   }

   @Test
   void givenJpgFalse_whenGetImageDynamicType_ThenContentTypeIsPng() throws Exception {
      mockMvc.perform(get("/get-image-dynamic-type?jpg=false"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.IMAGE_PNG))
            .andExpect(header().stringValues(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE));
   }

   @Test
   void whenGetFileViaByteArrayResource_ThenContentIsExpectedFile() throws Exception {
      mockMvc.perform(get("/get-file-via-byte-array-resource"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
            .andExpect(content().string("Hello Tuyucheng!"));
   }
}