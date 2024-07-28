package org.jenkins

import hudson.model.Job
import hudson.model.Run
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import hudson.model.ParametersAction
import hudson.model.StringParameterValue

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

    // Method to display job properties
    void displayProperties() {
        println "Job Name: ${getName()}"
        println "Job Class: ${getJobClass()}"
        println "Build Details: ${getBuildDetails().join('\n')}"
    }

    // Method to schedule this job to be executed on the specified node
    void scheduleBuildOnNode(String nodeName) {
        def jobToRun = job

        if (jobToRun instanceof WorkflowJob) {
            // Schedule the build with node restriction
            def parametersAction = new ParametersAction([
                new StringParameterValue("NODE_NAME", nodeName)
            ])
            jobToRun.scheduleBuild2(0, parametersAction)
        } else {
            // Handle other types of jobs if necessary
        }
    }
}






