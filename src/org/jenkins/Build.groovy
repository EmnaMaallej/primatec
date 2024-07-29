package org.jenkins

import jenkins.model.Jenkins

class Build {
    private def build

    Build(def build) {
        this.build = build
    }

    String getNumber() {
        return build ? build.number.toString() : "Build not found"
    }

    String getResult() {
        return build ? build.result.toString() : "Result not available"
    }

    long getDuration() {
        return build ? build.duration : 0
    }

    Date getTimestamp() {
        return build ? new Date(build.timestamp) : new Date()
    }

    List<String> getCauses() {
        return build ? build.causes.collect { it.toString() } : []
    }

    Map<String, Object> getParameters() {
        return build ? build.buildVariableResolver?.resolveAll() : [:]
    }

    Node getNode() {
        def nodeName = build?.builtOn?.name // Adjust this to fit the correct property or method to retrieve the node name
        return nodeName ? new Node(nodeName) : null
    }

    Map<String, Object> getBuildDetails() {
        return [
            "Number": getNumber(),
            "Result": getResult(),
            "Duration": getDuration(),
            "Timestamp": getTimestamp(),
            "Causes": getCauses(),
            "Parameters": getParameters(),
            "Node": getNode()?.getNodeProperties()
        ]
    }
}


