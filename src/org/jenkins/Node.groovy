package org.jenkins

import hudson.model.Computer
import jenkins.model.Jenkins


import hudson.model.Node as JenkinsNode
import java.util.Date

class Node {
    private JenkinsNode node
    private Computer computer

    Node(String nodeName) {
        this.node = Jenkins.instance.getNode(nodeName)
        this.computer = node?.toComputer()
    }

    String getName() {
        return node ? node.name : "Node not found"
    }

    boolean isOnline() {
        return computer ? !computer.isOffline() : false
    }

    boolean isTemporarilyOffline() {
        return computer ? computer.isTemporarilyOffline() : false
    }

    boolean isIdle() {
        return computer ? computer.isIdle() : false
    }

    int getNumberOfExecutors() {
        return computer ? computer.countExecutors() : 0
    }

    String getExecutors() {
        return computer ? computer.executors.collect { it.displayName }.join(', ') : "None"
    }

    Map getMonitorData() {
        return computer ? computer.monitorData : [:]
    }

    Date getConnectTime() {
        return computer ? new Date(computer.connectTime) : null
    }

    Date getLaunchTime() {
        if (computer instanceof hudson.slaves.SlaveComputer) {
            return new Date(computer.getConnectTime())
        } else {
            return null
        }
    }

    String getOfflineCause() {
        return computer ? (computer.offlineCause?.toString() ?: 'None') : 'None'
    }

    static List<Node> getAllNodes() {
        def jenkins = Jenkins.instance
        def nodes = jenkins.nodes
        return nodes.collect { new Node(it.name) }
    }
}

