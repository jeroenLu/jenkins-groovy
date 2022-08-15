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
    sh('git push https://jeroenLu:18371aeb567fac766575e4ccb5d1a338@github.com:jeroenLu/test-code-repo.git HEAD:master')

//    sshagent(credentials: ['GIT_SA_SSH']) {
//        sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/my-org/my-repo.git')
//        // "git push origin HEAD:master"
//    }
}
