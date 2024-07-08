def call() {
    stage('Integration Tests') {
        bat 'mvn verify -Pintegration-tests'
    }
}
