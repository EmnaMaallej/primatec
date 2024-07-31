package org.jenkins

import jenkins.model.Jenkins

class JobInfoManager {
    private String jobName
    private Job job

    JobInfoManager(String jobName) {
        this.jobName = jobName
        this.job = new Job(jobName)
    }

    Map<String, Object> getCompleteJobDetails() {
        def jobDetails = job.getJobProperties()
        def buildsInfo = getAllBuildsInfo()

        return [
            'Job Details': jobDetails,
            'Builds Info': buildsInfo
        ]
    }

    List<Map<String, Object>> getAllBuildsInfo() {
        def builds = job?.job?.builds ?: []
        return builds.collect { build ->
            [
                number: build?.number,
                result: build?.result?.toString(),
                duration: build?.duration,
                timestamp: build?.timestamp,
                causes: build?.causes?.collect { it?.toString() },
                parameters: job.getBuildParameters(build?.number),
                'Node Details': getNodeDetails(build)
            ]
        }
    }

    private Map<String, Object> getNodeDetails(def build) {
        if (build?.builtOn) {
            def node = new Node(build.builtOn.getNodeName())
            return node.getNodeProperties()
        } else {
            return [:]
        }
    }
}
