
## File name: jenkins_pipeline.groovy

pipeline {
    agent any
    environment {
        GIT_REPO = "git@github.com:linux/kernel.git"
    }
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'master', url: "${env.GIT_REPO}"
            }
        }
        stage('Lint JSON Files') {
            steps {
                sh 'find . -name "*.json" -type f -exec jsonlint -q {} \\;'
            }
        }
        stage('Send Message to Slack') {
            steps {
                slackSend channel: '#kernel-builds', message: "Verified that ${env.GIT_COMMIT} has all valid JSON!"
            }
        }
    }
}
