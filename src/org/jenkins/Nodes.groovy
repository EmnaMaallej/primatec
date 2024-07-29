package org.jenkins

import hudson.model.Node as HudsonNode
import jenkins.model.Jenkins


class Nodes {
    String nodeName
    def node

    Nodes(String nodeName) {
        this.nodeName = nodeName
        this.node = Jenkins.instance.getNode(nodeName)
    }

    // Example method to get node display name
    String getDisplayName() {
        return node?.displayName
    }

    // Example method to get node description
    String getDescription() {
        return node?.description
    }

    // Example method to get node label string
    String getLabelString() {
        return node?.labelString
    }

    // Example method to get the node status
    String getStatus() {
        return node?.toComputer()?.isOffline() ? "Offline" : "Online"
    }

    // Method to get all properties
    Map getAllProperties() {
        def properties = [:]
        properties['displayName'] = getDisplayName()
        properties['description'] = getDescription()
        properties['labelString'] = getLabelString()
        properties['status'] = getStatus()
        return properties
    }
}

