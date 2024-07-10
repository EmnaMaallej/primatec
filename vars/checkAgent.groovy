// vars/checkAgent.groovy
def call() {
    def agentChecker = new com.example.AgentChecker(this)

    echo "Is Unix: ${agentChecker.isUnix()}"
    echo "Has Label 'docker': ${agentChecker.hasLabel('docker')}"
    echo "Operating System: ${agentChecker.getOS()}"
}
