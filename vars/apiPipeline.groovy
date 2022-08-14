#!/usr/bin/env groovy


def call(String name = 'human') {
    echo "In Call"
    def sequentialStages = []

    pipeline {

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