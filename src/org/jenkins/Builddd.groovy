package org.jenkins
import jenkins.model.Jenkins
import hudson.model.Run


class Builds {
    String jobName
    int buildNumber

    Builds(String jobName, int buildNumber) {
        this.jobName = jobName
        this.buildNumber = buildNumber
    }

    def getJob() {
        return Jenkins.instance.getItem(jobName)
    }

    def getBuild() {
        def job = getJob()
        return job ? job.getBuildByNumber(buildNumber) : null
    }

    def getBuildNumber() {
        def build = getBuild()
        return build ? build.number : "Build not found."
    }

    def getBuildStatus() {
        def build = getBuild()
        return build ? build.result : "Build not found."
    }

    def getBuildDuration() {
        def build = getBuild()
        return build ? build.durationString : "Build not found."
    }

    def getBuildTimestamp() {
        def build = getBuild()
        return build ? build.timestampString : "Build not found."
    }

    def getBuildParameters() {
        def build = getBuild()
        return build ? build.buildVariables : "Build not found."
    }
}

