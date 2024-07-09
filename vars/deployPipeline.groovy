def call(Map params) {
    pipeline {
        agent any
        environment {
            GIT_URL = params.GIT_URL ?: 'https://github.com/example/repo.git'
            DEPLOY_TO = params.DEPLOY_TO ?: 'production'
        }
        stages {
            stage('Checkout') {
                steps {
                    checkout scm
                }
            }
            stage('Build') {
                steps {
                    sh 'mvn clean package'
                }
            }
            stage('Deploy to ${params.DEPLOY_TO}') {
                steps {
                    script {
                        if (params.DEPLOY_TO == 'production') {
                            sh 'kubectl apply -f production.yaml'
                        } else {
                            sh 'kubectl apply -f staging.yaml'
                        }
                    }
                }
            }
            stage('Notify') {
                steps {
                    echo "Deployment to ${params.DEPLOY_TO} completed successfully."
                }
            }
        }
    }
}
