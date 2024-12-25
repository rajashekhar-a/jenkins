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
                       echo "+++++++ Before"
                       ls -l
                       npm install
                       echo "+++++++ After"
                       ls -l
                    """
                }
            }

            stage('test') {
                steps {
                    sh ' echo test '
                }
            }

            stage('uplode artifacts') {
                when {
                    expression { sh([returnStdout: true, script: 'echo ${GIT_BRANCH} | grep tags || true' ]) }
                }
                script {
                   sh'echo upload artifacts'
                }

            }

        }
    }
}