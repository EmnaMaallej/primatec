package org.jenkins

class SharedLibrary {

    static List<String> getAgentProperties() {
        def jenkins = Jenkins.instance
        def agents = jenkins.nodes.collect { agent ->
            "Agent Name: ${agent.name}, Idle: ${agent.isIdle()}, Number of Executors: ${agent.getNumExecutors()}"
        }
        return agents
    }

    static List<String> getBuildInfo() {
        def jenkins = Jenkins.instance
        def builds = jenkins.getAllItems(hudson.model.AbstractProject.class).collect { build ->
            "Build Name: ${build.fullName}, Build Number: ${build.lastBuild.number}, Build Status: ${build.lastBuild.result}"
        }
        return builds
    }
}

