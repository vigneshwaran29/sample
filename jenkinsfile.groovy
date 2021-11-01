pipeline {
    agent {
      tools {
        terraform 'terraform-jenkin'
    } 
    }

    stages {
      stage('fetch_latest_code') {
        steps {
          git credentialsId: 'github', url: 'https://github.com/vigneshwaran29/sample'
        }
      }

      stage('TF Init&Plan') {
        steps {
          sh 'terraform init'
          sh 'terraform plan'
        }      
      }

      stage('Approval') {
        steps {
          script {
            def userInput = input(id: 'confirm', message: 'Apply Terraform?', parameters: [ [$class: 'BooleanParameterDefinition', defaultValue: false, description: 'Apply terraform', name: 'confirm'] ])
          }
        }
      }

      stage('TF Apply') {
        steps {
          sh 'terraform apply -input=false'
        }
      }
    } 
  }
