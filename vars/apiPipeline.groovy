#!/usr/bin/env groovy


void call() {
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