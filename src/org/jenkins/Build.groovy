package org.jenkins

import hudson.model.AbstractProject
import hudson.model.Run
import hudson.model.Result




class Build {
    def jobName
    def buildNumber

    Build(String jobName, int buildNumber) {
        this.jobName = jobName
        this.buildNumber = buildNumber
    }

    def getNumber() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        if (job) {
            def build = job.getBuildByNumber(buildNumber)
            return build ? build.getNumber() : "Build not found"
        }
        return "Job not found"
    }

    def getResult() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        if (job) {
            def build = job.getBuildByNumber(buildNumber)
            return build ? build.getResult() : "Build not found"
        }
        return "Job not found"
    }

    def getDuration() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        if (job) {
            def build = job.getBuildByNumber(buildNumber)
            return build ? build.getDuration() : "Build not found"
        }
        return "Job not found"
    }

    def getTimestamp() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        if (job) {
            def build = job.getBuildByNumber(buildNumber)
            return build ? build.getTimeInMillis() : "Build not found"
        }
        return "Job not found"
    }

    def getCauses() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        if (job) {
            def build = job.getBuildByNumber(buildNumber)
            return build ? build.getCauses().collect { it.getShortDescription() } : "Build not found"
        }
        return "Job not found"
    }

    def getParameters() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        if (job) {
            def build = job.getBuildByNumber(buildNumber)
            return build ? build.getAction(hudson.model.ParametersAction)?.parameters.collect {
                "${it.getName()}: ${it.getValue()}"
            } : "Build not found"
        }
        return "Job not found"
    }
}



