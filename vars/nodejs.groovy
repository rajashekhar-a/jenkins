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
                      sonar-scanner -Dsonar.projectKey=${params.COMPONENT} -Dsonar.sources=. -Dsonar.host.url=http://172.31.33.183:9000 -Dsonar.token=sqp_33ac861ce67fb94b8909bd66792c714b8caeb632
                      echo ok
                    """
                }
            }

            stage('Check Code Quality Gate') {
                steps {
                    sh """
                      sonar-quality-gate.sh admin admin123 172.31.28.20 ${params.COMPONENT}
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