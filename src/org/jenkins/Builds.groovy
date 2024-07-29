package org.jenkins
import jenkins.model.Jenkins
import hudson.model.Run

class Builds {
    int number
    Jobs job

    Builds(int number, Jobs job) {
        this.number = number
        this.job = job
    }

    def getBuildInstance() {
        return job.getJobInstance().getBuildByNumber(number)
    }

    def getBuildNumber() {
        return getBuildInstance().number
    }

    def getResult() {
        return getBuildInstance().result.toString()
    }

    def getDuration() {
        return getBuildInstance().duration
    }

    def getTimestamp() {
        return getBuildInstance().timestamp
    }

    def getBuiltOn() {
        return getBuildInstance().builtOnStr
    }

    def getChangeSet() {
        return getBuildInstance().changeSet
    }

    def getCulprits() {
        return getBuildInstance().culprits.collect { it.fullName }
    }

    def getNodeProperties() {
        def nodeName = getBuiltOn()
        def node = new Nodes(nodeName)
        return node.getAllProperties()
    }

    def getAllProperties() {
        return [
            buildNumber  : getBuildNumber(),
            result       : getResult(),
            duration     : getDuration(),
            timestamp    : getTimestamp(),
            builtOn      : getBuiltOn(),
            changeSet    : getChangeSet(),
            culprits     : getCulprits(),
            nodeProperties: getNodeProperties()
        ]
    }
}
