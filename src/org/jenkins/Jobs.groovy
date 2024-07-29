package org.jenkins

import hudson.model.Job as HudsonJob
import hudson.model.Run
import hudson.model.ParametersAction
import hudson.model.StringParameterValue
import jenkins.model.Jenkins

class Jobs {
    String name

    Jobs(String name) {
        this.name = name
    }

    def getJobInstance() {
        return Jenkins.instance.getJob(name)
    }

    def getJobName() {
        return getJobInstance().displayName
    }

    def getDescription() {
        return getJobInstance().description
    }

    def getLastBuild() {
        return getJobInstance().lastBuild.number
    }

    def getLastCompletedBuild() {
        return getJobInstance().lastCompletedBuild.number
    }

    def getNextBuildNumber() {
        return getJobInstance().nextBuildNumber
    }

    def getBuilds() {
        return getJobInstance().builds.collect { it.number }
    }

    def getBuildProperties(int buildNumber) {
        def build = new Builds(buildNumber, this)
        return build.getAllProperties()
    }

    def getAllBuildsAndNodeProperties() {
        def builds = getBuilds()
        return builds.collect { buildNumber ->
            getBuildProperties(buildNumber)
        }
    }

    def getAllProperties() {
        return [
            name                 : getJobName(),
            description          : getDescription(),
            lastBuild            : getLastBuild(),
            lastCompletedBuild   : getLastCompletedBuild(),
            nextBuildNumber      : getNextBuildNumber(),
            builds               : getBuilds()
        ]
    }
}
