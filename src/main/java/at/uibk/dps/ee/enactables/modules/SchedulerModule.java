package at.uibk.dps.ee.enactables.modules;

import at.uibk.dps.ee.enactables.schedule.ScheduleInterpreterUser;
import at.uibk.dps.ee.enactables.schedule.ScheduleInterpreterUserSingle;
import at.uibk.dps.ee.enactables.schedule.Scheduler;
import at.uibk.dps.ee.enactables.schedule.SchedulerSingleOption;
import at.uibk.dps.ee.guice.modules.EeModule;

public class SchedulerModule extends EeModule {

  @Override
  protected void config() {
    bind(ScheduleInterpreterUser.class).to(ScheduleInterpreterUserSingle.class);
    bind(Scheduler.class).to(SchedulerSingleOption.class);
  }
}
