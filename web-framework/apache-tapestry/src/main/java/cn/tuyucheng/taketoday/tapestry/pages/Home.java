package cn.tuyucheng.taketoday.tapestry.pages;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.slf4j.Logger;

import java.util.Date;

public class Home {

   @Property
   private String appName = "apache-tapestry";
   @Inject
   private Logger logger;
   @Inject
   private AjaxResponseRenderer ajaxResponseRenderer;
   @Inject
   private Block ajaxBlock;

   public Date getCurrentTime() {
      return new Date();
   }

   @Log
   void onCallAjax() {
      logger.info("Ajax call");
      ajaxResponseRenderer.addRender("ajaxZone", ajaxBlock);
   }

}
