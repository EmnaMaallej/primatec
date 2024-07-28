package org.jenkins

import hudson.model.Computer
import jenkins.model.Jenkins

class Node {
    def nodeName

    Node(String nodeName) {
        this.nodeName = nodeName
    }

    def getName() {
        def node = Jenkins.instance.getNode(nodeName)
        return node ? node.getNodeName() : "Node not found"
    }

    def getDescription() {
        def node = Jenkins.instance.getNode(nodeName)
        return node ? node.getNodeDescription() : "Node not found"
    }

    def getRemoteFS() {
        def node = Jenkins.instance.getNode(nodeName)
        return node ? node.getRemoteFS() : "Node not found"
    }

    def getNumExecutors() {
        def node = Jenkins.instance.getNode(nodeName)
        return node ? node.getNumExecutors() : "Node not found"
    }

    def getLabels() {
        def node = Jenkins.instance.getNode(nodeName)
        return node ? node.getLabelString() : "Node not found"
    }

    def isOffline() {
        def node = Jenkins.instance.getNode(nodeName)
        return node ? node.toComputer().isOffline() : "Node not found"
    }
}
