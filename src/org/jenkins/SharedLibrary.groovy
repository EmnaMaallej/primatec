package org.jenkins

import hudson.model.Computer
import hudson.model.Node
import hudson.model.AbstractBuild



class SharedLibrary {

    static List<String> getAgentProperties() {
        def jenkins = Jenkins.instance
        def nodes = jenkins.nodes

        nodes.collect { node ->
            def computer = node.toComputer()
            "Agent Name: ${node.name}, Offline: ${computer.isOffline()}, Number of Executors: ${computer.countExecutors()}"
        }
    }

    static List<String> getBuildInfo() {
        def jenkins = Jenkins.instance
        def builds = jenkins.getAllItems(AbstractProject.class)

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
        def jobs = jenkins.getAllItems(Job.class)

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



       
