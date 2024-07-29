package org.jenkins

import hudson.model.Job as HudsonJob
import hudson.model.Run
import hudson.model.ParametersAction
import hudson.model.StringParameterValue
import jenkins.model.Jenkins



class Jobs {
    String jobName

    Jobs(String jobName) {
        this.jobName = jobName
    }

    def getJob() {
        return Jenkins.instance.getItem(jobName)
    }

    def getAllBuilds() {
        def job = getJob()
        return job ? job.builds : []
    }

    def getBuildsWithProperties() {
        def builds = getAllBuilds()
        def buildProperties = []
        builds.each { build ->
            def buildNode = build.getBuiltOnStr()
            def node = new Nodes(buildNode)
            def properties = [
                "Build Number": build.number,
                "Status": build.result,
                "Duration": build.durationString,
                "Timestamp": build.timestampString,
                "Node Name": buildNode,
                "Node Description": node.getNodeDescription(),
                "Executors": node.getNumExecutors(),
                "Remote FS": node.getRemoteFS(),
                "Labels": node.getLabels()
            ]
            buildProperties.add(properties)
        }
        return buildProperties
    }
}


