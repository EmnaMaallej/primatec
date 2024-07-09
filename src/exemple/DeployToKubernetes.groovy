package example

def call(Map params) {
    def deploymentName = params.deploymentName
    def image = params.image
    sh "kubectl set image deployment/${deploymentName} ${deploymentName}=${image} --record"
}
