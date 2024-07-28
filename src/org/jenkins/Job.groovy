package org.jenkins

import hudson.model.Job as HudsonJob
import hudson.model.ParametersAction
import hudson.model.StringParameterValue
import jenkins.model.Jenkins

class Job {
    private HudsonJob job

    Job(String jobName) {
        this.job = Jenkins.instance.getItemByFullName(jobName)
    }

    String getName() {
        return job ? job.name : "Job not found"
    }

    String getJobClass() {
        return job ? job.class.simpleName : "Class not found"
    }

    String getDescription() {
        return job ? job.description : "Job not found"
    }

    int getBuildCount() {
        return job ? job.builds.size() : 0
    }

    boolean isDisabled() {
        return job ? job.disabled : false
    }

    List<Map<String, String>> getBuildParameters(int buildNumber) {
        def build = job ? job.getBuildByNumber(buildNumber) : null
        if (build) {
            Build buildInstance = new Build(job.name, buildNumber)
            return buildInstance.getParameters()
        
        }
        return []
    }

    List<Map<String, Object>> getAllBuildsInfo() {
        def builds = job ? job.builds : []
        return builds.collect { build ->
            def buildInstance = new Build(job.name, build.number)
            [
                number: build.number,
                result: buildInstance.getResult(),
                duration: buildInstance.getDuration(),
                timestamp: buildInstance.getTimestamp(),
                causes: buildInstance.getCauses(),
                parameters: buildInstance.getParameters(),
                agent: buildInstance.getAgent() // Use Build class method to get agent info
            ]
        }
    }
}
