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

            stage('Submit Code Quality') {
                steps {
                    sh """
                      sonar-scanner -Dsonar.projectKey=${params.COMPONENT} -Dsonar.sources=. -Dsonar.host.url=http://172.31.39.179:9000 -Dsonar.token=sqp_b67d70b6d68c9af9a9a23efcb4df943ad8be35e9
                      echo ok
                    """
                }
            }

            stage('Check Code Quality Gate') {
                steps {
                    sh """
                      sonar-quality-gate.sh admin admin123 172.31.39.179 ${params.COMPONENT}
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
                steps {
                   sh'echo upload artifacts'
                }

            }

        }
    }
}