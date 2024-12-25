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
                    npm audit fix --force
                    npm audit
                    """

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