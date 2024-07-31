package org.jenkins

import jenkins.model.Jenkins

class JobInfoManager {
    private Job job

    JobInfoManager(String jobName) {
        this.job = new Job(jobName)
    }

    Map<String, Object> getJobDetails() {
        return [
            "Job Name": job.getName(),
            "Job Class": job.getJobClass(),
            "Description": job.getDescription(),
            "Build Count": job.getBuildCount(),
            "Disabled": job.isDisabled()
        ]
    }

    List<Map<String, Object>> getAllBuildsInfo() {
        def allBuildsInfo = job.getAllBuildsInfo()
        return allBuildsInfo.collect { buildInfo ->
            def build = new Build(job.getName(), buildInfo.number)
            def nodeName = build.build ? build.build.executor.owner.name : "Unknown"
            def node = new Node(nodeName)
            buildInfo["Node Details"] = node.getNodeProperties()
            return buildInfo
        }
    }

    Map<String, Object> getCompleteJobDetails() {
        return [
            "Job Details": getJobDetails(),
            "Builds Info": getAllBuildsInfo()
        ]
    }
}
