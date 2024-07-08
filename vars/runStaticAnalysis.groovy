def call() {
    stage('Static Code Analysis') {
        bat 'mvn sonar:sonar'
    }
}
