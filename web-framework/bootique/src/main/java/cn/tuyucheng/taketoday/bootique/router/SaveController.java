package cn.tuyucheng.taketoday.bootique.router;

import cn.tuyucheng.taketoday.bootique.service.HelloService;
import com.google.inject.Inject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/save")
public class SaveController {

   @Inject
   HelloService helloService;

   @POST
   public String save() {
      return "Data Saved!";
   }

}
