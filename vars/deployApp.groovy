def call() {
    stage('Deployment') {
        bat 'ansible-playbook -i inventory/staging deploy.yml'
    }
}
