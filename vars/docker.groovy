def call(Map params = [:]) {

    def args = [
            COMPONENT                  : '',
            LABEL                      : 'master'
    ]
    args << params

    pipeline {
        agent {
            label params.LABEL
        }

        stages {
            stage('Labeling Build') {
                steps {
                    script {
                        str = GIT_BRANCH.split('/').last()
                        addShortText background: 'yellow', color: 'black', borderColor: 'yellow', text: "COMPONENT = ${params.COMPONENT}"
                        addShortText background: 'yellow', color: 'black', borderColor: 'yellow', text: "BRANCH = ${str}"
                    }
                }
            }

            stage('Docker Build') {
                steps {
                  sh """
                  aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 975050272810.dkr.ecr.us-east-1.amazonaws.com
                  docker build -t 975050272810.dkr.ecr.us-east-1.amazonaws.com/${params.COMPONENT} .
                  docker tag 975050272810.dkr.ecr.us-east-1.amazonaws.com/${params.COMPONENT}:
                  docker push 975050272810.dkr.ecr.us-east-1.amazonaws.com/${params.COMPONENT}:latest
                  """
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
