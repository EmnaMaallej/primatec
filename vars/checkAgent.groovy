// vars/checkAgent.groovy
def call() {
    def agentChecker = new com.example.AgentChecker(this)

    echo "Is Unix: ${agentChecker.isUnix()}"
    echo "Has Label 'docker': ${agentChecker.hasLabel('docker')}"
    echo "Operating System: ${agentChecker.getOS()}"
    echo "Jenkins Version: ${agentChecker.getJenkinsVersion()}"
    echo "Java Version: ${agentChecker.getJavaVersion()}"
    echo "Free Disk Space: ${agentChecker.getFreeDiskSpace()} GB"
    echo "Total Disk Space: ${agentChecker.getTotalDiskSpace()} GB"
    echo "Environment Variable 'HOME': ${agentChecker.getEnvironmentVariable('HOME')}"
    echo "Agent Name: ${agentChecker.getAgentName()}"
}

