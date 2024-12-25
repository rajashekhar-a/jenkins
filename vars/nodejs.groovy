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
                    sh """
                    str = GIT_BRANCH.split(\'/\').last()
                    echo ${str}
                    """
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