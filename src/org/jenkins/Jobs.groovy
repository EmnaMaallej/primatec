package org.jenkins

import hudson.model.Job as HudsonJob
import hudson.model.Run
import hudson.model.ParametersAction
import hudson.model.StringParameterValue
import jenkins.model.Jenkins
import hudson.model.Node



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
            def node = build.getBuiltOn()
            def nodeName = node ? node.getNodeName() : "Unknown Node"
            def nodeDetails = node ? new Nodes(nodeName) : new Nodes("")

            def properties = [
                "Build Number": build.number,
                "Status": build.result,
                "Duration": build.durationString,
                "Timestamp": build.timestampString,
                "Node Name": nodeName,
                "Node Description": nodeDetails.getNodeDescription(),
                "Executors": nodeDetails.getNumExecutors(),
                "Remote FS": nodeDetails.getRemoteFS(),
                "Labels": nodeDetails.getLabels()
            ]
            buildProperties.add(properties)
        }
        return buildProperties
    }
}
