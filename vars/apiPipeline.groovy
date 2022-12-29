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
        }

        stages {
            stage('Prepare pipeline') {
                steps {
                    script {
                        echo "Adding stages to sequential build"


                        // TODO: githubFlowStart?

                        sequentialStages.addAll(pipelineComponentBuild())

                        // TODO: Move to util
                        if(true){
                            // TODO: updateVersion.
                            sequentialStages.addAll(pipelineVersionUpdate())

                            // TODO: pushToDockerHub
                            // TODO: Update deployment-repo
                        }


                        // TODO: githubFlowFinish?

                        sequentialStages.each { it.call() }
                    }
                }
            }
        }
    }
}
