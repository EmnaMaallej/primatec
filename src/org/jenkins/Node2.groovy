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
        def computer = node ? node.toComputer() : null
        def monitorData = [:]
        if (computer) {
            monitorData.put("hudson.node_monitors.ArchitectureMonitor", computer.getMonitorData().get("hudson.node_monitors.ArchitectureMonitor"))
            monitorData.put("hudson.node_monitors.ClockMonitor", computer.getMonitorData().get("hudson.node_monitors.ClockMonitor"))
            monitorData.put("hudson.node_monitors.DiskSpaceMonitor", computer.getMonitorData().get("hudson.node_monitors.DiskSpaceMonitor"))
            monitorData.put("hudson.node_monitors.ResponseTimeMonitor", computer.getMonitorData().get("hudson.node_monitors.ResponseTimeMonitor"))
            monitorData.put("hudson.node_monitors.SwapSpaceMonitor", computer.getMonitorData().get("hudson.node_monitors.SwapSpaceMonitor"))
            monitorData.put("hudson.node_monitors.TemporarySpaceMonitor", computer.getMonitorData().get("hudson.node_monitors.TemporarySpaceMonitor"))
        }
        return monitorData
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
