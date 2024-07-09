def call(String buildStatus) {
    stage('Notification') {
        def subject = "${env.JOB_NAME} - Build #${env.BUILD_NUMBER} - ${buildStatus}"
        def body = "Check the build details at ${env.BUILD_URL}"
        
        emailext (
            subject: subject,
            body: body,
            recipientProviders: [[$class: 'DevelopersRecipientProvider']],
            to: 'emna.maallej@gmail.com'
        )
    }
}

