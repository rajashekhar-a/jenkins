def call(Map params = [:]) {

    def args = [
            COMPONENT                  : '',
            LABEL                      : ''
    ]
    args << params
    pipeline{

        agent {
            label params.lABEL
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

    }

}