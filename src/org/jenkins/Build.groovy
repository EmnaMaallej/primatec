package org.jenkins

import hudson.model.AbstractProject
import hudson.model.Run
import hudson.model.Result

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
