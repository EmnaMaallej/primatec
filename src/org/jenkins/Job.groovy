package org.jenkins

import hudson.model.Job
import hudson.model.Run
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import hudson.model.ParametersAction
import hudson.model.StringParameterValue



class Job {
    def jobName

    Job(String jobName) {
        this.jobName = jobName
    }

    def getName() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        return job ? job.getName() : "Job not found"
    }

    def getDescription() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        return job ? job.getDescription() : "Job not found"
    }

    def getLastBuildNumber() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        return job ? job.getLastBuild()?.getNumber() : "Job not found"
    }

    def getLastStableBuildNumber() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        return job ? job.getLastStableBuild()?.getNumber() : "Job not found"
    }

    def getLastSuccessfulBuildNumber() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        return job ? job.getLastSuccessfulBuild()?.getNumber() : "Job not found"
    }

    def getBuildCount() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        return job ? job.getBuilds().size() : "Job not found"
    }

    def isDisabled() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        return job ? job.isDisabled() : "Job not found"
    }
}
