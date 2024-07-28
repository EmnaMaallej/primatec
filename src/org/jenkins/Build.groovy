package org.jenkins

import hudson.model.AbstractProject
import hudson.model.Run
import hudson.model.Result

class Build implements JenkinsEntity {
    private AbstractProject project
    private Run lastBuild

    Build(AbstractProject project) {
        this.project = project
        this.lastBuild = project.lastBuild
    }

    @Override
    String getName() {
        return project.fullName
    }

    int getLastBuildNumber() {
        return lastBuild ? lastBuild.number : -1
    }

    String getLastBuildStatus() {
        return lastBuild ? (lastBuild.result ? lastBuild.result.toString() : 'IN_PROGRESS') : 'No builds found'
    }

    JobInfo getJob() {
        JobInfo.getAllJobs().find { it.getName() == this.getProjectName() }
    }

    Node getNode() {
        def executor = this.lastBuild.getExecutor()
        def owner = executor.getOwner()
        owner.getNode()
    }

    static List<Build> getAllBuilds() {
        def jenkins = Jenkins.instance
        def projects = jenkins.getAllItems(AbstractProject.class)
        return projects.collect { new Build(it) }
    }

    static Build getBuildByNumber(String jobName, int buildNumber) {
        def job = JobInfo.getAllJobs().find { it.getName() == jobName }
        if (job) {
            def builds = job.getBuilds()
            return builds.find { it.getLastBuildNumber() == buildNumber }
        }
        return null
    }
}


