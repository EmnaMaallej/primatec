package org.jenkins

import jenkins.model.Jenkins

class Nodes {
    String name

    Nodes(String name) {
        this.name = name
    }

    def getNode() {
        return Jenkins.instance.getNode(name)
    }

    def getNodeName() {
        def node = getNode()
        return node ? node.getNodeName() : "Node not found."
    }

    def getNodeDescription() {
        def node = getNode()
        return node ? node.getNodeDescription() : "Node not found."
    }

    def getNumExecutors() {
        def node = getNode()
        return node ? node.getNumExecutors() : "Node not found."
    }

    def getRemoteFS() {
        def node = getNode()
        return node ? node.getRemoteFS() : "Node not found."
    }

    def getLabels() {
        def node = getNode()
        return node ? node.getLabelString() : "Node not found."
    }
}

