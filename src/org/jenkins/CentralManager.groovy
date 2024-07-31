package org.jenkins

import hudson.model.Node
import jenkins.model.Jenkins

class CentralManager {
    List<Node> nodes
    List<Job> jobs
    List<Build> builds

    CentralManager(List<String> nodeNames, List<String> jobNames, List<Integer> buildNumbers) {
        this.nodes = nodeNames.collect { new Node(it) }
        this.jobs = jobNames.collect { new Job(it) }
        this.builds = []

        jobs.each { job ->
            buildNumbers.each { buildNumber ->
                this.builds.add(new Build(job.getName(), buildNumber))
            }
        }
    }

    List<Map<String, Object>> getNodeDetails() {
        nodes.collect { node ->
            [
                name: node.getNodeName(),
                online: node.isOnline(),
                temporarilyOffline: node.isTemporarilyOffline(),
                idle: node.isIdle(),
                numberOfExecutors: node.getNumberOfExecutors(),
                executors: node.getExecutors(),
                monitorData: node.getMonitorData(),
                connectTime: node.getConnectTime(),
                launchTime: node.getLaunchTime(),
                offlineCause: node.getOfflineCause(),
                nodeProperties: node.getNodeProperties()
            ]
        }
    }

    List<Map<String, Object>> getJobDetails() {
        jobs.collect { job ->
            [
                name: job.getName(),
                jobClass: job.getJobClass(),
                description: job.getDescription(),
                buildCount: job.getBuildCount(),
                disabled: job.isDisabled(),
                allBuildsInfo: job.getAllBuildsInfo()
            ]
        }
    }

    List<Map<String, Object>> getBuildDetails() {
        builds.collect { build ->
            [
                number: build.getNumber(),
                result: build.getResult(),
                duration: build.getDuration(),
                timestamp: build.getTimestamp(),
                causes: build.getCauses(),
                parameters: build.getParameters()
            ]
        }
    }
}
