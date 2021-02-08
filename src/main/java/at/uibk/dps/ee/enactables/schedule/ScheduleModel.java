package at.uibk.dps.ee.enactables.schedule;

import java.util.Set;

import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * Interface for the class used to maintain the information that the EE has on
 * the scheduling of its tasks.
 * 
 * @author Fedor Smirnov
 */
public interface ScheduleModel {

	/**
	 * Returns true if the task is already scheduled.
	 * 
	 * @param functionTask the requested task
	 * @return true if the task is already scheduled
	 */
	boolean isScheduled(Task functionTask);

	/**
	 * Sets the schedule for the given task.
	 * 
	 * @param task
	 * @param schedule
	 */
	void setTaskSchedule(Task task, Set<Mapping<Task, Resource>> schedule);

	/**
	 * Returns the schedule annotated for the requested task.
	 * 
	 * @param task the requested task
	 * @return the task schedule, in the form of (annotated) mapping edges
	 */
	Set<Mapping<Task, Resource>> getTaskSchedule(Task task);
}
