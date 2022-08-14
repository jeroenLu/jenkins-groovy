#!/usr/bin/env groovy


def call(Map callParams = [:]) {
    println("Printing arguments");
    for(String arguments : callParams) {
        println (arguments);
    }
    println("End arguments");

    def sequentialStages = []

    pipeline {
        environment {
//            BRANCH_NAME = getBranchName()
//            APPLICATION_NAME = getRepoName()
            //PROJECT_KEY = getBitbucketProjectKey()
            //GITHUB_URL = System.getenv("BITBUCKET_URL")
            // maven credentials used by the maven container to deploy to Artifactory
            //MVN_CREDS = credentials('MVN_CREDS')
        }

        stages {
            stage('Prepare pipeline') {
                steps {
                    script {
                        echo "Adding stages to sequential build"

                        sequentialStages.addAll(pipelineComponentBuild(callParams))

                        sequentialStages.each { it.call() }
                    }
                }
            }
        }
    }
}