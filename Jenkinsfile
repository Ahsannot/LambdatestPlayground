pipeline {

    agent any

    tools {
        jdk 'MyJava'      // JDK configured in Jenkins
        maven 'MyMaven'   // Maven configured in Jenkins
    }

    stages {

        stage('Checkout Code') {
            steps {
                // Clone repository
                git 'https://github.com/Ahsannot/LambdatestPlayground.git'
            }
        }

        stage('Build') {
            steps {
                // Compile the project on Windows
                bat 'mvn clean compile'
            }
        }

        stage('Run TestNG Tests') {
            steps {
                // Execute TestNG tests via Maven
                bat 'mvn test'
            }
        }

        stage('Archive TestNG Report') {
            steps {
                // Your TestNG report is generated at root folder under "reports"
                // Archive it so you can view in Jenkins
                archiveArtifacts artifacts: 'reports/SparkReport_*.html', fingerprint: true
            }
        }
    }

    post {
        always {
            // Archive build artifacts (like JARs)
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }

        success {
            echo 'Build Successful - TestNG tests executed'
        }

        failure {
            echo 'Build Failed - Check reports/SparkReport_*.html for details'
        }
    }
}