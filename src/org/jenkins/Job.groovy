package org.jenkins

import hudson.model.Job
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import jenkins.model.Jenkins

class Job {
    private Job job

    Job(Job job) {
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

    static List<Job> getAllJobs() {
        def jenkins = Jenkins.instance
        def jobs = jenkins.getAllItems(Job.class)
        return jobs.collect { new Job(it) }
    }
}


