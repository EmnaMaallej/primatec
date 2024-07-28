package org.jenkins

import hudson.model.Computer
import hudson.model.Node as JenkinsNode
import java.util.Date

class Node implements JenkinsEntity {
    private JenkinsNode node
    private Computer computer

    Node(JenkinsNode node) {
        this.node = node
        this.computer = node.toComputer()
    }

    @Override
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

    List<JobInfo> getJobs() {
        JobInfo.getAllJobs().findAll { it.getNode().getName() == this.getName() }
    }

    List<Build> getBuilds() {
        Build.getAllBuilds().findAll { it.getNode().getName() == this.getName() }
    }

    static List<Node> getAllNodes() {
        def jenkins = Jenkins.instance
        def nodes = jenkins.nodes
        return nodes.collect { new Node(it) }
    }

    // New method to run a job on this node and display properties
    void runJob(JobInfo job) {
        // Display properties of the job
        job.displayProperties()
        
        // Display properties of this node
        this.displayProperties()

        // Schedule the job to run on this node
        job.scheduleBuildOnNode(this.getName())
    }

    
}






