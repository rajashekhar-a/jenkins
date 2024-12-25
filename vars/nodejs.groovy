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
                steps {
                    sh'echo upload artifacts'
                }

            }

        }
    }
}