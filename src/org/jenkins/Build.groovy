package org.jenkins

import hudson.model.Run
import jenkins.model.Jenkins
import java.text.SimpleDateFormat

class Build {
    private Run build
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    // Constructor
    Build(String jobName, int buildNumber) {
        def job = Jenkins.instance.getItemByFullName(jobName)
        this.build = job ? job.getBuildByNumber(buildNumber) : null
    }

    // Methods
    int getNumber() {
        return build ? build.number : -1
    }

    String getResult() {
        return build ? build.result.toString() : "Unknown"
    }

    long getDuration() {
        return build ? build.duration : 0
    }

    String getFormattedTimestamp() {
        return build ? dateFormat.format(new Date(build.getTimeInMillis())) : "Timestamp not available"
    }

    String getNodeName() {
        // For Pipeline builds, use the build.getBuiltOnStr() method
        return build ? build.getBuiltOnStr() : "Node not available"
    }

    List<String> getCauses() {
        return build ? build.causes.collect { it.toString() } : []
    }

    List<Map<String, String>> getParameters() {
        def params = build?.actions.find { it instanceof hudson.model.ParametersAction }?.parameters ?: []
        return params.collect { param ->
            [name: param.name, value: param instanceof hudson.model.StringParameterValue ? param.value : 'Non-string parameter']
        }
    }
}






