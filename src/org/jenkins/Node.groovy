package org.jenkins

import hudson.model.Computer
import jenkins.model.Jenkins

class Node {
    def script
    def nodeName

    Node(script, nodeName) {
        this.script = script
        this.nodeName = nodeName
    }

    def getNodeName() {
        return nodeName
    }

    def isOnline() {
        return script.sh(script: "curl -s http://jenkins/api/json | jq '.nodes[] | select(.displayName==\"${nodeName}\").offline'", returnStdout: true).trim() == "false"
    }

    def isTemporarilyOffline() {
        return script.sh(script: "curl -s http://jenkins/api/json | jq '.nodes[] | select(.displayName==\"${nodeName}\").temporarilyOffline'", returnStdout: true).trim() == "true"
    }

    def isIdle() {
        return script.sh(script: "curl -s http://jenkins/api/json | jq '.nodes[] | select(.displayName==\"${nodeName}\").idle'", returnStdout: true).trim() == "true"
    }

    def getNumberOfExecutors() {
        return script.sh(script: "curl -s http://jenkins/api/json | jq '.nodes[] | select(.displayName==\"${nodeName}\").numExecutors'", returnStdout: true).trim().toInteger()
    }

    def getExecutors() {
        return script.sh(script: "curl -s http://jenkins/api/json | jq '.nodes[] | select(.displayName==\"${nodeName}\").executors[]'", returnStdout: true).trim()
    }

    def getMonitorData() {
        return script.sh(script: "curl -s http://jenkins/api/json | jq '.nodes[] | select(.displayName==\"${nodeName}\").monitorData'", returnStdout: true).trim()
    }

    def getConnectTime() {
        return script.sh(script: "curl -s http://jenkins/api/json | jq '.nodes[] | select(.displayName==\"${nodeName}\").connectTime'", returnStdout: true).trim()
    }

    def getLaunchTime() {
        return script.sh(script: "curl -s http://jenkins/api/json | jq '.nodes[] | select(.displayName==\"${nodeName}\").launchTime'", returnStdout: true).trim()
    }

    def getOfflineCause() {
        return script.sh(script: "curl -s http://jenkins/api/json | jq '.nodes[] | select(.displayName==\"${nodeName}\").offlineCause'", returnStdout: true).trim()
    }

    def displayProperties() {
        script.echo "Agent Name: ${getNodeName()}"
        script.echo "Online: ${isOnline()}"
        script.echo "Temporarily Offline: ${isTemporarilyOffline()}"
        script.echo "Idle: ${isIdle()}"
        script.echo "Number of Executors: ${getNumberOfExecutors()}"
        script.echo "Executors: ${getExecutors()}"
        script.echo "Monitor Data: ${getMonitorData()}"
        script.echo "Connect Time: ${getConnectTime()}"
        script.echo "Launch Time: ${getLaunchTime()}"
        script.echo "Offline Cause: ${getOfflineCause()}"
    }
}






