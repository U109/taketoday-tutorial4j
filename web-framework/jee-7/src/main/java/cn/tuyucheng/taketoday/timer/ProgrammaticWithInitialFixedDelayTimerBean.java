package cn.tuyucheng.taketoday.timer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * author: Cristian Chiovari
 */
@Startup
@Singleton
public class ProgrammaticWithInitialFixedDelayTimerBean {

   @Inject
   Event<TimerEvent> event;

   @Resource
   TimerService timerService;

   @PostConstruct
   public void initialize() {
      timerService.createTimer(10000l, 5000l, "Delay 10 seconds then every 5 second timer");
   }

   @Timeout
   public void programmaticTimout(Timer timer) {
      event.fire(new TimerEvent(timer.getInfo().toString()));
   }
}
