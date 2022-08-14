#!/usr/bin/env groovy


def call(String name = 'bla') {
    println "In Call"
    def sequentialStages = []

    pipeline {

        stages {
            stage('Prepare pipeline') {
                steps {
                    script {
                        println "Adding stages to sequential build"

//                        sequentialStages.addAll(pipelineComponentBuild())
//
//                        sequentialStages.each { it.call() }
                    }
                }
            }
        }
    }
}