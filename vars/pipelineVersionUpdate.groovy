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
    sh 'mvn build-helper:parse-version versions:set -DnewVersion=\\${parsedVersion.majorVersion}.\\${parsedVersion.minorVersion}.\\${parsedVersion.nextIncrementalVersion}'
    echo('Done updating version')
}

void commitVersion() {
    echo "Committing new pom.xml version"

    final projectName =  readMavenPom().getScm().getTag()
    final applicationName =  readMavenPom().getArtifactId()
    final version = readMavenPom().getVersion()
    echo projectName
    echo applicationName
    echo version

    build(job: "/deployment-repo",
            parameters: [
                    string(name: 'PROJECT_NAME', value: projectName),
                    string(name: 'APPLICATION_NAME', value: applicationName),
                    string(name: 'APPLICATION_VERSION', value: version),
            ],
            wait: false)


    withCredentials([gitUsernamePassword(credentialsId: 'jenkins-pat')]) {

        // get version

        // get file

//        sh '''
//            git config --global user.name jeroenLu
//            git config --global user.email jeroenluers@gmail.com
//            git branch
//            git status
//
//            git checkout -B newBranch
//
//            git branch
//            git status
//
//            git add pom.xml
//            git status
//
//            git commit -am 'Update pom.xml [ci skip]'
//            git status
//
//            git push origin newBranch:master
//
//           '''
    }
}
