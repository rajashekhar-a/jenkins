def call(Map params = [:]) {

    def args = [
            COMPONENT                  : '',
            LABEL                      : 'master'
    ]
    args << params
    pipeline{

        agent {
            label param.lABEL
        }

        stages{
            stage('Build'){
                steps{
                    sh 'echo build code'
                }
            }

            stage('test'){
                steps{
                    sh 'echo test code'
                }
            }

            stage('uplode artifacts'){
                steps{
                    sh 'echo uplode artifacts'
                }
            }

        }
        post {
            always {
                cleanWs()
            }
        }
    }

}