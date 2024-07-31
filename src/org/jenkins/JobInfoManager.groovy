package org.jenkins

import jenkins.model.Jenkins
import hudson.model.Job
import hudson.model.Run
import hudson.model.Node

class JobInfoManager {
    String jobName

    JobInfoManager(String jobName) {
        this.jobName = jobName
    }

    Map getCompleteJobDetails() {
        def jobDetails = [:]
        def job = Jenkins.instance.getJob(jobName)
        
        if (job) {
            jobDetails['Job Details'] = getJobProperties(job)
            jobDetails['Builds Info'] = getAllBuildsInfo(job)
        } else {
            println "Job ${jobName} not found."
        }

        return jobDetails
    }

    Map getJobProperties(Job job) {
        return [
            'name': job.name,
            'url': job.url,
            'description': job.description,
            'displayName': job.displayName,
            'fullName': job.fullName,
            'buildable': job.buildable,
            'inQueue': job.inQueue
        ]
    }

    List<Map> getAllBuildsInfo(Job job) {
        def buildsInfo = []
        job.builds.each { Run build ->
            def buildInfo = [:]
            buildInfo['number'] = build.number
            buildInfo['result'] = build.result
            buildInfo['duration'] = build.duration
            buildInfo['timestamp'] = build.timestamp
            buildInfo['causes'] = build.causes.toString()
            buildInfo['parameters'] = build.getAction(hudson.model.ParametersAction)?.parameters?.collectEntries { [(it.name): it.value] }
            buildInfo['Node Details'] = getNodeDetails(build)
            buildsInfo << buildInfo
        }
        return buildsInfo
    }

    Map getNodeDetails(Run build) {
        def nodeDetails = [:]
        def node = build.executor?.owner?.node
        
        if (node) {
            nodeDetails['name'] = node.displayName
            nodeDetails['numExecutors'] = node.numExecutors
            nodeDetails['isIdle'] = node.toComputer()?.isIdle()
            nodeDetails['isOffline'] = node.toComputer()?.isOffline()
            nodeDetails['labels'] = node.assignedLabels.collect { it.name }
        } else {
            println "Node information not available for build ${build.number}."
        }

        return nodeDetails
    }
}

