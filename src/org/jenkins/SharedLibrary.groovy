package org.jenkins

import hudson.model.Computer
import hudson.model.Node
import org.jenkinsci.plugins.workflow.job.WorkflowJob

import java.util.Date

class SharedLibrary {

    static List<String> getAgentProperties() {
        def jenkins = Jenkins.instance
        def nodes = jenkins.nodes

        nodes.collect { node ->
            def computer = node.toComputer()
            def nodeDetails = "Agent Name: ${node.name}"

            if (computer) {
                nodeDetails += "\n\tOnline: ${!computer.isOffline()}"
                nodeDetails += "\n\tTemporarily Offline: ${computer.isTemporarilyOffline()}"
                nodeDetails += "\n\tIdle: ${computer.isIdle()}"
                nodeDetails += "\n\tNumber of Executors: ${computer.countExecutors()}"
                nodeDetails += "\n\tExecutors: ${computer.executors.collect { it.displayName }.join(', ')}"
                nodeDetails += "\n\tMonitor Data: ${computer.monitorData}"
                nodeDetails += "\n\tConnect Time: ${new Date(computer.connectTime)}"
                
                // Check if the computer is an instance of SlaveComputer
                if (computer instanceof hudson.slaves.SlaveComputer) {
                    nodeDetails += "\n\tLaunch Time: ${new Date(computer.getConnectTime())}"
                } else {
                    nodeDetails += "\n\tLaunch Time: Not applicable"
                }
                
                nodeDetails += "\n\tOffline Cause: ${computer.offlineCause?.toString() ?: 'None'}"
            } else {
                nodeDetails += "\n\tOffline: true (Not connected)"
            }

            nodeDetails
        }
    }

    static List<String> getBuildInfo() {
        def jenkins = Jenkins.instance
        def builds = jenkins.getAllItems(hudson.model.AbstractProject.class)

        builds.collect { project ->
            def lastBuild = project.lastBuild
            if (lastBuild) {
                "Build Name: ${project.fullName}, Build Number: ${lastBuild.number}, Build Status: ${lastBuild.result ?: 'IN_PROGRESS'}"
            } else {
                "No builds found for project: ${project.fullName}"
            }
        }
    }

    static List<String> getJobDetails() {
        def jenkins = Jenkins.instance
        def jobs = jenkins.getAllItems(hudson.model.Job.class)

        jobs.collect { job ->
            def details = "Job Name: ${job.fullName}, Class: ${job.class.simpleName}"
            if (job instanceof WorkflowJob) {
                def builds = job.builds.limit(5)
                details += builds.collect { build ->
                    "\n\tBuild Name: ${build.fullDisplayName}, Duration: ${build.durationString}, Result: ${build.result ?: 'IN_PROGRESS'}"
                }.join("\n\t")
            } else {
                def lastBuild = job.lastBuild
                if (lastBuild) {
                    details += "\n\tBuild Name: ${lastBuild.fullDisplayName}, Build Number: ${lastBuild.number}, Build Status: ${lastBuild.result ?: 'IN_PROGRESS'}"
                } else {
                    details += "\n\tNo builds found"
                }
            }
            details
        }
    }
}


