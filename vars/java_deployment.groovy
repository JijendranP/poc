import java.text.SimpleDateFormat
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import hudson.nodel.*

node {

    def IMAGE_NAME = "java-app"
    def TAG = "latest"

    stage('Checkout') {
        echo "Cloning source code..."
        git branch: 'main',
            url: 'https://github.com/your-repo/java-app.git',
            credentialsId: 'git-credentials-id'
    }

    stage('Build (Skip Tests)') {
        echo "Building application with Maven..."
        sh 'mvn clean package -DskipTests'
    }
/*
    stage('SonarQube Analysis') {
        echo "Running SonarQube analysis..."

        withSonarQubeEnv('sonarqube-server') {
            sh '''
            mvn sonar:sonar \
            -Dsonar.projectKey=java-app \
            -Dsonar.projectName=java-app
            '''
        }
    }

    stage('Quality Gate') {
        echo "Waiting for Quality Gate result..."

        timeout(time: 5, unit: 'MINUTES') {
            waitForQualityGate abortPipeline: true
        }
    }

    stage('Build Docker Image') {
        echo "Building Docker image..."

        sh "docker build -t ${IMAGE_NAME}:${TAG} ."
    }

    stage('Run Docker Container') {
        echo "Running container..."

        sh """
        docker rm -f java-app-container || true
        docker run -d -p 8080:8080 --name java-app-container ${IMAGE_NAME}:${TAG}
        """
    }
    */
}
