#!/usr/bin/env groovy


def call(Map callParams) {
    echo "In Call"
    echo "Updating deployment with parameters (callParameters=${callParams})"

    def sequentialStages = []

    pipeline {
        agent any

        tools {
            // Install the Maven version configured as "M3" and add it to the path.
            maven "M3"
            jdk 'jdk11'
        }

        stages {
            stage('Prepare pipeline') {
                steps {
                    script {
                        echo "in deploy pipeline"

                        sequentialStages.each { it.call() }
                    }
                }
            }
        }
    }
}
