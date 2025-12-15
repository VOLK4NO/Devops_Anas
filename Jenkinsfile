pipeline {
    agent any

    environment {
        IMAGE_NAME = "selmianas/student_management"
    }

    stages {

        stage('GitHub Checkout') {
            steps {
                git credentialsId: 'github-credentials',
                    branch: 'anas',
                    url: 'https://github.com/VOLK4NO/Devops_Anas.git'
            }
        }

        stage('Build with Maven') {
            steps {
                dir('StudentManagement') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Docker Build Image') {
            steps {
                dir('StudentManagement') {
                    sh 'docker build -t $IMAGE_NAME .'
                }
            }
        }

        stage('Docker Push Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-credentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    docker push $IMAGE_NAME
                    '''
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                dir('StudentManagement') {
                    withCredentials([string(
                        credentialsId: 'jenkins-sonar',
                        variable: 'SONAR_TOKEN'
                    )]) {
                        sh '''
                        mvn sonar:sonar \
                        -Dsonar.login=$SONAR_TOKEN \
                        -Dsonar.projectKey=gestion-ue-student
                        '''
                    }
                }
            }
        }

        stage('Kubernetes Deployment') {
            steps {
                dir('StudentManagement') {
                    sh '''
                    kubectl apply -f k8s/mysql-deployment.yaml
                    kubectl apply -f k8s/springboot-deployment.yaml
                    '''
                }
            }
        }
    }
}
