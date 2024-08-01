package org.jenkins

import hudson.model.Run
import jenkins.model.Jenkins

class Build {
    private Run build
    private String jobName
    private int buildNumber

    Build(String jobName, int buildNumber) {
        def job = Jenkins.instance.getItemByFullName(jobName)
        this.build = job ? job.getBuildByNumber(buildNumber) : null
        this.jobName = jobName
        this.buildNumber = buildNumber
    }

    Run getRun() {
        def job = Jenkins.instance.getItemByFullName(jobName)
        return job?.getBuildByNumber(buildNumber)
    }

    String getNodeName() {
        def run = getRun()
        if (run) {
            def executor = run.getExecutor()
            if (executor) {
                def node = executor.getOwner().getNode()
                if (node) {
                    return node.getNodeName()
                } else {
                    println "Node not found for build ${buildNumber} of job ${jobName}"
                }
            } else {
                println "Executor not found for build ${buildNumber} of job ${jobName}"
            }
        } else {
            println "Run not found for build ${buildNumber} of job ${jobName}"
        }
        return "UNKNOWN"
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
        def parametersAction = build ? build.getAction(hudson.model.ParametersAction) : null
        return parametersAction ? parametersAction.parameters.collectEntries {
            [(it.name): it.value]
        } : [:]
    }

    String getNodeName() {
        return build ? build.executor?.owner?.node?.displayName : "Node not found"
    }
}









