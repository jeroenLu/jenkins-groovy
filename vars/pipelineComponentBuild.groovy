/**
 * Returns a list of stages to be executed for building the project (when applicable)
 * @param isXldEnabled indicates whether an XLD configuration is present in the repository
 * @param callParams parameters specified in the Jenkinsfile
 * @param params parameters specified in the pipeline
 * @return a list of stages to be executed
 */
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
    //sh "mvn compile -B"
    echo('Done compiling sources')
}

void mvnTest() {
    echo "Run unit tests"
    //sh "mvn test"
}
