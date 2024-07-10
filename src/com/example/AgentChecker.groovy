// src/com/example/AgentChecker.groovy
package com.example

class AgentChecker implements Serializable {
    def script

    AgentChecker(script) {
        this.script = script
    }

    boolean isUnix() {
        return script.isUnix()
    }

    boolean hasLabel(String label) {
        return script.node {
            return script.env.NODE_LABELS?.contains(label)
        }
    }

    String getOS() {
        return script.node {
            return script.isUnix() ? 'Unix' : 'Windows'
        }
    }

    String getJenkinsVersion() {
        return script.node {
            return script.Jenkins.instance.version.toString()
        }
    }

    String getJavaVersion() {
        return script.node {
            return System.getProperty("java.version")
        }
    }

    long getFreeDiskSpace() {
        return script.node {
            def root = new File('/')
            return root.getFreeSpace() / (1024 * 1024 * 1024) // in GB
        }
    }

    long getTotalDiskSpace() {
        return script.node {
            def root = new File('/')
            return root.getTotalSpace() / (1024 * 1024 * 1024) // in GB
        }
    }

    String getEnvironmentVariable(String name) {
        return script.node {
            return script.env."${name}"
        }
    }

    String getAgentName() {
        return script.node {
            return script.env.NODE_NAME
        }
    }
}


