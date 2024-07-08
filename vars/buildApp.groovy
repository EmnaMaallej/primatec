def call() {
    stage('Build') {
        bat 'mvn clean install'
    }
}
