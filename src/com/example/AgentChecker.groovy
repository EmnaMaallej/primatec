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
}

