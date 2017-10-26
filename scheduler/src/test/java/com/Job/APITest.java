package com.Job;

import com.toy.api.ProperJob;
import com.toy.scheduler.execute.Executer;
import com.toy.scheduler.job.DAGScheduler;
import com.toy.scheduler.job.Job;
import com.toy.scheduler.job.element.CommentAction;
import com.toy.scheduler.job.element.Element;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

public class APITest {
    @Test
    public void createJob1() {
        ProperJob properJob = new ProperJob("job1");
        Job job = properJob.build();
        printJob(job);
    }

    @Test
    public void createJob2() {
        ProperJob properJob = new ProperJob("job1");
        CommentAction comm11 = new CommentAction("comm11");
        CommentAction comm12 = new CommentAction("comm12");
        CommentAction comm21 = new CommentAction("comm21");
        CommentAction comm22 = new CommentAction("comm22");
        properJob.setStar(comm11);
        properJob.setStar(comm12);
        properJob.setFollow(comm21, "comm11");
        properJob.setFollow(comm22, "comm12");
        Job job = properJob.build();
        printJob(job);
    }

    private void printJob(Job job) {
        printE(job.getElement().getSub(), 1);
    }

    private void printE(List<Element> element, int i) {
        for (Element anElement : element) {
            for (int l = 0; l < i; l++)
                System.out.print("-");
            System.out.println(anElement.getName());
            int j = i + 1;
            printE(anElement.getSub(), j);
        }
    }

    @Test
    public void smitubJob() throws InterruptedException {
        ProperJob properJob = new ProperJob("job1");
        ActionTest comm11 = new ActionTest("1", "comm11");
        ActionTest comm12 = new ActionTest("2", "comm12");
        ActionTest comm21 = new ActionTest("3", "comm21");
        ActionTest comm22 = new ActionTest("4", "comm22");
        properJob.setStar(comm11);
        properJob.setStar(comm12);
        properJob.setFollow(comm21, "comm11");
        properJob.setFollow(comm22, "comm12");
        Job job = properJob.build();
        new DAGScheduler(new Properties(), new Executer(new Properties())).jobSubmitted(job);
        while (true) {
            Thread.sleep(10000);
        }
    }


}
