List<Closure> call() {
    echo "In pipelineComponentBuild"

    List<Closure> output = []

    output.add({
        stage('Compile') {
            script {
                mvnCompile()
            }
        }
    })
    output.add({
        stage('Test') {
            script {
                mvnTest()
            }
        }
    })

    return output
}

void mvnCompile() {
    echo('Compile sources')
    sh "mvn compile -B"
    echo('Done compiling sources')
}

void mvnTest() {
    echo "Run unit tests"
    //sh "mvn test -B"
}
