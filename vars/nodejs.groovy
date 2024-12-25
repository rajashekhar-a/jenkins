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
                sh ' echo test '
                }
            }

            stage('uplode artifacts'){
                steps{
                    script{
                        str = GIT_BRANCH.split('/').last()
                        echo "${str}"
                    }
                    when { branch 'main' }
                    sh 'echo uplode artifacts'
                }
            }

        }

    }

}