pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/iRajapaksha/BookMyShoot.git'
        BRANCH = 'main'
        DOCKER_REGISTRY = 'irajapaksha'
        APP_NAME = 'BookMyShoot'
        DEPLOY_SERVER = '172.20.10.2'
        SSH_CREDENTIALS_ID = 'your_ssh_credentials_id'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: "${BRANCH}", url: "${REPO_URL}"
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    sh 'docker-compose build'
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    sh '''
                    docker-compose push
                    '''
                }
            }
        }

        stage('Deploy Application') {
            steps {
                sshagent (credentials: ['${SSH_CREDENTIALS_ID}']) {
                    script {
                        sh '''
                        ssh -o StrictHostKeyChecking=no user@${DEPLOY_SERVER} <<EOF
                        cd /path/to/deployment/folder
                        docker-compose pull
                        docker-compose up -d
                        EOF
                        '''
                    }
                }
            }
        }
    }
}
