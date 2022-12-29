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

    withCredentials([gitUsernamePassword(credentialsId: 'jenkins-pat')]) {
        sh '''
            git config --global user.name jeroenLu
            git config --global user.email jeroenluers@gmail.com
            git branch
            git status
            
            git checkout -B newBranch
            
            git branch
            git status
            
            git add pom.xml
            git status
            
            git commit -am 'Bumped version number [ci skip]'
            git status
            
            git push origin newBranch:master

           '''
    }
}
