package org.jenkins

import hudson.model.Node
import hudson.model.AbstractProject

class JenkinsInfo {
    
    static void printAgentProperties() {
        def jenkins = Jenkins.instance
        def agents = jenkins.nodes

        agents.each { agent ->
            echo "Agent Name: ${agent.name}"
            echo "Idle: ${agent.isIdle()}"
            echo "Number of Executors: ${agent.getNumExecutors()}"
        }
    }

    static void printBuildInfo() {
        def jenkins = Jenkins.instance
        def builds = jenkins.getAllItems(AbstractProject.class)

        builds.each { build ->
            echo "Build Name: ${build.fullName}"
            echo "Build Number: ${build.lastBuild.number}"
            echo "Build Status: ${build.lastBuild.result}"
        }
    }
}
