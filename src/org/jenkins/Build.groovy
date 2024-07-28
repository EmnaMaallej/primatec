package org.jenkins

import hudson.model.Build
import hudson.model.Run
import hudson.model.ParametersAction
import hudson.model.StringParameterValue
import jenkins.model.Jenkins

class Build {
    private Run build

    Build(String jobName, int buildNumber) {
        def job = Jenkins.instance.getItemByFullName(jobName)
        this.build = job ? job.getBuildByNumber(buildNumber) : null
    }

    int getNumber() {
        return build ? build.number : -1
    }

    String getResult() {
        return build ? build.result.toString() : "Result not found"
    }

    long getDuration() {
        return build ? build.duration : 0
    }

    long getTimestamp() {
        return build ? build.timestamp : 0
    }

    List<String> getCauses() {
        return build ? build.causes.collect { it.toString() } : []
    }

    List<Map<String, String>> getParameters() {
        def params = build?.actions.find { it instanceof ParametersAction }?.parameters ?: []
        return params.collect { param ->
            [name: param.name, value: param instanceof StringParameterValue ? param.value : 'Non-string parameter']
        }
    }
    
    String getAgent() {
        return build ? build.executor?.node?.name : "Agent not found"
    }
}


