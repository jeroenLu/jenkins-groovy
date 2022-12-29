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
    echo "Current workspace is ${env.WORKSPACE}"


    
//    sh "git commit -am \"jenkins pom update\""
 //   sh('git push https://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@github.com/jeroenLu/pipeline-test-repo.git HEAD:master --force')

    withCredentials([gitUsernamePassword(credentialsId: 'jenkins-pat')]) {
        sh '''
            git config --global user.name jeroenLu
            git config --global user.email jeroenluers@gmail.com
            git branch
            git status
            
            git remote show origin
            
            git switch -c newBranch
            
            git branch
            git status
            
            git add pom.xml
            git status
            
            git commit -am 'Bumped version number [ci skip]'
            git status
            
            git push origin newBranch:master

           '''
    }
    
//
//                   git branch newBranch
//            git checkout newBranchgit push --set-upstream origin newBranch
//       sh 'git pull origin master'
//          sh 'git config user.name jeroenLu'
//          sh 'git config user.email jeroenluers@gmail.com'
//          sh 'git status'
//          sh "git add pom.xml"
//          sh 'git status'
//          sh 'git commit -am pomUpdate'
//          sh 'git status'
//          sh 'git push -f origin master'
    
//    sshagent(credentials: ['GIT_SA_SSH']) {
//        sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/my-org/my-repo.git')
//        // "git push origin HEAD:origin master"
//    }
}
