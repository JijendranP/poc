def call() {
node {
  
  def image
  def mvnHome = tool 'Maven3'

  
     stage ('checkout') {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '9ffd4ee4-3647-4a7d-a357-5e8746463282', url: 'https://bitbucket.org/ananthkannan/myawesomeangularapprepo/']]])       
        }
    
    
    stage ('Build') {
            sh 'mvn -f MyAwesomeApp/pom.xml clean install'            
        }
        
    stage ('archive') {
            archiveArtifacts '**/*.jar'
        }
}
}
