package org.jenkins

import hudson.model.Job as HudsonJob
import hudson.model.Run
import hudson.model.ParametersAction
import hudson.model.StringParameterValue
import jenkins.model.Jenkins


class Jobs {
    String jobName
    def job

    Jobs(String jobName) {
        this.jobName = jobName
        this.job = Jenkins.instance.getItemByFullName(jobName)
    }

    
    Map getAllProperties() {
        def properties = [:]
        properties['displayName'] = job?.displayName
        properties['description'] = job?.description
        properties['url'] = job?.absoluteUrl
        properties['builds'] = job?.builds?.collect { build ->
            [
                'buildNumber': build.number,
                'result': build.result.toString(),
                'duration': build.duration,
                'node': build.builtOnStr,
                'nodeProperties': new Nodes(build.builtOnStr)?.getAllProperties()
            ]
        }
        return properties
    }

   
    Map getBuildProperties(int buildNumber) {
        def build = job?.getBuildByNumber(buildNumber)
        def properties = [:]
        if (build) {
            properties['result'] = build.result.toString()
            properties['duration'] = build.duration
            properties['node'] = build.builtOnStr
            properties['nodeProperties'] = new Nodes(build.builtOnStr)?.getAllProperties()
        } else {
            properties['error'] = "Build number ${buildNumber} not found."
        }
        return properties
    }

    
    List getAllBuildsAndNodeProperties() {
        def builds = []
        job?.builds?.each { build ->
            def buildProperties = [:]
            buildProperties['buildNumber'] = build.number
            buildProperties['result'] = build.result.toString()
            buildProperties['duration'] = build.duration
            buildProperties['node'] = build.builtOnStr
            buildProperties['nodeProperties'] = new Nodes(build.builtOnStr)?.getAllProperties()
            builds << buildProperties
        }
        return builds
    }
}

