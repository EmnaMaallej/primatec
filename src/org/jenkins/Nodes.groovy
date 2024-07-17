package org.jenkins

import hudson.model.Computer
import hudson.model.Node as JenkinsNode
import hudson.model.AbstractProject
import hudson.model.Job
import hudson.model.Run
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

class Build {
    private AbstractProject project
    private Run lastBuild

    Build(AbstractProject project) {
        this.project = project
        this.lastBuild = project.lastBuild
    }

    String getProjectName() {
        return project.fullName
    }

    int getLastBuildNumber() {
        return lastBuild ? lastBuild.number : -1
    }

    String getLastBuildStatus() {
        return lastBuild ? (lastBuild.result ? lastBuild.result.toString() : 'IN_PROGRESS') : 'No builds found'
    }

    static List<Build> getAllBuilds() {
        def jenkins = Jenkins.instance
        def projects = jenkins.getAllItems(AbstractProject.class)
        return projects.collect { new Build(it) }
    }
}

class Job {
    private Job job

    JobInfo(Job job) {
        this.job = job
    }

    String getName() {
        return job.fullName
    }

    String getJobClass() {
        return job.class.simpleName
    }

    List<String> getBuildDetails() {
        if (job instanceof WorkflowJob) {
            def builds = job.builds.limit(5)
            return builds.collect { build ->
                "Build Name: ${build.fullDisplayName}, Duration: ${build.durationString}, Result: ${build.result ?: 'IN_PROGRESS'}"
            }
        } else {
            def lastBuild = job.lastBuild
            if (lastBuild) {
                return ["Build Name: ${lastBuild.fullDisplayName}, Build Number: ${lastBuild.number}, Build Status: ${lastBuild.result ?: 'IN_PROGRESS'}"]
            } else {
                return ["No builds found"]
            }
        }
    }

    static List<JobInfo> getAllJobs() {
        def jenkins = Jenkins.instance
        def jobs = jenkins.getAllItems(Job.class)
        return jobs.collect { new JobInfo(it) }
    }
}

