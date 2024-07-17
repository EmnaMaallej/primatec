package org.jenkins

import hudson.model.Computer
import hudson.model.Node as JenkinsNode
import org.jenkinsci.plugins.workflow.job.WorkflowJob

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
        return nodes.collect { new Node(it) }
    }
}




