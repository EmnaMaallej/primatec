package org.jenkins

import hudson.model.Computer
import hudson.model.Node as JenkinsNode
import java.util.Date

class Node {
    private JenkinsNode node
    private Computer computer

    Node(JenkinsNode node) {
        this.node = node
        this.computer = node.toComputer()
    }

    String getName() {
        return node.name
    }

    boolean isOnline() {
        return computer && !computer.isOffline()
    }

    boolean isTemporarilyOffline() {
        return computer?.isTemporarilyOffline() ?: false
    }

    boolean isIdle() {
        return computer?.isIdle() ?: false
    }

    int getNumberOfExecutors() {
        return computer?.countExecutors() ?: 0
    }

    String getExecutors() {
        return computer?.executors?.collect { it.displayName }?.join(', ') ?: 'None'
    }

    String getMonitorData() {
        return computer?.monitorData?.toString() ?: 'None'
    }

    Date getConnectTime() {
        return computer ? new Date(computer.connectTime) : null
    }

    Date getLaunchTime() {
        return (computer instanceof hudson.slaves.SlaveComputer) ? new Date(computer.getConnectTime()) : null
    }

    String getOfflineCause() {
        return computer?.offlineCause?.toString() ?: 'None'
    }

    static List<Node> getAllNodes() {
        def jenkins = Jenkins.instance
        def nodes = jenkins.nodes
        return nodes.collect { new Node(it) }
    }
}


