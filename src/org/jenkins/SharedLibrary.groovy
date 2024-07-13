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

        builds.collect { build ->
            "Build Name: ${build.fullName}, Build Number: ${build.lastBuild.number}, Build Status: ${build.lastBuild.result}"
        }
    }
}

