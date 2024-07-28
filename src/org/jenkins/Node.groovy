package org.jenkins

import hudson.model.Node as HudsonNode
import jenkins.model.Jenkins

class Node {
    private HudsonNode node

    Node(String nodeName) {
        this.node = Jenkins.instance.getNode(nodeName)
    }

    String getName() {
        return node ? node.name : "Node not found"
    }

    boolean isOnline() {
        return node ? node.toComputer()?.isOnline() : false
    }

    boolean isTemporarilyOffline() {
        return node ? node.toComputer()?.isTemporarilyOffline() : false
    }

    boolean isIdle() {
        return node ? node.toComputer()?.isIdle() : false
    }

    int getNumberOfExecutors() {
        return node ? node.numExecutors : 0
    }

    List<String> getExecutors() {
        return node ? (0..<node.numExecutors).collect { "Executor ${it + 1}" } : []
    }

    Map<String, Object> getMonitorData() {
        def monitor = node ? node.toComputer()?.monitor : null
        return monitor ? [
            "hudson.node_monitors.Jvm" : monitor.jvm,
            "hudson.node_monitors.ResponseTime" : monitor.responseTime,
            "hudson.node_monitors.SwapSpace" : monitor.swapSpace,
            "hudson.node_monitors.TmpSpace" : monitor.tmpSpace,
            "hudson.node_monitors.DiskSpace" : monitor.diskSpace,
            "hudson.node_monitors.Throughput" : monitor.throughput
        ] : [:]
    }

    long getConnectTime() {
        return node ? node.toComputer()?.connectTime : 0
    }

    long getLaunchTime() {
        return node ? node.toComputer()?.launchTime : 0
    }

    String getOfflineCause() {
        return node ? node.toComputer()?.offlineCause?.message : "Offline cause not available"
    }

    Map<String, Object> getNodeProperties() {
        return node ? [
            "Name": node.name,
            "Online": isOnline(),
            "Temporarily Offline": isTemporarilyOffline(),
            "Idle": isIdle(),
            "Number of Executors": getNumberOfExecutors(),
            "Executors": getExecutors(),
            "Monitor Data": getMonitorData(),
            "Connect Time": getConnectTime(),
            "Launch Time": getLaunchTime(),
            "Offline Cause": getOfflineCause()
        ] : [:]
    }
}


