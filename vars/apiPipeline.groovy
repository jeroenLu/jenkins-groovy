#!/usr/bin/env groovy


def call(Map callParams) {
    echo "In Call"
    echo "Updating API with parameters (callParameters=${callParams})"

    def sequentialStages = []

    pipeline {
        agent any

        tools {
            // Install the Maven version configured as "M3" and add it to the path.
            maven "M3"
            jdk 'jdk11'
        }

        environment  {
            VERSION = readMavenPom().getVersion()
            PROJECT_NAME = readMavenPom().getScm().getTag()
            APPLICATION_NAME = readMavenPom().getName()
        }

        stages {
            stage('Prepare pipeline') {
                steps {
                    script {
                        echo "Adding stages to sequential build"

                        sequentialStages.addAll(pipelineComponentBuild())

                        sequentialStages.addAll(pipelineVersionUpdate())

                        // TODO: pushToDockerHub


                        sequentialStages.each { it.call() }
                    }
                }
            }
        }
    }
}
