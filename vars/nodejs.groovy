def call(Map params = [:]) {

    def args = [
            COMPONENT                  : '',
            LABEL                      : 'master'
    ]
    args << params
    pipeline{

        agent {
            label params.lABEL
        }
        stages{

            stage('Download NodeJS Dependencies'){
                steps{
                    sh """
                    npm install
                    """

                }
            }

            stage('test'){
                steps{
                    sh 'env'
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