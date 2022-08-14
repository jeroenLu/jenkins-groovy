#!/usr/bin/env groovy


def call(Map callParams = [:]) {
    echo "In Call"

    def sequentialStages = []

    pipeline {
        agent any

        tools {
            // Install the Maven version configured as "M3" and add it to the path.
            maven "M3"
            jdk 'openjdk-11'
        }

        stages {
            stage('Prepare pipeline') {
                steps {
                    script {
                        echo "Adding stages to sequential build"

                        sequentialStages.addAll(pipelineComponentBuild())

                        sequentialStages.each { it.call() }
                    }
                }
            }
        }
    }
}