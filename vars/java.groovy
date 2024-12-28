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

            stage('labeling build') {
                steps {
                    script {
                        str = GIT_BRANCH.split('/').last()
                        addBadge background: 'yellow', color: 'black', borderColor: 'yellow', text: "COMPONENT = ${params.COMPONENT}"
                        addBadge background: 'yellow', color: 'black', borderColor: 'yellow', text: "BRANCH = ${str}"
                    }
                }
            }

            stage('Maven Package') {
                steps {
                    sh """
                      mvn package
                    """
                }
            }

            stage('Submit Code Quality') {
                steps {
                    sh """
                      #sonar-scanner -Dsonar.projectKey=${params.COMPONENT} -Dsonar.sources=. -Dsonar.host.url=http://172.31.39.179:9000 -Dsonar.token=sqp_b67d70b6d68c9af9a9a23efcb4df943ad8be35e9
                      env
                    """
                }
            }

            stage('Check Code Quality Gate') {
                steps {
                    sh """
                      #sonar-quality-gate.sh admin admin123 172.31.39.179 ${params.COMPONENT}
                      echo ok
                      """
                }
            }

            stage('test cases') {
                steps {
                    sh ' echo test cases '
                }
            }

            stage('uplode artifacts') {
                when {
                    expression { sh([returnStdout: true, script: 'echo ${GIT_BRANCH} | grep tags || true' ]) }
                }
                steps {
                    sh"""
                     GIT_TAG=`echo ${GIT_BRANCH} | awk -F / '{print \$NF}'`
                     cp target/shipping-1.0.jar shipping.jar
                     zip -r ${params.COMPONENT}-\${GIT_TAG}.zip shipping.jar
                     curl -v -u admin:admin123 --upload-file ${params.COMPONENT}-\${GIT_TAG}.zip http://172.31.35.162:8081/repository/${params.COMPONENT}/${params.COMPONENT}-\${GIT_TAG}.zip
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