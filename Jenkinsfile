pipeline {

    agent any

    tools {
        jdk 'MyJava'      // JDK configured in Jenkins
        maven 'MyMaven'   // Maven configured in Jenkins
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Ahsannot/LambdatestPlayground.git'
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
    }

    post {
        success {
            echo 'Build Successful - TestNG tests executed'
        }

        failure {
            echo 'Build Failed - Check reports/SparkReport_*.html for details'
        }
    }
}