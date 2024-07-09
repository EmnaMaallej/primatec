def sendNotification(String result, String recipient) {
    echo "Sending notification for build result: ${result}"
    try {
        emailext (
            subject: "Jenkins Build: ${result}",
            body: "The build has ${result}.",
            to: recipient
        )
    } catch (Exception e) {
        echo "Failed to send email notification: ${e.message}"
        currentBuild.result = 'FAILURE' // Optionally mark build as failed if email sending fails
    }
}

// Usage example:
sendNotification(currentBuild.result ?: 'SUCCESS', 'emna.maallej@gmail.com')


