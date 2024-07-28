package org.jenkins

import hudson.model.AbstractProject
import hudson.model.Run
import hudson.model.Result

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
        return build ? build.result.toString() : "Build not found"
    }

    long getDuration() {
        return build ? build.duration : 0L
    }

    Date getTimestamp() {
        return build ? new Date(build.timeInMillis) : null
    }

    String getCauses() {
        return build ? build.causes.collect { it.shortDescription }.join(', ') : "Build not found"
    }

    Map getParameters() {
        return build ? build.getAction(hudson.model.ParametersAction)?.parameters.collectEntries {
            [(it.name): it.value]
        } : [:]
    }
}
