package org.jenkins

import hudson.model.Job as HudsonJob
import hudson.model.Run
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
        def params = build?.actions.find { it instanceof ParametersAction }?.parameters ?: []
        return params.collect { param ->
            [name: param.name, value: param instanceof StringParameterValue ? param.value : 'Non-string parameter']
        }
    }

    List<Map<String, Object>> getAllBuildsInfo() {
        def builds = job ? job.builds : []
        return builds.collect { build ->
            [
                number: build.number,
                result: build.result.toString(),
                duration: build.duration,
                timestamp: build.timestamp,
                causes: build.causes.collect { it.toString() },
                parameters: getBuildParameters(build.number)
            ]
        }
    }
}



