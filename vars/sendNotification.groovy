def call(String result) {
    echo "Sending notification for build result: ${result}"
    emailext(
        subject: "Jenkins Build: ${result}",
        body: "The build has ${result}.",
        to: 'emna.maallej@gmail.com',
        from: 'your-email@gmail.com',
        smtpServer: 'smtp.gmail.com',
        replyTo: 'your-email@gmail.com',
        mimeType: 'text/plain',
        charset: 'UTF-8',
        recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
        authUser: 'your-email@gmail.com',
        authPassword: 'your-app-password'
    )
}

