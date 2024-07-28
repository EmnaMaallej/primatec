package org.jenkins

import hudson.model.Computer
import jenkins.model.Jenkins

class Node {
    def node

    Node(def node) {
        this.node = node
    }

    String getName() {
        return node.nodeName
    }

    boolean isOnline() {
        return node.toComputer()?.isOnline()
    }

    boolean isTemporarilyOffline() {
        return node.toComputer()?.isTemporarilyOffline()
    }

    boolean isIdle() {
        return node.toComputer()?.isIdle()
    }

    int getNumberOfExecutors() {
        return node.numExecutors
    }

    List<Computer> getExecutors() {
        return node.toComputer()?.executors
    }

    Map getMonitorData() {
        return node.toComputer()?.getMonitorData()
    }

    long getConnectTime() {
        return node.toComputer()?.connectTime
    }

    long getLaunchTime() {
        return node.toComputer()?.startTime
    }

    def getOfflineCause() {
        return node.toComputer()?.offlineCause?.description
    }

    void runJob(JobInfo jobInfo) {
        // Implement job running logic here, for example:
        def job = jobInfo.getJob()
        job.scheduleBuild2(0, new hudson.model.Cause.UserIdCause())
    }
}







