package com.toy.scheduler.job.element;

import com.toy.scheduler.execute.ActionTask;
import com.toy.scheduler.job.Job;

/**
 * Created by Administrator on
 * 2017/9/21.
 */
public interface DAGSchedulerEvent {
    final class JobSubmitted implements DAGSchedulerEvent {
        public String jobid;
        public Job job;

        public JobSubmitted(String jobid, Job job) {
            this.jobid = jobid;
            this.job = job;
        }
    }

    final class TakeSuccess implements DAGSchedulerEvent {

    }

    final class TakeFailed implements DAGSchedulerEvent {

    }

    final class TaskSubmitted implements DAGSchedulerEvent {
        public ActionTask task;

        public TaskSubmitted(ActionTask take) {
            this.task = take;
        }
    }

    final class TakeStart implements DAGSchedulerEvent {

    }

    final class JobStart implements DAGSchedulerEvent {

    }

    final class JobEnd implements DAGSchedulerEvent {

    }

    final class JobSuccess implements DAGSchedulerEvent {

    }

    final class JobFailed implements DAGSchedulerEvent {

    }
}
