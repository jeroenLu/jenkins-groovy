List<Closure> call() {
    echo "In docker hub upgrade"

    List<Closure> output = []

    output.add({
        stage('Create docker image') {
            script {
                createDockerImage()
            }
        }
    })

    return output
}

void createDockerImage() {
    echo('creating docker image')

    final projectName =  readMavenPom().getScm().getTag()
    final applicationName =  readMavenPom().getArtifactId()
    final version = readMavenPom().getVersion()
    echo projectName
    echo applicationName
    echo version

    sh "docker build -t jeroenluers/${projectName}_${applicationName}:${version} ."
    sh "echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin"
    sh "docker push jeroenluers/${projectName}_${applicationName}:${version}"
    sh "docker logout"
}