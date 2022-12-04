pipeline {
    agent none
    environment {
        DOCKER_CREDENTIAL = credentials('dockerPushRegistryCredential')
    }
    stages {
        stage('Build and Deploy') {
            agent any
            steps {
                sh "./build-all.sh"
            }
        }
    }
}