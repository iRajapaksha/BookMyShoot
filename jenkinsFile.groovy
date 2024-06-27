pipeline {
    agent any
triggers{
    githubPush()
  }
    environment {
        REPO_URL = 'https://github.com/iRajapaksha/BookMyShoot.git'
        BRANCH = 'main'
        DOCKER_REGISTRY = 'irajapaksha'
        APP_NAME = 'BookMyShoot'
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
                script {
                    sh 'docker-compose down'
                    sh 'docker-compose up -d'
                }
            }
        }
    }
}



// pipeline {
//   agent any
//   triggers{
//     githubPush()
//   }
//   stages {
//     stage('Stage 1'){
//       steps {
//         echo 'This is Stage 1'
//       }
//     }
//     stage('Stage 2'){
//       steps{
//         echo 'This is Stage 2'
//       }
//     }
//     stage('Final'){
//       steps{
//         echo 'this is Final Stage'
//       }
//     }
//     stage('Deploy') {
//             steps {
//                 echo 'Deploying application...'
//             }
//         }
//     }
// }