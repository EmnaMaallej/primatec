package org.jenkins

import hudson.model.Job
import hudson.model.Run
import org.jenkinsci.plugins.workflow.job.WorkflowJob

class JobInfo implements JenkinsEntity {
    private Job job

    JobInfo(Job job) {
        this.job = job
    }

    @Override
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

    List<Build> getBuilds() {
        Build.getAllBuilds().findAll { it.getProjectName() == this.getName() }
    }

    Node getNode() {
        def build = this.job.getLastBuild()
        build.getExecutor().getOwner().getNode()
    }

    static List<JobInfo> getAllJobs() {
        def jenkins = Jenkins.instance
        def jobs = jenkins.getAllItems(Job.class)
        return jobs.collect { new JobInfo(it) }
    }

    void executeOnNode(Node node) {
        // Implement logic to schedule this job to be executed on the specified node
    }
}



