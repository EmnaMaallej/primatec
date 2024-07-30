package org.jenkins

class CentralManager {
    List<Node> nodes
    List<Job> jobs
    List<Build> builds

    CentralManager(List<String> nodeNames, List<String> jobNames) {
        this.nodes = nodeNames.collect { new Node(it) }
        this.jobs = jobNames.collect { new Job(it) }
        this.builds = []
        jobs.each { job ->
            builds.addAll(job.getAllBuildsInfo())
        }
    }

    def getNodeJobBuildInfo() {
        nodes.collect { node ->
            [
                nodeName: node.name,
                nodeDetails: node.getNodeDetails(),
                jobs: jobs.collect { job ->
                    [
                        jobName: job.name,
                        jobDetails: job.getJobDetails(),
                        builds: builds.findAll { it.jobName == job.name }.collect { build ->
                            [
                                buildNumber: build.number,
                                buildDetails: build.getBuildDetails()
                            ]
                        }
                    ]
                }
            ]
        }
    }
}
