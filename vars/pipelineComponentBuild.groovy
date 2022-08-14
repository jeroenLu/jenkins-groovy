/**
 * Returns a list of stages to be executed for building the project (when applicable)
 * @param isXldEnabled indicates whether an XLD configuration is present in the repository
 * @param callParams parameters specified in the Jenkinsfile
 * @param params parameters specified in the pipeline
 * @return a list of stages to be executed
 */
List<Closure> call(Map callParams) {
    echo "In pipelineComponentBuild"

    stage('Compile') {
        script {
            mvnCompile()
        }
    }

    stage('Test') {
        script {
            mvnTest()
        }
    }
}

void mvnCompile() {
    echo('Compile sources')
    sh "mvn compile -B"
    echo('Done compiling sources')
}

void mvnTest() {
    echo "Run unit tests"
    sh "mvn test"
}