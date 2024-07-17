@Library('my-shared-library') _  // Replace 'my-shared-library' with the name of your shared library

import org.jenkins.Build

pipeline {
    agent any

    stages {
        stage('Build Info') {
            steps {
                script {
                    // Fetch all builds using the static method
                    def builds = Build.getAllBuilds()

                    // Print build information
                    builds.each { build ->
                        println "Project Name: ${build.getProjectName()}"
                        if (build.getLastBuildNumber() != -1) {
                            println "\tLast Build Number: ${build.getLastBuildNumber()}"
                            println "\tLast Build Status: ${build.getLastBuildStatus()}"
                        } else {
                            println "\tNo builds found for this project"
                        }
                    }
                }
            }
        }
    }
}
