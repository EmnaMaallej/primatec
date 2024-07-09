def call(String result) {
    echo "Sending notification for build result: ${result}"
    emailext (
        subject: "Jenkins Build: ${result}",
        body: "The build has ${result}.",
        to: 'emna.maallej@gmail.com'
    )
}
