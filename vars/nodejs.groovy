def call(Map params = [:]) {

    def args = [
            COMPONENT: '',
            LABEL    : 'master'
    ]
    args << params
    pipeline {

        agent {
            label params.lABEL
        }
        stages {

            stage('Download NodeJS Dependencies') {
                steps {
                    sh """
                    npm install
                    """

                }
            }

            stage('test') {
                steps {
                    sh ' echo test '
                }
            }

            stage('uplode artifacts') {
                steps {
                    script {
                        def output = sh(script: "echo${env.GIT_BRANCH}", returnStdout: true)
                        echo "output: ${output}"
                    }

                }

            }

        }
    }
}