List<Closure> call() {
    echo "In pipelineComponentBuild"

    List<Closure> output = []

    output.add({
        stage('Update Version') {
            script {
                mvnUpdateVersion()
            }
        }
    })
    output.add({
        stage('Commit Version') {
            script {
                commitVersion()
            }
        }
    })

    return output
}

void mvnUpdateVersion() {
    echo('Updating version')
    // sh "mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion}"
    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\${parsedVersion.majorVersion}.\\${parsedVersion.minorVersion}.\\${parsedVersion.nextIncrementalVersion}'
    echo('Done updating version')
}

void commitVersion() {
    echo "Committing new pom.xml version"

    sh "git add pom.xml"
    sh "git commit -am \"jenkins pom update\""
    sh('git push https://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@github.com/jeroenLu/pipeline-test-repo.git HEAD:master --force')

//    sshagent(credentials: ['GIT_SA_SSH']) {
//        sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/my-org/my-repo.git')
//        // "git push origin HEAD:master"
//    }
}
