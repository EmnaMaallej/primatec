package org.jenkins

import hudson.model.Node as HudsonNode
import jenkins.model.Jenkins


class Nodes {
    String name

    Nodes(String name) {
        this.name = name
    }

    def getNodeInstance() {
        return Jenkins.instance.getNode(name)
    }

    def getName() {
        return getNodeInstance().displayName
    }

    def isIdle() {
        return getNodeInstance().isIdle()
    }

    def getNumExecutors() {
        return getNodeInstance().numExecutors
    }

    def getMode() {
        return getNodeInstance().mode.toString()
    }

    def getLabels() {
        return getNodeInstance().assignedLabels
    }

    def getDescription() {
        return getNodeInstance().nodeDescription
    }

    def getOfflineCause() {
        return getNodeInstance().getOfflineCauseReason()
    }

    def getLaunchTimeout() {
        return getNodeInstance().launchTimeoutMilliseconds
    }

    def getAllProperties() {
        return [
            name            : getName(),
            idle            : isIdle(),
            numExecutors    : getNumExecutors(),
            mode            : getMode(),
            labels          : getLabels(),
            description     : getDescription(),
            offlineCause    : getOfflineCause(),
            launchTimeout   : getLaunchTimeout()
        ]
    }
}

