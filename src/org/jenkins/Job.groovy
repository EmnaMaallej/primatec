package org.jenkins

import hudson.model.Job
import hudson.model.Run
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import hudson.model.ParametersAction
import hudson.model.StringParameterValue

import jenkins.model.Jenkins

class Job {
    private hudson.model.Job job

    Job(String jobName) {
        this.job = Jenkins.instance.getItemByFullName(jobName)
    }

    String getName() {
        return job ? job.name : "Job not found"
    }

    String getJobClass() {
        return job.class.simpleName

    String getDescription() {
        return job ? job.description : "Job not found"
    }

    int getBuildCount() {
        return job ? job.builds.size() : 0
    }

    boolean isDisabled() {
        return job ? job.isDisabled() : false
    }
}
